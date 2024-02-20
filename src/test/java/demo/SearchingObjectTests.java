package demo;


import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.web.reactive.function.client.WebClient;

import demo.boundries.ObjectBoundary;
import demo.entities.ObjectEntity;
import reactor.core.publisher.Flux;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class SearchingObjectTests {
	private String url;
	private WebClient webClient;
	
	@BeforeEach
	public void setup() {
		this.url = "http://localhost:8085/superapp";
		this.webClient = WebClient.create(this.url);
	}

	@AfterEach
	public void cleanup() {
		// delete all messages
		this.webClient
			.delete()
			.uri("/admin/objects")
			.retrieve()
			.bodyToMono(Void.class)
			.block();
	}
	
	public ObjectEntity InitialEntity()
	{
		   ObjectEntity entity = new ObjectEntity();
	       entity.setObjectId("2024a.otiel.malik:id");
	        entity.setType("boris");
	        entity.setAlias("moro");
	        entity.setActive(true);
	        entity.setCreatedTimestamp(new java.util.Date());
	        entity.setUserIdEmail("user@email.com");
	        entity.setUserIdSuperapp("2024a.otiel.malik");
	        Map<String, Object> details = new HashMap<>();
	        details.put("key", "value");
	        entity.setObjectDetails(details);
			System.err.println(entity+"        before \n");
return entity;
	}
	
	@Test
	public void testPostStoresMessageInDatabase() throws Exception {
		  // Create an ObjectEntity
        ObjectBoundary boundary = new ObjectBoundary(InitialEntity());
		ObjectBoundary actualObjectStoredInDatabase =
		  this.webClient
			.post()
			.uri("/objects")
			.bodyValue(boundary)
			.retrieve()
			.bodyToMono(ObjectBoundary.class)
			.block();
		// THEN the server stores the message in the database
  assertThat(this.webClient
			.get()
			.uri("/objects/{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}"
					,actualObjectStoredInDatabase.getObjectId().getSuperapp(),actualObjectStoredInDatabase.getObjectId().getSuperapp()
					+":"+actualObjectStoredInDatabase.getObjectId().getId(),"2024a.otiel.malik","user@email.com")
			.retrieve()
			.bodyToMono(ObjectId.class)
			.block()).extracting("boris","moro")
  .containsExactly(actualObjectStoredInDatabase.getType(),actualObjectStoredInDatabase.getAlias());
		
	
		
			
	}
	
	@Test
	public void testSearchByImportanceAndVersion() throws Exception {
		// GIVEN the server is up
		// AND the server contains 3 messages - 
		//    {"important":true, "version":2}
		//	  {"important":false, "version":2}
		//	  {"important":true, "version":1}
		ObjectBoundary expectedOutput =  this.webClient
				.post()
				.bodyValue(new ObjectBoundary())
				.retrieve()
				.bodyToMono(ObjectBoundary.class)
				.block();
		List<ObjectBoundary> expectedList = new ArrayList<>();
		expectedList.add(expectedOutput);
		
		List<ObjectBoundary> irrelevantMessagesOnServer = 
		  Flux.just(
				new MessageBoundary("bad importance field", false, 2.0),
				new MessageBoundary("bad version field", true, 1.0))
			.flatMap(m->this.webClient
				.post()
				.bodyValue(m)
				.retrieve()
				.bodyToMono(MessageBoundary.class)) // Flux<MessageBoundary>
			.collectList() // Mono<List<MessageBoundary>>
			.block();
			
		// WHEN I invoke GET /messages/searchByCriteria/importanceAndVersion?important=true&version=2.0
		// THEN the server returns a flux with {"important":true, "version":2.0} only 
		assertThat(
		  this.webClient
			.get()
			.uri("/searchByCriteria/importanceAndVersion?version={atLeastVersion}&important={important}", 2.0, true)
			.retrieve()
			.bodyToFlux(MessageBoundary.class)
			.collectList()
			.block())//List<MessageBoundary>
			.usingRecursiveFieldByFieldElementComparator()
			.containsExactlyInAnyOrderElementsOf(expectedList)
			.usingRecursiveFieldByFieldElementComparator()
			.doesNotContainAnyElementsOf(irrelevantMessagesOnServer);
			
	}

	@Test
	public void testChangeImportanceOfMessagesAndThenSearchByImportanceAndVersion() throws Exception {
		// GIVEN the server is up
		// AND the server contains 3 messages - 
		//    {"important":true, "version":1}
		//	  {"important":false, "version":1}
		//	  {"important":false, "version":1}
		MessageBoundary expectedOutput =  this.webClient
				.post()
				.bodyValue(new MessageBoundary("hit me", true, 1.0))
				.retrieve()
				.bodyToMono(MessageBoundary.class)
				.block();
		List<MessageBoundary> expectedList = new ArrayList<>();
		expectedList.add(expectedOutput);
		
		List<MessageBoundary> irrelevantMessagesOnServer = new ArrayList<>(
		  Flux.just(
				new MessageBoundary("bad importance field", false, 1.0))
			.flatMap(m->this.webClient
				.post()
				.bodyValue(m)
				.retrieve()
				.bodyToMono(MessageBoundary.class)) // Flux<MessageBoundary>
			.collectList() // Mono<List<MessageBoundary>>
			.block());
			
		List<MessageBoundary> modifiedAndIrrelevantMessagesOnServer = 
				  Flux.just(
						new MessageBoundary("bad version field", true, 1.0))
					.flatMap(m->this.webClient
						.post()
						.bodyValue(m)
						.retrieve()
						.bodyToMono(MessageBoundary.class)) // Flux<MessageBoundary>
					.flatMap(messageBeforeChange->{
						return this.webClient
							.put()
							.uri("/{id}", messageBeforeChange.getId())
							.bodyValue(Collections.singletonMap("important", false))
							.retrieve()
							.bodyToMono(Void.class) // Mono<Void>
						.then(this.webClient
							.get()
							.uri("/{id}", messageBeforeChange.getId())
							.retrieve()
							.bodyToMono(MessageBoundary.class)
							.log());
					})
					.collectList() // Mono<List<MessageBoundary>>
					.block();

		irrelevantMessagesOnServer.addAll(modifiedAndIrrelevantMessagesOnServer);

		// WHEN I invoke GET /messages/searchByCriteria/importanceAndVersion?important=true&version=1
		// THEN the server returns a flux with {"important":true, "version":1} only 
		assertThat(
		  this.webClient
			.get()
			.uri("/searchByCriteria/importanceAndVersion?version={atLeastVersion}&important={important}", 1.0, true)
			.retrieve()
			.bodyToFlux(MessageBoundary.class)
			.collectList()
			.block())//List<MessageBoundary>
			.usingRecursiveFieldByFieldElementComparator()
			.containsExactlyInAnyOrderElementsOf(expectedList)
			.usingRecursiveFieldByFieldElementComparator()
			.doesNotContainAnyElementsOf(irrelevantMessagesOnServer);
			
	}
}









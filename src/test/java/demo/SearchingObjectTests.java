package demo;

import demo.boundries.*;
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
	private static int i=1;
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
	
	public ObjectBoundary InitialObject(String type,String alias,boolean active)
	{
		   ObjectEntity entity = new ObjectEntity();
		   i++;
	       entity.setObjectId("2024a.otiel.malik:id");
	        entity.setType(type);
	        entity.setAlias(alias);
	        entity.setActive(true);
	        entity.setCreatedTimestamp(new java.util.Date());
	        entity.setUserIdEmail("user"+i+"@email.com");
	        entity.setUserIdSuperapp("2024a.otiel.malik");
	        Map<String, Object> details = new HashMap<>();
	        details.put("key", "value");
	        entity.setObjectDetails(details);
	        ObjectBoundary boundary = new ObjectBoundary(entity);
	    
return boundary;
	}
	
	public UserBoundary createUser(String email, Role role) {
		NewUserBoundary ub = new NewUserBoundary();
		i++;
		ub.setEmail(email);
		ub.setRole(role);
		ub.setUserName("index"+i);
		ub.setAvatar("gaya");
		UserBoundary actualUserStoredInDatabase = this.webClient.post().uri("/users").bodyValue(ub).retrieve()
				.bodyToMono(UserBoundary.class).block();
		return actualUserStoredInDatabase;
	}
	
	@Test
	public void SearchByTypeTest()
	{
		UserBoundary [] users= {createUser("e@gmail.com", Role.MINIAPP_USER) ,
				createUser("super@gmail.com", Role.SUPERAPP_USER) ,
				createUser("admin@gmail.com", Role.ADMIN) ,
		};
		
		ObjectBoundary [] objects= {
				InitialObject("gaya","alma",true),InitialObject("haya","bb",false),
				InitialObject("abab","bkjg",true)
		};
		
		
		List<ObjectBoundary> check=	
				Flux.just(objects[0],objects[1],objects[2])
					.flatMap(m->this.webClient
						.post()
						.uri("/objects")
						.bodyValue(m)
						.retrieve()
						.bodyToMono(ObjectBoundary.class)) // Flux<MessageBoundary>
					.collectList() // Mono<List<MessageBoundary>>
					.block();
		
		
		assertThat(
				  this.webClient
					.get()
					.uri("/objects/search/byType/{type}?userSuperapp={superapp}&userEmail={email}",objects[0].getType(),objects[0].getCreatedBy().getUserId().getSuperapp(),objects[0].getType(),objects[0].getCreatedBy().getUserId().getEmail() )
					.retrieve()
					.bodyToFlux(ObjectBoundary.class)
					.collectList()
					.block())
					
					.containsAnyOf(objects[0])
					.usingRecursiveFieldByFieldElementComparator();
	}
	
}









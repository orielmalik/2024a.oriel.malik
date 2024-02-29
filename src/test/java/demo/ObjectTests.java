package demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.convert.Delimiter;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import demo.boundries.NewUserBoundary;
import demo.boundries.ObjectBoundary;
import demo.boundries.UserBoundary;
import reactor.core.publisher.Flux;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class ObjectTests {
	private String url;
	private WebClient webClient;

	@BeforeEach
	public void setup() {
		this.url = "http://localhost:8085/superapp";
		this.webClient = WebClient.create(this.url);
		createUser("tchjha3@gmail.com", Role.ADMIN, "avichai98", "AS");
		createUser("tchjha2@gmail.com", Role.SUPERAPP_USER, "avichai98", "AS");
		createUser("Avichai.Shchori@s.afeka.ac.il", Role.MINIAPP_USER, "avichai98", "AS");
	}

	public void createUser(String email, Role role, String userName, String avatar) {
		NewUserBoundary ub = new NewUserBoundary();
		ub.setEmail(email);
		ub.setRole(role);
		ub.setUsername(userName);
		ub.setAvatar(avatar);
		this.webClient.post().uri("/users").bodyValue(ub).retrieve()
				.bodyToMono(UserBoundary.class).block();

		// THEN the server stores the object in the database
	}
	
	public void createObject(String type, String alias, boolean active) {
		CreatedBy createdBy = new CreatedBy("2024a.otiel.malik", "tchjha2@gmail.com");
		Map<String, Object> objectDetails = new HashMap<>();
		objectDetails.put("Product", "flowers");
		ObjectBoundary ob = new ObjectBoundary(type, alias, active, null, createdBy, objectDetails);
		this.webClient.post().uri("/objects").bodyValue(ob).retrieve()
				.bodyToMono(ObjectBoundary.class).block();
	}

	@AfterEach
	public void cleanup() {
		//delete all objects
		createUser("tchjha1@gmail.com", Role.ADMIN, "avichai98", "AS");
	this.webClient
		.delete()
		.uri("/admin/objects?userSuperapp={userSuperapp}&userEmail={userEmail}",
		"2024a.otiel.malik", "tchjha1@gmail.com")
		.retrieve()
		.bodyToMono(Void.class)
			.block();
	
	  //delete all users
	this.webClient
	.delete()
	.uri("/admin/users?userSuperapp={userSuperapp}&userEmail={userEmail}",
			"2024a.otiel.malik", "tchjha1@gmail.com")
	.retrieve()
	.bodyToMono(Void.class)
		.block();
}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testPostStoresObjectInDatabase() throws Exception {
		// GIVEN the server is up
		// GIVEN the user with role of SUPERAPP_USER is exist

		/*
		 * WHEN I Post object {" "type": "sport", "alias": "?", "active": true,
		 * "createdBy": { "userId": { "superapp": "2024a.otiel.malik", "email":
		 * "tchjha2@gmail.com" }"}
		 */
		CreatedBy createdBy = new CreatedBy("2024a.otiel.malik", "tchjha2@gmail.com");
		Map<String, Object> objectDetails = new HashMap<>();
		objectDetails.put("Product", "flowers");
		ObjectBoundary ob = new ObjectBoundary("sport", "?", true, null, createdBy, objectDetails);
		ObjectBoundary actualObjectStoredInDatabase = this.webClient.post().uri("/objects").bodyValue(ob).retrieve()
				.bodyToMono(ObjectBoundary.class).block();

		// THEN the server stores the object in the database
		assertThat(this.webClient.get()
				.uri("/objects/{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}",
						actualObjectStoredInDatabase.getObjectId().getSuperapp(), // superapp
						actualObjectStoredInDatabase.getObjectId().getId(), // id
						"2024a.otiel.malik", "tchjha2@gmail.com")
				.retrieve().bodyToMono(ObjectBoundary.class).block()).extracting("type", "alias")
				.containsExactly("sport", "?");
	}
	
	@Test
	public void testGetObject() {
	    // GIVEN the server is up and an object is available
		CreatedBy createdBy = new CreatedBy("2024a.otiel.malik", "tchjha2@gmail.com");
		Map<String, Object> objectDetails = new HashMap<>();
		objectDetails.put("Product", "flowers");
		ObjectBoundary ob = new ObjectBoundary("sport", "?", true, null, createdBy, objectDetails);
		ObjectBoundary actualObjectStoredInDatabase = this.webClient.post().uri("/objects").bodyValue(ob).retrieve()
				.bodyToMono(ObjectBoundary.class).block();
		
	    // WHEN requesting the object by ID
	    ObjectBoundary retrievedObject = this.webClient.get()
	            .uri("/objects/{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}",
	            		actualObjectStoredInDatabase.getObjectId().getSuperapp(), // superapp
	            		actualObjectStoredInDatabase.getObjectId().getId(), // id
	                    "2024a.otiel.malik",
	                    "tchjha2@gmail.com")
	            .retrieve()
	            .bodyToMono(ObjectBoundary.class)
	            .block();

	    // THEN the retrieved object matches the created object
	    assertThat(retrievedObject).isNotNull();
	}

	@Test
	public void testUpdateObject() {
	    // GIVEN the server is up and an object is available
		CreatedBy createdBy = new CreatedBy("2024a.otiel.malik", "tchjha2@gmail.com");
		Map<String, Object> objectDetails = new HashMap<>();
		objectDetails.put("Product", "flowers");
		ObjectBoundary ob = new ObjectBoundary("sport", "?", true, null, createdBy, objectDetails);
		ObjectBoundary actualObjectStoredInDatabase = this.webClient.post().uri("/objects").bodyValue(ob).retrieve()
				.bodyToMono(ObjectBoundary.class).block();

	    // Update the object
	    ObjectBoundary update = new ObjectBoundary();
	    update.setType("updated sport");
	    update.setAlias("updated ?");
	    update.setActive(false);
	    update.setCreationTimestamp(null);
	    update.setCreatedBy(createdBy);
	    update.setObjectDetails(objectDetails);
	    // Set other fields as needed for update

	 // WHEN requesting the object by ID
	    this.webClient.put()
	            .uri("/objects/{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={userEmail}",
	            		actualObjectStoredInDatabase.getObjectId().getSuperapp(), // superapp
	            		actualObjectStoredInDatabase.getObjectId().getId(), // id
	                    "2024a.otiel.malik",
	                    "tchjha2@gmail.com")
	            .bodyValue(update)
	            .retrieve()
	            .bodyToMono(ObjectBoundary.class)
	            .block();

	    // Fetch the updated object
	    ObjectBoundary updatedObject = this.webClient.get()
	    		.uri("/objects/{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}",
						actualObjectStoredInDatabase.getObjectId().getSuperapp(), // superapp
						actualObjectStoredInDatabase.getObjectId().getId(), // id
						"2024a.otiel.malik", "tchjha2@gmail.com")
	            .retrieve()
	            .bodyToMono(ObjectBoundary.class)
	            .block();

	    // THEN the object should be updated
	    assertThat(updatedObject).isNotNull();
	    assertThat(updatedObject.getType()).isEqualTo("updated sport");
	    assertThat(updatedObject.getAlias()).isEqualTo("updated ?");
	    assertThat(updatedObject.getActive()).isFalse();
	    // Add more assertions as needed
	}

	@Test
	public void testGetAllObjects() {
	    // GIVEN the server is up and objects are available
	    createObject("123", "?", true);
	    createObject("12345", "?", true);
	    createObject("1234567", "?", true);

	    // WHEN requesting all objects
	    Flux<ObjectBoundary> objectsFlux = this.webClient.get()
	            .uri("/objects?userSuperapp={userSuperapp}&userEmail={email}",
	                    "2024a.otiel.malik",
	                    "tchjha2@gmail.com")
	            .accept(MediaType.TEXT_EVENT_STREAM)
	            .retrieve()
	            .bodyToFlux(ObjectBoundary.class);

	    // THEN we should receive a flux of objects
	    List<ObjectBoundary> objectList = objectsFlux.collectList().block();
	    assertThat(objectList).isNotNull();
	    assertThat(objectList.size()).isEqualTo(3); // Assuming we created 3 objects
	}
}

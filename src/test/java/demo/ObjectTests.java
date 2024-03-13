package demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import demo.entities.UserId;
import demo.boundries.NewUserBoundary;
import demo.boundries.ObjectBoundary;
import demo.boundries.UserBoundary;
import reactor.core.publisher.Flux;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class ObjectTests {
	private String url;
	private WebClient webClient;

	@Value("${spring.application.name}")
	private String superAppName;

	@Value("${helper.delimiter}")
	private String delimiter;
	
	@BeforeEach
	public void setup() {
		this.url = "http://localhost:8085/superapp";
		this.webClient = WebClient.create(this.url);
		createUser("admin@gmail.com", Role.ADMIN, "avichai98", "AS");
		createUser("tchjha2@gmail.com", Role.SUPERAPP_USER, "avichai98", "AS");
		createUser("Avichai.Shchori@s.afeka.ac.il", Role.MINIAPP_USER, "avichai98", "AS");
	}

	public void createUser(String email, Role role, String userName, String avatar) {
		NewUserBoundary ub = new NewUserBoundary();
		ub.setEmail(email);
		ub.setRole(role);
		ub.setUsername(userName);
		ub.setAvatar(avatar);
		this.webClient.post().uri("/users").bodyValue(ub).retrieve().bodyToMono(UserBoundary.class).block();

		// THEN the server stores the object in the database
	}

	// General object
	public void createGeneralObject(String type, String alias, boolean active) {
		CreatedBy createdBy = new CreatedBy("2024a.otiel.malik", "tchjha2@gmail.com");
		Map<String, Object> objectDetails = new HashMap<>();
		objectDetails.put("Product", "flowers");
		ObjectBoundary ob = new ObjectBoundary(type, alias, active, null, createdBy, objectDetails);
		this.webClient.post().uri("/objects").bodyValue(ob).retrieve().bodyToMono(ObjectBoundary.class).block();
	}

	// Dreamer object
	public void createDreamerObject(String alias, boolean active, String username, String password, String location,
			String gender, Integer birthdate, String avatar, String email) {
		CreatedBy createdBy = new CreatedBy("2024a.otiel.malik", email);
		Map<String, Object> objectDetails = new HashMap<>();
		objectDetails.put("username", username);
		objectDetails.put("password", password);
		objectDetails.put("gender", gender);
		objectDetails.put("birthdate", birthdate);
		objectDetails.put("location", location);
		objectDetails.put("avatar", avatar);
		ObjectBoundary ob = new ObjectBoundary("dreamer", alias, active, null, createdBy, objectDetails);
		this.webClient.post().uri("/objects").bodyValue(ob).retrieve().bodyToMono(ObjectBoundary.class).block();
	}

	@AfterEach
	public void cleanup() {
		// delete all objects
		// createUser("tchjha2@gmail.com", Role.ADMIN, "avichai98", "AS");
		this.webClient.delete().uri("/admin/objects?userSuperapp={userSuperapp}&userEmail={userEmail}",
				"2024a.otiel.malik", "admin@gmail.com").retrieve().bodyToMono(Void.class).block();

		 // delete all users
		this.webClient.delete().uri("/admin/users?userSuperapp={userSuperapp}&userEmail={userEmail}",
				"2024a.otiel.malik", "admin@gmail.com").retrieve().bodyToMono(Void.class).block();
	}

	@Test
	public void contextLoads() {
	}

	// General object
	@Test
	public void testPostStoresGeneralObjectInDatabase() throws Exception {
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

	// Counselor object
	@Test
	public void testPostStoresCounselorlInDatabase() throws Exception {
		// GIVEN the server is up
		// GIVEN the user with role of SUPERAPP_USER is exist

		/*
		 * WHEN I Post object {" "type": "counselor", "alias": "avichai98", "active": true,
		 * "createdBy": { "userId": { "superapp": "2024a.otiel.malik", "email":
		 * "counselor@gmail.com" }"}
		 */
		
		CreatedBy createdBy = new CreatedBy("2024a.otiel.malik", "counselor@gmail.com");
		Map<String, Object> objectDetails = new HashMap<>();
		objectDetails.put("username", "avichai98");
		objectDetails.put("password", "123456789");
		objectDetails.put("gender", "male");
		objectDetails.put("birthdate", 1998);
		objectDetails.put("phoneNumber", "0506404014");
		objectDetails.put("avatar", "AS");
		objectDetails.put("specialization", "psychology");
		objectDetails.put("experience", 3);
		ObjectBoundary ob = new ObjectBoundary("counselor", "avichai98", true, null, createdBy, objectDetails);
		ObjectBoundary actualObjectStoredInDatabase = this.webClient.post().uri("/objects").bodyValue(ob).retrieve()
				.bodyToMono(ObjectBoundary.class).block();

		// THEN the server stores the object in the database
		assertThat(this.webClient.get()
				.uri("/objects/{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}",
						actualObjectStoredInDatabase.getObjectId().getSuperapp(), // superapp
						actualObjectStoredInDatabase.getObjectId().getId(), // id
						"2024a.otiel.malik", "tchjha2@gmail.com") // SUPERAPP_USER allowed to get object
				.retrieve().bodyToMono(ObjectBoundary.class).block()).extracting("type", "alias")
				.containsExactly("counselor", "avichai98");

		// AND the server stores the user in the database
		UserId id = new UserId();
		id.setSuperapp(superAppName);
		id.setEmail("counselor@gmail.com");
		assertThat(this.webClient.get().uri("/users/login/{Superapp}/{email}", "2024a.otiel.malik", // superapp
				createdBy.getUserId().getEmail()).retrieve().bodyToMono(UserBoundary.class).block())
				.extracting("userId", "avatar")
				.containsExactly(id, "AS");
	}

	// Dreamer object
	@Test
	public void testPostStoresDreamerInDatabase() throws Exception {
		// GIVEN the server is up
		// GIVEN the user with role of SUPERAPP_USER is exist

		/*
		 * WHEN I Post object {" "type": "dreamer", "alias": "?", "active": true,
		 * "createdBy": { "userId": { "superapp": "2024a.otiel.malik", "email":
		 * "dreamer@gmail.com" }"}
		 */
		
		CreatedBy createdBy = new CreatedBy("2024a.otiel.malik", "dreamer@gmail.com");
		Map<String, Object> objectDetails = new HashMap<>();
		objectDetails.put("username", "avichai98");
		objectDetails.put("password", "123456789");
		objectDetails.put("gender", "male");
		objectDetails.put("birthdate", 1998);
		objectDetails.put("location", "Yavne");
		objectDetails.put("avatar", "AS");
		ObjectBoundary ob = new ObjectBoundary("dreamer", "avichai98", true, null, createdBy, objectDetails);
		ObjectBoundary actualObjectStoredInDatabase = this.webClient.post().uri("/objects").bodyValue(ob).retrieve()
				.bodyToMono(ObjectBoundary.class).block();

		// THEN the server stores the object in the database
		assertThat(this.webClient.get()
				.uri("/objects/{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}",
						actualObjectStoredInDatabase.getObjectId().getSuperapp(), // superapp
						actualObjectStoredInDatabase.getObjectId().getId(), // id
						"2024a.otiel.malik", "dreamer@gmail.com")
				.retrieve().bodyToMono(ObjectBoundary.class).block()).extracting("type", "alias")
				.containsExactly("dreamer", "avichai98");

		// AND the server stores the user in the database
		UserId id = new UserId();
		id.setSuperapp(superAppName);
		id.setEmail("dreamer@gmail.com");
		assertThat(this.webClient.get().uri("/users/login/{Superapp}/{email}", "2024a.otiel.malik", // superapp
				createdBy.getUserId().getEmail()).retrieve().bodyToMono(UserBoundary.class).block())
				.extracting("userId", "avatar")
				.containsExactly(id, "AS");
	}

	@Test
	public void testGetObject() {
		// GIVEN the server is up and an object is available
		CreatedBy createdBy = new CreatedBy("2024a.otiel.malik", "tchjha2@gmail.com");
		Map<String, Object> objectDetails = new HashMap<>();
		objectDetails.put("Product", "flowers");
		ObjectBoundary ob = new ObjectBoundary("sport", "avichai98", true, null, createdBy, objectDetails);
		ObjectBoundary actualObjectStoredInDatabase = this.webClient.post().uri("/objects").bodyValue(ob).retrieve()
				.bodyToMono(ObjectBoundary.class).block();

		// WHEN requesting the object by ID
		ObjectBoundary retrievedObject = this.webClient.get()
				.uri("/objects/{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}",
						actualObjectStoredInDatabase.getObjectId().getSuperapp(), // superapp
						actualObjectStoredInDatabase.getObjectId().getId(), // id
						"2024a.otiel.malik", "tchjha2@gmail.com")
				.retrieve().bodyToMono(ObjectBoundary.class).block();

		// THEN the retrieved object matches the created object
		assertThat(retrievedObject).isNotNull();
	}

	@Test
	public void testUpdateObject() {
		// GIVEN the server is up and an object is available
		CreatedBy createdBy = new CreatedBy("2024a.otiel.malik", "tchjha2@gmail.com");
		Map<String, Object> objectDetails = new HashMap<>();
		objectDetails.put("Product", "flowers");
		ObjectBoundary ob = new ObjectBoundary("sport", "avichai98", true, null, createdBy, objectDetails);
		ObjectBoundary actualObjectStoredInDatabase = this.webClient.post().uri("/objects").bodyValue(ob).retrieve()
				.bodyToMono(ObjectBoundary.class).block();

		// Update the object
		ObjectBoundary update = new ObjectBoundary();
		update.setType("Tennis");
		update.setAlias("avichai");
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
						"2024a.otiel.malik", "tchjha2@gmail.com")
				.bodyValue(update).retrieve().bodyToMono(ObjectBoundary.class).block();

		// Fetch the updated object
		ObjectBoundary updatedObject = this.webClient.get()
				.uri("/objects/{superapp}/{id}?userSuperapp={userSuperapp}&userEmail={email}",
						actualObjectStoredInDatabase.getObjectId().getSuperapp(), // superapp
						actualObjectStoredInDatabase.getObjectId().getId(), // id
						"2024a.otiel.malik", "tchjha2@gmail.com")
				.retrieve().bodyToMono(ObjectBoundary.class).block();

		// THEN the object should be updated
		assertThat(updatedObject).isNotNull();
		assertThat(updatedObject.getType()).isEqualTo("Tennis");
		assertThat(updatedObject.getAlias()).isEqualTo("avichai");
		assertThat(updatedObject.getActive()).isFalse();
		// Add more assertions as needed
	}

	@Test
	public void testGetAllObjects() {
		// GIVEN the server is up and objects are available
		createGeneralObject("admin", "admin", true);
		createDreamerObject("dream", true, "dd", "123456789", "Jerusalem", "male", 1998, "dd", "dreamer@walla.com");

		// WHEN requesting all objects
		Flux<ObjectBoundary> objectsFlux = this.webClient.get()
				.uri("/objects?userSuperapp={userSuperapp}&userEmail={email}", "2024a.otiel.malik", "tchjha2@gmail.com")
				.accept(MediaType.TEXT_EVENT_STREAM).retrieve().bodyToFlux(ObjectBoundary.class);

		// THEN we should receive a flux of objects
		List<ObjectBoundary> objectList = objectsFlux.collectList().block();
		assertThat(objectList).isNotNull();
		assertThat(objectList.size()).isEqualTo(2); // Assuming we created 2 objects
	}

	public String getUrl() {
		return this.url;
	}

	public WebClient getWebClient() {
		return this.webClient;
	}	
}

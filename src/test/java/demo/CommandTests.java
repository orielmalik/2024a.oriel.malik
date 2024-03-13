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
import demo.boundries.MiniAppCommandBoundary;
import demo.boundries.NewUserBoundary;
import demo.boundries.ObjectBoundary;
import demo.boundries.UserBoundary;
import reactor.core.publisher.Flux;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class CommandTests {
	private String commandUrl;
	private WebClient webClientCommand;
	private ObjectTests objectTests = new ObjectTests();

	@Value("${spring.application.name}")
	private String superAppName;

	@Value("${helper.delimiter}")
	private String delimiter;

	@BeforeEach
	public void setup() {
		objectTests.setup();
		this.commandUrl = "http://localhost:8085/superapp";
		// this.objectUrl = "http://localhost:8085/superapp";
		this.webClientCommand = WebClient.create(this.commandUrl);
	}

	@AfterEach
	public void cleanup() {
		this.webClientCommand.delete().uri("/admin/miniapp?userSuperapp={userSuperapp}&userEmail={userEmail}",
				"2024a.otiel.malik", "admin@gmail.com").retrieve().bodyToMono(Void.class).block();

		// delete all objects and users
		objectTests.cleanup();
	}

	@Test
	public void contextLoads() {
	}

	// General object
	@Test
	public void testPostStoresCommandInDatabase() throws Exception {
		// GIVEN the server is up
		// GIVEN the user with role of MINIAPP_USER is exist

		/*
		 * WHEN I Post miniapp {" "type": "sport", "alias": "?", "active": true,
		 * "createdBy": { "userId": { "superapp": "2024a.otiel.malik", "email":
		 * "tchjha2@gmail.com" }"}
		 */
		CreatedBy createdBy1 = new CreatedBy("2024a.otiel.malik", "counselor@gmail.com");
		Map<String, Object> objectDetails1 = new HashMap<>();
		objectDetails1.put("username", "avichai98");
		objectDetails1.put("password", "123456789");
		objectDetails1.put("gender", "male");
		objectDetails1.put("birthdate", 1998);
		objectDetails1.put("phoneNumber", "0506404014");
		objectDetails1.put("avatar", "AS");
		objectDetails1.put("specialization", "psychology");
		objectDetails1.put("experience", 3);
		ObjectBoundary ob1 = new ObjectBoundary("counselor", "avichai98", true, null, createdBy1, objectDetails1);
		ObjectBoundary actualObjectStoredInDatabase1 = objectTests.getWebClient().post().uri("/objects").bodyValue(ob1)
				.retrieve().bodyToMono(ObjectBoundary.class).block();

		Map<String, Object> objectDetails = new HashMap<>();
		CreatedBy createdBy = new CreatedBy("2024a.otiel.malik", "dreamer@gmail.com");
		objectDetails.put("username", "avichai98");
		objectDetails.put("password", "123456789");
		objectDetails.put("gender", "male");
		objectDetails.put("birthdate", 1998);
		objectDetails.put("location", "Yavne");
		objectDetails.put("avatar", "AS");
		ObjectBoundary ob = new ObjectBoundary("dreamer", "avichai98", true, null, createdBy, objectDetails);
		ObjectBoundary actualObjectStoredInDatabase = objectTests.getWebClient().post().uri("/objects").bodyValue(ob)
				.retrieve().bodyToMono(ObjectBoundary.class).block();

		Map<String, Object> commandAttributes = new HashMap<>();
		commandAttributes.put("tips", "flowers");
		ObjectId oId = new ObjectId(superAppName, actualObjectStoredInDatabase.getObjectId().getId());
		UserId uId = new UserId(superAppName, "counselor@gmail.com");
		TargetObject target = new TargetObject(oId);
		InvokedBy invok = new InvokedBy(uId);
		MiniAppCommandBoundary macb = new MiniAppCommandBoundary();
		macb.setCommand("counselor-GiveTips");
		macb.setCommandAttributes(commandAttributes);
		macb.setTargetObject(target);
		macb.setInvokedBy(invok);
		MiniAppCommandBoundary actualMiniAppStoredInDatabase = this.webClientCommand.post()
				.uri("/miniapp/{miniAppName}", superAppName).bodyValue(macb).retrieve()
				.bodyToFlux(MiniAppCommandBoundary.class).blockLast();

		// THEN the server stores the miniapp in the database
		Flux<MiniAppCommandBoundary> miniAppFlux = this.webClientCommand.get()
				.uri("/admin/miniapp", actualMiniAppStoredInDatabase.getCommandId().getMiniapp(), // superapp
						"admin@gmail.com")
				.accept(MediaType.TEXT_EVENT_STREAM).retrieve().bodyToFlux(MiniAppCommandBoundary.class);

		List<MiniAppCommandBoundary> miniAppList =  miniAppFlux.collectList().block(null);
		assertThat(miniAppList).isNotNull();
		assertThat(miniAppList.size()).isEqualTo(2); // Assuming we created 2 miniApp
	}
}

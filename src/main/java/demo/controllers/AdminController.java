package demo.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.boundries.MiniAppCommandBoundary;
import demo.boundries.UserBoundary;
import demo.interfaces.AdminService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/superapp/admin")
public class AdminController {

	private AdminService adminService;

	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}

	@DeleteMapping(path = { "/users" })
	public Mono<Void> deleteAllUsers(@RequestParam(name = "userSuperapp") String userSuperapp,
			@RequestParam(name = "userEmail", required = true) String userEmail) {
		return this.adminService.deleteAllUsers(userSuperapp, userEmail);
	}

	@DeleteMapping(path = { "/objects" })
	public Mono<Void> deleteAllObjects(@RequestParam(name = "userSuperapp") String userSuperapp,
			@RequestParam(name = "userEmail") String userEmail) {
		return this.adminService.deleteAllObjects(userSuperapp, userEmail);
	}

	@DeleteMapping(path = { "/miniapp" })
	public Mono<Void> deleteAllCommands(@RequestParam(name = "userSuperapp") String userSuperapp,
			@RequestParam(name = "userEmail") String userEmail) {
		return this.adminService.delteAllCommands(userSuperapp, userEmail);
	}

	@GetMapping(path = { "/users" }, produces = { MediaType.TEXT_EVENT_STREAM_VALUE })
	public Flux<UserBoundary> getAllUsers(@RequestParam(name = "userSuperapp") String userSuperapp,
			@RequestParam(name = "userEmail") String userEmail) {
		return this.adminService.fetchAllUsers(userSuperapp, userEmail);
	}

	@GetMapping(path = { "/miniapp" }, produces = { MediaType.TEXT_EVENT_STREAM_VALUE })
	public Flux<MiniAppCommandBoundary> getAllMiniAppCommands(@RequestParam(name = "userSuperapp") String userSuperapp,
			@RequestParam(name = "userEmail") String userEmail) {
		return this.adminService.fetchAllMiniappCommands(userSuperapp, userEmail);
	}

	@GetMapping(path = { "/miniapp/{miniAppName}" }, produces = { MediaType.TEXT_EVENT_STREAM_VALUE })
	public Flux<MiniAppCommandBoundary> getMiniAppCommand(@PathVariable("miniAppName") String miniAppName,
			@RequestParam(name = "userSuperapp") String userSuperapp,
			@RequestParam(name = "userEmail") String userEmail) {
		return this.adminService.fetchMiniappCommand(userSuperapp, userEmail, miniAppName);
	}

}

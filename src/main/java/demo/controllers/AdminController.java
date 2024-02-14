package demo.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
//	private ObjectService objectService;
//	private CommandService commandService;
	
	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}
	
	
	@GetMapping(path = {"/users"},produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
	public Flux<UserBoundary> getAllUsers() {
		return this.adminService.fetchAllUsers();
	}
	
	@GetMapping(path = {"/miniapp"},produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
	public Flux<MiniAppCommandBoundary> getAllCommands() {
		return this.adminService.fetchAllMiniappCommands();
	}
	
	
	@DeleteMapping(path = {"/users"})
	public Mono<Void> deleteAllUsers() {
		return this.adminService.deleteAllUsers();
	}	
	
	@DeleteMapping(path = {"/objects"})
	public Mono<Void> deleteAllObjects() {
		return this.adminService.deleteAllObjects();
	}
	
	@DeleteMapping(path = {"/miniapp"})
	public Mono<Void> deleteAllCommands() {
		return this.adminService.delteAllCommands();
	}
	

	
}

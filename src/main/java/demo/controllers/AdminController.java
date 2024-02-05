package demo.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public Flux<UserBoundary> getAll() {
		return this.adminService.fetchAllUsers();
	}
	
	@DeleteMapping(path = {"/users"})
	public Mono<Void> deleteAll() {
		return this.adminService.deleteAllUsers();
	}	
	
}

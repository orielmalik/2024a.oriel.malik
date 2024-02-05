package demo.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.boundries.NewUserBoundary;
import demo.boundries.UserBoundary;
import demo.interfaces.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = {"/superapp/users"})	// ${spring.application.name}
public class UserController {
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})	
	public Mono<NewUserBoundary> create(@RequestBody NewUserBoundary user) {
		return this.userService.create(user);
	}
	
	@GetMapping(path = {"/login/{superapp}/{email}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Mono<UserBoundary> login(@PathVariable("superapp") String superapp, @PathVariable("email") String email) {
		return this.userService.login(superapp, email);
	}
	
	@PutMapping(path = {"/{superapp}/{email}"}, consumes = {MediaType.APPLICATION_JSON_VALUE})
	public Mono<Void> update(@PathVariable("superapp") String superapp, @PathVariable("email") String email, @RequestBody UserBoundary user) {
		return this.userService.update(superapp,email, user);
	}
	
//	@GetMapping(produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
//	public Flux<UserBoundary> getAll() {
//		return this.userService.getAll();
//	}
//	
//	@DeleteMapping
//	public Mono<Void> deleteAll() {
//		return this.userService.deleteAll();
//	}	
}

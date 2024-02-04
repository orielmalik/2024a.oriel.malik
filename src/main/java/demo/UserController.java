package demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = {"/${spring.application.name}/users"})
public class UserController {
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})	
	public Mono<UserBoundary> create(@RequestBody UserBoundary user) {
		return this.userService.create(user);
	}
	
	@GetMapping(path = {"/{email}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Mono<UserBoundary> login(@PathVariable("email") String email) {
		return this.userService.login(email);
	}
	
	@PutMapping(path = {"/{id}"}, consumes = {MediaType.APPLICATION_JSON_VALUE})
	public Mono<Void> update(@PathVariable("id") String id, @RequestBody UserBoundary user) {
		return this.userService.update(id, user);
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

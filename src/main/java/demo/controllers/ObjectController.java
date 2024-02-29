package demo.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.boundries.ObjectBoundary;
import demo.interfaces.ObjectService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = { "/superapp/objects" })
public class ObjectController {

	private ObjectService objectService;

	public ObjectController(ObjectService objectService) {
		super();
		this.objectService = objectService;
	}

	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public Mono<ObjectBoundary> create(@RequestBody ObjectBoundary message) {
		return this.objectService.create(message);
	}

	@GetMapping(path = { "/{superapp}/{id}" }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Mono<ObjectBoundary> getObject(@PathVariable("superapp") String objectSuperapp,@PathVariable("id") String id,
			@RequestParam(name = "userSuperapp", required = false) String userSuperapp,
			@RequestParam(name = "userEmail", required = false) String userEmail) {
		return this.objectService.getObject(objectSuperapp,id, userSuperapp, userEmail);
	}

	@GetMapping(produces = { MediaType.TEXT_EVENT_STREAM_VALUE })
	public Flux<ObjectBoundary> getAllObjects(
			@RequestParam(name = "userSuperapp", required = false) String userSuperapp,
			@RequestParam(name = "userEmail", required = false) String userEmail) {
		return this.objectService.getAllObjects(userSuperapp, userEmail);
	}

	@PutMapping(path = { "/{superapp}/{id}" }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public Mono<Void> updateObject(@PathVariable("superapp") String objectSuperapp,@PathVariable("id") String id, @RequestBody ObjectBoundary update,
			@RequestParam(name = "userSuperapp", required = false) String userSuperapp,
			@RequestParam(name = "userEmail", required = false) String userEmail) {
		return this.objectService.updateObject(objectSuperapp,id, update, userSuperapp, userEmail);
	}

}

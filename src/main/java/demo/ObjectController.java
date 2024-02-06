package demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public Mono<ObjectBoundary> getObject(@PathVariable("id") String id) {
		return this.objectService.getObject(id);
	}

	@GetMapping(produces = { MediaType.TEXT_EVENT_STREAM_VALUE })
	public Flux<ObjectBoundary> getAllObjects() {
		return this.objectService.getAllObjects();
	}

	@PutMapping(path = { "/{superapp}/{id}" }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public Mono<Void> updateObject(@PathVariable("id") String id, @RequestBody ObjectBoundary update) {
		return this.objectService.updateObject(id, update);
	}

}

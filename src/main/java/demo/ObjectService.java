package demo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ObjectService {

	public Mono<ObjectBoundary> create(ObjectBoundary object);

	public Mono<ObjectBoundary> getObject(String id);

	public Flux<ObjectBoundary> getAllObjects();

	public Mono<Void> updateObject(String id, ObjectBoundary update);
}

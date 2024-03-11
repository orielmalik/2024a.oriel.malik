package demo.interfaces;

import demo.boundries.MiniAppCommandBoundary;
import demo.boundries.ObjectBoundary;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ObjectService {

	public Mono<ObjectBoundary> create(ObjectBoundary object);

	public Mono<ObjectBoundary> getObject(String objectSuperapp, String id, String userSuperapp, String userEmail);

	public Flux<ObjectBoundary> getAllObjects(String userSuperapp, String userEmail);

	public Mono<Void> updateObject(String objectSuperapp, String id, ObjectBoundary update, String userSuperapp,
			String userEmail);

	public Flux<ObjectBoundary> searchbyType(String type, String superApp, String userEmail);

	public Flux<ObjectBoundary> searchbyAlias(String alias, String superApp, String userEmail);

	public Flux<ObjectBoundary> searchbyAliasPattern(String pattern, String superApp, String userEmail);

	public Flux<MiniAppCommandBoundary> execute(MiniAppCommandBoundary input);
}

package demo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
	public Mono<UserBoundary> create(UserBoundary user);
	
	public Mono<UserBoundary> login(String email);
	
	public Flux<UserBoundary> getAll();
	
	public Mono<Void> deleteAll();
	
	public Mono<Void> update(String id, UserBoundary user);
}

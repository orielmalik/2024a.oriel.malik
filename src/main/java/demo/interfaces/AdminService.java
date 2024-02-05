package demo.interfaces;

import demo.boundries.UserBoundary;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AdminService {
	public Mono<Void> deleteAllUsers();
	
//	public Mono<Void> deleteAllObjects();
	
//	public Mono<Void> delteAllCommands();
	
	public Flux<UserBoundary> fetchAllUsers();
	
//	public Flux<ObjectBoundary> fetchAllObjects();
	
//	public Flux<CommandBoundary> fetchAllCommands();
}

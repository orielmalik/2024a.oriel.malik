package demo.interfaces;

import demo.boundries.MiniAppCommandBoundary;
import demo.boundries.ObjectBoundary;
import demo.boundries.UserBoundary;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AdminService {
	public Mono<Void> deleteAllUsers();
	
	public Mono<Void> deleteAllObjects();
	
	public Mono<Void> delteAllCommands();
	
	public Flux<UserBoundary> fetchAllUsers();
	
//	public Flux<MiniAppCommandBoundary> fetchMiniappCommand(String miniAppName);
	
	public  Flux<MiniAppCommandBoundary> fetchAllMiniappCommands();
	
//	public Flux<MiniAppCommandBoundary> fetchAllCommands();
}

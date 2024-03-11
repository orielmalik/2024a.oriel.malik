package demo.interfaces;

import demo.boundries.MiniAppCommandBoundary;
import demo.boundries.UserBoundary;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AdminService {
	public Mono<Void> deleteAllUsers(String superappName, String email);

	public Mono<Void> deleteAllObjects(String superappName, String email);

	public Mono<Void> delteAllCommands(String superappName, String email);

	public Flux<UserBoundary> fetchAllUsers(String superappName, String email);

	public Flux<MiniAppCommandBoundary> fetchMiniappCommand(String superappName, String email, String miniAppName);

	public Flux<MiniAppCommandBoundary> fetchAllMiniappCommands(String superappName, String email);

}

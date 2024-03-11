package demo.interfaces;

import demo.boundries.NewUserBoundary;
import demo.boundries.UserBoundary;
import reactor.core.publisher.Mono;

public interface UserService {
	public Mono<UserBoundary> create(NewUserBoundary user);

	public Mono<UserBoundary> login(String superapp, String email);

	public Mono<Void> update(String superapp, String email, UserBoundary user);
}

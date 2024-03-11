package demo.interfaces;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import demo.entities.UserEntity;
import reactor.core.publisher.Mono;

public interface UserCrud extends ReactiveMongoRepository<UserEntity, String> {
	public Mono<Boolean> existsByEmail(String email);
}

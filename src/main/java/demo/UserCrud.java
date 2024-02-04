package demo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserCrud extends ReactiveMongoRepository<UserEntity, String>{
	
}

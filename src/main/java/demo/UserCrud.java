package demo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import demo.entities.UserEntity;

public interface UserCrud extends ReactiveMongoRepository<UserEntity, String>{
	
}

package demo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MessageCrud extends
	ReactiveMongoRepository<MessageEntity, String>{

}

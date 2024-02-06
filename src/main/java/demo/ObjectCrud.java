package demo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ObjectCrud extends ReactiveMongoRepository<ObjectEntity, String>{

}

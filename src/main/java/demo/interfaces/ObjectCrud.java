package demo.interfaces;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import demo.entities.ObjectEntity;

public interface ObjectCrud extends ReactiveMongoRepository<ObjectEntity, String>{

}

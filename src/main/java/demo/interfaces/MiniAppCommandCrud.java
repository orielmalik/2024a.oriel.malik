package demo.interfaces;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import demo.entities.ObjectEntity;

public interface MiniAppCommandCrud extends ReactiveMongoRepository<ObjectEntity, String>{

}


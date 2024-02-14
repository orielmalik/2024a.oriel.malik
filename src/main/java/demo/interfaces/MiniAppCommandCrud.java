package demo.interfaces;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import demo.entities.MiniAppCommandEntity;

public interface MiniAppCommandCrud extends ReactiveMongoRepository<MiniAppCommandEntity, String>{

}


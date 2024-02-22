package demo.interfaces;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import demo.entities.ObjectEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ObjectCrud extends ReactiveMongoRepository<ObjectEntity, String>{
	
	public Mono<ObjectEntity> findByObjectIdAndActiveIsTrue(String id);
	public Flux<ObjectEntity> findAllByActiveIsTrue();
}

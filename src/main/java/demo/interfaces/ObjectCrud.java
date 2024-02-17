package demo.interfaces;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;

import demo.entities.ObjectEntity;
import reactor.core.publisher.Flux;

public interface ObjectCrud extends ReactiveMongoRepository<ObjectEntity, String>{
	//sprint 3
public Flux<ObjectEntity>findByType(@Param ("type") String type);
public Flux<ObjectEntity>findByAlias(@Param ("alias") String alias);
}

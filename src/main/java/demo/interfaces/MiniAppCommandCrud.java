package demo.interfaces;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import demo.entities.MiniAppCommandEntity;
import reactor.core.publisher.Flux;

public interface MiniAppCommandCrud extends ReactiveMongoRepository<MiniAppCommandEntity, String>{
	public Flux<MiniAppCommandEntity> findAllByCommandIdLike(String miniAppName);
}


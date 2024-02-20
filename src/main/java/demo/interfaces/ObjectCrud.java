package demo.interfaces;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;

import demo.entities.ObjectEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ObjectCrud extends ReactiveMongoRepository<ObjectEntity, String>{
	
	public Mono<ObjectEntity> findByObjectIdAndActiveIsTrue(String id);
	public Flux<ObjectEntity> findAllByActiveIsTrue();
	
	public Flux<ObjectEntity>findByType(@Param ("type") String type,@Param ("superapp") String superapp,@Param ("email") String email	);
	public Flux<ObjectEntity>findByAlias(@Param ("alias") String alias,@Param ("superapp") String superapp,@Param ("email") String email);
	public Flux<ObjectEntity> findAllByAliasLike(
			@Param("pattern") String pattern,@Param ("superapp") String superapp,@Param ("email") String email);
}

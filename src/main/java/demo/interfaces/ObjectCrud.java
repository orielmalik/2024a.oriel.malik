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
	//public Flux<ObjectEntity>findByAlias(@Param ("alias") String alias,@Param ("superapp") String superapp,@Param ("email") String email);
	//public Flux<ObjectEntity> findAllByAliasLike(@Param("pattern") String pattern,@Param ("superapp") String superapp,@Param ("email") String email);
	
////
	public Flux<ObjectEntity> findAllByType(String type); // SUPERAPP_USER
	public Flux<ObjectEntity> findAllByAlias(@Param ("alias") String alias); // SUPERAPP_USER
	public Flux<ObjectEntity> findAllByAliasLike(@Param("pattern") String pattern); // SUPERAPP_USER
	
	public Flux<ObjectEntity> findAllByTypeAndActiveIsTrue(String type); // MINIAPP_USER
	public Flux<ObjectEntity> findAllByAliasAndActiveIsTrue(@Param ("alias") String alias);// MINIAPP_USER
	public Flux<ObjectEntity> findAllByActiveIsTrueAndAliasLike(
			@Param("pattern") String pattern);// MINIAPP_USER
}

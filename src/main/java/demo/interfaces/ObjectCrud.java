package demo.interfaces;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;

import demo.entities.ObjectEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ObjectCrud extends ReactiveMongoRepository<ObjectEntity, String> {

	public Mono<ObjectEntity> findByObjectIdAndActiveIsTrue(String id);

	public Flux<ObjectEntity> findAllByActiveIsTrue();

	public Flux<ObjectEntity> findAllByType(String type); // SUPERAPP_USER

	public Flux<ObjectEntity> findAllByAlias(@Param("alias") String alias); // SUPERAPP_USER

	public Flux<ObjectEntity> findAllByAliasLike(@Param("pattern") String pattern); // SUPERAPP_USER

	public Flux<ObjectEntity> findByBirthdateGreaterThan(long birthdate); // MINIAPP_USER

	Flux<ObjectEntity> findByAliasOrderByViewscountDesc(String alias);

	Flux<ObjectEntity> findByAliasOrderByViewscountAsc(String alias);

	Flux<ObjectEntity> findByGenderNot(boolean gender);
	// Flux<ObjectEntity> findByGender(boolean gender);

	public Flux<ObjectEntity> findByTypeOrderByPriceAsc(String type, double price); // SUPERAPP_USER

	public Flux<ObjectEntity> findAllByTypeAndActiveIsTrue(String type); // MINIAPP_USER

	public Flux<ObjectEntity> findAllByAliasAndActiveIsTrue(@Param("alias") String alias);// MINIAPP_USER

	public Flux<ObjectEntity> findAllByActiveIsTrueAndAliasLike(@Param("pattern") String pattern);// MINIAPP_USER

	Flux<ObjectEntity> findAllByOrderByBirthdateAsc();

	public Flux<ObjectEntity> findByGender(String gender);

	// public Flux<ObjectEntity> findByAgeGreaterThan(int age);
	public Flux<ObjectEntity> findByLocation(String location);

	public Flux<ObjectEntity> findByTypeOrderByPriceDesc(String string, double price);

}

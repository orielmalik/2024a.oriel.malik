package demo.interfaces;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;

import demo.entities.ObjectEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ObjectCrud extends ReactiveMongoRepository<ObjectEntity, String>{
	
	public Mono<ObjectEntity> findByObjectIdAndActiveIsTrue(String id);
	public Flux<ObjectEntity> findAllByActiveIsTrue();
	public Flux<ObjectEntity> findAllByType(String type); // SUPERAPP_USER
	public Flux<ObjectEntity> findAllByAlias(@Param ("alias") String alias); // SUPERAPP_USER
	public Flux<ObjectEntity> findAllByAliasLike(@Param("pattern") String pattern); // SUPERAPP_USER
	//public Flux<ObjectEntity> findByAgeGreaterThan(int Age); // MINIAPP_USER

	public Flux<ObjectEntity> findAllByTypeAndActiveIsTrue(String type); // MINIAPP_USER
	
	public Flux<ObjectEntity> findAllByAliasAndActiveIsTrue(@Param ("alias") String alias);// MINIAPP_USER
	public Flux<ObjectEntity> findAllByActiveIsTrueAndAliasLike(
			@Param("pattern") String pattern);// MINIAPP_USER
	// Find documents by a specific key in the mapAttribute
    //Flux<ObjectEntity> findByObjectDeatilsKey(String key);


    Flux<ObjectEntity> findAllByBirthdateBeforeOrderByBirthdate(LocalDate birthdate);

	public Flux<ObjectEntity> findByGender(String gender);
	//public Flux<ObjectEntity> findByAgeGreaterThan(int age);



}

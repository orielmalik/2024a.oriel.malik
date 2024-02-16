package demo.interfaces;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;

import demo.entities.ObjectEntity;
import reactor.core.publisher.Mono;

public interface ObjectCrud extends ReactiveMongoRepository<ObjectEntity, String>{

	public Mono<ObjectEntity> findByObjectIdAndUserIdSuperAppAndUserIdEmail(
			@Param("objectId") String objectId,
			@Param("userIdSuperapp") String userSuperapp,
			@Param("UserIdEmail") String userIdEmail);
}

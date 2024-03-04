package demo.interfaces;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import demo.entities.MiniAppCommandEntity;
import demo.services.List;
import demo.services.Pageable;
import demo.services.Query;
import demo.services.SuperAppObjectEntity;
import reactor.core.publisher.Flux;

public interface MiniAppCommandCrud extends ReactiveMongoRepository<MiniAppCommandEntity, String>{
	public Flux<MiniAppCommandEntity> findAllByCommandIdLike(String miniAppName);
	
//	@Query("{ 'location' : { $geoWithin : { $centerSphere : [ [ ?1, ?0 ], ?2 ] } } }")
//	List<Object> findWithinCircle(double centerLat, double centerLng, double radius,Pageable pageable);
//
//	@Query("{ $and: [ { 'location': { $geoWithin: { $centerSphere: [ [ ?1, ?0 ], ?2 ] } } }, { 'active': true } ] }")
//	List<Object> findWithinCircleAndActiveIsTrue(double centerLat, double centerLng, double radius,Pageable pageable);
}


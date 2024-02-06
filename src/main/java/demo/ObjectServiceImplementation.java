package demo;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ObjectServiceImplementation implements ObjectService {

	private ObjectCrud objectCrud;

	@Value("@{spring.application.name}")
	private String superAppName;
	
	public ObjectServiceImplementation(ObjectCrud objectCrud) {
		super();
		this.objectCrud = objectCrud;
	}

	@Override
	public Mono<ObjectBoundary> create(ObjectBoundary object) {
		object.getObjectId().setId(UUID.randomUUID().toString());
		object.getObjectId().setSuperapp(superAppName);
		return Mono.just(object)
				.flatMap(boundary->{
					if(boundary.getType() == null || boundary.getObjectDetails() == null)
						return Mono.empty();
					return Mono.just(boundary);
				})
				.map(ObjectBoundary::toEntity)
				.flatMap(this.objectCrud::save)
				.map(entity-> new ObjectBoundary(entity))
				.log();
	}

	@Override
	public Mono<ObjectBoundary> getObject(String id) {
		
		return this.objectCrud
				.findById(id)
				.map(entity-> new ObjectBoundary(entity))
				.log();
	}

	@Override
	public Flux<ObjectBoundary> getAllObjects() {
		return this.objectCrud
				.findAll()
				.map(entity->new ObjectBoundary(entity))
				.log();
	}

	@Override
	public Mono<Void> updateObject(String id, ObjectBoundary update) {
		return this.objectCrud
				.findById(id)
				.map(entity->{
					entity.setActive(update.getActive());
					entity.setType(update.getType());
					entity.setObjectDetails(update.getObjectDetails());
					entity.setAlias(update.getAlias());
					return entity;
				})
				.flatMap(this.objectCrud::save)
				.map(ObjectBoundary::new)
				.log()
				.then();
	}

}

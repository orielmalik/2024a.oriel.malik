package demo.services;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import demo.ObjectId;
import demo.boundries.ObjectBoundary;
import demo.interfaces.ObjectCrud;
import demo.interfaces.ObjectService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ObjectServiceImplementation implements ObjectService {

	private ObjectCrud objectCrud;

	@Value("${spring.application.name}")
	private String superAppName;

	public ObjectServiceImplementation(ObjectCrud objectCrud) {
		super();
		this.objectCrud = objectCrud;
	}

	@Override
	public Mono<ObjectBoundary> create(ObjectBoundary object) {
		if (object.getObjectId() == null) {
			object.setObjectId(new ObjectId());
		}
		object.getObjectId().setId(UUID.randomUUID().toString());
		object.getObjectId().setSuperapp(superAppName);
		object.setCreatedTimestamp(new Date());
		object.getCreatedBy().getUserId().setSuperapp(superAppName);
		return Mono.just(object).flatMap(boundary -> {
			if (boundary.getType() == null || boundary.getObjectDetails() == null || boundary.getCreatedBy() == null
					|| boundary.getCreatedBy().getUserId() == null
					|| boundary.getCreatedBy().getUserId().getEmail() == null)
				return Mono.empty(); // TODO throw exception
			return Mono.just(boundary);
		}).map(ObjectBoundary::toEntity).flatMap(this.objectCrud::save).map(entity -> new ObjectBoundary(entity)).log();
	}

	@Override
	public Mono<ObjectBoundary> getObject(String id) {

		return this.objectCrud.findById(id).switchIfEmpty(Mono.empty()).map(entity -> new ObjectBoundary(entity)).log();
	}

	@Override
	public Flux<ObjectBoundary> getAllObjects() {
		return this.objectCrud.findAll().map(entity -> new ObjectBoundary(entity)).log();
	}

	@Override
	public Mono<Void> updateObject(String id, ObjectBoundary update) {
		return this.objectCrud.findById(id).map(entity -> {
			entity.setActive(update.getActive());
			entity.setType(update.getType());
			entity.setObjectDetails(update.getObjectDetails());
			entity.setAlias(update.getAlias());
			return entity;
		}).flatMap(this.objectCrud::save).map(ObjectBoundary::new).log().then();
	}

}

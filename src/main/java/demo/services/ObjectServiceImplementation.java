package demo.services;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import demo.ObjectId;
import demo.Role;
import demo.boundries.ObjectBoundary;
import demo.entities.UserEntity;
import demo.exception.BadRequest400;
import demo.exception.UnauthorizedAccess401;
import demo.exception.NotFound404;
import demo.interfaces.ObjectCrud;
import demo.interfaces.ObjectService;
import demo.interfaces.UserCrud;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ObjectServiceImplementation implements ObjectService {

	private ObjectCrud objectCrud;
	private UserCrud userCrud;

	@Value("${spring.application.name}")
	private String superAppName;

	public ObjectServiceImplementation(ObjectCrud objectCrud, UserCrud userCrud) {
		super();
		this.objectCrud = objectCrud;
		this.userCrud = userCrud;
	}

	@Override
	public Mono<ObjectBoundary> create(ObjectBoundary object) {
		if (object.getObjectId() == null) {
			object.setObjectId(new ObjectId());
		}
		object.getObjectId().setId(UUID.randomUUID().toString()).setSuperapp(superAppName);
		object.setCreatedTimestamp(new Date());
		object.getCreatedBy().getUserId().setSuperapp(superAppName);
		
		return Mono.just(object).flatMap(boundary -> {
			if ((boundary.getType() == null || boundary.getType().isEmpty()) || boundary.getObjectDetails() == null
					|| boundary.getCreatedBy() == null || boundary.getCreatedBy().getUserId() == null
					|| boundary.getCreatedBy().getUserId().getEmail() == null)
				return Mono.error(() -> new BadRequest400("Some needed attribute are null"));
			return Mono.just(boundary);
		}).map(ObjectBoundary::toEntity).flatMap(this.objectCrud::save).map(entity -> new ObjectBoundary(entity)).log();
	}

	@Override
	public Mono<ObjectBoundary> getObject(String id, String userSuperapp, String userEmail) {
	
		// find the user with this userSuperapp and userEmail.
		return this.userCrud.findById(userSuperapp + ":" + userEmail).flatMap(user -> {
			// check if he has permission to update objects (if his role is SUPERAPP_USER).
			if (user.getRole().equals(Role.SUPERAPP_USER)) {
				// return the object if found,
				// else return a NotFound message.
				return this.objectCrud.findById(id)
						.switchIfEmpty(Mono.error(() -> new NotFound404("Object not found in database.")))
						.map(entity -> new ObjectBoundary(entity)).log();
			}
			// check if the user role is MINIAPP_USER.
			else if (user.getRole().equals(Role.MINIAPP_USER)) {
				// return the object if found and only if the Active attribute is True.
				// else return an empty Mono.
				return this.objectCrud.findByObjectIdAndActiveIsTrue(id)
						.switchIfEmpty(Mono.empty())
						.map(entity -> new ObjectBoundary(entity)).log();
			}
			// other roles, return UnauthorizedAccess message.
			else {
				return Mono.error(() -> new UnauthorizedAccess401("You dont have permission to get objects."));
			}
		}).switchIfEmpty(Mono.error(new NotFound404("User not found"))); // return a NotFound message if the user is not
		// in the database.
	}

	@Override
	public Flux<ObjectBoundary> getAllObjects(String userSuperapp, String userEmail) {
		// find the user with this userSuperapp and userEmail.
		return this.userCrud.findById(userSuperapp + ":" + userEmail).flatMapMany(user -> {
			// check if he has permission to update objects (if his role is SUPERAPP_USER).
			if (user.getRole().equals(Role.SUPERAPP_USER)) {
				return this.objectCrud.findAll().map(entity -> new ObjectBoundary(entity)).log();
				// check if the user role is MINIAPP_USER.
			} else if (user.getRole().equals(Role.MINIAPP_USER)) {
				// return all objects with Active attribute is True.
				return this.objectCrud.findAllByActiveIsTrue().map(entity -> new ObjectBoundary(entity)).log();
			}
			// other roles, return UnauthorizedAccess message.
			else {
				return Flux.error(new UnauthorizedAccess401("You dont have permission to get objects."));
			}
		}).switchIfEmpty(Flux.error(new NotFound404("User not found"))); // return a NotFound message if the user is not
		// in the database.
	}

	@Override
	public Mono<Void> updateObject(String id, ObjectBoundary update, String userSuperapp, String userEmail) {
		// find the user with this userSuperapp and userEmail
		return this.userCrud.findById(userSuperapp + ":" + userEmail).flatMap(user -> {
			// check if he has permission to update objects (if his role is SUPERAPP_USER).
			if (!user.getRole().equals(Role.SUPERAPP_USER)) {
				// return an UnauthorizedAccess message if the role is not SUPERAPP_USER.
				return Mono.error(() -> new UnauthorizedAccess401("You dont have permission to udpate."));
			} else {
				// update the needed object.
				return this.objectCrud.findById(id).map(entity -> {
					entity.setActive(update.getActive());
					// check if type is null or empty string.
					if (update.getType() != null && update.getType() != "") {
						entity.setType(update.getType());
					}
					entity.setObjectDetails(update.getObjectDetails());
					// check if alias is null or empty string.
					if (update.getAlias() != null && update.getAlias() != "") {
						entity.setAlias(update.getAlias());
					}

					return entity;
				}).flatMap(this.objectCrud::save).map(ObjectBoundary::new).log().then();
			}

		}).switchIfEmpty(Mono.error(new NotFound404("User not found"))); // return a NotFound message if the user is not
																			// in the database.

	}
	
	
	@Override
	public Flux<ObjectBoundary> searchbyType(String type, String superApp, String userEmail) {
		
		return this.userCrud.findById(superApp + ":" + userEmail).flatMapMany(user -> {
			// check if he has permission to search objects (if his role is SUPERAPP_USER).
			if( (user.getRole().equals(Role.MINIAPP_USER)&&this.objectCrud.findAllByActiveIsTrue()!=null) ||user.getRole().equals(Role.SUPERAPP_USER)){
			
					// TODO add/edit what you want (start from here)
					return this.objectCrud.findByType(type, superApp, userEmail).map(ObjectBoundary::new).log();
			}
				else {
					return Flux.error(new UnauthorizedAccess401("You dont have permission to get objects."));
				}
			
			
		
		}).switchIfEmpty(Flux.error(new NotFound404("User not found"))); // return a NotFound message if the user is not
		// in the database.
		
	}

	@Override
	public Flux<ObjectBoundary> searchbyAlias(String alias, String superApp, String userEmail) {
		
		return this.userCrud.findById(superApp + ":" + userEmail).flatMapMany(user -> {
			// check if he has permission to search objects (if his role is SUPERAPP_USER).
			if( (user.getRole().equals(Role.MINIAPP_USER)&&this.objectCrud.findAllByActiveIsTrue()!=null) ||user.getRole().equals(Role.SUPERAPP_USER)){
			
					// TODO add/edit what you want (start from here)
					return this.objectCrud.findByAlias(alias, superApp, userEmail).map(ObjectBoundary::new).log();
			}
				else {
					return Flux.error(new UnauthorizedAccess401("You dont have permission to get objects."));
				}
			
		}).switchIfEmpty(Flux.error(new NotFound404("User not found"))); // return a NotFound message if the user is not
		// in the database.
		
		
	}

	@Override
	public Flux<ObjectBoundary> searchbyAliasPattern(String pattern, String superApp, String userEmail) {
		return this.userCrud.findById(superApp + ":" + userEmail).flatMapMany(user -> {
			// check if he has permission to search objects (if his role is SUPERAPP_USER).
			if( (user.getRole().equals(Role.MINIAPP_USER)&&this.objectCrud.findAllByActiveIsTrue()!=null) ||user.getRole().equals(Role.SUPERAPP_USER)){
			
					// TODO add/edit what you want (start from here)
					return this.objectCrud.findAllByAliasLike(pattern, superApp, userEmail).map(ObjectBoundary::new).log();
			}
				

			else {
				return Flux.error(new UnauthorizedAccess401("You dont have permission to get objects."));
			}
		}).switchIfEmpty(Flux.error(new NotFound404("User not found"))); // return a NotFound message if the user is not
		// in the database.
		

	}
}

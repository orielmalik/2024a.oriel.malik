package demo.services;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import demo.ObjectId;
import demo.Role;
import demo.boundries.MiniAppCommandBoundary;
import demo.boundries.NewUserBoundary;
import demo.boundries.ObjectBoundary;
import demo.exception.BadRequest400;
import demo.exception.NotFound404;
import demo.exception.UnauthorizedAccess401;
import demo.interfaces.CommandExec;
import demo.interfaces.ObjectCrud;
import demo.interfaces.ObjectService;
import demo.interfaces.UserCrud;
import demo.interfaces.UserService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ObjectServiceImplementation implements ObjectService, CommandExec {

	private ObjectCrud objectCrud;
	private UserCrud userCrud;
	private UserService userService;
	private ApplicationContext applicationContext;

	@Value("${spring.application.name}")
	private String superAppName;

	@Value("${helper.delimiter}")
	private String delimiter;

	public ObjectServiceImplementation(ObjectCrud objectCrud, UserCrud userCrud, UserService userService,
			ApplicationContext applicationContext) {
		super();
		this.objectCrud = objectCrud;
		this.userCrud = userCrud;
		this.userService = userService;
		this.applicationContext = applicationContext;
	}

	@Override
	public Mono<ObjectBoundary> create(ObjectBoundary object) {
		if (object.getObjectId() == null) {
			object.setObjectId(new ObjectId());
		}
		object.getObjectId().setId(UUID.randomUUID().toString()).setSuperapp(superAppName);
		object.setCreationTimestamp(new Date());
		object.getCreatedBy().getUserId().setSuperapp(superAppName);

		// check if mandatory fields are null.
		if ((object.getType() == null || object.getType().isEmpty()) || object.getObjectDetails() == null
				|| object.getCreatedBy() == null || object.getCreatedBy().getUserId() == null
				|| object.getCreatedBy().getUserId().getEmail() == null || object.getAlias() == null
				|| object.getAlias().isEmpty()) {
			return Mono.error(() -> new BadRequest400("Some needed attribute are null"));
		}

		// if the type of the object is Dreamer (Dreamer is a user with more details).
		if (object.getType().equalsIgnoreCase("Dreamer")) {
			// Get data from objectDetails map
			String email = object.getCreatedBy().getUserId().getEmail();
			String username = object.getObjectDetails().containsKey("username")
					? (String) object.getObjectDetails().get("username")
					: null;
			String avatar = object.getObjectDetails().containsKey("avatar")
					? (String) object.getObjectDetails().get("avatar")
					: null;
			String location = object.getObjectDetails().containsKey("location")
					? (String) object.getObjectDetails().get("location")
					: null;
			Integer birthdate = object.getObjectDetails().containsKey("birthdate")
					? (Integer) object.getObjectDetails().get("birthdate")
					: null;
			String gender = object.getObjectDetails().containsKey("gender")
					? (String) object.getObjectDetails().get("gender")
					: null;
			String password = object.getObjectDetails().containsKey("password")
					? (String) object.getObjectDetails().get("password")
					: null;

			if (email == null || email.isEmpty() || username == null || username.isEmpty() || avatar == null
					|| avatar.isEmpty() || location == null || location.isEmpty() || birthdate == null || gender == null
					|| gender.isEmpty() || password == null || password.isEmpty()) {
				return Mono.error(() -> new BadRequest400("Some needed attribute are null or empty"));
			}

			// Create new UserBoundary
			NewUserBoundary nub = new NewUserBoundary();

			// Set NewUserBoundary
			nub.setEmail(email);
			nub.setRole(Role.SUPERAPP_USER);
			nub.setUsername(username);
			nub.setAvatar(avatar);

			return this.userService.create(nub)// create a new user with "nub" details.
					.onErrorResume(error -> { // if failed to create a new user
						return Mono.error(error); // return the occurred error.
					}).flatMap(createdUser -> { // if creating a new user succeeded.
						return Mono.just(object); // return the object boundary.
					}).map(ObjectBoundary::toEntity).flatMap(this.objectCrud::save)
					.map(entity -> new ObjectBoundary(entity)).log();
		}

		// if the type of the object is Counselor (Counselor is a user with more
		// details).
		else if (object.getType().equalsIgnoreCase("Counselor")) {
			// Get data from objectDetails map
			String email = object.getCreatedBy().getUserId().getEmail();
			String username = object.getObjectDetails().containsKey("username")
					? (String) object.getObjectDetails().get("username")
					: null;
			String avatar = object.getObjectDetails().containsKey("avatar")
					? (String) object.getObjectDetails().get("avatar")
					: null;
			Integer birthdate = object.getObjectDetails().containsKey("birthdate")
					? (Integer) object.getObjectDetails().get("birthdate")
					: null;
			String phoneNumber = object.getObjectDetails().containsKey("phoneNumber")
					? (String) object.getObjectDetails().get("phoneNumber")
					: null;
			Integer experience = object.getObjectDetails().containsKey("experience")
					? (Integer) object.getObjectDetails().get("experience")
					: null;
			String specialization = object.getObjectDetails().containsKey("specialization")
					? (String) object.getObjectDetails().get("specialization")
					: null;
			String password = object.getObjectDetails().containsKey("password")
					? (String) object.getObjectDetails().get("password")
					: null;
			String gender = object.getObjectDetails().containsKey("gender")
					? (String) object.getObjectDetails().get("gender")
					: null;

			if (email == null || email.isEmpty() || username == null || username.isEmpty() || avatar == null
					|| avatar.isEmpty() || birthdate == null || phoneNumber == null || phoneNumber.isEmpty()
					|| experience == null || specialization == null || specialization.isEmpty() || password == null
					|| password.isEmpty() || gender == null || gender.isEmpty()) {
				return Mono.error(() -> new BadRequest400("Some needed attribute are null or empty"));
			}

			// Create new UserBoundary
			NewUserBoundary nub = new NewUserBoundary();

			// Set NewUserBoundary
			nub.setEmail(email);
			nub.setRole(Role.MINIAPP_USER);
			nub.setUsername(username);
			nub.setAvatar(avatar);

			return this.userService.create(nub)// create a new user with "nub" details.
					.onErrorResume(error -> { // if failed to create a new user
						return Mono.error(error); // return the occurred error.
					}).flatMap(createdUser -> { // if creating a new user succeeded.
						return Mono.just(object); // return the object boundary.
					}).map(ObjectBoundary::toEntity).flatMap(this.objectCrud::save)
					.map(entity -> new ObjectBoundary(entity)).log();
		}

		// else, if another type of objects.
		else {
			return Mono.just(object).flatMap(boundary -> {
				return this.userCrud
						.findById(object.getCreatedBy().getUserId().getSuperapp() + delimiter
								+ object.getCreatedBy().getUserId().getEmail())
						.switchIfEmpty(Mono.error(new NotFound404("User not found."))).flatMap(user -> {
							if (user.getRole() != Role.SUPERAPP_USER) {
								return Mono
										.error(() -> new BadRequest400("You dont have permission to create object."));
							}
							return Mono.just(boundary);
						});
			}).map(ObjectBoundary::toEntity).flatMap(this.objectCrud::save).map(entity -> new ObjectBoundary(entity))
					.log();
		}
	}

	@Override
	public Mono<ObjectBoundary> getObject(String objectSuperapp, String id, String userSuperapp, String userEmail) {
		// find the user with this userSuperapp and userEmail.
		return this.userCrud.findById(userSuperapp + delimiter + userEmail)
				.switchIfEmpty(Mono.error(new NotFound404("User not found"))).flatMap(user -> {
					// check if he has permission to update objects (if his role is SUPERAPP_USER).
					if (user.getRole().equals(Role.SUPERAPP_USER)) {
						// return the object if found,
						// else return a NotFound message.
						return this.objectCrud.findById(objectSuperapp + delimiter + id)
								.switchIfEmpty(Mono.error(() -> new NotFound404("Object not found in database.")))
								.map(entity -> new ObjectBoundary(entity)).log();
					}
					// check if the user role is MINIAPP_USER.
					else if (user.getRole().equals(Role.MINIAPP_USER)) {
						// return the object if found and only if the Active attribute is True.
						// else return an empty Mono.
						return this.objectCrud.findByObjectIdAndActiveIsTrue(id).switchIfEmpty(Mono.empty())
								.map(entity -> new ObjectBoundary(entity)).log();
					}
					// other roles, return UnauthorizedAccess message.
					else {
						return Mono.error(() -> new UnauthorizedAccess401("You dont have permission to get objects."));
					}
				});
	}

	@Override
	public Flux<ObjectBoundary> getAllObjects(String userSuperapp, String userEmail) {
		// find the user with this userSuperapp and userEmail.
		return this.userCrud.findById(userSuperapp + ":" + userEmail)
				.switchIfEmpty(Mono.error(new NotFound404("User not found"))).flatMapMany(user -> {
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
				});
	}

	@Override
	public Mono<Void> updateObject(String objectSuperapp, String id, ObjectBoundary update, String userSuperapp,
			String userEmail) {
		// find the user with this userSuperapp and userEmail
		return this.userCrud.findById(userSuperapp + ":" + userEmail)
				.switchIfEmpty(Mono.error(new NotFound404("User not found"))).flatMap(user -> {
					// check if he has permission to update objects (if his role is SUPERAPP_USER).
					if (!user.getRole().equals(Role.SUPERAPP_USER)) {
						// return an UnauthorizedAccess message if the role is not SUPERAPP_USER.
						return Mono.error(() -> new UnauthorizedAccess401("You dont have permission to udpate."));
					} else {
						// update the needed object.
						return this.objectCrud.findById(objectSuperapp + delimiter + id).map(entity -> {
							entity.setActive(update.getActive());
							// check if type is null or empty string.
							if (update.getType() != null && update.getType() != "") {
								entity.setType(update.getType());
							}
							for (Object key : update.getObjectDetails().entrySet()) {
								Object value = update.getObjectDetails().get(key);
								if (value != null && value != "") {
									entity.getObjectDetails().put((String) key, value);
								}
							}

							// check if alias is null or empty string.
							if (update.getAlias() != null && update.getAlias() != "") {
								entity.setAlias(update.getAlias());
							}
							return entity;
						}).flatMap(this.objectCrud::save).map(ObjectBoundary::new).log().then();
					}

				});

	}
	
	@Override
	public Flux<MiniAppCommandBoundary> execute(MiniAppCommandBoundary input) {
		// get the command from the command name.
		// *THE COMMAND MUST BE GiveTips or Offers, else the execution will not work*
		// counselor-GiveTips / counselor-Offers
		String executionName = input.getCommand().substring(input.getCommand().indexOf('-') + 1);

		CommandExec command;
		try {
			// get the command bean.
			command = this.applicationContext.getBean(executionName, CommandExec.class);
		} catch (Exception e) {
			input.getCommandAttributes().put("result", "There is no service name like this.");
			return Flux.just(input);
		}
		// execute the command.
		return command.execute(input);
	}

	@Override
	public Flux<ObjectBoundary> searchbyType(String type, String superApp, String userEmail) {

		return this.userCrud.findById(superApp + ":" + userEmail).flatMapMany(user -> {
			// Check if the user exists
			if (user != null) {
				// Check if the user is a MINIAPP_USER
				if (user.getRole().equals(Role.MINIAPP_USER)) {
					// Miniapp users can only search for objects associated with their miniapp
					return this.objectCrud.findAllByTypeAndActiveIsTrue(type).map(ObjectBoundary::new).log();
				}
				// Check if the user is a SUPERAPP_USER
				else if (user.getRole().equals(Role.SUPERAPP_USER)) {
					// Superapp users can search for all objects
					return this.objectCrud.findAllByType(type).map(ObjectBoundary::new).log();
				} else {
					// Unauthorized access for other roles
					return Flux.error(new UnauthorizedAccess401("You don't have permission to get objects."));
				}
			} else {
				// Return a NotFound error if the user is not found
				return Flux.error(new NotFound404("User not found"));
			}
		});
	}

	@Override
	public Flux<ObjectBoundary> searchbyAlias(String alias, String superApp, String userEmail) {
		return this.userCrud.findById(superApp + ":" + userEmail).flatMapMany(user -> {
			// Check if the user exists
			if (user != null) {
				// Check if the user is a MINIAPP_USER
				if (user.getRole().equals(Role.MINIAPP_USER)) {
					// Miniapp users can only search for objects associated with their miniapp
					return this.objectCrud.findAllByAliasAndActiveIsTrue(alias).map(ObjectBoundary::new).log();
				}
				// Check if the user is a SUPERAPP_USER
				else if (user.getRole().equals(Role.SUPERAPP_USER)) {
					// Superapp users can search for all objects
					return this.objectCrud.findAllByAlias(alias).map(ObjectBoundary::new).log();
				} else {
					// Unauthorized access for other roles
					return Flux.error(new UnauthorizedAccess401("You don't have permission to get objects."));
				}
			} else {
				// Return a NotFound error if the user is not found
				return Flux.error(new NotFound404("User not found"));
			}
		});
	}

	@Override
	public Flux<ObjectBoundary> searchbyAliasPattern(String pattern, String superApp, String userEmail) {
		return this.userCrud.findById(superApp + ":" + userEmail).flatMapMany(user -> {
			// Check if the user exists
			if (user != null) {
				// Check if the user is a MINIAPP_USER
				if (user.getRole().equals(Role.MINIAPP_USER)) {
					// Miniapp users can only search for objects associated with their miniapp
					return this.objectCrud.findAllByActiveIsTrueAndAliasLike("" + pattern + "").map(ObjectBoundary::new)
							.log();
				}
				// Check if the user is a SUPERAPP_USER
				else if (user.getRole().equals(Role.SUPERAPP_USER)) {
					// Superapp users can search for all objects
					return this.objectCrud.findAllByActiveIsTrueAndAliasLike(pattern).map(ObjectBoundary::new).log();
				} else {
					// Unauthorized access for other roles
					return Flux.error(new UnauthorizedAccess401("You don't have permission to get objects."));
				}
			} else {
				// Return a NotFound error if the user is not found
				return Flux.error(new NotFound404("User not found"));
			}
		});
	}

	

}

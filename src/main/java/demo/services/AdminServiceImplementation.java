package demo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import demo.Role;
import demo.boundries.MiniAppCommandBoundary;
import demo.boundries.UserBoundary;
import demo.exception.BadRequest400;
import demo.exception.NotFound404;
import demo.exception.UnauthorizedAccess401;
import demo.interfaces.AdminService;
import demo.interfaces.MiniAppCommandCrud;
import demo.interfaces.ObjectCrud;
import demo.interfaces.UserCrud;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AdminServiceImplementation implements AdminService {

	private UserCrud userCrud;
	private ObjectCrud objectCrud;
	private MiniAppCommandCrud miniAppCommandCrud;

	@Value("${helper.delimiter}")
	String delimiter;

	public AdminServiceImplementation(UserCrud userCrud, ObjectCrud objectCrud, MiniAppCommandCrud miniAppCommandCrud) {
		super();
		this.userCrud = userCrud;
		this.objectCrud = objectCrud;
		this.miniAppCommandCrud = miniAppCommandCrud;
	}

	@Override
	public Mono<Void> deleteAllUsers(String superappName, String email) {
		if (superappName == null || superappName == "")
			return Mono.error(() -> new BadRequest400("superappName attribute must not be null or empty"));

		if (email == null || email == "")
			return Mono.error(() -> new BadRequest400("email attribute must not be null or empty"));

		return userCrud.findById(superappName + delimiter + email)
				.switchIfEmpty(Mono.error(new NotFound404("User not found"))).flatMap(entity -> {
					if (entity.getRole() == Role.ADMIN)
						return this.userCrud.deleteAll();
					else
						return Mono.error(() -> new UnauthorizedAccess401("You dont have permission to delete users"));
				});
	}

	@Override
	public Mono<Void> deleteAllObjects(String superappName, String email) {
		if (superappName == null || superappName == "")
			return Mono.error(() -> new BadRequest400("superappName attribute must not be null or empty"));

		if (email == null || email == "")
			return Mono.error(() -> new BadRequest400("email attribute must not be null or empty"));

		return userCrud.findById(superappName + delimiter + email)
				.switchIfEmpty(Mono.error(new NotFound404("User not found"))).flatMap(entity -> {
					if (entity.getRole() == Role.ADMIN)
						return this.objectCrud.deleteAll().log();
					else
						return Mono
								.error(() -> new UnauthorizedAccess401("You dont have permission to delete objects"));
				});
	}

	@Override
	public Mono<Void> delteAllCommands(String superappName, String email) {
		if (superappName == null || superappName == "")
			return Mono.error(() -> new BadRequest400("superappName attribute must not be null or empty"));

		if (email == null || email == "")
			return Mono.error(() -> new BadRequest400("email attribute must not be null or empty"));

		return userCrud.findById(superappName + delimiter + email)
				.switchIfEmpty(Mono.error(new NotFound404("User not found"))).flatMap(entity -> {
					if (entity.getRole() == Role.ADMIN)
						return this.miniAppCommandCrud.deleteAll();
					else
						return Mono
								.error(() -> new UnauthorizedAccess401("You dont have permission to delete commands"));
				});
	}

	@Override
	public Flux<UserBoundary> fetchAllUsers(String superappName, String email) {
		if (superappName == null || superappName == "")
			return Flux.error(() -> new BadRequest400("superappName attribute must not be null or empty"));

		if (email == null || email == "")
			return Flux.error(() -> new BadRequest400("email attribute must not be null or empty"));

		return userCrud.findById(superappName + delimiter + email)
				.switchIfEmpty(Mono.error(() -> new NotFound404("Users not found"))).flatMapMany(entity -> {
					if (entity.getRole() == Role.ADMIN)
						return this.userCrud.findAll().map(UserBoundary::new);
					else
						return Mono
								.error(() -> new UnauthorizedAccess401("You dont have permission to fetch users data"));
				});
	}

	@Override
	public Flux<MiniAppCommandBoundary> fetchAllMiniappCommands(String superappName, String email) {
		if (superappName == null || superappName == "")
			return Flux.error(() -> new BadRequest400("superappName attribute must not be null or empty"));

		if (email == null || email == "")
			return Flux.error(() -> new BadRequest400("email attribute must not be null or empty"));

		return userCrud.findById(superappName + delimiter + email).flatMapMany(entity -> {
			if (entity.getRole() == Role.ADMIN)
				return this.miniAppCommandCrud.findAll().map(MiniAppCommandBoundary::new);
			else
				return Mono.error(() -> new UnauthorizedAccess401("You dont have permission to fetch commands data"));
		}).switchIfEmpty(Flux.empty()).log();
	}

	@Override
	public Flux<MiniAppCommandBoundary> fetchMiniappCommand(String superappName, String email, String miniAppName) {
		if (superappName == null || superappName == "")
			return Flux.error(() -> new BadRequest400("superappName attribute must not be null or empty"));

		if (email == null || email == "")
			return Flux.error(() -> new BadRequest400("email attribute must not be null or empty"));

		return userCrud.findById(superappName + delimiter + email).flatMapMany(entity -> {
			if (entity.getRole() == Role.ADMIN)
				return this.miniAppCommandCrud.findAllByCommandIdLike("*" + miniAppName + "*")
						.map(MiniAppCommandBoundary::new);
			else
				return Mono.error(() -> new UnauthorizedAccess401("You dont have permission to fetch commands data"));
		}).switchIfEmpty(Flux.empty()).log();
	}
}

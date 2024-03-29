package demo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import demo.ValidateEmail;
import demo.boundries.NewUserBoundary;
import demo.boundries.UserBoundary;
import demo.entities.UserId;
import demo.exception.BadRequest400;
import demo.exception.NotFound404;
import demo.interfaces.UserCrud;
import demo.interfaces.UserService;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImplementation implements UserService {

	private UserCrud userCrud;

	@Value("${spring.application.name}")
	private String suparappName;

	@Value("${helper.delimiter}")
	private String delimiter;

	public UserServiceImplementation(UserCrud userCrud) {
		super();
		this.userCrud = userCrud;
	}

	@Override
	public Mono<UserBoundary> create(NewUserBoundary user) {
		if (user.getEmail() == null || user.getEmail() == "")
			return Mono.error(() -> new BadRequest400("Email cant be null or empty string"));

		if (!ValidateEmail.isValidPattern(user.getEmail())) {
			return Mono.error(() -> new BadRequest400("Email pattern is not valid."));
		}

		if (user.getAvatar() == null || user.getAvatar() == "") {
			return Mono.error(() -> new BadRequest400("Avatar cant be null or empty string."));
		}

		if (user.getRole() == null)
			return Mono.error(() -> new BadRequest400("Role cant be null"));

		if (user.getUsername() == null || user.getUsername() == "")
			return Mono.error(() -> new BadRequest400("Username cant be null or empty string"));

		// check if the user's email is already in use.
		return this.userCrud.existsByEmail(user.getEmail()).flatMap(exists -> {
			if (exists) { // if used
				return Mono.error(new BadRequest400("This email is already used.")); // return an error.
			} else {// else, save the user in the database.
				UserBoundary ub = new UserBoundary();
				ub.setUserId(new UserId(suparappName, user.getEmail())).setRole(user.getRole())
						.setUsername(user.getUsername()).setAvatar(user.getAvatar());
				return this.userCrud.save(ub.toEntity()).map(UserBoundary::new);
			}
		}).log();

	}

	@Override
	public Mono<UserBoundary> login(String superapp, String email) {
		if (superapp == null || superapp == "")
			return Mono.error(() -> new BadRequest400("Superapp cant be null or empty string"));

		if (email == null || email == "")
			return Mono.error(() -> new BadRequest400("Email cant be null or empty string"));

		return this.userCrud.findById(superapp + delimiter + email).map(UserBoundary::new)
				.switchIfEmpty(Mono.error(() -> new NotFound404("User not found."))).log();
	}

	@Override
	public Mono<Void> update(String superapp, String email, UserBoundary user) {
		if (superapp == null || superapp == "")
			return Mono.error(() -> new BadRequest400("Superapp cant be null or empty string"));

		if (email == null || email == "")
			return Mono.error(() -> new BadRequest400("Email cant be null or empty string"));

		if (user.getRole() == null)
			return Mono.error(() -> new BadRequest400("Role cant be null"));

		if (user.getUsername() == null || user.getUsername() == "")
			return Mono.error(() -> new BadRequest400("Username cant be null or empty string"));

		return this.userCrud.findById(superapp + delimiter + email).map(entity -> {
			entity.setUsername(user.getUsername());
			entity.setRole(user.getRole());
			entity.setAvatar(user.getAvatar());
			return entity;
		}).flatMap(this.userCrud::save).map(UserBoundary::new).log().then();
	}
}

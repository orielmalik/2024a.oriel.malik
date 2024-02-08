package demo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import demo.boundries.NewUserBoundary;
import demo.boundries.UserBoundary;
import demo.interfaces.UserCrud;
import demo.interfaces.UserService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImplementation implements UserService{

	private UserCrud userCrud;
	
	@Value("${spring.application.name}")
    private String suparappName;
	
	
	public UserServiceImplementation(UserCrud userCrud) {
		super();
		this.userCrud = userCrud;
	}
	
	@Override
	public Mono<NewUserBoundary> create(NewUserBoundary user) {
		if (user.getEmail() == null)
			return Mono.empty();
		user.setId(suparappName + ":" + user.getEmail());
		return this.userCrud
				.save(user.toEntity())
				.map(NewUserBoundary::new)
				.log();
	}

	@Override
	public Mono<UserBoundary> login(String superapp, String email) {
		if (superapp == null || email == null)
			return Mono.empty();
        return this.userCrud
        	.findById(superapp + ":" + email)
        	.map(UserBoundary::new)
        	.log();
	}	

	@Override
	public Mono<Void> update(String superapp, String email, UserBoundary user) {
		if (superapp == null || email == null)
			return Mono.empty();
		return this.userCrud
			.findById(superapp + ":" + email)
			.map(entity -> {
				entity.setUserName(user.getUserName());
				entity.setRole(user.getRole());
				entity.setAvatar(user.getAvatar());
				return entity;
			})
			.flatMap(this.userCrud::save)
			.map(UserBoundary::new)
			.log()
			.then();
	}
}

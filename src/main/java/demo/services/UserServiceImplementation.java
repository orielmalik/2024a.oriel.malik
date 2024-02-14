package demo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import demo.boundries.NewUserBoundary;
import demo.boundries.UserBoundary;
import demo.entities.UserId;
import demo.interfaces.UserCrud;
import demo.interfaces.UserService;
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
	public Mono<UserBoundary> create(NewUserBoundary user) {
		UserBoundary ub = new UserBoundary();
		UserId id = new UserId(suparappName, user.getEmail());
		ub.setUserId(id)
			.setEmail(user.getEmail())
			.setRole(user.getRole())
			.setUserName(user.getUserName())
			.setAvatar(user.getAvatar());
		
		return this.userCrud
				.save(ub.toEntity())
				.map(UserBoundary::new)
				.log();
	}

	@Override
	public Mono<UserBoundary> login(String superapp, String email) {
        return this.userCrud
        	.findById(superapp + ":" + email)
        	.map(UserBoundary::new)
        	.log();
	}	

	@Override
	public Mono<Void> update(String superapp, String email, UserBoundary user) {
		System.out.println("check");
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

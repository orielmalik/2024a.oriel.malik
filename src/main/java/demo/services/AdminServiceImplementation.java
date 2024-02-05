package demo.services;

import org.springframework.stereotype.Service;

import demo.boundries.UserBoundary;
import demo.interfaces.AdminService;
import demo.interfaces.UserCrud;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AdminServiceImplementation implements AdminService{
	
	private UserCrud userCrud;
//	private ObjectCrud objectCrud;
//	private CommandCrud commandCrud;
	
	public AdminServiceImplementation(UserCrud userCrud) {
		super();
		this.userCrud = userCrud;
//		this.objectCrud = objectCrud;
//		this.commandCrud = commandCrud;
	}

	@Override
	public Mono<Void> deleteAllUsers() {
		return this.userCrud.deleteAll().log();
	}

	@Override
	public Flux<UserBoundary> fetchAllUsers() {
		return this.userCrud
				.findAll()
				.map(UserBoundary::new)
				.log();
	}
}

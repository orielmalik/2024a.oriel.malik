package demo.services;

import org.springframework.stereotype.Service;

import demo.boundries.MiniAppCommandBoundary;
import demo.boundries.ObjectBoundary;
import demo.boundries.UserBoundary;
import demo.interfaces.AdminService;
import demo.interfaces.MiniAppCommandCrud;
import demo.interfaces.ObjectCrud;
import demo.interfaces.UserCrud;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AdminServiceImplementation implements AdminService{
	
	private UserCrud userCrud;
	private ObjectCrud objectCrud;
	private MiniAppCommandCrud miniAppCommandCrud;
	
	public AdminServiceImplementation(UserCrud userCrud, ObjectCrud objectCrud, MiniAppCommandCrud miniAppCommandCrud) {
		super();
		this.userCrud = userCrud;
		this.objectCrud = objectCrud;
		this.miniAppCommandCrud = miniAppCommandCrud;
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

	@Override
	public Mono<Void> deleteAllObjects() {
		return this.objectCrud.deleteAll().log();
	}

	@Override
	public Flux<ObjectBoundary> fetchAllObjects() {
		return this.objectCrud
			.findAll()
			.map(ObjectBoundary::new)
			.log();
	}

	@Override
	public Mono<Void> delteAllCommands() {
		this.miniAppCommandCrud.deleteAll().log();
		return null;
	}

//	@Override
//	public Flux<MiniAppCommandBoundary> fetchAllCommands() {
//		return this.miniAppCommandCrud
//			.findAll()
//			.map(MiniAppCommandBoundary::new)
//			.log();
//	}
//	
	
}

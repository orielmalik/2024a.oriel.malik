package demo;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImplementation implements UserService{
	
	private UserCrud userCrud;
	 @Autowired
	 private ReactiveMongoTemplate reactiveMongoTemplate;
	
	public UserServiceImplementation(UserCrud userCrud) {
		super();
		this.userCrud = userCrud;
	}

	@Override
	public Mono<UserBoundary> create(UserBoundary user) {
		user.setId(UUID.randomUUID().toString());
		user.setCreatedAt(new Date());
		return this.userCrud
			.save(user.toEntity())
			.map(UserBoundary::new)	// = entity -> new UserBoundary(entity)
			.log();
	}

	@Override
	public Mono<UserBoundary> login(String email) {
        Query query = new Query(Criteria.where("email").is(email));
        return this.reactiveMongoTemplate
        	.findOne(query, UserEntity.class)
        	.map(UserBoundary::new)
        	.log();
	}	
	
	@Override
	public Flux<UserBoundary> getAll() {
		return this.userCrud
			.findAll()
			.map(entity -> new UserBoundary(entity))
			.log();
	}

	@Override
	public Mono<Void> update(String id, UserBoundary user) {
		return this.userCrud
			.findById(id)
			.map(entity -> {
				entity.setUserName(user.getUserName());
				entity.setEmail(user.getUserName());
				entity.setPhoneNumber(user.getPhoneNumber());
				entity.setStatus(user.getStatus());
				entity.setGender(user.getGender());
				entity.setBirthDate(user.getBirthDate());
				entity.setLocation(user.getLocation());
				entity.setRole(user.getRole());
				return entity;
			})
			.flatMap(this.userCrud::save)
			.map(UserBoundary::new)
			.log()
			.then();
	}

	@Override
	public Mono<Void> deleteAll() {
		return this.userCrud.deleteAll().log();
	}
}

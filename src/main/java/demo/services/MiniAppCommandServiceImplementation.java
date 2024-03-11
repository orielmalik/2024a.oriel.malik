package demo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import demo.CommandId;
import demo.Role;
import demo.boundries.MiniAppCommandBoundary;
import demo.boundries.ObjectBoundary;
import demo.entities.ObjectEntity;
import demo.exception.BadRequest400;
import demo.exception.NotFound404;
import demo.exception.UnauthorizedAccess401;
import demo.interfaces.MiniAppCommandCrud;
import demo.interfaces.MiniAppCommandSevice;
import demo.interfaces.ObjectCrud;
import demo.interfaces.ObjectService;
import demo.interfaces.UserCrud;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MiniAppCommandServiceImplementation implements MiniAppCommandSevice {

	private MiniAppCommandCrud commandCrud;
	private UserCrud userCrud;
	private ObjectCrud objectCrud;
	private ObjectService objectService;

	@Value("${spring.application.name}")
	private String suparappName;

	@Value("${helper.delimiter}")
	private String delimiter;

	public MiniAppCommandServiceImplementation(MiniAppCommandCrud commandCrud, UserCrud usercrud, ObjectCrud objectCrud,
			ObjectService objectService) {
		super();
		this.commandCrud = commandCrud;
		this.userCrud = usercrud;
		this.objectCrud = objectCrud;
		this.objectService = objectService;
	}

	@Override
	public Flux<MiniAppCommandBoundary> invoke(MiniAppCommandBoundary command, String miniAppName) {

		if (miniAppName == null || miniAppName.isEmpty() || command.getCommand() == null
				|| command.getCommand().isEmpty()) {
			return Flux.error(new BadRequest400("MINI APP OR COMMAND NAME IS NULL"));
		}
		CommandId comma = new CommandId(suparappName, miniAppName, UUID.randomUUID().toString());
		command.setCommandId(comma);

		command.setInvocationTimestamp(new Date());

		return Flux.just(command).flatMap(commandBoundary -> {

			return this.userCrud // Check if the user exists
					.findById(command.getInvokedBy().getUserId().getSuperapp() + ":"
							+ command.getInvokedBy().getUserId().getEmail())
					.switchIfEmpty(Mono.error(new NotFound404("User not found."))).flatMapMany(user -> {

						// Check if the user is a MINIAPP_USER
						if (user.getRole().equals(Role.MINIAPP_USER)) {
							// target object id.
							String targetObjectid = command.getTargetObject().getObjectId().getSuperapp() + ":"
									+ command.getTargetObject().getObjectId().getId();
							// check if the object is found
							return this.objectCrud.findByObjectIdAndActiveIsTrue(targetObjectid)
									.switchIfEmpty(Mono.error(new NotFound404("Object not found")))
									.flatMapMany(targetObject -> {

										return switchPosition(commandBoundary);

									});

						}
						// return error if the user role is not MINIAPP_USER.
						return Flux.error(new UnauthorizedAccess401("You dont have permission to invoke command."));

					});
		}).map(MiniAppCommandBoundary::toEntity).flatMap(this.commandCrud::save)
				.map(entity -> new MiniAppCommandBoundary(entity)).log();

	}

	public void marketObject(Mono<ObjectEntity> objectMono, String a) {
		objectMono.subscribe(objectEntity -> {
			objectEntity.getObjectDetails().put("searched", a);
			this.objectCrud.save(objectEntity);
		});
	}

	private Flux<MiniAppCommandBoundary> switchPosition(MiniAppCommandBoundary m) {
		String tar = m.getCommand().substring(0, m.getCommand().indexOf('-'));// format :name of command from start
																				// index to -
		switch (tar) {
		case ("search"):
			SearchByCriteriaCommand searchCommand = new SearchByCriteriaCommand(this.objectCrud, m);
			return searchCommand.execute().flatMap(objectEntity -> {
				return updateObjectEntinty(objectEntity.getObjectId(), m.getTargetObject().getObjectId().getId(), 0)
						.then(updateObjectEntinty(m.getTargetObject().getObjectId().getId(), objectEntity.getObjectId(),
								1))
						.thenMany(Flux.just(m)).log();

			});
		case ("meet"):
			break;
		case ("counselor"):
			return this.objectService.execute(m);
		default:

			return Flux.error(new BadRequest400("Your error request "));

		}
		return Flux.error(new BadRequest400("Your error request "));

	}

	private Mono<Void> updateObjectEntinty(String objectEntityID, String TargetObjectID, int mode) {
		return this.objectCrud.findById(objectEntityID).map(entity -> {
			// System.err.println(entity);
			ArrayList<String> lst;

			try {
				if (entity.getObjectDetails() != null) {
					if ((mode == 0)) {
						initlst("views", entity);

						lst = (ArrayList<String>) entity.getObjectDetails().get("views");
						lst.add(TargetObjectID);
						entity.getObjectDetails().put("views", lst);
					} else if (mode == 1) {
						initlst("seen", entity);

						lst = (ArrayList<String>) entity.getObjectDetails().get("seen");
						lst.add(TargetObjectID);
						entity.getObjectDetails().put("seen", lst);

					} else {

					}
				}
			} catch (NullPointerException n) {
				System.err.println(n.toString());
			}

			return entity;
		}).flatMap(this.objectCrud::save)
				// .map(entity->new MessageBoundary(entity))
				.map(ObjectBoundary::new).then();

	}

	private void initlst(String key, ObjectEntity o) {
		if (o.getObjectDetails().get(key) == null) {
			o.getObjectDetails().put(key, new ArrayList<String>());
		}
	}

}

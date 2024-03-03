package demo.services;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import demo.CommandId;
import demo.Role;
import demo.boundries.MiniAppCommandBoundary;
import demo.boundries.ObjectBoundary;
import demo.exception.BadRequest400;
import demo.exception.NotFound404;
import demo.exception.UnauthorizedAccess401;
import demo.interfaces.MiniAppCommandCrud;
import demo.interfaces.MiniAppCommandSevice;
import demo.interfaces.ObjectCrud;
import demo.interfaces.UserCrud;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MiniAppCommandServiceImplementation implements MiniAppCommandSevice {

	private MiniAppCommandCrud commandCrud;
	private UserCrud userCrud;
	private ObjectCrud objectCrud;

	@Value("${spring.application.name}")
	private String suparappName;

	@Value("${helper.delimiter}")
	private String delimiter;

	public MiniAppCommandServiceImplementation(MiniAppCommandCrud commandCrud, UserCrud usercrud,
			ObjectCrud objectCrud) {
		super();
		this.commandCrud = commandCrud;
		this.userCrud = usercrud;
		this.objectCrud = objectCrud;
	}

	@Override
	public Flux<MiniAppCommandBoundary> invoke(MiniAppCommandBoundary command, String miniAppName) {

		if (miniAppName.isEmpty()|| command.getCommand() == null || command.getCommand().isEmpty()) {
			return Flux.error(new BadRequest400("MINI APP OR COMMAND NAME  IS NULL"));
		}
		CommandId comma = new CommandId(suparappName, miniAppName, UUID.randomUUID().toString());
		command.setCommandId(comma);

		command.setInvocationTimestamp(new Date());
		System.err.println(command.toString());
		return this.userCrud
				.findById(command.getInvokedBy().getUserId().getSuperapp() + ":"
						+ command.getInvokedBy().getUserId().getEmail())
				.switchIfEmpty(Mono.error(new NotFound404("i dont know this user"))).flatMapMany(user -> {
					// Check if the user exists
					// Check if the user is a MINIAPP_USER

					if (user.getRole().equals(Role.MINIAPP_USER) && user != null) {
						String id = command.getTargetObject().getObjectId().getSuperapp() + ":"
								+ command.getTargetObject().getObjectId().getId();
						 this.objectCrud.findByObjectIdAndActiveIsTrue(id)
								.switchIfEmpty(Mono.error(new NotFound404("i dont know this object")));
								
					}

				else {
						return Flux.error(new UnauthorizedAccess401("You dont have permission to invoke."));
					}
				}).map(MiniAppCommandBoundary::toEntity).flatMapMany(this.commandCrud::save).map(entity -> new MiniAppCommandBoundary(entity)).log();;
	}

}

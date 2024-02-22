package demo.services;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import demo.Role;
import demo.boundries.MiniAppCommandBoundary;
import demo.exception.NotFound404;
import demo.interfaces.MiniAppCommandCrud;
import demo.interfaces.MiniAppCommandSevice;
import demo.interfaces.ObjectCrud;
import demo.interfaces.UserCrud;
import reactor.core.publisher.Flux;

@Service
public class MiniAppCommandServiceImplementation implements MiniAppCommandSevice {

	private MiniAppCommandCrud commandCrud;
	private UserCrud userCrud;
	private ObjectCrud objectCrud;

	@Value("${spring.application.name}")
	private String suparappName;

	@Value("${helper.delimiter}")
	private String delimiter;

	public MiniAppCommandServiceImplementation(MiniAppCommandCrud commandCrud) {
		super();
		this.commandCrud = commandCrud;
	}

	@Override
	public Flux<MiniAppCommandBoundary> invoke(MiniAppCommandBoundary command, String miniAppName) {
		command.getCommandId().setId(suparappName).setMiniapp(miniAppName).setId(UUID.randomUUID().toString());
		command.setInvocationTimestamp(new Date());
		this.commandCrud.save(command.toEntity());
		return this.userCrud.findById(command.getInvokedBy().getSuperapp() + ":" + command.getInvokedBy().getEmail())
				.flatMapMany(user -> {
					// Check if the user exists
					// Check if the user is a MINIAPP_USER
					if (user.getRole().equals(Role.MINIAPP_USER) && user != null) {
						if (objectCrud.findByObjectIdAndActiveIsTrue(command.getTargetObject().getId()) == null) {
							return Flux.error(new NotFound404(" not found"));

						}

						return this.commandCrud.findAll().map(MiniAppCommandBoundary::new).log();
					}

				else {
						return Flux.empty();
					}

				});
	}
}

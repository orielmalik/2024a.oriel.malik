package demo.services;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import demo.boundries.MiniAppCommandBoundary;
import demo.interfaces.MiniAppCommandCrud;
import demo.interfaces.MiniAppCommandSevice;
import reactor.core.publisher.Flux;

@Service
public class MiniAppCommandServiceImplementation implements MiniAppCommandSevice {
	
	private MiniAppCommandCrud commandCrud;
	
	@Value("${spring.application.name}")
    private String suparappName;

	public MiniAppCommandServiceImplementation(MiniAppCommandCrud commandCrud) {
		super();
		this.commandCrud = commandCrud;
	}
	
	@Override
	public Flux<MiniAppCommandBoundary> invoke(MiniAppCommandBoundary command, String miniAppName) {
		command.getCommandId()
			.setId(suparappName)
			.setMiniapp(miniAppName)
			.setId(UUID.randomUUID().toString());
		command.setInvocationTimestamp(new Date());
		
		this.commandCrud
			.save(command.toEntity()).map(MiniAppCommandBoundary::new).log();
		return this.commandCrud
				.findAll()
				.map(MiniAppCommandBoundary::new)
				.log();
	}

}

package demo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import demo.CommandId;
import demo.Role;
import demo.boundries.MiniAppCommandBoundary;
import demo.boundries.ObjectBoundary;
import demo.entities.ObjectEntity;
import demo.exception.BadRequest400;
import demo.exception.NotFound404;
import demo.exception.UnauthorizedAccess401;
import demo.interfaces.GeneralCommand;
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

		if (miniAppName == null || miniAppName.isEmpty() || command.getCommand() == null
				|| command.getCommand().isEmpty()) {
			return Flux.error(new BadRequest400("MINI APP OR COMMAND NAME  IS NULL"));
		}
		CommandId comma = new CommandId(suparappName, miniAppName, UUID.randomUUID().toString());
		command.setCommandId(comma);

		command.setInvocationTimestamp(new Date());
		// System.err.println(command.toString());

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
										// return the commandBoundary if the user and target object is valid.
										/*commandBoundary.setCommand("searchByUserName");
										Map<String,Object>m=new HashMap<>();
										m.put("UserName", "gay");
										SearchByCriteriaCommand c=new SearchByCriteriaCommand(this.objectCrud,commandBoundary) ;*/
									
									
										switchPosition(commandBoundary);
										return Flux.just(commandBoundary);
									});

						}
						// return error if the user role is not MINIAPP_USER.
						return Flux.error(new UnauthorizedAccess401("You dont have permission to invoke command."));

					});
		}).map(MiniAppCommandBoundary::toEntity).flatMap(this.commandCrud::save)
				.map(entity -> new MiniAppCommandBoundary(entity)).log();

	}
	private void switchPosition(MiniAppCommandBoundary m)
	{
		
		String tar=m.getCommand().substring(0, m.getCommand().indexOf('-'));//format :name of  command from start index to -
		switch(tar) {
		case ("search"):

			SearchByCriteriaCommand searchCommand=new SearchByCriteriaCommand(this.objectCrud,m) ;
		searchCommand.execute().subscribe(objectEntity -> {
		    // 
			m.getCommandAttributes().put("results", 	objectEntity.getAlias());//to check results
//update-findById-map
			
			try {
			ArrayList<String>lst= (ArrayList<String>) objectEntity.getObjectDetails().get("views");
			lst.add(objectEntity.getObjectId()+":"+objectEntity.getAlias());
			m.getCommandAttributes().put("views", 	lst);//to check results
			objectEntity.getObjectDetails().put("viewscount ", ((ArrayList<String>) objectEntity.getObjectDetails().get("views")).size());
			ObjectEntity object=findTargetObject(m);
			object.getViews().add(objectEntity.getObjectId());
			this.objectCrud.save(objectEntity);			
		} catch (NullPointerException e) {
			System.err.println("Key not found!");
		    
		}
			
		});
		break;
		case ("meet"):
RequestMeetingCommand  RequestMeetingCommand=new RequestMeetingCommand(m,this.objectCrud);
	default:
		
		System.err.println("bb");

		}
	}
	private ObjectEntity findTargetObject(MiniAppCommandBoundary miniAppCommandBoundary)
	{
		String oId =miniAppCommandBoundary.getTargetObject().getObjectId().getSuperapp()+":"+miniAppCommandBoundary.getTargetObject().getObjectId().getId();
		this.objectCrud.findById(oId).map(object->{
			return object;
		});
		return null;
	}

}

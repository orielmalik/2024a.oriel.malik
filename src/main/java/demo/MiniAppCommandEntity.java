package demo;

import java.util.Date;
import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "Comannd")

public class MiniAppCommandEntity {

	private CommandId commandId;
	private UserId invokedBy;
	private ObjectIdBoundary TargetObject;
	private String TargetCommand;//command-"do something"
	private Date invocationTimeStamp;
	private Map<String,Object> commandAttributes;
	
	
	public MiniAppCommandEntity() {}
	

	public CommandId getCommandId() {
		return commandId;
	}
	
	public void setCommandId(CommandId commandId) {
		this.commandId = commandId;
	}
	public UserId getUserid() {
		return invokedBy;
	}
	public void setUserid(UserId userid) {
		invokedBy = userid;
	}
	public ObjectIdBoundary getObjectId() {
		return TargetObject;
	}
	public void setObjectId(ObjectIdBoundary objectId) {
		TargetObject = objectId;
	}

	public String getTargetCommand() {
		return TargetCommand;
	}

	public void setTargetCommand(String objectIdBoundary) {
		TargetCommand = objectIdBoundary;
	}


	public Date getInvocationTimeStamp() {
		return invocationTimeStamp;
	}


	public void setInvocationTimeStamp(Date invocationTimeStamp) {
		this.invocationTimeStamp = invocationTimeStamp;
	}


	public Map<String,Object> getCommandAttributes() {
		return commandAttributes;
	}


	public void setCommandAttributes(Map<String,Object> commandAttributes) {
		this.commandAttributes = commandAttributes;
	}



	
	
	
	
}

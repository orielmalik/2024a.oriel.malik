package demo.entities;

import java.util.Date;
import java.util.Map;
import org.springframework.data.mongodb.core.mapping.Document;

import demo.CommandId;
import demo.ObjectId;
@Document(collection = "Comannd")

public class MiniAppCommandEntity {

	private CommandId commandId;
	private UserId invokedBy;
	private ObjectId TargetObject;
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
	public ObjectId getObjectId() {
		return TargetObject;
	}
	public void setObjectId(ObjectId objectId) {
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

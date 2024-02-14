package demo.entities;

import java.util.Date;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import demo.ObjectId;

@Document(collection = "Commands")
public class MiniAppCommandEntity {
	@Id String commandId;
	private String command;
	private ObjectId targetObject;
	private Date invocationTimestamp;
	private UserId invokedBy;
	private Map<String,Object> commandAttributes;
	
	public MiniAppCommandEntity() {
	}

	public String getCommandId() {
		return commandId;
	}

	public MiniAppCommandEntity setCommandId(String commandId) {
		this.commandId = commandId;
		return this;
	}

	public String getCommand() {
		return command;
	}

	public MiniAppCommandEntity setCommand(String command) {
		this.command = command;
		return this;
	}

	public ObjectId getTargetObject() {
		return targetObject;
	}

	public MiniAppCommandEntity setTargetObject(ObjectId targetObject) {
		this.targetObject = targetObject;
		return this;
	}

	public Date getInvocationTimestamp() {
		return invocationTimestamp;
	}

	public MiniAppCommandEntity setInvocationTimestamp(Date invocationTimestamp) {
		this.invocationTimestamp = invocationTimestamp;
		return this;
	}

	public UserId getInvokedBy() {
		return invokedBy;
	}

	public MiniAppCommandEntity setInvokedBy(UserId invokedBy) {
		this.invokedBy = invokedBy;
		return this;
	}

	public Map<String, Object> getCommandAttributes() {
		return commandAttributes;
	}

	public MiniAppCommandEntity setCommandAttributes(Map<String, Object> commandAttributes) {
		this.commandAttributes = commandAttributes;
		return this;
	}
	
	
	
	

	
	
	
}

package demo.entities;

import java.util.Date;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import demo.InvokedBy;
import demo.TargetObject;

@Document(collection = "Commands")
public class MiniAppCommandEntity {
	@Id
	String commandId;
	private String command;
	private TargetObject targetObject;
	private Date invocationTimestamp;
	private InvokedBy invokedBy;
	private Map<String, Object> commandAttributes;

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

	public TargetObject getTargetObject() {
		return targetObject;
	}

	public MiniAppCommandEntity setTargetObject(TargetObject targetObject) {
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

	public InvokedBy getInvokedBy() {
		return invokedBy;
	}

	public MiniAppCommandEntity setInvokedBy(InvokedBy invokedBy) {
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

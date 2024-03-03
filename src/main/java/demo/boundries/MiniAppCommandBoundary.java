package demo.boundries;

import java.util.Date;
import java.util.Map;

import demo.CommandId;
import demo.InvokedBy;
import demo.ObjectId;
import demo.TargetObject;
import demo.entities.MiniAppCommandEntity;
import demo.entities.UserId;

public class MiniAppCommandBoundary {

	private CommandId commandId;
	private String command;
	private TargetObject targetObject;
	private Date invocationTimestamp;
	private InvokedBy invokedBy;
	private Map<String,Object> commandAttributes;
	
	public MiniAppCommandBoundary() {
	}
	
	public MiniAppCommandBoundary(MiniAppCommandEntity entity) {
		String splitedCommandId[] = entity.getCommandId().split(":");
		
		this.commandId = new CommandId();
		if (entity.getCommandId() != null)
			this.commandId.setSuperapp(splitedCommandId[0])
				.setMiniapp(splitedCommandId[1])
				.setId(splitedCommandId[2]);
		
		this.setCommand(entity.getCommand())
			.setTargetObject(entity.getTargetObject())
			.setInvocationTimestamp(entity.getInvocationTimestamp())
			.setInvokedBy(entity.getInvokedBy())
			.setCommandAttributes(entity.getCommandAttributes());
	}

	public CommandId getCommandId() {
		return commandId;
	}

	public MiniAppCommandBoundary setCommandId(CommandId commandId) {
		this.commandId = commandId;
		return this;
	}

	public String getCommand() {
		return command;
	}

	public MiniAppCommandBoundary setCommand(String command) {
		this.command = command;
		return this;
	}

	public TargetObject getTargetObject() {
		return targetObject;
	}

	public MiniAppCommandBoundary setTargetObject(TargetObject targetObject) {
		this.targetObject = targetObject;
		return this;
	}

	public Date getInvocationTimestamp() {
		return invocationTimestamp;
	}

	public MiniAppCommandBoundary setInvocationTimestamp(Date invocationTimestamp) {
		this.invocationTimestamp = invocationTimestamp;
		return this;
	}

	public InvokedBy getInvokedBy() {
		return invokedBy;
	}

	public MiniAppCommandBoundary setInvokedBy(InvokedBy invokedBy) {
		this.invokedBy = invokedBy;
		return this;
	}

	public Map<String, Object> getCommandAttributes() {
		return commandAttributes;
	}

	public MiniAppCommandBoundary setCommandAttributes(Map<String, Object> commandAttributes) {
		this.commandAttributes = commandAttributes;
		return this;
	}
	
	public MiniAppCommandEntity toEntity() {
		MiniAppCommandEntity entity = new MiniAppCommandEntity();
		
		entity.setCommandId(this.getCommandId().getSuperapp() + 
				":" + this.getCommandId().getMiniapp() +
				":" + this.getCommandId().getId())
				.setCommand(this.getCommand())
				.setTargetObject(this.getTargetObject())
				.setInvocationTimestamp(this.getInvocationTimestamp())
				.setInvokedBy(this.getInvokedBy())
				.setCommandAttributes(this.getCommandAttributes());
		
		return entity;
	}
	
	
}

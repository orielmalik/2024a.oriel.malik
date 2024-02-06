package demo;

import java.util.Date;
import java.util.Map;

public class MIniAppCommandBoundary {

	private CommandId commandId;
	private UserId invokedBy;
	private ObjectIdBoundary TargetObject;
	private String TargetCommand;//command-"do something"
	private Date invocationTimeStamp;
	private Map<String,Object> commandAttributes;
	
	public MIniAppCommandBoundary() {
	}
	
	public MIniAppCommandBoundary(CommandId cID, UserId uID,ObjectIdBoundary Oid,String tar,Date d,Map<String,Object> co)
	{
		this.commandId=cID;
		this.invokedBy=uID;
		this.TargetObject=Oid;
		this.TargetCommand=tar;
		setInvocationTimeStamp(d);
		this.commandAttributes =co;
	}
	public MIniAppCommandBoundary(MiniAppCommandEntity m)
	{
		this.commandId=m.getCommandId();
		this.invokedBy=m.getUserid();
		this.TargetObject=m.getObjectId();
		this.TargetCommand=m.getTargetCommand();
		setInvocationTimeStamp(m.getInvocationTimeStamp());
		this.commandAttributes =m.getCommandAttributes();
	}
	public MiniAppCommandEntity toEntity() {
		MiniAppCommandEntity entity = new MiniAppCommandEntity();
		
		entity.setCommandId(this.getCommandId());
		entity.setUserid(this.getUserid());
		entity.setObjectId(this.getObjectId());
		entity.setInvocationTimeStamp(this.getInvocationTimeStamp());
		entity.setUserid(this.getUserid());
		entity.setTargetCommand(this.getTargetCommand());

		return entity;	
	}
	public CommandId getCommandId() {
		return commandId;
	}
	@Override
	public String toString() {
		return "Minapp	Command [id=" + commandId + 
				", invocationTimeStamp=" + getInvocationTimeStamp()  + "]";
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

	public void setTargetCommand(String targetCommand) {
		TargetCommand = targetCommand;
	}

	public Date getInvocationTimeStamp() {
		return invocationTimeStamp;
	}

	public void setInvocationTimeStamp(Date invocationTimeStamp) {
		this.invocationTimeStamp = invocationTimeStamp;
	}

	public UserId getInvokedBy() {
		return invokedBy;
	}

	public void setInvokedBy(UserId invokedBy) {
		this.invokedBy = invokedBy;
	}

	public ObjectIdBoundary getTargetObject() {
		return TargetObject;
	}

	public void setTargetObject(ObjectIdBoundary targetObject) {
		TargetObject = targetObject;
	}

	public Map<String, Object> getCommandAttributes() {
		return commandAttributes;
	}

	public void setCommandAttributes(Map<String, Object> commandAttributes) {
		this.commandAttributes = commandAttributes;
	}
}

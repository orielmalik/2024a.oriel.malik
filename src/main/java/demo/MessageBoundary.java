package demo;

import java.util.Date;

public class MessageBoundary {
	private String id;
	private String message;
	private Date createdTimestamp;

	public MessageBoundary() {
		this.createdTimestamp = new Date();
	}

	public MessageBoundary(MessageEntity entity) {
		this.setId(entity.getId());
		this.setMessage(entity.getMessage());
		this.setCreatedTimestamp(entity.getCreatedTimestamp());
	}
	
	public MessageBoundary(String message) {
		this();
		this.message = message;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}
	
	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}
	
	public MessageEntity toEntity() {
		MessageEntity entity = new MessageEntity();
		
		entity.setId(this.getId());
		entity.setMessage(this.getMessage());
		entity.setCreatedTimestamp(this.getCreatedTimestamp());
		
		return entity;	
	}
	
	@Override
	public String toString() {
		return "MessageBoundary ["
			+ "id=" + id 
			+ ", message=" + message 
			+ ", createdTimestamp=" + createdTimestamp 
			+ "]";
	}

}

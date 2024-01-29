package demo;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "MESSAGES")
public class MessageEntity {
	@Id private String id;
	private String message;
	private Date createdTimestamp;

	public MessageEntity() {
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

	@Override
	public String toString() {
		return "MessageEntity [id=" + id + ", message=" + message + ", createdTimestamp=" + createdTimestamp + "]";
	}

}

package demo;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Meating")
public class MeetingEntity {
	@Id
	private String meetingId;
	private String type;
	private String content;
	private Date createdTimestamp;

	public MeetingEntity() {

	}

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	@Override
	public String toString() {
		return "MeetingEntity [meetingId=" + meetingId + ", type=" + type + ", content=" + content
				+ ", createdTimestamp=" + createdTimestamp + "]";
	}



}

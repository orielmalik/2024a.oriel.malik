package demo;

import java.util.Date;

public class MeetingBoundary {
	private String meetingId;
	private String type;
	private String content;
	private Date createdTimestamp;

	public MeetingBoundary() {
		this.createdTimestamp = new Date();
	}

	public MeetingBoundary(MeetingEntity entity) {
		this.setMeetingId(entity.getMeetingId());
		this.setCreatedTimestamp(entity.getCreatedTimestamp());
		this.setType(entity.getType());
		this.setContent(entity.getContent());
	}

	public MeetingBoundary(String type, String content) {
		this();
		this.type = type;
		this.content = content;
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

	public MeetingEntity toEntity() {
		MeetingEntity entity = new MeetingEntity();

		entity.setCreatedTimestamp(this.getCreatedTimestamp());
		entity.setMeetingId(this.getMeetingId());
		entity.setType(this.getType());
		entity.setContent(this.getContent());

		return entity;
	}

	@Override
	public String toString() {
		return "MeetingBoundary [meetingId=" + meetingId + ", type=" + type + ", content=" + content
				+ ", createdTimestamp=" + createdTimestamp + "]";
	}

}

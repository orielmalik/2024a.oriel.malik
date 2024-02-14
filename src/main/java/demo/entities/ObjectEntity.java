package demo.entities;

import java.util.Date;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SuperAppObject")
public class ObjectEntity {
	@Id
	private String objectId;
	private String superApp;
	private String type;
	private String alias;
	private boolean active;
	private Date createdTimestamp;
	private String userIdSuperapp;
	private String userIdEmail;
	private Map<String, Object> objectDetails;

	public ObjectEntity() {

	}

	public String getObjectId() {
		return objectId;
	}

	public ObjectEntity setObjectId(String objectId) {
		this.objectId = objectId;
		return this;
	}

	public String getSuperApp() {
		return superApp;
	}

	public ObjectEntity setSuperApp(String superApp) {
		this.superApp = superApp;
		return this;
	}

	public String getType() {
		return type;
	}

	public ObjectEntity setType(String type) {
		this.type = type;
		return this;
	}

	public String getAlias() {
		return alias;
	}

	public ObjectEntity setAlias(String alias) {
		this.alias = alias;
		return this;
	}

	public boolean getActive() {
		return active;
	}

	public ObjectEntity setActive(boolean active) {
		this.active = active;
		return this;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public ObjectEntity setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
		return this;
	}

	public Map<String, Object> getObjectDetails() {
		return objectDetails;
	}

	public ObjectEntity setObjectDetails(Map<String, Object> objectDetails) {
		this.objectDetails = objectDetails;
		return this;
	}

	public String getUserIdSuperapp() {
		return userIdSuperapp;
	}

	public ObjectEntity setUserIdSuperapp(String userIdSuperapp) {
		this.userIdSuperapp = userIdSuperapp;
		return this;
	}

	public String getUserIdEmail() {
		return userIdEmail;
	}

	public ObjectEntity setUserIdEmail(String userIdEmail) {
		this.userIdEmail = userIdEmail;
		return this;
	}

	@Override
	public String toString() {
		return "SuperAppObjectEntity [objectId=" + objectId + ", superApp=" + superApp + ", type=" + type + ", alias="
				+ alias + ", active=" + active + ", createdTimestamp=" + createdTimestamp + ", userIdSuperapp="
				+ userIdSuperapp + ", userIdEmail=" + userIdEmail + ", objectDetails=" + objectDetails + "]";
	}

}

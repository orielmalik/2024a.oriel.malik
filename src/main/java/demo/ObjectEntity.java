package demo;

import java.util.Date;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "super_app_object")
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

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getSuperApp() {
		return superApp;
	}

	public void setSuperApp(String superApp) {
		this.superApp = superApp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public Map<String, Object> getObjectDetails() {
		return objectDetails;
	}

	public void setObjectDetails(Map<String, Object> objectDetails) {
		this.objectDetails = objectDetails;
	}

	public String getUserIdSuperapp() {
		return userIdSuperapp;
	}

	public void setUserIdSuperapp(String userIdSuperapp) {
		this.userIdSuperapp = userIdSuperapp;
	}

	public String getUserIdEmail() {
		return userIdEmail;
	}

	public void setUserIdEmail(String userIdEmail) {
		this.userIdEmail = userIdEmail;
	}

	@Override
	public String toString() {
		return "SuperAppObjectEntity [objectId=" + objectId + ", superApp=" + superApp + ", type=" + type + ", alias="
				+ alias + ", active=" + active + ", createdTimestamp=" + createdTimestamp + ", userIdSuperapp="
				+ userIdSuperapp + ", userIdEmail=" + userIdEmail + ", objectDetails=" + objectDetails + "]";
	}

}

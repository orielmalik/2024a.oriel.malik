package demo.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

@Document(collection = "SuperAppObject")
public class ObjectEntity {
	@Id
	private String objectId;
	private String superApp;
	private String type;
	private String alias;
	private boolean active;
	private Date creationTimestamp;
	private String userIdSuperapp;
	private String userIdEmail;
	private Map<String, Object> objectDetails;
	//new attributes for this map
	private String gender;
	private long viewscount;
	private ArrayList<String>views;
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
public void setViews( ArrayList<String>v)
{
	this.views=v;
}
public ArrayList<String> getViews()
{
	return views;
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

	public Date getCreationTimestamp() {
		return creationTimestamp;
	}

	public ObjectEntity setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
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
				+ alias + ", active=" + active + ", createdTimestamp=" + creationTimestamp + ", userIdSuperapp="
				+ userIdSuperapp + ", userIdEmail=" + userIdEmail + ", objectDetails=" + objectDetails + "]";
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}



	public long getViewscount() {
		return viewscount;
	}

	public void setViewscount(long viewscount) {
		this.viewscount = viewscount;
	}



}

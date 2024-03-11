package demo.entities;

import java.util.ArrayList;
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
	private Date creationTimestamp;
	private String userIdSuperapp;
	private String userIdEmail;
	private Map<String, Object> objectDetails;
	// new attributes for this map

	private String location;
	private boolean gender;// 1-male
	private long viewscount;
	private long birthdate;
	private double price;
	private ArrayList<String> views;
	private ArrayList<String> offers;
	private int supplyDays;

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

	public void setViews(ArrayList<String> v) {
		this.views = v;
	}

	public ArrayList<String> getViews() {
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

	public boolean getGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public long getViewscount() {
		return viewscount;
	}

	public void setViewscount(long viewscount) {
		this.viewscount = viewscount;
	}

	public long getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(long birthdate) {
		this.birthdate = birthdate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ArrayList<String> getOffers() {
		// TODO Auto-generated method stub
		return this.offers;
	}

	public int getsupplyDays() {
		// TODO Auto-generated method stub
		return this.supplyDays;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "ObjectEntity [objectId=" + objectId + ", superApp=" + superApp + ", type=" + type + ", alias=" + alias
				+ ", active=" + active + ", creationTimestamp=" + creationTimestamp + ", userIdSuperapp="
				+ userIdSuperapp + ", userIdEmail=" + userIdEmail + ", objectDetails=" + objectDetails + ", location="
				+ location + ", gender=" + gender + ", viewscount=" + viewscount + ", birthdate=" + birthdate
				+ ", price=" + price + ", views=" + views + ", offers=" + offers + ", supplyDays=" + supplyDays + "]";
	}

}

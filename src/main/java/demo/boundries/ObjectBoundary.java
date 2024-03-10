package demo.boundries;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import demo.CreatedBy;
import demo.ObjectId;
import demo.entities.ObjectEntity;

public class ObjectBoundary {
	private ObjectId objectId;
	private String type;
	private String alias;
	private boolean active;
	private Date creationTimestamp;
	private CreatedBy createdBy;
	private Map<String, Object> objectDetails;

	public ObjectBoundary() {

	}

	public ObjectBoundary(ObjectEntity entity) {
		String splitedObjectId[] = entity.getObjectId().split(":");

		this.objectId = new ObjectId();
		if (entity.getObjectId() != null)
			this.objectId.setSuperapp(splitedObjectId[0]).setId(splitedObjectId[1]);
		this.setType(entity.getType()).setAlias(entity.getAlias()).setActive(entity.getActive())
				.setCreationTimestamp(entity.getCreationTimestamp())
				.setCreatedBy(new CreatedBy(entity.getUserIdSuperapp(), entity.getUserIdEmail()));
		this.setObjectDetails(entity.getObjectDetails());
		//putValuesAtObjectDeatils(entity,type);
	}

	private void putValuesAtObjectDeatils(ObjectEntity entity,String type)
	{
	if(type=="dreamer"||type=="counselor") {
		this.getObjectDetails().put("gender", entity.getGender());
		this.getObjectDetails().put("birthdate", entity.getBirthdate());
		this.getObjectDetails().put("username", entity.getGender());
		this.getObjectDetails().put("password", entity.getGender());
if(type=="dreamer")
{
	this.getObjectDetails().put("viewscount", entity.getViewscount());
	this.getObjectDetails().put("offers", entity.getOffers());

}
else if(type=="counselor")
{
	
}
else if(type=="product")
{
	this.getObjectDetails().put("price", entity.getPrice());
	this.getObjectDetails().put("supplyDays", entity.getsupplyDays());
	this.getObjectDetails().put("gender", entity.getGender());

}
		}
		
	}
	public ObjectBoundary(String type, String alias, boolean active, Date createdTimestamp, CreatedBy createdBy,
			Map<String, Object> objectDetails) {
		super();
		this.type = type;
		this.alias = alias;
		this.active = active;
		this.creationTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.objectDetails = objectDetails;
	}

	public ObjectId getObjectId() {
		return objectId;
	}

	public ObjectBoundary setObjectId(ObjectId objectId) {
		this.objectId = objectId;
		return this;
	}

	public String getType() {
		return type;
	}

	public ObjectBoundary setType(String type) {
		this.type = type;
		return this;
	}

	public String getAlias() {
		return alias;
	}

	public ObjectBoundary setAlias(String alias) {
		this.alias = alias;
		return this;
	}

	public boolean getActive() {
		return active;
	}

	public ObjectBoundary setActive(boolean active) {
		this.active = active;
		return this;
	}

	public Date getCreationTimestamp() {
		return creationTimestamp;
	}

	public ObjectBoundary setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
		return this;
	}

	public CreatedBy getCreatedBy() {
		return createdBy;
	}

	public ObjectBoundary setCreatedBy(CreatedBy createdBy) {
		this.createdBy = createdBy;
		return this;
	}

	public Map<String, Object> getObjectDetails() {
		return objectDetails;
	}

	public ObjectBoundary setObjectDetails(Map<String, Object> objectDetails) {
		this.objectDetails = objectDetails;
		return this;
	}

	public ObjectEntity toEntity() {
		ObjectEntity entity = new ObjectEntity();

		entity.setObjectId(this.getObjectId().getSuperapp() + ":" + this.getObjectId().getId())
				.setSuperApp(this.getObjectId().getSuperapp()).setCreationTimestamp(this.getCreationTimestamp())
				.setType(this.getType()).setActive(this.getActive()).setAlias(this.getAlias())
				.setObjectDetails(this.getObjectDetails()).setUserIdEmail(this.getCreatedBy().getUserId().getEmail())
				.setUserIdSuperapp(this.getCreatedBy().getUserId().getSuperapp());


		//entity.setGender((boolean)this.getObjectDetails().get("gender"));
		return entity;
	}

	@Override
	public String toString() {
		return "SuperAppObjectBoundary [objectId=" + objectId + ", type=" + type + ", alias=" + alias + ", active="
				+ active + ", createdTimestamp=" + creationTimestamp + ", createdBy=" + createdBy + ", objectDetails="
				+ objectDetails + "]";
	}

}

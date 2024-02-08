package demo.boundries;

import java.util.Date;
import java.util.Map;

import demo.CreatedBy;
import demo.ObjectId;
import demo.entities.ObjectEntity;

public class ObjectBoundary {
	private ObjectId objectId;
	private String type;
	private String alias;
	private boolean active;
	private Date createdTimestamp;
	private CreatedBy createdBy;
	private Map<String, Object> objectDetails;

	public ObjectBoundary() {

	}

	public ObjectBoundary(ObjectEntity entity) {

	}

	public ObjectBoundary(String type, String alias, boolean active, Date createdTimestamp,
			CreatedBy createdBy, Map<String, Object> objectDetails) {
		super();
		this.type = type;
		this.alias = alias;
		this.active = active;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.objectDetails = objectDetails;
	}

	public ObjectId getObjectId() {
		return objectId;
	}

	public void setObjectId(ObjectId objectId) {
		this.objectId = objectId;
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

	public boolean getActive() {
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

	public CreatedBy getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(CreatedBy createdBy) {
		this.createdBy = createdBy;
	}

	public Map<String, Object> getObjectDetails() {
		return objectDetails;
	}

	public void setObjectDetails(Map<String, Object> objectDetails) {
		this.objectDetails = objectDetails;
	}

	public ObjectEntity toEntity() {
		ObjectEntity entity = new ObjectEntity();

		entity.setObjectId(this.getObjectId().getId());
		entity.setSuperApp(this.getObjectId().getSuperapp());
		entity.setCreatedTimestamp(this.getCreatedTimestamp());
		entity.setType(this.getType());
		entity.setActive(this.getActive());
		entity.setAlias(this.getAlias());
		entity.setObjectDetails(this.getObjectDetails());
		entity.setUserIdEmail(this.getCreatedBy().getUserId().getEmail());
		entity.setUserIdSuperapp(this.getCreatedBy().getUserId().getSuperapp());

		return entity;
	}

	@Override
	public String toString() {
		return "SuperAppObjectBoundary [objectId=" + objectId + ", type=" + type + ", alias=" + alias + ", active="
				+ active + ", createdTimestamp=" + createdTimestamp + ", createdBy=" + createdBy + ", objectDetails="
				+ objectDetails + "]";
	}

}

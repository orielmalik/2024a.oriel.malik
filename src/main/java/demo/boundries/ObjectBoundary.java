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
		String splitedObjectId[] = entity.getObjectId().split(":");
		
		this.objectId = new ObjectId();
		if (entity.getObjectId() != null)
			this.objectId.setSuperapp(splitedObjectId[0]).setId(splitedObjectId[1]);
		
		this.setType(entity.getType())
			.setAlias(entity.getAlias())
			.setActive(entity.getActive())
			.setCreatedTimestamp(entity.getCreatedTimestamp())
			.setCreatedBy(new CreatedBy(entity.getUserIdSuperapp(), entity.getUserIdEmail()))
			.setObjectDetails(entity.getObjectDetails());
	}

	public ObjectBoundary(String type, String alias, boolean active, Date createdTimestamp, CreatedBy createdBy,
			Map<String, Object> objectDetails) {
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

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public ObjectBoundary setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
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
			.setSuperApp(this.getObjectId().getSuperapp())
			.setCreatedTimestamp(this.getCreatedTimestamp())
			.setType(this.getType())
			.setActive(this.getActive())
			.setAlias(this.getAlias())
			.setObjectDetails(this.getObjectDetails())
			.setUserIdEmail(this.getCreatedBy().getUserId().getEmail())
			.setUserIdSuperapp(this.getCreatedBy().getUserId().getSuperapp());

		return entity;
	}

	@Override
	public String toString() {
		return "SuperAppObjectBoundary [objectId=" + objectId + ", type=" + type + ", alias=" + alias + ", active="
				+ active + ", createdTimestamp=" + createdTimestamp + ", createdBy=" + createdBy + ", objectDetails="
				+ objectDetails + "]";
	}

}
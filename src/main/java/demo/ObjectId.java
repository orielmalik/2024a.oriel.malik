package demo;

public class ObjectId {
	private String superapp;
	private String id;
	
	public ObjectId() {
	}
	
	public ObjectId(String superapp, String id) {
		super();
		this.superapp = superapp;
		this.id = id;
	}

	public String getSuperapp() {
		return superapp;
	}

	public ObjectId setSuperapp(String superapp) {
		this.superapp = superapp;
		return this;
	}

	public String getId() {
		return id;
	}

	public ObjectId setId(String id) {
		this.id = id;
		return this;
	}

	@Override
	public String toString() {
		return "ObjectIdBoundary [superapp=" + superapp + ", id=" + id + "]";
	}

}

package demo;

public class ObjectIdBoundary {
	private String superapp;
	private String id;
	
	public ObjectIdBoundary() {
	}
	public ObjectIdBoundary(String superapp, String id) {
		super();
		this.superapp = superapp;
		this.id = id;
	}

	public String getSuperapp() {
		return superapp;
	}

	public void setSuperapp(String superapp) {
		this.superapp = superapp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ObjectIdBoundary [superapp=" + superapp + ", id=" + id + "]";
	}

}

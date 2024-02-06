package demo;

public class CreatedByBoundary {
	private UserId userId;
	
	public CreatedByBoundary(String superapp, String email) {
		this.userId = new UserId(superapp,email);
	}

	public UserId getUserId() {
		return userId;
	}

	public void setUserId(UserId userId) {
		this.userId = userId;
	}
	
	
}

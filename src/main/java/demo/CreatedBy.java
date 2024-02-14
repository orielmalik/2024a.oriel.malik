package demo;

import demo.entities.UserId;

public class CreatedBy {
	private UserId userId;
	
	public CreatedBy() {
	}
	
	public CreatedBy(String superapp, String email) {
		this.userId = new UserId(superapp,email);
	}

	public UserId getUserId() {
		return userId;
	}

	public void setUserId(UserId userId) {
		this.userId = userId;
	}
	
	
}

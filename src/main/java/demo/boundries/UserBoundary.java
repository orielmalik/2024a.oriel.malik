package demo.boundries;

import demo.Role;
import demo.entities.UserEntity;
import demo.entities.UserId;

public class UserBoundary {
	private UserId userId;
	private String email;
	private Role role;
	private String userName;
	private String avatar;
	
	public UserBoundary() {
	}
	
	public UserBoundary(UserEntity user) {
		String splitedUserId[] = user.getId().split(":");
		this.userId = new UserId(splitedUserId[0], splitedUserId[1]);
		this.setUserName(user.getUserName());
		this.setEmail(user.getEmail());
		this.setRole(user.getRole());
		this.setAvatar(user.getAvatar());
	}
		
	@Override
	public String toString() {
		return "UserBoundary [userId=" + userId + ", email=" + email + ", role=" + role + ", userName=" + userName
				+ ", avatar=" + avatar + "]";
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public UserId getUserId() {
		return this.userId;
	}

	public void setUserId(UserId userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return this.role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	

}

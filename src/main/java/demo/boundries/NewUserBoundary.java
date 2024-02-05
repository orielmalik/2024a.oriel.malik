package demo.boundries;

import demo.Role;
import demo.entities.UserEntity;

public class NewUserBoundary {
	private String id;
	private String email;
	private Role role;
	private String userName;
	private String avatar;
	
	public NewUserBoundary() {
	}
	
	public NewUserBoundary(UserEntity user) {
		this.setId(user.getId());;
		this.setUserName(user.getUserName());
		this.setEmail(user.getEmail());
		this.setRole(user.getRole());
		this.setAvatar(user.getAvatar());
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public UserEntity toEntity() {
		UserEntity entity = new UserEntity();
		
		entity.setId(this.getId());
		entity.setUserName(this.getUserName());
		entity.setEmail(this.getEmail());
		entity.setRole(this.getRole());
		entity.setAvatar(this.getAvatar());
		
		return entity;
	}

	@Override
	public String toString() {
		return "NewUserBoundary [id=" + id + ", email=" + email + ", role=" + role + ", userName=" + userName
				+ ", avatar=" + avatar + "]";
	} 
}

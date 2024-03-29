package demo.boundries;

import demo.Role;
import demo.entities.UserEntity;
import demo.entities.UserId;

public class UserBoundary {
	private UserId userId;
	private Role role;
	private String username;
	private String avatar;

	public UserBoundary() {
	}

	public UserBoundary(UserEntity user) {
		String splitedUserId[] = user.getId().split(":");

		this.userId = new UserId();
		if (user.getId() != null)
			this.userId.setSuperapp(splitedUserId[0]).setEmail(splitedUserId[1]);

		this.setUsername(user.getUsername()).setRole(user.getRole()).setAvatar(user.getAvatar());
	}

	public String getAvatar() {
		return avatar;
	}

	public UserBoundary setAvatar(String avatar) {
		this.avatar = avatar;
		return this;
	}

	public UserId getUserId() {
		return this.userId;
	}

	public UserBoundary setUserId(UserId userId) {
		this.userId = userId;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public UserBoundary setUsername(String username) {
		this.username = username;
		return this;
	}

	public Role getRole() {
		return this.role;
	}

	public UserBoundary setRole(Role role) {
		this.role = role;
		return this;
	}

	public UserEntity toEntity() {
		UserEntity entity = new UserEntity();

		entity.setId(this.getUserId().toString()).setUsername(this.getUsername()).setEmail(this.getUserId().getEmail())
				.setRole(this.getRole()).setAvatar(this.getAvatar());

		return entity;
	}

}

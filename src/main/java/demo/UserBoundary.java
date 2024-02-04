package demo;

import java.util.Date;
import java.util.UUID;

public class UserBoundary {
	private String id;
	private Date createdAt;
	private String userName;
	private String email;
	private String phoneNumber;
	private String status;
	private String gender;
	private String birthDate;
	private String location;
	private Role role;
	
	public UserBoundary() {
		this.createdAt = new Date();
		this.id = UUID.randomUUID().toString();
	}
	
	public UserBoundary(UserEntity user) {
		this.setId(user.getId());
		this.setCreatedAt(user.getCreatedAt());
		this.setUserName(user.getUserName());
		this.setEmail(user.getEmail());
		this.setPhoneNumber(user.getPhoneNumber());
		this.setStatus(user.getStatus());
		this.setGender(user.getGender());
		this.setBirthDate(user.getBirthDate());
		this.setLocation(user.getLocation());
		this.setRole(user.getRole());
	}
	
	public UserBoundary(String userName, String email, String phoneNumber, String status, String gender, String birthDate, String location, Role role) {
		this();
		this.setUserName(userName);
		this.setEmail(email);
		this.setPhoneNumber(phoneNumber);
		this.setStatus(status);
		this.setGender(gender);
		this.setBirthDate(birthDate);
		this.setLocation(location);
		this.setRole(role);
	}
		
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public Role getRole() {
		return this.role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public UserEntity toEntity() {
		UserEntity entity = new UserEntity();
		
		entity.setId(this.getId());
		entity.setCreatedAt(this.getCreatedAt());
		entity.setUserName(this.getUserName());
		entity.setEmail(this.getEmail());
		entity.setPhoneNumber(this.getPhoneNumber());
		entity.setStatus(this.getStatus());
		entity.setGender(this.getGender());
		entity.setBirthDate(this.getBirthDate());
		entity.setLocation(this.getLocation());
		entity.setRole(this.getRole());
		return entity;
	}

	@Override
	public String toString() {
		return "UserBoundary [id=" + id + ", createdAt=" + createdAt + ", userName=" + userName + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + ", status=" + status + ", gender=" + gender + ", birthDate="
				+ birthDate + ", location=" + location + ", role =" + role + "]";
	}
}

package demo.entities;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import demo.Role;



@Document(collection = "Users")
public class UserEntity {
	@Id private String id;
	private Date createdAt;
	private String userName;
	private String email;
	private String phoneNumber;
	private String status;
	private String gender;
	private String birthDate;
	private String location;
	private String avatar;
	private Role role;
	
	
	public UserEntity() {		
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

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", createdAt=" + createdAt + ", userName=" + userName + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + ", status=" + status + ", gender=" + gender + ", birthDate="
				+ birthDate + ", location=" + location + ", avatar=" + avatar + ", role=" + role + "]";
	}
	
}

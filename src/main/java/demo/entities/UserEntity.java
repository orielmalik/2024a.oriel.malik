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

	public UserEntity setId(String id) {
		this.id = id;
		return this;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public UserEntity setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
		return this;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public UserEntity setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public UserEntity setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public UserEntity setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public UserEntity setStatus(String status) {
		this.status = status;
		return this;
	}

	public String getGender() {
		return gender;
	}

	public UserEntity setGender(String gender) {
		this.gender = gender;
		return this;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public UserEntity setBirthDate(String birthDate) {
		this.birthDate = birthDate;
		return this;
	}

	public String getLocation() {
		return location;
	}

	public UserEntity setLocation(String location) {
		this.location = location;
		return this;
	}
	
	public Role getRole() {
		return this.role;
	}
	
	public UserEntity setRole(Role role) {
		this.role = role;
		return this;
	}

	public String getAvatar() {
		return avatar;
	}

	public UserEntity setAvatar(String avatar) {
		this.avatar = avatar;
		return this;
	}
}

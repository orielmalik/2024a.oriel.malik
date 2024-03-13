package demo.entities;

import java.util.Objects;

public class UserId {
	private String superapp;
	private String email;

	public UserId() {
	}

	public UserId(String superapp, String email) {
		this.superapp = superapp;
		this.email = email;
	}

	public String getSuperapp() {
		return superapp;
	}

	public UserId setSuperapp(String superapp) {
		this.superapp = superapp;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public UserId setEmail(String email) {
		this.email = email;
		return this;
	}

	 @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        UserId userId = (UserId) o;
	        return Objects.equals(superapp, userId.superapp) &&
	                Objects.equals(email, userId.email);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(superapp, email);
	    }
	
	@Override
	public String toString() {
		return superapp + ":" + email;
	}
}

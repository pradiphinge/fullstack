package com.mpscstarter.web.domain.frontend;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


/**
 * Basic user account users will have access to this
 * 
 * @author Pradipkumar Hinge created on July 21, 2019
 */

public class BasicAccountPayload implements Serializable {

	/* Creates Serial Version UID for Serializable Classes **/
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Email
	private String email;
	
	@NotNull
	private String username;

	@NotNull
	private String password;

	@NotNull
	private String confirmPassword;

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	private String description;

	@NotNull
	private String phoneNumber;

	@NotNull
	private String country;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicAccountPayload other = (BasicAccountPayload) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BasicAccountPayload [email=" + email + ", username=" + username + ", password=" + password
				+ ", confirmPassword=" + confirmPassword + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", description=" + description + ", phoneNumber=" + phoneNumber + ", country=" + country + "]";
	}

}

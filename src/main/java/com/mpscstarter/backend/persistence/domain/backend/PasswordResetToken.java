package com.mpscstarter.backend.persistence.domain.backend;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mpscstarter.backend.persistence.converters.LocalDateTimeAttributeConverter;

@Entity
public class PasswordResetToken implements Serializable {

	/* Creates Serial Version UID for Serializable Classes **/	
	
	private static final long serialVersionUID = 1L;
	
	/** The application logger*/
	
	private static final Logger LOG = LoggerFactory.getLogger(PasswordResetToken.class);

	private static final int DEFAULT_EXPIRY_IN_MINUTES = 120;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(unique = true)
	private String token;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name ="expiry_date")
	@Convert(converter=LocalDateTimeAttributeConverter.class)
	private LocalDateTime expiryDate;


	
	/**
	 * No Arg Constructor
	 */
	public PasswordResetToken() {
		super();
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	
	
	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}


	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		PasswordResetToken other = (PasswordResetToken) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/**
	 * @param token The user token must not be null
	 * @param user The user must not be null
	 * @param creationDateTime must not be null
	 * @param expiryInMinutes  The length, in minutes, for which token will be valid. 
	 * 						   If set to 0 will be assigned default value of 120 minutes(2 hours)
	 * @throws IllegalArgumentsException if token, user or creationDateTime is/are null
	 */
	

	public PasswordResetToken(String token, User user, LocalDateTime creationDateTime, int expiryInMinutes) {
		super();
		if((null==token) || (null==user) || (null==creationDateTime))
			throw new IllegalArgumentException("token, user and creation date time can not be null");	
		
		if (expiryInMinutes == 0) {
			LOG.warn("The token expiration date is set to 0. Assigning the default value {}",DEFAULT_EXPIRY_IN_MINUTES);
			expiryInMinutes = DEFAULT_EXPIRY_IN_MINUTES;
		}
			
		
		this.token=token;
		this.user = user;
		this.expiryDate = creationDateTime.plusMinutes(expiryInMinutes);
		
	}

	
	
	
	 

}

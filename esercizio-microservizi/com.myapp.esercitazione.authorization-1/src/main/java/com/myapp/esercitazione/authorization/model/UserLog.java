package com.myapp.esercitazione.authorization.model;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity(name="userlog")
public class UserLog {
	@Id 	
	@Column(name="id",unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;	
	@Column(name="username")
	private String username;
	@Column(name="password")
	private String password;
	@Column(name="token")
	private String token;
	@Column(name = "tokenbirth")	
	private Date tokenbirth;
	
	public Date getTokenbirth() {
		return tokenbirth;
	}

	public void setTokenbirth(Date tokenbirth) {
		this.tokenbirth = tokenbirth;
	}

	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
}

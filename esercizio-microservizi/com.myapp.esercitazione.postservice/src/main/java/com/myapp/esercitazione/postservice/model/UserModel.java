package com.myapp.esercitazione.postservice.model;
import java.util.List;


public class UserModel {
	private long id;
	private String username;
	private String email;
	private String password;
	// ----------------------------------------------------------
	private List<PostModel> postuser;
	// ----------------------------------------------------------		
	public UserModel() {

	}
	/***
	 * 
	 * @param id - ID dello user 
	 * @param username - USERNAME 
	 * @param email - Email associato allo USER
	 * @param password - Password Associata 
	 * @param postuser - Lista dei post creati dallo User
	 */
	public UserModel(long id, String username, String email, String password, List<PostModel> postuser) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.postuser = postuser;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<PostModel> getPostuser() {
		return this.postuser;
	}

	public void setPostuser(List<PostModel> postuser) {			
		this.postuser = postuser;
	}
}

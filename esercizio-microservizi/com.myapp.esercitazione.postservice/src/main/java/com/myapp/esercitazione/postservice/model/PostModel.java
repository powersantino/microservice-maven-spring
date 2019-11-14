package com.myapp.esercitazione.postservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PostModel {	
	private long id;
	@JsonIgnore
	private long idUser;
	private String title;	
	private String body;
	/***
	 * 
	 * @param id - ID del post
	 * @param idUser - ID dello USER associato al post 
	 * @param title - Titolo del post
	 * @param body - Corpo del post
	 */
	public PostModel(long id,long idUser, String title, String body) {
		super();
		this.id = id;
		this.idUser = idUser;
		this.title = title;
		this.body = body;
	}
	public PostModel() {
		super();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public long getIdUser() {
		return idUser;
	}
	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}
}

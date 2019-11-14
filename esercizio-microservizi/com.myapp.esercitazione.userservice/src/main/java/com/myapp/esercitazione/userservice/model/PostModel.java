package com.myapp.esercitazione.userservice.model;

public class PostModel {	
	private long id;
	private long idUser;
	private String title;	
	private String body;
	
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
	public long getIduser() {
		return idUser;
	}
	public void setIduser(long idUser) {
		this.idUser = idUser;
	}
}

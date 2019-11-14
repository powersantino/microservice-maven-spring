package com.myapp.esercitazione.postservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "PostEntity")
public class PostEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private long id;	
	@Column(name="idUser")
	private long idUser;	
	@Column(name="title")
	private String title;
	@Column(name="body")
	private String body;
	
	public PostEntity() {
		super();		
	}
	/***
	 * 
	 * @param id - Id del post
	 * @param idUser - Id dell'user associato
	 * @param title - Titolo del post 
	 * @param body - Corpo del post
	 */
	public PostEntity(long id, long idUser,String title, String body) {
		super();
		this.id = id;
		this.idUser = idUser;
		this.title = title;
		this.body = body;
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

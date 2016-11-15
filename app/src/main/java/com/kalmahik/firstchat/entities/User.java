package com.kalmahik.firstchat.entities;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject{
	@PrimaryKey
	private String id;
	private String name;
	private String description;
	private String image;

	public User(){

	}

	public User(String id, String name, String description, String image) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.image = image;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}

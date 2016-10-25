package com.kalmahik.firstchat;


public class User {
	private String name;
	private String status;

	public User(String name, String status) {
		this.name = name;
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public String getStatus() {
		return status;
	}
}

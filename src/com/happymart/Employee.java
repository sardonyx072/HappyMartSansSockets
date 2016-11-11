package com.happymart;

import java.util.UUID;

public class Employee {
	private UUID id;
	private String name;
	private String user;
	private String pass;
	
	public Employee (UUID id, String name, String user, String pass) {
		this.id = id;
		this.name = name;
		this.user = user;
		this.pass = pass;
	}
	public UUID getID() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUser() {
		return this.user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPass() {
		return this.pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
}

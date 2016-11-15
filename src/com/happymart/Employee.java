package com.happymart;

import java.util.UUID;

/**
 * A class that represents an employee. 
 * @author hoff3539
 *
 */
public class Employee {
	/**
	 * Represents the employee's ID number, used for looking up the employee quickly.
	 */
	private UUID id;
	
	/**
	 * Represents the employee's name.
	 */
	private String name;
	
	/**
	 * Represents the employee's username. Most usernames should be set with a total of 8 characters: up to 4 letters of the last name and the remaining characters being numbers that create a unique identifier.
	 */
	private String user;
	
	/**
	 * Represents the employee's password.
	 */
	private String pass;
	
	public Employee (String name, String user, String pass) {
		this.id = UUID.randomUUID();
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
	public String getUsername() {
		return this.user;
	}
	public void setUsername(String user) {
		this.user = user;
	}
	public String getPassword() {
		return this.pass;
	}
	public void setPassword(String pass) {
		this.pass = pass;
	}
}

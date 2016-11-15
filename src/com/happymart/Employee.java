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
	
	/**
	 * Constructor for Employee objects. Randomly assigns ID independently of other classes.
	 * @param name - the employee's name
	 * @param user - the employee's username
	 * @param pass - the employee's password
	 */
	public Employee (String name, String user, String pass) {
		//TODO: check to see if UUID already taken //not necessary?
		this.id = UUID.randomUUID();
		this.setName(name);
		this.setUsername(user);
		this.setPassword(pass);
	}
	
	/**
	 * Get method for employee ID number.
	 * @return id - employee's ID number
	 */
	public UUID getID() {
		return this.id;
	}
	
	/**
	 * Get method for employee's name.
	 * @return name - employee's name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Set method for employee's name.
	 * @param name - employee's name
	 */
	public void setName(String name) {
		//TODO: cannot be blank or null
		this.name = name;
	}
	
	/**
	 * Get method for employee's username.
	 * @return user - employee's username
	 */
	public String getUsername() {
		return this.user;
	}
	
	/**
	 * Set method for employee's username.
	 * @param user - employee's username
	 */
	public void setUsername(String user) {
		//TODO: cannot be blank or null
		this.user = user;
	}
	
	/**
	 * Get method for employee's password.
	 * @return pass - employee's password
	 */
	public String getPassword() {
		return this.pass;
	}
	
	/**
	 * Set method for employee's password.
	 * @param pass - employee's password
	 */
	public void setPassword(String pass) {
		//TODO: cannot be blank or null
		this.pass = pass;
	}
}

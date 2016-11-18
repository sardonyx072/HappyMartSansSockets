package com.happymart;

import java.util.*;

public class Application {
	private Scanner scan;
	private Map<UUID,Employee> employees;
	
	private 
	
	public static void main(String[] args) {
		Application app = new Application();
		app.init();
		app.login();
	}
	private void init() {
		Employee admin = new Employee(UUID.randomUUID(),"admin","admin","password");
		this.employees.put(admin.getID(),admin);
		Employee mitch = new Employee(UUID.randomUUID(),"Mitchell Hoffmann","hoff3539","password");
		this.employees.put(admin.getID(),mitch);
	}
	private Application login(Application app) {
		
	}
}

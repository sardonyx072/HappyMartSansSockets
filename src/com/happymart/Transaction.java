package com.happymart;

import java.util.Date;
import java.util.HashSet;

public class Transaction {
	private int id;
	private Employee employee;
	private HashSet<Integer> referencedIDs;
	private HashSet<ItemQuantity> purchased;
	private HashSet<ItemQuantity> returned;
	private Date timestamp;
	public Transaction (int id, Employee employee, HashSet<Integer> referencedIDs, HashSet<ItemQuantity> purchased, HashSet<ItemQuantity> returned) {
		this.id = id;
		this.employee = employee;
		this.referencedIDs = referencedIDs;
		this.purchased = purchased;
		this.returned = returned;
		this.timestamp = new Date();
	}
	public int getID() {
		return this.id;
	}
	public Employee getEmployee() {
		return this.employee;
	}
	public HashSet<Integer> getReferencedIDs() {
		return this.referencedIDs;
	}
	public HashSet<ItemQuantity> getPurchased() {
		return this.purchased;
	}
	public HashSet<ItemQuantity> getReturned() {
		return this.returned;
	}
	public Date getTimestamp() {
		return this.timestamp;
	}
}

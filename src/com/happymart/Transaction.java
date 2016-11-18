package com.happymart;

import java.util.Date;
import java.util.HashSet;

public class Transaction{
	private int id;
	private Employee employee;
	private HashSet<Integer> referencedIDs;
	private HashSet<ItemQuantity> purchased;
	private HashSet<ItemQuantity> returned;
	private Date timestamp;
	private int purchasedSubtotal;
	private int returnedSubtotal;
	private int total;
	public Transaction (int id, Date timstamp, Employee employee, HashSet<Integer> referencedIDs, HashSet<ItemQuantity> purchased, HashSet<ItemQuantity> returned) {
		this.id = id;
		this.employee = employee;
		this.referencedIDs = referencedIDs;
		this.purchased = purchased;
		this.returned = returned;
		this.timestamp = new Date();
		this.purchasedSubtotal = 0;
		this.returnedSubtotal = 0;
		for (ItemQuantity i : this.purchased) {
			this.purchasedSubtotal += (i.getItemType().getPrice()*i.getQuantity());
		}
		for (ItemQuantity i : this.returned) {
			this.returnedSubtotal += (i.getItemType().getPrice()*i.getQuantity());
		}
		this.total = this.purchasedSubtotal - this.returnedSubtotal;
	}
	public Transaction (int id, Employee employee, HashSet<Integer> referencedIDs, HashSet<ItemQuantity> purchased, HashSet<ItemQuantity> returned) {
		this(id,new Date(),employee,referencedIDs,purchased,returned);
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
	public int getPurchasedSubtotal() {
		return this.purchasedSubtotal;
	}
	public int getReturnedSubtotal() {
		return this.returnedSubtotal;
	}
	public int getTotal() {
		return this.total;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Record of Transaction #" + this.getID());
		builder.append("Completed: " + this.timestamp);
		builder.append("\nEmployee" + this.employee.getID() + ": " + this.employee.getName());
		builder.append("\n");
		if (this.returned.size() > 0) {
			builder.append("\nPurchased:");
		}
		int psubtotal = 0, rsubtotal = 0, total = 0;
		for (ItemQuantity item : this.purchased) {
			psubtotal += (item.getItemType().getPrice()*item.getQuantity());
			builder.append("\n$" + ((item.getItemType().getPrice()*item.getQuantity())/100.0) + "=");
			if (item.getQuantity() > 1) {
				builder.append("(" + item.getQuantity() + "x" + item.getItemType().getPrice() + ")");
			}
			builder.append(item.getItemType().getName());
		}
		if (this.returned.size() > 0) {
			builder.append("\nSubtotal: $" + (psubtotal/100.0) + "\n\nReturned:");
		}
		for (ItemQuantity item : this.returned) {
			rsubtotal += (item.getItemType().getPrice()*item.getQuantity());
			builder.append("\n$(" + ((item.getItemType().getPrice()*item.getQuantity())/100.0) + ")=");
			if (item.getQuantity() > 1) {
				builder.append("(" + item.getQuantity() + "x" + item.getItemType().getPrice() + ")");
			}
			builder.append(item.getItemType().getName());
		}
		if (this.returned.size() > 0) {
			builder.append("\nSubtotal: $(" + (rsubtotal/100.0) + ")\n\nReturned:");
		}
		total = psubtotal - rsubtotal;
		builder.append("\n\nTotal: $" + (total > 0 ? (total/100.0) : "(" + (total/100.0) + ")"));
		return builder.toString();
	}
}

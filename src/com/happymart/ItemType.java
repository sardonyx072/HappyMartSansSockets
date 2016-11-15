package com.happymart;

import java.util.UUID;

public class ItemType {
	private UUID id;
	private String name;
	private int price;
	
	public ItemType (String name, int price) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.price = price;
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
	public int getPrice() {
		return this.price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
}

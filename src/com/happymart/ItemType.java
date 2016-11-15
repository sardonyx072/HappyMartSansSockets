package com.happymart;

import java.util.UUID;

public class ItemType {
	
	/**
	 * Represents the item type's unique identification number. This does NOT identify individual items, only their type.
	 */
	private UUID id;
	
	/**
	 * The item type's descriptor, which can include name brands, titles, distributors or suppliers names, or the generic type of the item.
	 */
	private String name;
	
	/**
	 * The price of the item in United States cents. The value is represented as an integer to avoid floating point math until necessary.
	 */
	private int price;
	
	/**
	 * The constructor for ItemType objects. Randomly assigns ID independently of other classes.
	 * @param name - the item type's name and descriptor
	 * @param price - the item type's price in US cents
	 */
	public ItemType (String name, int price) {
		//TODO: check to make sure UUID not already taken //not necessary?
		this.id = UUID.randomUUID();
		this.setName(name);
		this.setPrice(price);
	}
	
	/**
	 * Get for the item type's ID number.
	 * @return id - the item type's ID number
	 */
	public UUID getID() {
		return this.id;
	}
	
	/**
	 * Get for the item type's name and descriptor.
	 * @return name - the item type's name and descriptor
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Set for the item type's name and descriptor.
	 * @param name - the item type's name and descriptor
	 */
	public void setName(String name) {
		//TODO: cannot be blank or null
		this.name = name;
	}
	
	/**
	 * Get for the item type's price in US cents.
	 * @return price - the item type's price in US cents
	 */
	public int getPrice() {
		return this.price;
	}
	
	/**
	 * Set for the item type's price in US cents.
	 * @param price - the item type's price in US cents
	 */
	public void setPrice(int price) {
		//TODO: cannot be negative
		this.price = price;
	}
}

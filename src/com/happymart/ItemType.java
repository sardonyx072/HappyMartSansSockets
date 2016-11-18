package com.happymart;

public class ItemType implements Comparable {
	
	/**
	 * Represents the item type's unique identification number. This does NOT identify individual items, only their type.
	 */
	private int id;
	
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
	 * @param id - the item type's unique identification number
	 * @param name - the item type's name and descriptor
	 * @param price - the item type's price in US cents
	 */
	public ItemType (int id, String name, int price) {
		this.id = id;
		this.setName(name);
		this.setPrice(price);
	}
	
	/**
	 * Get for the item type's ID number.
	 * @return id - the item type's ID number
	 */
	public int getID() {
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

	@Override
	public int compareTo(Object arg0) {
		return this.name.compareTo(((ItemType)arg0).name);
	}
	
	@Override
	public boolean equals(Object arg0) {
		return this.id == ((ItemType)arg0).id;
	}
}

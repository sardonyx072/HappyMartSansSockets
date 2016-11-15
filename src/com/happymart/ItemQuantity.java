package com.happymart;

public class ItemQuantity {
	
	private ItemType item;
	
	private int quantity;
	
	public ItemQuantity (ItemType item, int quantity) {
		this.item = item;
		this.setQuantity(quantity);
	}
	
	public ItemType getItemType() {
		return this.item;
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	
	public void setQuantity(int quantity) {
		//TODO: cannot be negative
		this.quantity = quantity;
	}
	
	public void addQuantity(int amount) {
		//TODO: param cannot be negative
		this.setQuantity(this.getQuantity()+amount);
	}
	
	public void subtractQuantity(int amount) {
		//TODO: param cannot be negative
		this.setQuantity(this.getQuantity()-amount);
	}
}

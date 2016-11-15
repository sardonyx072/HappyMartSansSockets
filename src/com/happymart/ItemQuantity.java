package com.happymart;

public class ItemQuantity {
	private ItemType item;
	private int quantity;
	
	public ItemQuantity (ItemType item, int quantity) {
		this.item = item;
		this.quantity = quantity;
	}
	
	public ItemType getItemType() {
		return this.item;
	}
	public int getQuantity() {
		return this.quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public void addQuantity(int amount) {
		this.quantity += amount;
	}
	public void subtractQuantity(int amount) {
		this.quantity -= amount;
	}
}

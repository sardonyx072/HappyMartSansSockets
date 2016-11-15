package com.happymart;

public class ItemQuantityManaged extends ItemQuantity {
	private int threshold;
	private boolean moreNeeded;
	
	public ItemQuantityManaged (ItemType item, int quantity, int threshold) {
		super (item, quantity);
		this.threshold = threshold;
		this.setMoreNeeded();
	}
	
	public int getThreshold() {
		return this.threshold;
	}
	public void setThreshold(int threshold) {
		this.threshold = threshold;
		this.setMoreNeeded();
	}
	public boolean isMoreNeeded() {
		return this.moreNeeded;
	}
	private void setMoreNeeded() {
		this.moreNeeded = this.getQuantity() < this.getThreshold();
	}
}

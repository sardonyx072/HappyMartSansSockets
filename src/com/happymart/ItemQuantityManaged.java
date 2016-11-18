package com.happymart;

public class ItemQuantityManaged extends ItemQuantity {
	
	private int threshold;
	
	private boolean moreNeeded;
	
	public ItemQuantityManaged (ItemType item, int quantity, int threshold) {
		super (item, quantity);
		this.threshold = threshold;
		this.setMoreNeeded();
	}

	@Override
	public void addQuantity (int amount) {
		super.addQuantity(amount);
		this.setMoreNeeded();
	}
	
	@Override
	public void subtractQuantity (int amount) {
		super.subtractQuantity(amount);
		this.setMoreNeeded();
	}
	
	public int getThreshold() {
		return this.threshold;
	}
	
	public void setThreshold(int threshold) {
		//TODO: cannot be negative
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

package com.happymart;

import java.util.Date;

public class PerishableItemType extends ItemType {
	
	private Date expirationDate;
	
	public PerishableItemType (String name, int price, Date expirationDate) {
		super(name, price);
		this.setExpirationDate(expirationDate);
	}
	
	public Date getExpirationDate() {
		return this.expirationDate;
	}
	
	public void setExpirationDate(Date expirationDate) {
		//TODO: must be in the future
		this.expirationDate = expirationDate;
	}
}

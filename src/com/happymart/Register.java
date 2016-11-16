package com.happymart;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.UUID;

public class Register {
	private static final String INVENTORY_LOCATION = "res\\inventory.txt";
	private static final String TRANSACTION_LOCATION = "res\\transactions\\";
	private HashSet<ItemQuantity> purchasing;
	private HashSet<ItemQuantity> returning;
	private int moneyInDrawer;
	
	public Register () {
		//TODO: save and load money in drawer from file.
		this.purchasing = new HashSet<ItemQuantity>();
		this.returning = new HashSet<ItemQuantity>();
	}
	public int getMoneyInDrawer() {
		return this.moneyInDrawer;
	}
	public void addMoneyToDrawer(int amount) {
		this.moneyInDrawer += amount;
	}
	public void removeMoneyFromDrawer(int amount) {
		//TODO: cannot be negative
		this.moneyInDrawer -= amount;
	}
	public int getTotalForTransaction() {
		return this.getTotalForPurchasedGoods() - this.getTotalForReturnedGoods();
	}
	public int getTotalForPurchasedGoods() {
		return this.findTotal(this.purchasing);
	}
	public int getTotalForReturnedGoods() {
		return this.findTotal(this.returning);
	}
	private int findTotal (HashSet<ItemQuantity> items) {
		int total = 0;
		for (ItemQuantity item : items) {
			total += item.getItemType().getPrice() * item.getQuantity();
		}
		return total;
	}
	public int makeTransaction(int cashTendered) {
		if (cashTendered >= this.getTotalForTransaction()) {
			//TODO: this could be sped up...a lot, use hashmaps?
			HashSet<ItemQuantityManaged> inventory = this.getInventory();
			for (ItemQuantity typePurchased : this.purchasing) {
				for (ItemQuantityManaged typeInStock : inventory) {
					if (typePurchased.equals(typeInStock)) {
						typeInStock.subtractQuantity(typePurchased.getQuantity());
					}
				}
			}
			for (ItemQuantity typeReturned : this.returning) {
				for (ItemQuantityManaged typeInStock : inventory) {
					if (typeReturned.equals(typeInStock)) {
						typeInStock.addQuantity(typeReturned.getQuantity());
					}
				}
			}
			this.setInventory(inventory);
			this.moneyInDrawer += this.getTotalForTransaction();
			this.purchasing = new HashSet<ItemQuantity>();
			this.returning = new HashSet<ItemQuantity>();
		}
		return cashTendered - this.getTotalForTransaction();
	}
	private HashSet<ItemQuantityManaged> getInventory() {
		HashSet<ItemQuantityManaged> inventory = new HashSet<ItemQuantityManaged>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(INVENTORY_LOCATION));
			while(reader.ready()) {
				String line = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line);
				UUID id = UUID.fromString(tokenizer.nextToken());
				String name = tokenizer.nextToken();
				int price = Integer.parseInt(tokenizer.nextToken());
				int amount = Integer.parseInt(tokenizer.nextToken());
				int minimum = Integer.parseInt(tokenizer.nextToken());
				inventory.add(new ItemQuantityManaged(new ItemType(id,name,price),amount,minimum));
			}
			reader.close();
		} catch (FileNotFoundException e){
		} catch (IOException e) {
		}
		return inventory;
	}
	private void setInventory(HashSet<ItemQuantityManaged> inventory) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(INVENTORY_LOCATION));
			for (ItemQuantityManaged item : inventory) {
				String line = item.getItemType().getID() + "," + item.getItemType().getName() + "," + item.getItemType().getPrice() + "," + item.getQuantity() + "," + item.getThreshold();
				writer.write(line);
			}
			writer.close();
		} catch (IOException e) {
		}
	}
}

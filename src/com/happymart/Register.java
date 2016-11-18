package com.happymart;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.UUID;

public class Register {
	private static final String DRAWER_LOCATION = "res\\drawer.txt";
	private static final String EMPLOYEES_LOCATION = "res\\employees.txt";
	private static final String INVENTORY_LOCATION = "res\\inventory.txt";
	private static final String TRANSACTION_LOCATION = "res\\transactions\\";
	private static ArrayList<Integer> availableIDs;
	private HashSet<Employee> employees;
	private HashSet<Integer> referencedIDs;
	private HashSet<ItemQuantity> purchasing;
	private HashSet<ItemQuantity> returning;
	private int moneyInDrawer;
	private Employee employee;
	
	public Register () {
		this.loadDrawerFromFile();
		this.loadEmployeesFromFile();
		this.referencedIDs = new HashSet<Integer>();
		this.purchasing = new HashSet<ItemQuantity>();
		this.returning = new HashSet<ItemQuantity>();
		for (int i = 0; i < 10000; i++) {
			this.availableIDs.add(i);
		}
		HashSet<ItemQuantityManaged> temp = this.getInventory();
		for (ItemQuantityManaged item : temp) {
			this.availableIDs.remove(item.getItemType().getID());
		}
		this.employee = null;
	}
	public int getRandomID() {
		return this.availableIDs.remove((int)Math.random()*this.availableIDs.size());
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
	private void loadDrawerFromFile() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(DRAWER_LOCATION));
			this.moneyInDrawer = Integer.parseInt(reader.readLine());
			reader.close();
		} catch (NumberFormatException | IOException e) {
			this.moneyInDrawer = 0;
		}
	}
	private void writeDrawerToFile() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(DRAWER_LOCATION));
			writer.write(this.moneyInDrawer);
			writer.close();
		} catch (IOException e) {
		}
	}
	private void loadEmployeesFromFile() {
		this.employees = new HashSet<Employee>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(EMPLOYEES_LOCATION));
			while (reader.ready()) {
				String line = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line);
				int id = Integer.parseInt(tokenizer.nextToken());
				String name = tokenizer.nextToken();
				String user = tokenizer.nextToken();
				String pass = tokenizer.nextToken();
				this.employees.add(new Employee(id,name,user,pass));
			}
			reader.close();
		} catch (NumberFormatException | IOException e) {
			this.employees.add(new Employee(this.getRandomID(),"admin","admin","password"));
		}
	}
	public void writeEmployeesToFile() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(EMPLOYEES_LOCATION));
			for (Employee employee : this.employees) {
				writer.write(employee.getID() + "," + employee.getName() + "," + employee.getUsername() + "," + employee.getPassword());
			}
			writer.close();
		} catch (IOException e) {
		}
	}
	public Employee getEmployee() {
		return this.employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public HashSet<Integer> getReferencedIDs() {
		return this.referencedIDs;
	}
	public void addReferencedID(int id) {
		this.referencedIDs.add(id);
	}
	public void removeReferencedID(int id) {
		this.referencedIDs.remove(id);
	}
	public void clearReferencedIDs() {
		this.referencedIDs = new HashSet<Integer>();
	}
	public HashSet<ItemQuantity> getPurchasing() {
		return this.purchasing;
	}
	public void addToPurchasing(ItemQuantity item) {
		for (ItemQuantity i : this.purchasing) {
			if (i.equals(item)) {
				i.addQuantity(item.getQuantity());
				return;
			}
		}
		this.purchasing.add(item);
	}
	public void removeFromPurchasing(ItemQuantity item) {
		for (ItemQuantity i : this.purchasing) {
			if (i.equals(item)) {
				i.subtractQuantity(item.getQuantity());
				return;
			}
		}
	}
	public void clearPurchasing() {
		this.purchasing = new HashSet<ItemQuantity>();
	}
	public HashSet<ItemQuantity> getReturning() {
		return this.returning;
	}
	public void addToReturning (ItemQuantity item) {
		for (ItemQuantity i : this.returning) {
			if (i.equals(item)) {
				i.addQuantity(item.getQuantity());
				return;
			}
		}
		this.returning.add(item);
	}
	public void removeFromReturning (ItemQuantity item) {
		for (ItemQuantity i : this.returning) {
			if (i.equals(item)) {
				i.subtractQuantity(item.getQuantity());
				return;
			}
		}
	}
	public void clearReturning() {
		this.returning = new HashSet<ItemQuantity>();
	}
	public void clearSale() {
		this.clearReferencedIDs();
		this.clearPurchasing();
		this.clearReturning();
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
			this.writeTransactionToFile(new Transaction(this.getRandomID(),this.employee,this.referencedIDs,this.purchasing,this.returning));
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
			this.writeDrawerToFile();
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
	private ArrayList<Transaction> loadTransactionsFromFiles() {
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		
		return transactions;
	}
	private void writeTransactionToFile(Transaction t) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTION_LOCATION + t.getTimestamp()));
			writer.write(t.toString());
			writer.close();
		} catch (IOException e) {
		}
	}
}

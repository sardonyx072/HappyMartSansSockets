package com.happymart;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Register {
	private static final String DRAWER_LOCATION = "..\\res\\drawer.txt";
	private static final String EMPLOYEES_LOCATION = "..\\res\\employees.txt";
	private static final String INVENTORY_LOCATION = "..\\res\\inventory.txt";
	private static final String TRANSACTION_LOCATION = "..\\res\\transactions\\";
	private static final String REPORTS_LOCATION = "..\\res\\reports\\";
	private ArrayList<Integer> availableIDs;
	private HashSet<Employee> employees;
	private HashSet<Integer> referencedIDs;
	private HashSet<ItemQuantity> purchasing;
	private HashSet<ItemQuantity> returning;
	private int moneyInDrawer;
	private Employee employee;
	
	public Register () {
		this.availableIDs = new ArrayList<Integer>();
		for (int i = 0; i < 10000; i++) {
			this.availableIDs.add(i);
		}
		this.referencedIDs = new HashSet<Integer>();
		this.purchasing = new HashSet<ItemQuantity>();
		this.returning = new HashSet<ItemQuantity>();
		this.moneyInDrawer = 0;
		this.employee = null;
		this.loadDrawerFromFile();
		this.loadEmployeesFromFile();
		HashSet<ItemQuantityManaged> temp = this.getInventory();
		for (ItemQuantityManaged item : temp) {
			this.availableIDs.remove(item.getItemType().getID());
		}
		for (Employee e : this.employees) {
			this.availableIDs.remove(e.getID());
		}
	}
	public int getRandomID() {
		return this.availableIDs.remove((int)(Math.random()*this.availableIDs.size()));
	}
	public int getMoneyInDrawer() {
		return this.moneyInDrawer;
	}
	public String getMoneyInDrawerAsString() {
		return NumberFormat.getCurrencyInstance().format(this.moneyInDrawer/100.0);
	}
	public void addMoneyToDrawer(int amount) {
		this.moneyInDrawer += amount;
	}
	public void removeMoneyFromDrawer(int amount) {
		//TODO: cannot be negative
		this.moneyInDrawer -= amount;
	}
	public void setMoneyInDrawer (int amount) {
		//TODO: cannot be negative
		this.moneyInDrawer = amount;
	}
	public ItemType getItemType(int id) {
		for (ItemQuantityManaged i : this.getInventory()) {
			if (i.getItemType().getID() == id) {
				return i.getItemType();
			}
		}
		return null;
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
				StringTokenizer tokenizer = new StringTokenizer(line,"\t");
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
			boolean first = true;
			for (Employee employee : this.employees) {
				if (first){
					writer.write(employee.getID() + "\t" + employee.getName() + "\t" + employee.getUsername() + "\t" + employee.getPassword());
					first = false;
				}
				else 
					writer.write("\n" + employee.getID() + "\t" + employee.getName() + "\t" + employee.getUsername() + "\t" + employee.getPassword());
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
	public boolean areValidEmployeeCredentials(String username, String password) {
		for (Employee e : this.employees) {
			if (e.getUsername().equals(username) && e.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}
	public boolean signInWithCredentials(String username, String password) {
		for (Employee e : this.employees) {
			if (e.getUsername().equals(username) && e.getPassword().equals(password)) {
				this.setEmployee(e);
				return true;
			}
		}
		return false;
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
	public String getCurrentSaleAsString() {
		StringBuilder builder = new StringBuilder();
		int i = 0;
		if (this.returning.size() > 0) {
			builder.append("Purchasing:");
			for (ItemQuantity item : this.purchasing) {
				builder.append("\n" + (i++) + ". " + item.toString());
			}
			builder.append("\n");
			builder.append("Returning:");
			for (ItemQuantity item : this.returning) {
				builder.append("\n" + (i++) + ". " + item.toString());
			}
		}
		else {
			for (ItemQuantity item : this.purchasing) {
				builder.append("\n" + (i++) + ". " + item.toString());
			}
		}
		return builder.toString();
	}
	public String getPurchasingAsString() {
		StringBuilder builder = new StringBuilder();
		int i = 0;
		for (ItemQuantity item : this.purchasing) {
			builder.append("\n" + (i++) + ". " + item.toString());
		}
		return builder.toString();
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
	public boolean canAddToPurchasingAndDo(int id, int quantity) {
		for (ItemQuantity i : this.getInventory()) {
			if (i.getItemType().getID() == id && i.getQuantity() >= quantity) {
				this.addToPurchasing(new ItemQuantity(i.getItemType(),quantity));
				return true;
			}
		}
		return false;
	}
	public boolean isValidTransactionNumber(int transactionID) {
		ArrayList<Transaction> transactions = this.loadTransactionsFromFiles();
		for (Transaction t : transactions) {
			if (t.getID() == transactionID) {
				return true;
			}
		}
		return false;
	}
	public int numberOfItemBought(int transactionID, int itemID) {
		ArrayList<Transaction> transactions = this.loadTransactionsFromFiles();
		for (Transaction t : transactions) {
			if (t.getID() == transactionID) {
				for (ItemQuantity i : t.getPurchased()) {
					if (i.getItemType().getID() == itemID) {
						return i.getQuantity();
					}
				}
			}
		}
		return 0;
	}
	public boolean canAddToReturnAndDo(int transactionID, int itemID) {
		if (this.isValidTransactionNumber(transactionID) && this.numberOfItemBought(transactionID, itemID) > 0) {
			this.referencedIDs.add(transactionID);
			this.returning.add(new ItemQuantity(this.getItemType(itemID),1));
			return true;
		}
		return false;
	}
	public void removeFromPurchasing(ItemQuantity item) {
		for (ItemQuantity i : this.purchasing) {
			if (i.equals(item)) {
				i.subtractQuantity(item.getQuantity());
				return;
			}
		}
	}
	public void removeFromPurchasing(ItemType item) {
		this.purchasing.remove(item);
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
	public String getTotalForTransactionAsString() {
		return NumberFormat.getCurrencyInstance().format(this.getTotalForTransaction()/100.0);
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
				StringTokenizer tokenizer = new StringTokenizer(line,"\t");
				int id = Integer.parseInt(tokenizer.nextToken());
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
			boolean first = true;
			for (ItemQuantityManaged item : inventory) {
				if (first) {
					writer.write(item.getItemType().getID() + "\t" + item.getItemType().getName() + "\t" + item.getItemType().getPrice() + "\t" + item.getQuantity() + "\t" + item.getThreshold());
					first = false;
				}
				else
					writer.write("\n" + item.getItemType().getID() + "\t" + item.getItemType().getName() + "\t" + item.getItemType().getPrice() + "\t" + item.getQuantity() + "\t" + item.getThreshold());
			}
			writer.close();
		} catch (IOException e) {
		}
	}
	private void writeGeneratedReport(String report, String filename) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(REPORTS_LOCATION + filename));
			writer.write(report);
			writer.close();
		} catch (IOException e) {
		}
	}
	public void generateReport() {
		StringBuilder builder = new StringBuilder();
		ArrayList<Transaction> transactions = this.loadTransactionsFromFiles();
		int numberOfSales = 0;
		int totalSalesAmount = 0;
		int totalReturnsAmount = 0;
		int net = 0;
		builder.append("Store Report:");
		for (Transaction t : transactions) {
			numberOfSales++;
			totalSalesAmount += t.getPurchasedSubtotal();
			totalReturnsAmount += t.getReturnedSubtotal();
			builder.append("\n\n**********************************\n" + t);
		}
		net = totalSalesAmount - totalReturnsAmount;
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		String totalMadeFormatted = formatter.format(totalSalesAmount/100.0);
		String meanMadeFormatted = formatter.format(1.0*totalSalesAmount/numberOfSales/100.0);
		String totalLostFormatted = formatter.format(totalReturnsAmount/100.0);
		String meanLostFormatted = formatter.format(1.0*totalReturnsAmount/numberOfSales/100.0);
		String totalNetFormatted = formatter.format(net/100.0);
		String meanNetFormatted = formatter.format(1.0*net/numberOfSales/100.0);
		builder.append("\n\n**********************************");
		builder.append("\nStatistics:");
		builder.append("\nNumber of transactions: " + numberOfSales);
		builder.append("\nTotal money made: " + totalMadeFormatted);
		builder.append("\nMean money made per transaction: " + meanMadeFormatted);
		builder.append("\nTotal money lost: " + totalLostFormatted);
		builder.append("\nMean money lost per transaction: " + meanLostFormatted);
		builder.append("\nNet money made: " + totalNetFormatted);
		builder.append("\nNet money made per transaction: " + meanNetFormatted);
		this.writeGeneratedReport(builder.toString(), "Store Report - " + new Date());
	}
	public void generateReport(Employee employee) {
		StringBuilder builder = new StringBuilder();
		ArrayList<Transaction> transactions = this.loadTransactionsFromFiles();
		int numberOfSales = 0;
		int totalSalesAmount = 0;
		int totalReturnsAmount = 0;
		int net = 0;
		builder.append("Store Report:");
		for (Transaction t : transactions) {
			if (t.getEmployee().equals(employee)) {
				numberOfSales++;
				totalSalesAmount += t.getPurchasedSubtotal();
				totalReturnsAmount += t.getReturnedSubtotal();
				builder.append("\n\n**********************************\n" + t);
			}
		}
		net = totalSalesAmount - totalReturnsAmount;
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		String totalMadeFormatted = formatter.format(totalSalesAmount/100.0);
		String meanMadeFormatted = formatter.format(1.0*totalSalesAmount/numberOfSales/100.0);
		String totalLostFormatted = formatter.format(totalReturnsAmount/100.0);
		String meanLostFormatted = formatter.format(1.0*totalReturnsAmount/numberOfSales/100.0);
		String totalNetFormatted = formatter.format(net/100.0);
		String meanNetFormatted = formatter.format(1.0*net/numberOfSales/100.0);
		builder.append("\n\n**********************************");
		builder.append("\nStatistics:");
		builder.append("\nNumber of transactions: " + numberOfSales);
		builder.append("\nTotal money made: " + totalMadeFormatted);
		builder.append("\nMean money made per transaction: " + meanMadeFormatted);
		builder.append("\nTotal money lost: " + totalLostFormatted);
		builder.append("\nMean money lost per transaction: " + meanLostFormatted);
		builder.append("\nNet money made: " + totalNetFormatted);
		builder.append("\nNet money made per transaction: " + meanNetFormatted);
		this.writeGeneratedReport(builder.toString(), "Employee Report - " + employee + " - " + new Date());
	}
	private ArrayList<Transaction> loadTransactionsFromFiles() {
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		File path = new File(TRANSACTION_LOCATION);
		File[] files = path.listFiles();
		for (File f : files) {
			if (f.isFile() && f.getName().endsWith(".txt")) {
				try {
					BufferedReader reader = new BufferedReader(new FileReader(f));
					int tid = Integer.parseInt(reader.readLine());
					Date ts = new Date(reader.readLine());
					int eid = Integer.parseInt(reader.readLine());
					Employee empl = new Employee(-1,"error","eror","error");
					for (Employee j : this.employees) {
						if (j.getID() == eid) {
							empl = j;
							break;
						}
					}
					int refsSize = Integer.parseInt(reader.readLine());
					HashSet<Integer> refs = new HashSet<Integer>();
					for (int i = 0; i < refsSize; i++) {
						refs.add(new Integer(reader.readLine()));
					}
					int purchSize = Integer.parseInt(reader.readLine());
					HashSet<ItemQuantity> purch = new HashSet<ItemQuantity>();
					for (int i = 0; i < purchSize; i++) {
						StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
						int pid = Integer.parseInt(tokenizer.nextToken());
						int pq = Integer.parseInt(tokenizer.nextToken());
						ItemType type = new ItemType(pid,"error",-1);
						for (ItemQuantity j : this.getInventory()) {
							if (j.getItemType().getID() == pid) {
								type = j.getItemType();
								break;
							}
						}
						purch.add(new ItemQuantity(type,pq));
					}
					int retSize = Integer.parseInt(reader.readLine());
					HashSet<ItemQuantity> ret = new HashSet<ItemQuantity>();
					for (int i = 0; i < retSize; i++) {
						StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
						int pid = Integer.parseInt(tokenizer.nextToken());
						int pq = Integer.parseInt(tokenizer.nextToken());
						ItemType type = new ItemType(pid,"error",-1);
						for (ItemQuantity j : this.getInventory()) {
							if (j.getItemType().getID() == pid) {
								type = j.getItemType();
								break;
							}
						}
						ret.add(new ItemQuantity(type,pq));
					}
					reader.close();
					transactions.add(new Transaction(tid,ts,empl,refs,purch,ret));
				} catch (FileNotFoundException e) {
				} catch (NumberFormatException e) {
				} catch (IOException e) {
				}
			}
		}
		//bubble sort
		for (int i = 0; i < transactions.size(); i++) {
			for (int j = 0; j < (transactions.size() - 1 - i); j++) {
				if (transactions.get(j).getTimestamp().compareTo(transactions.get(j+1).getTimestamp()) > 0) {
					transactions.add(j, transactions.remove(j+1));
				}
			}
		}
		return transactions;
	}
	private void writeTransactionToFile(Transaction t) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTION_LOCATION + t.getTimestamp()));
			writer.write(t.getID()+"\n"+t.getTimestamp()+"\n"+t.getEmployee().getID());
			writer.write("\n" + t.getReferencedIDs().size());
			for (Integer i : t.getReferencedIDs()) {
				writer.write("\n" + i);
			}
			writer.write("\n" + t.getPurchased().size());
			for (ItemQuantity i : t.getPurchased()) {
				writer.write("\n" + i.getItemType().getID() + "," + i.getQuantity());
			}
			writer.write("\n" + t.getReturned().size());
			for (ItemQuantity i : t.getReturned()) {
				writer.write("\n" + i.getItemType().getID() + "," + i.getQuantity());
			}
			writer.close();
		} catch (IOException e) {
		}
	}
	public void saveState() {
		this.writeDrawerToFile();
		this.writeEmployeesToFile();
	}
}

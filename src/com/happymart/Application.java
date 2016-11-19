package com.happymart;

public class Application implements Runnable {
	
	private Register reg;
	private int lastItemScannedID;
	private boolean lastItemIsReturn;
	
	public Application() {
		this.reg = new Register();
		this.lastItemScannedID = -1;
	}

	@Override
	public void run() {
		while (this.login()) {
			while(this.home()) {
				
			}
		}
	}
	
	private boolean login () {
		String input = "";
		boolean first = true;
		while (!InputType.isExit(input) && !InputType.isQuit(input)) {
			if (first) {
				input = PromptAndInput.LOGIN_FIRST_USERNAME.getInput();
				if (InputType.isCancel(input) || InputType.isExit(input) || InputType.isQuit(input)) {
					continue;
				}
				else {
					String username = input;
					input = PromptAndInput.LOGIN_FIRST_PASSWORD.getInput(username);
					if (InputType.isCancel(input) || InputType.isExit(input) || InputType.isQuit(input)) {
						continue;
					}
					else {
						String password = input;
						if (this.reg.signInWithCredentials(username, password)) {
							return true;
						}
						else {
							first = false;
							continue;
						}
					}
				}
			}
			else {
				input = PromptAndInput.LOGIN_USERNAME.getInput();
				if (InputType.isCancel(input) || InputType.isExit(input) || InputType.isQuit(input)) {
					continue;
				}
				else {
					String username = input;
					input = PromptAndInput.LOGIN_PASSWORD.getInput(username);
					if (InputType.isCancel(input) || InputType.isExit(input) || InputType.isQuit(input)) {
						continue;
					}
					else {
						String password = input;
						if (this.reg.signInWithCredentials(username, password)) {
							return true;
						}
						else {
							continue;
						}
					}
				}
			}
		}
		return false;
	}
	
	private boolean home() {
		String[] opts = {"open drawer","return item","scan item","manually enter volume of last scanned item","remove item","check inventory","complete sale","clear sale","log out"};
		String input = "";
		boolean first = true;
		boolean unavailable = false;
		while (!InputType.isCancel(input) && !InputType.isExit(input) && !InputType.isQuit(input)) {
			if (first) {
				input = PromptAndInput.HOME_FIRST.getInput(reg.getEmployee().getName(),reg.getTotalForTransactionAsString(),reg.getCurrentSaleAsString(),PromptAndInput.formatArray(opts));
				unavailable = false;
			}
			else {
				if (unavailable) {
					input = PromptAndInput.HOME_UNAVAILABLE.getInput(reg.getEmployee().getName(),reg.getTotalForTransactionAsString(),reg.getCurrentSaleAsString(),PromptAndInput.formatArray(opts));
				}
				else {
					input = PromptAndInput.HOME.getInput(reg.getEmployee().getName(),reg.getTotalForTransactionAsString(),reg.getCurrentSaleAsString(),PromptAndInput.formatArray(opts));
					unavailable = false;
				}
			}
			switch (Integer.parseInt(input)) {
			case 0: //open drawer
				while(this.drawer());
				break;
			case 1: //return item
				while(processReturn());
				break;
			case 2: //scan item
				while(this.scan());
				break;
			case 3: //set number of item
				if (this.lastItemScannedID != -1)
					while(this.volume());
				else
					unavailable = true;
				break;
			case 4: //remove item
				if (this.reg.getPurchasing().size() > 0 || this.reg.getReturning().size() > 0) {
					
				}
				else {
					unavailable = true;
				}
				break;
			case 5: //check inventory
				break;
			case 6: //complete sale
				if (this.reg.getPurchasing().size() > 0 || this.reg.getReturning().size() > 0) {
					
				}
				else {
					unavailable = true;
				}
				break;
			case 7: //clear sale
				this.reg.clearSale();
				this.lastItemScannedID = -1;
				break;
			case 8: //log out
				this.lastItemScannedID = -1;
				this.reg.clearSale();
				this.reg.setEmployee(null);
				this.reg.saveState();
				return false;
			default:
				first = false;
				break;
			}
		}
		return false;
	}
	
	private boolean drawer() {
		String[] opts = {"add money","take money","set money","close drawer"};
		String input = "";
		boolean first = true;
		while (!InputType.isCancel(input) && !InputType.isExit(input) && !InputType.isQuit(input)) {
			if (first) {
				input = PromptAndInput.DRAWER_HOME_FIRST.getInput(reg.getEmployee().getName(),reg.getMoneyInDrawerAsString(),PromptAndInput.formatArray(opts));
			}
			else {
				input = PromptAndInput.DRAWER_HOME.getInput(reg.getEmployee().getName(),reg.getMoneyInDrawerAsString(),PromptAndInput.formatArray(opts));
			}
			switch (Integer.parseInt(input)) {
			case 0: //add money
				input = PromptAndInput.DRAWER_ADD.getInput(reg.getEmployee().getName(),reg.getMoneyInDrawerAsString());
				if (!InputType.isCancel(input) && !InputType.isExit(input) && !InputType.isQuit(input)) {
					reg.addMoneyToDrawer((int)(Double.parseDouble(input)*100));
				}
				break;
			case 1: //take money
				boolean first_1 = true;
				while (!InputType.isCancel(input) && !InputType.isExit(input) && !InputType.isQuit(input)) {
					if (first_1) {
						input = PromptAndInput.DRAWER_TAKE_FIRST.getInput(reg.getEmployee().getName(),reg.getMoneyInDrawerAsString());
					}
					else {
						input = PromptAndInput.DRAWER_TAKE.getInput(reg.getEmployee().getName(),reg.getMoneyInDrawerAsString());
					}
					if (!InputType.isCancel(input) && !InputType.isExit(input) && !InputType.isQuit(input)) {
						if (reg.getMoneyInDrawer() >= ((int)(Double.parseDouble(input)*100))) {
							reg.removeMoneyFromDrawer((int)(Double.parseDouble(input)*100));
							break;
						}
						else {
							first_1 = false;
						}
					}
				}
				break;
			case 2: //set money
				input = PromptAndInput.DRAWER_SET.getInput(reg.getEmployee().getName(),reg.getMoneyInDrawerAsString());
				if (!InputType.isCancel(input) && !InputType.isExit(input) && !InputType.isQuit(input)) {
					reg.setMoneyInDrawer((int)(Double.parseDouble(input)*100));
				}
				break;
			case 3: //close drawer
				return false;
			default:
				first = false;
				continue;
			}
		}
		return false; //temp
	}
	
	private boolean processReturn() {
		String input = "";
		while (!InputType.isCancel(input) && !InputType.isExit(input) && !InputType.isQuit(input)) {
			input = PromptAndInput.RETURN_TRANSACTION.getInput(reg.getEmployee().getName(),reg.getTotalForTransactionAsString(),reg.getCurrentSaleAsString());
			if (!InputType.isCancel(input) && !InputType.isExit(input) && !InputType.isQuit(input)) {
				int id = Integer.parseInt(input);
				boolean first = true;
				while (!InputType.isCancel(input) && !InputType.isExit(input) && !InputType.isQuit(input)) {
					if (first) {
						input = PromptAndInput.SCAN_ITEM_FIRST.getInput(reg.getEmployee().getName(),reg.getTotalForTransactionAsString(),reg.getCurrentSaleAsString());
					}
					else {
						input = PromptAndInput.SCAN_ITEM.getInput(reg.getEmployee().getName(),reg.getTotalForTransactionAsString(),reg.getCurrentSaleAsString());
					}
					if (reg.canAddToReturnAndDo(id, Integer.parseInt(input))) {
						this.lastItemScannedID = Integer.parseInt(input);
						this.lastItemIsReturn = true;
						return false;
					}
					else {
						this.lastItemScannedID = -1;
						first = false;
					}
				}
			}
		}
		return false;
	}
	
	private boolean scan() {
		String input = "";
		boolean first = true;
		while (!InputType.isCancel(input) && !InputType.isExit(input) && !InputType.isQuit(input)) {
			if (first) {
				input = PromptAndInput.SCAN_ITEM_FIRST.getInput(reg.getEmployee().getName(),reg.getTotalForTransactionAsString(),reg.getCurrentSaleAsString());
			}
			else {
				input = PromptAndInput.SCAN_ITEM.getInput(reg.getEmployee().getName(),reg.getTotalForTransactionAsString(),reg.getCurrentSaleAsString());
			}
			if (reg.canAddToPurchasingAndDo(Integer.parseInt(input), 1)) {
				this.lastItemScannedID = Integer.parseInt(input);
				this.lastItemIsReturn = false;
				return false;
			}
			else {
				this.lastItemScannedID = -1;
				first = false;
			}
		}
		return false;
	}
	
	private boolean volume() {
		String input = "";
		while (!InputType.isCancel(input) && !InputType.isExit(input) && !InputType.isQuit(input)) {
			input = PromptAndInput.VOLUME.getInput(reg.getEmployee().getName(),reg.getTotalForTransactionAsString(),reg.getCurrentSaleAsString(),reg.getItemType(this.lastItemScannedID).getName());
			if (!InputType.isCancel(input) && !InputType.isExit(input) && !InputType.isQuit(input)) {
				if (Integer.parseInt(input) == 0) {
					this.reg.removeFromPurchasing(this.reg.getItemType(this.lastItemScannedID));
				}
				else {
					this.reg.addToPurchasing(new ItemQuantity(this.reg.getItemType(this.lastItemScannedID),Integer.parseInt(input)));
				}
				break;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		new Application().run();
	}
}

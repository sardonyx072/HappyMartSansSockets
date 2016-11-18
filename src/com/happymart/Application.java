package com.happymart;

import java.text.NumberFormat;

public class Application implements Runnable {
	
	private Register reg;
	
	public Application() {
		this.reg = new Register();
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
		String[] opts = {"open drawer","scan item","set number of item","remove item","check inventory","clear sale","log out"};
		String input = "";
		boolean first = true;
		while (!InputType.isCancel(input) && !InputType.isExit(input) && !InputType.isQuit(input)) {
			if (first) {
				input = PromptAndInput.HOME_FIRST.getInput(reg.getEmployee().getName(),NumberFormat.getCurrencyInstance().format(reg.getTotalForTransaction()),reg.getPurchasingAsString(),PromptAndInput.formatArray(opts));
			}
			else {
				input = PromptAndInput.HOME.getInput(reg.getEmployee().getName(),NumberFormat.getCurrencyInstance().format(reg.getTotalForTransaction()),reg.getPurchasingAsString(),PromptAndInput.formatArray(opts));
			}
			switch (Integer.parseInt(input)) {
			case 0: //open drawer
				while(this.drawer());
				break;
			case 1: //scan item
				break;
			case 2: //set number of item
				break;
			case 3: //remove item
				break;
			case 4: //check inventory
				break;
			case 5: //clear sale
				this.reg.clearSale();
				break;
			case 6: //log out
				this.reg.clearSale();
				this.reg.setEmployee(null);
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
	
	private boolean scan() {
		String input = "";
		boolean first = true;
		while (!InputType.isCancel(input) && !InputType.isExit(input) && !InputType.isQuit(input)) {
			if (first) {
				input = PromptAndInput.SCAN_ITEM_FIRST.getInput(reg.getEmployee().getName(),NumberFormat.getCurrencyInstance().format(reg.getTotalForTransaction()),reg.getPurchasingAsString());
			}
			else {
				input = PromptAndInput.SCAN_ITEM.getInput(reg.getEmployee().getName(),NumberFormat.getCurrencyInstance().format(reg.getTotalForTransaction()),reg.getPurchasingAsString());
			}
			if (reg.canAddToPurchasingAndDo(Integer.parseInt(input), 1));
		}
		return false;
	}
	
	public static void main(String[] args) {
		new Application().run();
	}
}

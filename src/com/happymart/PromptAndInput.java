package com.happymart;

import java.io.IOException;
import java.util.Scanner;

public enum PromptAndInput {
	LOGIN_FIRST_USERNAME(
			"Welcome to Happy Mart Store Manager!" 								+ "\n" +
			"" 																	+ "\n" +
			"Please sign in with your employee username and password:" 			+ "\n" +
			"Username: ",InputType.TEXT
			),
	LOGIN_FIRST_PASSWORD(
			"Welcome to Happy Mart Store Manager!" 								+ "\n" +
			"" 																	+ "\n" +
			"Please sign in with your employee username and password:" 			+ "\n" +
			"Username: {{0}}"													+ "\n" +
			"Password: ",InputType.TEXT_OR_CANCEL
			),
	LOGIN_USERNAME(
			"Welcome to Happy Mart Store Manager!" 								+ "\n" +
			"" 																	+ "\n" +
			"Incorrect username and password combination entered."				+ "\n" +
			"Please sign in with your employee username and password:" 			+ "\n" +
			"Username: ",InputType.TEXT
			),
	LOGIN_PASSWORD(
			"Welcome to Happy Mart Store Manager!" 								+ "\n" +
			"" 																	+ "\n" +
			"Incorrect username and password combination entered."				+ "\n" +
			"Please sign in with your employee username and password:" 			+ "\n" +
			"Username: {{0}}"													+ "\n" +
			"Password: ",InputType.TEXT_OR_CANCEL
			),
	HOME_FIRST(
			"Happy Mart Store Manager"											+ "\n" +
			"Currently signed in as: {{0}}"										+ "\n" +
			"****************************************************************"	+ "\n" +
			"Current Sale: {{1}}"												+ "\n" +
			"{{2}}"																+ "\n" +
			"****************************************************************"	+ "\n" +
			"Options: "															+ "\n" +
			"{{3}}"																+ "\n" +
			""																	+ "\n" +
			"> ",InputType.OPTION_OR_CANCEL
			),
	HOME_UNAVAILABLE(
			"Happy Mart Store Manager"											+ "\n" +
			"Currently signed in as: {{0}}"										+ "\n" +
			"****************************************************************"	+ "\n" +
			"Current Sale: {{1}}"												+ "\n" +
			"{{2}}"																+ "\n" +
			"****************************************************************"	+ "\n" +
			"Options: "															+ "\n" +
			"{{3}}"																+ "\n" +
			""																	+ "\n" +
			"Option unavailable at this time."									+ "\n" +
			"> ",InputType.OPTION_OR_CANCEL
			),
	HOME(
			"Happy Mart Store Manager"											+ "\n" +
			"Currently signed in as: {{0}}"										+ "\n" +
			"****************************************************************"	+ "\n" +
			"Current Sale: {{1}}"												+ "\n" +
			"{{2}}"																+ "\n" +
			"****************************************************************"	+ "\n" +
			"Options: "															+ "\n" +
			"{{3}}"																+ "\n" +
			""																	+ "\n" +
			"Invalid input."													+ "\n" +
			"> ",InputType.OPTION_OR_CANCEL
			),
	DRAWER_HOME_FIRST(
			"Happy Mart Store Manager"											+ "\n" +
			"Currently signed in as: {{0}}"										+ "\n" +
			"****************************************************************"	+ "\n" +
			"Drawer Contains: {{1}}"											+ "\n" +
			"****************************************************************"	+ "\n" +
			"Options: "															+ "\n" +
			"{{2}}"																+ "\n" +
			""																	+ "\n" +
			"> ",InputType.OPTION_OR_CANCEL
			),
	DRAWER_HOME(
			"Happy Mart Store Manager"											+ "\n" +
			"Currently signed in as: {{0}}"										+ "\n" +
			"****************************************************************"	+ "\n" +
			"Drawer Contains: {{1}}"											+ "\n" +
			"****************************************************************"	+ "\n" +
			"Options: "															+ "\n" +
			"{{2}}"																+ "\n" +
			""																	+ "\n" +
			"Invalid input."													+ "\n" +
			"> ",InputType.OPTION_OR_CANCEL
			),
	DRAWER_ADD(
			"Happy Mart Store Manager"											+ "\n" +
			"Currently signed in as: {{0}}"										+ "\n" +
			"****************************************************************"	+ "\n" +
			"Drawer Contains: {{1}}"											+ "\n" +
			"****************************************************************"	+ "\n" +
			"Add how much?: ",InputType.MONEY_OR_CANCEL
			),
	DRAWER_TAKE_FIRST(
			"Happy Mart Store Manager"											+ "\n" +
			"Currently signed in as: {{0}}"										+ "\n" +
			"****************************************************************"	+ "\n" +
			"Drawer Contains: {{1}}"											+ "\n" +
			"****************************************************************"	+ "\n" +
			"Take how much?: ",InputType.MONEY_OR_CANCEL
			),
	DRAWER_TAKE(
			"Happy Mart Store Manager"											+ "\n" +
			"Currently signed in as: {{0}}"										+ "\n" +
			"****************************************************************"	+ "\n" +
			"Drawer Contains: {{1}}"											+ "\n" +
			"****************************************************************"	+ "\n" +
			"Invalid input."													+ "\n" +
			"Take how much?: ",InputType.MONEY_OR_CANCEL
			),
	DRAWER_SET(
			"Happy Mart Store Manager"											+ "\n" +
			"Currently signed in as: {{0}}"										+ "\n" +
			"****************************************************************"	+ "\n" +
			"Drawer Contains: {{1}}"											+ "\n" +
			"****************************************************************"	+ "\n" +
			"Set drawer value to how much?: ",InputType.MONEY_OR_CANCEL
			),
	RETURN_TRANSACTION(
			"Happy Mart Store Manager"											+ "\n" +
			"Currently signed in as: {{0}}"										+ "\n" +
			"****************************************************************"	+ "\n" +
			"Current Sale: {{1}}"												+ "\n" +
			"{{2}}"																+ "\n" +
			"****************************************************************"	+ "\n" +
			"Enter Receipt Number: ",InputType.OPTION_OR_CANCEL
			),
	SCAN_ITEM_FIRST(
			"Happy Mart Store Manager"											+ "\n" +
			"Currently signed in as: {{0}}"										+ "\n" +
			"****************************************************************"	+ "\n" +
			"Current Sale: {{1}}"												+ "\n" +
			"{{2}}"																+ "\n" +
			"****************************************************************"	+ "\n" +
			"Enter Item ID: ",InputType.OPTION_OR_CANCEL
			),
	SCAN_ITEM(
			"Happy Mart Store Manager"											+ "\n" +
			"Currently signed in as: {{0}}"										+ "\n" +
			"****************************************************************"	+ "\n" +
			"Current Sale: {{1}}"												+ "\n" +
			"{{2}}"																+ "\n" +
			"****************************************************************"	+ "\n" +
			"Invalid item ID."													+ "\n" +
			"Enter Item ID: ",InputType.OPTION_OR_CANCEL
			),
	VOLUME(
			"Happy Mart Store Manager"											+ "\n" +
			"Currently signed in as: {{0}}"										+ "\n" +
			"****************************************************************"	+ "\n" +
			"Current Sale: {{1}}"												+ "\n" +
			"{{2}}"																+ "\n" +
			"****************************************************************"	+ "\n" +
			"Enter number of {{3}} items: ",InputType.OPTION_OR_CANCEL
			),
	REMOVE_ITEM_FIRST(
			"Happy Mart Store Manager"											+ "\n" +
			"Currently signed in as: {{0}}"										+ "\n" +
			"****************************************************************"	+ "\n" +
			"Current Sale: {{1}}"												+ "\n" +
			"{{2}}"																+ "\n" +
			"****************************************************************"	+ "\n" +
			"Enter Item ID: ",InputType.OPTION_OR_CANCEL
			),
	CONFIRM_CANCEL_SALE(
			"Happy Mart Store Manager"											+ "\n" +
			"Currently signed in as: {{0}}"										+ "\n" +
			"****************************************************************"	+ "\n" +
			"Current Sale: {{1}}"												+ "\n" +
			"{{2}}"																+ "\n" +
			"****************************************************************"	+ "\n" +
			"Are you sure you want to cancel sale?: ",InputType.OK_OR_CANCEL
			);
    
	private String prompt;
	private InputType input;
	private Scanner scan;
	
	private PromptAndInput(String prompt, InputType input) {
		this.prompt = prompt;
		this.input = input;
		this.scan = new Scanner(System.in);
	}
	
	private static void clrscreen() {
			try {
				if (System.getProperty("os.name").contains("Windows"))
					new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
				else
					Runtime.getRuntime().exec("clear");
			} catch (InterruptedException | IOException e) {
			}
	}
	
	public static String formatArray(Object[] array) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			builder.append("\n" + i + ". " + array[i]);
		}
		return builder.toString();
	}
	
	public String getInput (String...strings) { //lists must be preformatted
		String temp = this.prompt;
		int i = 0;
		while (strings.length > 0 && temp.contains("{{"+i+"}}")) {
			temp = temp.replace("{{"+i+"}}", strings[i]);
			i++;
		}
		String raw;
		do {
			clrscreen();
			System.out.print(temp);
			raw = scan.nextLine();
		} while(!this.input.isValid(raw));
		return raw;
	}
}

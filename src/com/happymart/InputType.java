package com.happymart;

public enum InputType {
	OK,
	OK_OR_CANCEL,
	TEXT,
	TEXT_OR_CANCEL,
	OPTION,
	OPTION_OR_CANCEL,
	MONEY,
	MONEY_OR_CANCEL;
	
	private static final String CANCEL_MATCH = "cancel";
	private static final String OK_MATCH = "";
	private static final String QUIT_MATCH = "quit";
	private static final String EXIT_MATCH = "exit";
	
	public boolean isValid(String input) {
		switch(this) {
		case OK:
			return this.isOK(input);
		case OK_OR_CANCEL:
			return this.isOK(input) || this.isCancel(input);
		case TEXT:
			return this.isText(input);
		case TEXT_OR_CANCEL:
			return this.isText(input) || this.isCancel(input);
		case OPTION:
			return this.isOption(input);
		case OPTION_OR_CANCEL:
			return this.isOption(input) || this.isCancel(input);
		case MONEY:
			return this.isMoney(input);
		case MONEY_OR_CANCEL:
			return this.isMoney(input) || this.isCancel(input);
		default:
			return false;	
		}
	}
	
	public static boolean isCancel(String input) {
		return input.equals(CANCEL_MATCH);
	}
	
	public static boolean isQuit(String input) {
		return input.equals(QUIT_MATCH);
	}
	
	public static boolean isExit(String input) {
		return input.equals(EXIT_MATCH);
	}
	
	public static boolean isOK(String input) {
		return input.equals(OK_MATCH);
	}
	
	public static boolean isOption(String input) {
		//return input.matches("^?\\d+$");
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean isMoney(String input) {
		//return input.matches("?\\d+(\\.\\d+)?"); //wrong
		try {
			Double.parseDouble(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean isText(String input) {
		return input.length() > 0;
	}
}

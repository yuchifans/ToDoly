package com.siqi.taskadmin;

public class ToDolyMainEntry implements DialogGUI {

	final static int TOPLEVEL = 0;
	private CommandParser commandParser;
	private CommandMenu topMenu;

	ToDolyMainEntry() {
		commandParser = new CommandParser();
		topMenu = new CommandMenu();
	}

	public void start() {

		printWelcome();

		// Enter the main command loop. Here we repeatedly read commands and
		// execute them until the game is over.

		boolean finished = false;
		while (!finished) {
			Command command = commandParser.getTopMenuCommand();
			finished = processCommand(command);
		}
	}

	public boolean processCommand(Command command) {
		boolean wantToQuit = false;
		CommandWord commandWord = command.getCommandWord();
		switch (commandWord) {
		case UNKNOWN:
			System.out.println("Please type in a proper number...");
			break;
		case SHOW:
			show();
			wantToQuit = true;
			break;
		case ADD:
			System.out.println("add");
			break;
		case EDIT:
			System.out.println("edit");
			break;
		case SAVE:
			wantToQuit = quit(command);
			break;
		}
		return wantToQuit;
	}

	private void printWelcome() {
		System.out.println();
		System.out.println("Welcome to ToDoly!");
		System.out.println("Pick an option:");
		topMenu.printTopMenu();
	}

	private boolean quit(Command command) {
		System.out.println("Thank you for using ToDoly.  Good bye.");
		return true; // signal that we want to quit
	}

	private void show() {
		TaskSortGUI taskSortGUI = new TaskSortGUI();
		taskSortGUI.start();

	}

	public static void main(String[] args) {
		ToDolyMainEntry mainEntry = new ToDolyMainEntry();
		mainEntry.start();
	}
}

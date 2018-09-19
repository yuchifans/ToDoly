package com.siqi.taskadmin;

public class ToDolyMainEntry implements DialogGUI {

	final static int TOPLEVEL = 0;
	private CommandParser commandParser;
	private CommandMenu topMenu;
	private TaskDataProcessor taskDataProcessor;
	private int[] tasksNumberBystatus;
	

	ToDolyMainEntry() {
		commandParser = new CommandParser();
		topMenu = new CommandMenu();
		taskDataProcessor=new TaskDataProcessor();
		tasksNumberBystatus=new int[2];
	}

	public void start() {

		printWelcome();

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
			add();
			wantToQuit = true;
			break;
		case QUIT:
			wantToQuit = quit(command);
			break;
		}
		return wantToQuit;
	}

	private void printWelcome() {
		taskDataProcessor.load();
		tasksNumberBystatus=taskDataProcessor.getNumberOfTasksByStatus();
		System.out.println();
		System.out.println("Welcome to ToDoly!");
		System.out.println("You have "+tasksNumberBystatus[0]+" tasks todo and "+tasksNumberBystatus[1]+" tasks done.");
		topMenu.printTopMenu();
	}

	private boolean quit(Command command) {
		System.out.println("Thank you for using ToDoly.  Good bye.");
		return true; // signal that we want to quit
	}

	private void show() {
		System.out.println("------------------------------------------------------------------------------");
		TaskSortGUI taskSortGUI = new TaskSortGUI();
		taskSortGUI.start();
	}
	
	private void add() {
		System.out.println("------------------------------------------------------------------------------");
		AddTaskGUI addTaskGUI = new AddTaskGUI();
		addTaskGUI.start();
	}

	public static void main(String[] args) {
		ToDolyMainEntry mainEntry = new ToDolyMainEntry();
		mainEntry.start();
	}
}

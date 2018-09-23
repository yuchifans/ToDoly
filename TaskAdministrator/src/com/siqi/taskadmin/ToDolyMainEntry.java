package com.siqi.taskadmin;

import com.siqi.taskadmin.GUI.AddTaskGUI;
import com.siqi.taskadmin.GUI.DialogGUI;
import com.siqi.taskadmin.GUI.TaskSortGUI;
import com.siqi.taskadmin.data.TaskDataProcessor;
import com.siqi.taskadmin.menu.CommandMenu;
import com.siqi.taskadmin.parser.Command;
import com.siqi.taskadmin.parser.CommandParser;
import com.siqi.taskadmin.parser.CommandWord;

public class ToDolyMainEntry implements DialogGUI {

	private CommandParser commandParser;
	private CommandMenu topMenu;
	private TaskDataProcessor taskDataProcessor;
	private int[] tasksNumberBystatus;
	

	public ToDolyMainEntry() {
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
			wantToQuit = show();
			break;
		case ADD:
			wantToQuit = add();
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

	private boolean show() {
		System.out.println("------------------------------------------------------------------------------");
		TaskSortGUI taskSortGUI = new TaskSortGUI();
		taskSortGUI.start();
		return true;
	}
	
	private boolean add() {
		System.out.println("------------------------------------------------------------------------------");
		AddTaskGUI addTaskGUI = new AddTaskGUI();
		addTaskGUI.start();
		return true;
	}

	public static void main(String[] args) {
		ToDolyMainEntry mainEntry = new ToDolyMainEntry();
		mainEntry.start();
	}
}

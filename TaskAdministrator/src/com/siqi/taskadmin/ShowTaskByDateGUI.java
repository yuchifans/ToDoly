package com.siqi.taskadmin;

import java.util.ArrayList;

public class ShowTaskByDateGUI implements DialogGUI {
	private CommandMenu childMenuOfByDate;
	private CommandParser commandParser;
	private TaskDataProcessor dataProcessor;
	private Tasks tasks;
	
	
	ShowTaskByDateGUI(){
		commandParser = new CommandParser();
		childMenuOfByDate = new CommandMenu();
		dataProcessor = new TaskDataProcessor();
		tasks=new Tasks();
	}
	
	public void start() {
		dataProcessor.load();
		tasks=dataProcessor.sortByDate();
		if (tasks!=null&&tasks.getNumberOfTask()!=0) {
			tasks.showAllTheTask();
			childMenuOfByDate.printChildMenu(CommandWord.BYDATE);
		} else {
			System.out.println("There is no task currently!");
			returnToMain();
		}
		boolean finished = false;
		while (!finished) {
			Command command = commandParser.getChildMenuCommand(CommandWord.BYDATE);
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
		case EDIT:
			wantToQuit = true;
			break;
		case REMOVE:
			remove();
			wantToQuit = true;
			break;
		case RETURNTOMAIN:
			returnToMain();
			break;
		}
		return wantToQuit;	
	}
	private void returnToMain() {
		System.out.println("------------------------------------------------------------------------------");
		ToDolyMainEntry main = new ToDolyMainEntry();
		main.start();
	}
	
	private void remove() {
		System.out.println("------------------------------------------------------------------------------");
		ArrayList<Task> tasksTmp=(ArrayList<Task>)tasks.getTasks();
		RemoveTaskGUI removeTaskGUI=new RemoveTaskGUI(tasksTmp);
		removeTaskGUI.start();	
	}
	
	

}

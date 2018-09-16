package com.siqi.taskadmin;

public class ShowTaskByDateGUI implements DialogGUI {
	private CommandMenu childMenuOfByDate;
	private CommandParser commandParser;
	private TaskDataProcessor dataProcessor;
	
	ShowTaskByDateGUI(){
		commandParser = new CommandParser();
		childMenuOfByDate = new CommandMenu();
		dataProcessor = new TaskDataProcessor();
	}
	
	public void start() {
		dataProcessor.load();
		Tasks tasks = dataProcessor.read();
		if (tasks != null) {
			tasks.showAllTheTask();
			childMenuOfByDate.printChildMenu(CommandWord.BYDATE);
		} else {
			System.out.println("There is no task currently!");
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

}

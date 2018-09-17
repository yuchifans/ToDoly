package com.siqi.taskadmin;

public class FilterTaskByProjectGUI implements DialogGUI {
	private TaskDataProcessor dataProcessor;
	private ProjectNameParser projectNameParser;
	private CommandMenu childMenuOfFilterProject;
	private CommandParser commandParser;
	
	public FilterTaskByProjectGUI() {
		dataProcessor = new TaskDataProcessor();	
		projectNameParser=new ProjectNameParser();
		childMenuOfFilterProject= new CommandMenu();
		commandParser= new CommandParser();
	}
	
	public void start() {
		System.out.println("Please input project name: ");
		String projectName=projectNameParser.readProjectName();
		dataProcessor.load();
		Tasks tasks=dataProcessor.filterByProject(projectName);
		if (tasks != null) {
			tasks.showAllTheTask();
			childMenuOfFilterProject.printChildMenu(CommandWord.BYPROJECT);
		} else {
			System.out.println("There is no task currently!");
		}
		boolean finished = false;
		while (!finished) {
			Command command = commandParser.getChildMenuCommand(CommandWord.BYPROJECT);
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

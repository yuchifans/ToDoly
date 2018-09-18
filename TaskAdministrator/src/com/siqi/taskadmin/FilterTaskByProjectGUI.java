package com.siqi.taskadmin;

import java.util.ArrayList;

public class FilterTaskByProjectGUI implements DialogGUI {
	private TaskDataProcessor dataProcessor;
	private ProjectNameParser projectNameParser;
	private CommandMenu childMenuOfFilterProject;
	private CommandParser commandParser;
	private Tasks tasks;

	public FilterTaskByProjectGUI() {
		dataProcessor = new TaskDataProcessor();
		projectNameParser = new ProjectNameParser();
		childMenuOfFilterProject = new CommandMenu();
		commandParser = new CommandParser();
		tasks = new Tasks();
	}

	public void start() {
		System.out.println("Please input project name: ");
		String projectName = projectNameParser.readProjectName();
		dataProcessor.load();
		tasks = dataProcessor.filterByProject(projectName);
		boolean finished = false;
		if (tasks != null && tasks.getNumberOfTask() != 0) {
			tasks.showAllTheTask();
			childMenuOfFilterProject.printChildMenu(CommandWord.BYPROJECT);
			while (!finished) {
				Command command = commandParser.getChildMenuCommand(CommandWord.BYPROJECT);
				finished = processCommand(command);
			}
		} else {
			System.out.println("There is no task currently!");
			returnToMain();
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
			wantToQuit = true;
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
		ArrayList<Task> tasksTmp = (ArrayList<Task>) tasks.getTasks();
		RemoveTaskGUI removeTaskGUI = new RemoveTaskGUI(tasksTmp);
		removeTaskGUI.start();
	}

}

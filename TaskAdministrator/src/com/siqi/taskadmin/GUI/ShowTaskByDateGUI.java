package com.siqi.taskadmin.GUI;

import java.util.ArrayList;

import com.siqi.taskadmin.ToDolyMainEntry;
import com.siqi.taskadmin.data.TaskDataProcessor;
import com.siqi.taskadmin.menu.CommandMenu;
import com.siqi.taskadmin.model.Task;
import com.siqi.taskadmin.model.Tasks;
import com.siqi.taskadmin.parser.Command;
import com.siqi.taskadmin.parser.CommandParser;
import com.siqi.taskadmin.parser.CommandWord;

public class ShowTaskByDateGUI implements DialogGUI {
	private CommandMenu childMenuOfByDate;
	private CommandParser commandParser;
	private TaskDataProcessor dataProcessor;
	private Tasks tasks;

	ShowTaskByDateGUI() {
		commandParser = new CommandParser();
		childMenuOfByDate = new CommandMenu();
		dataProcessor = new TaskDataProcessor();
		tasks = new Tasks();
	}

	public void start() {
		dataProcessor.load();
		tasks = dataProcessor.sortByDate();
		if (tasks != null && tasks.getNumberOfTask() != 0) {
			tasks.showAllTheTask();
			childMenuOfByDate.printChildMenu(CommandWord.BYDATE);
			boolean finished = false;
			while (!finished) {
				Command command = commandParser.getChildMenuCommand(CommandWord.BYDATE);
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
			edit();
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
	
	private void edit() {
		System.out.println("------------------------------------------------------------------------------");
		ArrayList<Task> tasksTmp = (ArrayList<Task>) tasks.getTasks();
		EditTaskGUI editTaskGUI = new EditTaskGUI(tasksTmp);
		editTaskGUI.start();
	}

}

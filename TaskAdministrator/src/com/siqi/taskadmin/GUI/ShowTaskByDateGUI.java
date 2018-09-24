package com.siqi.taskadmin.GUI;

import com.siqi.taskadmin.ToDolyMainEntry;
import com.siqi.taskadmin.menu.CommandMenu;
import com.siqi.taskadmin.model.Tasks;
import com.siqi.taskadmin.controller.TasksAdmin;
import com.siqi.taskadmin.parser.Command;
import com.siqi.taskadmin.parser.CommandParser;
import com.siqi.taskadmin.parser.CommandWord;

public class ShowTaskByDateGUI implements DialogGUI {
	private CommandMenu childMenuOfByDate;
	private CommandParser commandParser;
	private Tasks tasks;
	private TasksAdmin tasksAdmin;

	public ShowTaskByDateGUI(Tasks tasks) {
		commandParser = new CommandParser();
		childMenuOfByDate = new CommandMenu();
		this.tasks = tasks;
		tasksAdmin = new TasksAdmin();
	}

	public void start() {
		Tasks tasksByDate=tasksAdmin.getTasksSortByDate(tasks);
		if (tasksByDate != null && tasksByDate.getNumberOfTask() != 0) {
			tasksByDate.showAllTheTask();
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
			wantToQuit = edit();
			break;
		case REMOVE:
			wantToQuit = remove();
			break;
		case RETURNTOMAIN:
			wantToQuit = returnToMain();
			break;
		}
		return wantToQuit;
	}

	private boolean returnToMain() {
		System.out.println("------------------------------------------------------------------------------");
		ToDolyMainEntry main = new ToDolyMainEntry();
		main.start();
		return true;
	}

	private boolean remove() {
		System.out.println("------------------------------------------------------------------------------");
		RemoveTaskGUI removeTaskGUI = new RemoveTaskGUI(tasks,tasks);
		removeTaskGUI.start();
		return true;
	}
	
	private boolean edit() {
		System.out.println("------------------------------------------------------------------------------");
		EditTaskGUI editTaskGUI = new EditTaskGUI(tasks);
		editTaskGUI.start();
		return true;
	}

}

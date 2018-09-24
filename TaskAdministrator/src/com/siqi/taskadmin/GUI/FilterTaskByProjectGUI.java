package com.siqi.taskadmin.GUI;

import com.siqi.taskadmin.ToDolyMainEntry;
import com.siqi.taskadmin.menu.CommandMenu;
import com.siqi.taskadmin.model.Tasks;
import com.siqi.taskadmin.controller.TasksAdmin;
import com.siqi.taskadmin.parser.Command;
import com.siqi.taskadmin.parser.CommandParser;
import com.siqi.taskadmin.parser.CommandWord;
import com.siqi.taskadmin.parser.ProjectNameParser;

public class FilterTaskByProjectGUI implements DialogGUI {
	private ProjectNameParser projectNameParser;
	private CommandMenu childMenuOfFilterProject;
	private CommandParser commandParser;
	private Tasks tasks;
	private Tasks tasksFiltered;
	private TasksAdmin tasksAdmin;

	public FilterTaskByProjectGUI(Tasks tasks) {
		projectNameParser = new ProjectNameParser();
		childMenuOfFilterProject = new CommandMenu();
		commandParser = new CommandParser();
		this.tasks = tasks;
		tasksFiltered = new Tasks();
		tasksAdmin = new TasksAdmin();
	}

	public void start() {
		System.out.println("Please input project name: ");
		String projectName = projectNameParser.readProjectName();
		tasksFiltered=tasksAdmin.getTasksFilterByProject(projectName,tasks);
		boolean finished = false;
		if (tasksFiltered != null && tasksFiltered.getNumberOfTask() != 0) {
			tasksFiltered.showAllTheTask();
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
		RemoveTaskGUI removeTaskGUI = new RemoveTaskGUI(tasks,tasksFiltered);
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

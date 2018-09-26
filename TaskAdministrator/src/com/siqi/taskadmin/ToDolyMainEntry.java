package com.siqi.taskadmin;

import com.siqi.taskadmin.command.Command;
import com.siqi.taskadmin.menu.CommandMenu;
import com.siqi.taskadmin.menu.CommandWord;
import com.siqi.taskadmin.parser.CommandParser;
import com.siqi.taskadmin.parser.ProjectNameParser;
import com.siqi.taskadmin.parser.TaskContentParser;
import com.siqi.taskadmin.parser.TaskIndexParser;
import com.siqi.taskadmin.util.DataUtil;
import com.siqi.taskadmin.controller.TasksAdmin;
import com.siqi.taskadmin.model.Task;
import com.siqi.taskadmin.model.Tasks;

public class ToDolyMainEntry {

	private CommandParser commandParser;
	private TaskContentParser taskContentParser;
	private CommandMenu menu;
	private TasksAdmin tasksAdmin;
	private Tasks tasks;
	private Tasks currentTasks;
	private Task task;
	private int[] tasksNumberBystatus;

	public ToDolyMainEntry() {
		commandParser = new CommandParser();
		menu = new CommandMenu();
		tasksAdmin = new TasksAdmin();
		tasks = new Tasks();
		task = new Task();
		currentTasks = new Tasks();
		tasks = tasksAdmin.loadAllTasks();
		tasksNumberBystatus = new int[2];
		taskContentParser = new TaskContentParser();
	}

	public ToDolyMainEntry(Tasks tasks) {
		commandParser = new CommandParser();
		menu = new CommandMenu();
		tasksAdmin = new TasksAdmin();
		this.tasks = tasks;
		tasksNumberBystatus = new int[2];
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
			quit(command);
			wantToQuit = true;
			break;
		case SAVEANDRETURNAFTERADD:
			temporarySave();
			start();
			wantToQuit = true;
			break;
		case QUITANDRETURNAFTERADD:
			start();
			wantToQuit = true;
			break;
		case SAVEANDRETURNAFTEREDIT:
			temporaryUpate();
			start();
			wantToQuit = true;
			break;
		case QUITANDRETURNAFTEREDIT:
			wantToQuit = true;
			start();
			break;
		case BYDATE:
			showByDate();
			wantToQuit = true;
			break;
		case BYPROJECT:
			filterByProject();
			wantToQuit = true;
			break;
		case EDITALL:
			editAll(currentTasks);
			wantToQuit = true;
			break;
		case REMOVE:
			remove(currentTasks);
			wantToQuit = true;
			break;
		case RETURNTOMAIN:
			wantToQuit = true;
			start();
			break;
		}
		return wantToQuit;
	}

	private void printWelcome() {
		tasksNumberBystatus = tasks.getNumberOfTasksByStatus();
		System.out.println();
		System.out.println("Welcome to ToDoly!");
		System.out.println(
				"You have " + tasksNumberBystatus[0] + " tasks todo and " + tasksNumberBystatus[1] + " tasks done.");
		menu.printTopMenu();
	}

	private void quit(Command command) {
		save();
		System.out.println("Thank you for using ToDoly.  Good bye.");
	}

	private void save() {
		tasksAdmin.saveAllTasks(tasks);
	}

	private void temporarySave() {
		tasks.add(task);
		Tasks.setBiggestId(Tasks.getBiggestId() + 1);
		System.out.println("The task has been added.");
	}

	private void temporaryUpate() {
		tasks = tasksAdmin.updateTask(tasks, task);
		System.out.println("The task has been updated.");
	}

	private void editAll(Tasks currentTasks) {
		System.out.println("------------------------------------------------------------------------------");
		System.out.println("Please input an id of task you want to edit.");
		boolean editFinished = false;
		int taskId = 0;
		while (!editFinished) {
			TaskIndexParser taskIndexParser = new TaskIndexParser();
			String idStr = taskIndexParser.readTaskId();
			if (!idStr.equals("") && DataUtil.isInteger(idStr)) {
				taskId = Integer.parseInt(idStr);
				if (currentTasks.containTask(taskId)) {
					printSelectedTask(taskId);
					getEditTaskItems();
					editFinished = true;
				}
			} else {
				System.out.println("Please input a proper id number.");
			}
		}
		menu.printChildMenu(CommandWord.EDITALL);
		boolean finished = false;
		while (!finished) {
			Command command = commandParser.getChildMenuCommand(CommandWord.EDITALL);
			finished = processCommand(command);
		}

	}

	private void printSelectedTask(int taskId) {
		task = tasks.getById(taskId);
		if (task != null) {
			System.out.println("*****************************************\r");
			System.out.println("TaskId:" + task.getId() + "\r" + "Task Title:" + task.getTitle());
		}
	}

	private void remove(Tasks currentTasks) {
		System.out.println("------------------------------------------------------------------------------");
		System.out.println("Please input an id of task you want to remove.");
		boolean finished = false;
		int taskId = 0;
		while (!finished) {
			TaskIndexParser taskIndexParser = new TaskIndexParser();
			String idStr = taskIndexParser.readTaskId();
			if (!idStr.equals("") && DataUtil.isInteger(idStr)) {
				taskId = Integer.parseInt(idStr);
				if (currentTasks.containTask(taskId)) {
					tasks.removeById(taskId);
					System.out.println("The task has been removed");
					start();
					finished = true;
				}
			} else {
				System.out.println("Please input a proper id number.");
			}
		}
	}

	private void show() {
		System.out.println("------------------------------------------------------------------------------");
		menu.printChildMenu(CommandWord.SHOW);
		boolean finished = false;
		while (!finished) {
			Command command = commandParser.getChildMenuCommand(CommandWord.SHOW);
			finished = processCommand(command);
		}
	}

	private void showByDate() {
		tasks = tasksAdmin.getTasksSortByDate(tasks);
		if (tasks != null && tasks.getNumberOfTask() != 0) {
			tasks.showAllTheTask();
			currentTasks = tasks;
			menu.printChildMenu(CommandWord.BYDATE);
			boolean finished = false;
			while (!finished) {
				Command command = commandParser.getChildMenuCommand(CommandWord.BYDATE);
				finished = processCommand(command);
			}
		} else {
			System.out.println("There is no task currently!");
			start();
		}
	}

	private void filterByProject() {
		System.out.println("Please input project name: ");
		ProjectNameParser projectNameParser = new ProjectNameParser();
		String projectName = projectNameParser.readProjectName();
		currentTasks = tasksAdmin.getTasksFilterByProject(projectName, tasks);
		boolean finished = false;
		if (currentTasks != null && currentTasks.getNumberOfTask() != 0) {
			currentTasks.showAllTheTask();
			menu.printChildMenu(CommandWord.BYPROJECT);
			while (!finished) {
				Command command = commandParser.getChildMenuCommand(CommandWord.BYPROJECT);
				finished = processCommand(command);
			}
		} else {
			System.out.println("There is no task currently!");
			start();
		}
	}

	private void add() {
		System.out.println("------------------------------------------------------------------------------");
		getAddTaskItems();
		menu.printChildMenu(CommandWord.ADD);
		boolean finished = false;
		while (!finished) {
			Command command = commandParser.getChildMenuCommand(CommandWord.ADD);
			finished = processCommand(command);
		}
	}

	private void getAddTaskItems() {
		boolean isDate = false;
		boolean isEmpty = true;
		String taskTitle = "";
		String dueDate = "";
		while (isEmpty) {
			System.out.println("TaskTitle(TaskTitle cannot be modified once created.):");
			taskTitle = taskContentParser.readTaskContent();
			if (taskTitle != null && !taskTitle.equals("")) {
				isEmpty = false;
			}
		}
		while (!isDate) {
			System.out.println("DueDate(YYYY--MM-DD):");
			dueDate = taskContentParser.readTaskContent();
			isDate = DataUtil.isDate(dueDate);
		}
		System.out.println("Project Name: ");
		String projectName = taskContentParser.readTaskContent();
		task.setId(Tasks.getBiggestId() + 1);
		task.setTitle(taskTitle);
		task.setDuedate(dueDate);
		task.setProject(projectName);
	}

	private void getEditTaskItems() {
		boolean isDate = false;
		boolean isStatus = false;
		String dueDate = "";
		String status = "";
		while (!isDate) {
			System.out.println("DueDate(YYYY--MM-DD):");
			dueDate = taskContentParser.readTaskContent();
			isDate = DataUtil.isDate(dueDate);
		}
		System.out.println("Project Name: ");
		String projectName = taskContentParser.readTaskContent();
		while (!isStatus) {
			System.out.println("Task Status(0:In progress; 1:Completed): ");
			status = taskContentParser.readTaskContent();
			isStatus = DataUtil.isStatus(status);
		}
		task.setDuedate(dueDate);
		task.setProject(projectName);
		if (status.equals("1")) {
			task.setStatus(true);
		} else {
			task.setStatus(false);
		}

	}

	public static void main(String[] args) {
		ToDolyMainEntry mainEntry = new ToDolyMainEntry();
		mainEntry.start();
	}
}

package com.siqi.taskadmin;

import java.util.Date;

import com.siqi.taskadmin.menu.CommandMenu;
import com.siqi.taskadmin.menu.CommandWord;
import com.siqi.taskadmin.parser.CommandParser;
import com.siqi.taskadmin.util.DataUtil;
import com.siqi.taskadmin.controller.TasksAdmin;
import com.siqi.taskadmin.model.Task;
import com.siqi.taskadmin.model.Tasks;

public class ToDolyMainEntry {

	private CommandParser commandParser;
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
	}

	public void start() {
		printWelcome();
		boolean finished = false;
		while (!finished) {
			CommandWord commandWord = commandParser.getTopMenuCommand();
			finished = processCommand(commandWord);
		}
	}

	public boolean processCommand(CommandWord commandWord) {
		boolean wantToQuit = false;
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
			quit();
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

	private void quit() {
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
			String idStr = commandParser.readCommand();
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
			CommandWord commandWord = commandParser.getChildMenuCommand(CommandWord.EDITALL);
			finished = processCommand(commandWord);
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
			String idStr = commandParser.readCommand();
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
			CommandWord commandword = commandParser.getChildMenuCommand(CommandWord.SHOW);
			finished = processCommand(commandword);
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
				CommandWord commandWord = commandParser.getChildMenuCommand(CommandWord.BYDATE);
				finished = processCommand(commandWord);
			}
		} else {
			System.out.println("There is no task currently!");
			start();
		}
	}

	private void filterByProject() {
		System.out.println("Please input project name: ");
		String projectName = commandParser.readCommand();
		currentTasks = tasksAdmin.getTasksFilterByProject(projectName, tasks);
		boolean finished = false;
		if (currentTasks != null && currentTasks.getNumberOfTask() != 0) {
			currentTasks.showAllTheTask();
			menu.printChildMenu(CommandWord.BYPROJECT);
			while (!finished) {
				CommandWord commandWord = commandParser.getChildMenuCommand(CommandWord.BYPROJECT);
				finished = processCommand(commandWord);
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
			CommandWord commandWord = commandParser.getChildMenuCommand(CommandWord.ADD);
			finished = processCommand(commandWord);
		}
	}

	private void getAddTaskItems() {
		boolean isDate = false;
		boolean isEmpty = true;
		String taskTitle = "";
		Date dueDate = new Date();
		while (isEmpty) {
			System.out.println("TaskTitle(TaskTitle cannot be modified once created.):");
			taskTitle = commandParser.readCommand();
			if (taskTitle != null && !taskTitle.equals("")) {
				isEmpty = false;
			}
		}
		while (!isDate) {
			System.out.println("DueDate(DD--MM-YYYY):");
			String dueDateStr  = commandParser.readCommand();
			dueDate = DataUtil.createDate(dueDateStr);
			if (dueDate!=null) {
				isDate=true;
			}
		}
		System.out.println("Project Name: ");
		String projectName = commandParser.readCommand();
		task.setId(Tasks.getBiggestId() + 1);
		task.setTitle(taskTitle);
		task.setDuedate(dueDate);
		task.setProject(projectName);
	}

	private void getEditTaskItems() {
		boolean isDate = false;
		boolean isStatus = false;
		Date dueDate = new Date();
		String status = "";
		while (!isDate) {
			System.out.println("DueDate(DD--MM-YYYY):");
			String dueDateStr  = commandParser.readCommand();
			dueDate = DataUtil.createDate(dueDateStr);
			if (dueDate!=null) {
				isDate=true;
			}
		}
		System.out.println("Project Name: ");
		String projectName = commandParser.readCommand();
		while (!isStatus) {
			System.out.println("Task Status(0:In progress; 1:Completed): ");
			status = commandParser.readCommand();
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

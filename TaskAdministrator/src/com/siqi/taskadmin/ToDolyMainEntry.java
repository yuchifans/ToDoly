package com.siqi.taskadmin;

/**
 *  This class is the main class of the "Toduly" application. 
 *  The application will allow a user to create new tasks, assign them a title and due date, and 
 *  choose a project for that task to belong to. They will need to use a text based user interface 
 *  via the command-line. By using the application, the user is able to also edit, mark as done or 
 *  remove tasks. They can also quit and save the current task list to file, and then restart the 
 *  application with the former state restored.
 * 
 *  To use this system, create an instance of this class and call the "start" method.
 * 
 *  This main class creates and initialises all the others: it creates taskadmin, command menu and 
 *  command parser.  It also evaluates and executes the commands that the parser returns.
 * 
 * @author  Siqi Qian
 * @version 2018.09.28 
 */

import java.util.Date;
import com.siqi.taskadmin.menu.CommandMenu;
import com.siqi.taskadmin.menu.CommandWord;
import com.siqi.taskadmin.parser.CommandParser;
import com.siqi.taskadmin.util.DataUtil;
import com.siqi.taskadmin.data.TaskDataProcessor;
import com.siqi.taskadmin.model.Task;
import com.siqi.taskadmin.model.TaskList;

public class ToDolyMainEntry {

	private CommandParser commandParser;
	private CommandMenu menu;
	private TaskList tasks;
	private TaskDataProcessor taskDataProcessor;
	private TaskList currentTasks;
	private Task task;
	private int[] tasksNumberBystatus;
	private int taskIndexToBeUpdated;

	/**
	 * Constructor. Create the main entry of the application and initialize all the
	 * components of the system.
	 */
	public ToDolyMainEntry() {

		commandParser = new CommandParser();
		menu = new CommandMenu();
		tasks = new TaskList();
		task = new Task();
		currentTasks = new TaskList();
		taskDataProcessor = new TaskDataProcessor();
		tasks = taskDataProcessor.read();
		tasksNumberBystatus = new int[2];

	}

	/**
	 * Start the system, print welcome infomation. Loops until the first proper
	 * command has been got.
	 */
	public void start() {

		printWelcome();

		// Enter the main command loop. Here we repeatedly read commands and
		// execute them the valid command has been type in by user.
		boolean finished = false;
		while (!finished) {
			CommandWord commandWord = commandParser.getTopMenuCommand();
			finished = processCommand(commandWord);
		}
	}

	/**
	 * Given a command, process the command.
	 * 
	 * @param command word to be processed.
	 * @return true if the command is a valid command, false otherwise.
	 */
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
			temporaryAdd();
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
			start();
			wantToQuit = true;
			break;
		case BYDATE:
			showByDate();
			wantToQuit = true;
			break;
		case BYPROJECT:
			filterByProject();
			wantToQuit = true;
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
			wantToQuit = true;
			start();
			break;
		}
		return wantToQuit;
	}

	/**
	 * Print the welcome infomation. Print the tasks numbers by status. Print the
	 * main menu("show" or "add" or "quit") and give the options to user.
	 */
	private void printWelcome() {

		System.out.println();
		System.out.println("Welcome to ToDoly!");
		tasksNumberBystatus = tasks.getNumberOfTasksByStatus(); // Get two numbers of in-porgress tasks and done tasks .
		System.out.println(
				"You have " + tasksNumberBystatus[0] + " tasks todo and " + tasksNumberBystatus[1] + " tasks done.");
		menu.printTopMenu();

	}

	/**
	 * Execute "show" operation. Print a menu ("sortbydate" or "filterbyproject")
	 * and give the user two options. Loops until the proper command has been got.
	 */
	private void show() {

		System.out.println("------------------------------------------------------------------------------");
		menu.printChildMenu(CommandWord.SHOW);
		boolean finished = false;
		while (!finished) {
			CommandWord commandword = commandParser.getChildMenuCommand(CommandWord.SHOW);
			finished = processCommand(commandword);
		}

	}

	/**
	 * Sort tasks by status and date. if task list is not empty, show all the task
	 * and print a menu("edit" or "remove" or "return") and give the user three
	 * options. Loops until the proper command has been got. if task list is empty,
	 * give the user a notification.
	 */
	private void showByDate() {
		tasks.getTasksSortByDate();
		if (tasks != null && tasks.getNumberOfTasks() != 0) {
			tasks.showAllTasks();
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

	/**
	 * Get a project name from user,filter task list by the project name. If the
	 * filtered task list is not empty, show all the task and print a menu("edit" or
	 * "remove" or "return") and give the user three options. Loops until the proper
	 * command has been got. if task list is empty, give the user a notification.
	 */
	private void filterByProject() {
		System.out.println("Please input project name: ");
		String projectName = commandParser.readCommand();
		currentTasks = tasks.getTasksFilterByProject(projectName);
		boolean finished = false;
		if (currentTasks != null && currentTasks.getNumberOfTasks() != 0) {
			System.out.println(currentTasks.getTasks().get(0)==tasks.getTasks().get(0));
			currentTasks.showAllTasks();
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

	/**
	 * "Add new task" option was choosed. Execute "add" operation. Get items of a
	 * specific task which is inputed by user, then print a menu (save or quit) and
	 * give the user two options. Loops until the proper command has been got.
	 */
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

	/**
	 * "Quit and save" option was choosed. Save all the changes and print welfare
	 * information.
	 */
	private void quit() {

		taskDataProcessor.write(tasks);
		System.out.println("Thank you for using ToDoly.  Good bye.");

	}

	/**
	 * Add a task temporarily to the task list. The change of "add" operation has
	 * not been saved to the file.
	 */
	private void temporaryAdd() {

		tasks.addTask(task);
		System.out.println("The task has been added.");

	}

	/**
	 * Update a task in the task list temporarily. The change of "update" operation
	 * has not been saved to the file.
	 */
	private void temporaryUpate() {
		
		Task taskToBeUpated = currentTasks.getTaskById(taskIndexToBeUpdated);
		taskToBeUpated.setDuedate(task.getDuedate());
		taskToBeUpated.setProject(task.getProject());
		taskToBeUpated.setStatus(task.isStatus());
		//tasks.updateTask(taskToBeUpated);
		System.out.println("The task has been updated.");

	}

	private void edit() {
		System.out.println("------------------------------------------------------------------------------");
		System.out.println("Please input an id of task you want to edit.");
		boolean editFinished = false;
		while (!editFinished) {
			String idStr = commandParser.readCommand();
			if (!idStr.equals("") && DataUtil.isInteger(idStr)) {
				taskIndexToBeUpdated = Integer.parseInt(idStr);
				if (currentTasks.containTask(taskIndexToBeUpdated)) {
					printSelectedTask(taskIndexToBeUpdated);
					getEditTaskItems();
					editFinished = true;
				}
			} else {
				System.out.println("Please input a proper id number.");
			}
		}
		menu.printChildMenu(CommandWord.EDIT);
		boolean finished = false;
		while (!finished) {
			CommandWord commandWord = commandParser.getChildMenuCommand(CommandWord.EDIT);
			finished = processCommand(commandWord);
		}

	}

	private void printSelectedTask(int taskId) {
		task = currentTasks.getTaskById(taskId);
		if (task != null) {
			System.out.println("*****************************************\r");
			System.out.println("TaskId:" + taskId + "\r" + "Task Title:" + task.getTitle());
		}
	}

	private void remove() {
		System.out.println("------------------------------------------------------------------------------");
		System.out.println("Please input an id of task you want to remove.");
		boolean finished = false;
		int taskId = 0;
		while (!finished) {
			String idStr = commandParser.readCommand();
			if (!idStr.equals("") && DataUtil.isInteger(idStr)) {
				taskId = Integer.parseInt(idStr);
				if (currentTasks.containTask(taskId)) {
					tasks.removeTask(currentTasks.getTaskById(taskId));
					System.out.println("The task has been removed");
					start();
					finished = true;
				}
			} else {
				System.out.println("Please input a proper id number.");
			}
		}
	}

	private void getAddTaskItems() {
		boolean isDate = false;
		boolean isEmpty = true;
		String taskTitle = "";
		Date dueDate = new Date();
		task = new Task();
		while (isEmpty) {
			System.out.println("TaskTitle(TaskTitle cannot be modified once created.):");
			taskTitle = commandParser.readCommand();
			if (taskTitle != null && !taskTitle.equals("")) {
				isEmpty = false;
			}
		}
		while (!isDate) {
			System.out.println("DueDate(DD--MM-YYYY):");
			String dueDateStr = commandParser.readCommand();
			dueDate = DataUtil.createDate(dueDateStr);
			if (dueDate != null) {
				isDate = true;
			}
		}
		System.out.println("Project Name: ");
		String projectName = commandParser.readCommand();
		task.setTitle(taskTitle);
		task.setDuedate(dueDate);
		task.setProject(projectName);
	}

	private void getEditTaskItems() {
		boolean isDate = false;
		boolean isStatus = false;
		Date dueDate = new Date();
		String status = "";
		task = new Task();
		while (!isDate) {
			System.out.println("DueDate(DD--MM-YYYY):");
			String dueDateStr = commandParser.readCommand();
			dueDate = DataUtil.createDate(dueDateStr);
			if (dueDate != null) {
				isDate = true;
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

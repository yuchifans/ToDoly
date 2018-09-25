package com.siqi.taskadmin.GUI;

import java.util.Iterator;

import com.siqi.taskadmin.ToDolyMainEntry;
import com.siqi.taskadmin.controller.TasksAdmin;
import com.siqi.taskadmin.menu.CommandMenu;
import com.siqi.taskadmin.model.Task;
import com.siqi.taskadmin.model.Tasks;
import com.siqi.taskadmin.parser.TaskIndexParser;
import com.siqi.taskadmin.parser.Command;
import com.siqi.taskadmin.parser.CommandParser;
import com.siqi.taskadmin.parser.CommandWord;
import com.siqi.taskadmin.parser.TaskContentParser;
import com.siqi.taskadmin.util.DataUtil;

public class EditTaskGUI implements DialogGUI {
	private TaskIndexParser taskIndexParser;
	private TaskContentParser taskContentParser;
	private CommandMenu childMenuOfShow;
	private CommandParser commandParser;
	private TasksAdmin tasksAdmin;
	private Tasks tasks;
	private Tasks originalTasks;
	private Task task;
	private int taskId;

	public EditTaskGUI(Tasks tasks) {
		taskIndexParser = new TaskIndexParser();
		taskContentParser = new TaskContentParser();
		commandParser = new CommandParser();
		childMenuOfShow = new CommandMenu();
		tasksAdmin = new TasksAdmin();
		task = new Task();
		this.tasks = tasks;
		originalTasks = new Tasks();
		originalTasks.setTasks(tasks.getTasks());
		taskId = 0;
	}

	public void start() {
		System.out.println("Please input an id of task you want to edit.");
		boolean editFinished = false;
		while (!editFinished) {
			editFinished = editTask();
		}
		childMenuOfShow.printChildMenu(CommandWord.EDIT);
		boolean finished = false;
		while (!finished) {
			Command command = commandParser.getChildMenuCommand(CommandWord.EDIT);
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
		case SAVEANDRETURN:
			tasks = tasksAdmin.updateTask(tasks, task);
			wantToQuit = returnToMain(true);
			break;
		case QUITANDRETURN:
			wantToQuit = returnToMain(false);
			break;
		}
		return wantToQuit;
	}

	public boolean editTask() {
		boolean isSuccess = false;
		if (getOperateItem()) {
			if (canOperate()) {
				printSingleTask();
				getTaskItem();
				isSuccess = true;
			}
		}
		return isSuccess;
	}

	private boolean canOperate() {
		boolean canOperate = false;
		Iterator<Task> it = tasks.getTasks().iterator();
		while (it.hasNext()) {
			Task task = (Task) it.next();
			if (task.getId() == taskId) {
				canOperate = true;
				return canOperate;
			}
		}
		System.out.println("Sorry, taskId does not exist!");
		return canOperate;
	}

	private boolean getOperateItem() {

		String idStr = taskIndexParser.readTaskId();
		if (!idStr.equals("") && DataUtil.isInteger(idStr)) {
			taskId = Integer.parseInt(idStr);
			return true;
		} else {
			System.out.println("Please input a proper id number.");
			return false;
		}
	}

	private void getTaskItem() {
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

	private void printSingleTask() {
		task = tasks.getById(taskId);
		if (task != null) {
			System.out.println("*****************************************\r");
			System.out.println("TaskId:" + task.getId() + "\r" + "Task Title:" + task.getTitle());
		}
	}

	private boolean returnToMain(boolean wannaSave) {
		System.out.println("------------------------------------------------------------------------------");
		if (wannaSave) {
			ToDolyMainEntry main = new ToDolyMainEntry(tasks);
			main.start();
		}else {
			ToDolyMainEntry main = new ToDolyMainEntry(originalTasks);
			main.start();
		}
		return true;
	}

}

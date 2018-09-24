package com.siqi.taskadmin.GUI;

import com.siqi.taskadmin.ToDolyMainEntry;
import com.siqi.taskadmin.menu.CommandMenu;
import com.siqi.taskadmin.model.Task;
import com.siqi.taskadmin.model.Tasks;
import com.siqi.taskadmin.parser.Command;
import com.siqi.taskadmin.parser.CommandParser;
import com.siqi.taskadmin.parser.CommandWord;
import com.siqi.taskadmin.parser.TaskContentParser;
import com.siqi.taskadmin.util.DataUtil;

public class AddTaskGUI implements DialogGUI {
	private CommandMenu childMenuOfShow;
	private CommandParser commandParser;
	private TaskContentParser taskContentParser;
	private Task task;
	private Tasks tasks;

	public AddTaskGUI(Tasks tasks) {
		commandParser = new CommandParser();
		childMenuOfShow = new CommandMenu();
		taskContentParser = new TaskContentParser();
		task = new Task();
		this.tasks= tasks;
	}

	public void start() {
		getTaskItem();
		childMenuOfShow.printChildMenu(CommandWord.ADD);
		boolean finished = false;
		while (!finished) {
			Command command = commandParser.getChildMenuCommand(CommandWord.ADD);
			finished = processCommand(command);
		}
	}

	private void getTaskItem() {
		boolean isDate=false;
		boolean isEmpty=true;
		String taskTitle="";
		String dueDate ="";
		while(isEmpty) {
			System.out.println("TaskTitle(TaskTitle cannot be modified once created.):");
			taskTitle = taskContentParser.readTaskContent();
			if(taskTitle!=null&&!taskTitle.equals("")) {
				isEmpty=false;
			}
		}
		while(!isDate) {
			System.out.println("DueDate(YYYY--MM-DD):");
			dueDate=taskContentParser.readTaskContent();
			isDate=DataUtil.isDate(dueDate);
		}
		System.out.println("Project Name: ");
		String projectName = taskContentParser.readTaskContent();
		task.setId(Tasks.getBiggestId()+1);
		task.setTitle(taskTitle);
		task.setDuedate(dueDate);
		task.setProject(projectName);
	}

	public boolean processCommand(Command command) {
		boolean wantToQuit = false;
		CommandWord commandWord = command.getCommandWord();
		switch (commandWord) {
		case UNKNOWN:
			System.out.println("Please type in a proper number...");
			break;
		case SAVEANDRETURN:
			tasks.add(task);
			Tasks.setBiggestId(Tasks.getBiggestId()+1);
			System.out.println("The task has been added.");
			wantToQuit = returnToMain();
			break;
		case QUITANDRETURN:
			wantToQuit = returnToMain();
			break;
		}
		return wantToQuit;
	}

	private boolean returnToMain() {
		System.out.println("------------------------------------------------------------------------------");
		ToDolyMainEntry main = new ToDolyMainEntry(tasks);
		main.start();
		return true;
	}

}

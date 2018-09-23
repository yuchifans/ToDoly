package com.siqi.taskadmin.GUI;


import com.siqi.taskadmin.ToDolyMainEntry;
import com.siqi.taskadmin.data.TaskDataProcessor;
import com.siqi.taskadmin.menu.CommandMenu;
import com.siqi.taskadmin.model.Task;
import com.siqi.taskadmin.parser.Command;
import com.siqi.taskadmin.parser.CommandParser;
import com.siqi.taskadmin.parser.CommandWord;
import com.siqi.taskadmin.parser.TaskContentParser;
import com.siqi.taskadmin.util.DataUtil;

public class AddTaskGUI implements DialogGUI {
	private CommandMenu childMenuOfShow;
	private CommandParser commandParser;
	private TaskContentParser taskContentParser;
	private TaskDataProcessor dataProcessor;
	private Task task;

	public AddTaskGUI() {
		commandParser = new CommandParser();
		childMenuOfShow = new CommandMenu();
		taskContentParser = new TaskContentParser();
		dataProcessor = new TaskDataProcessor();
		task = new Task();
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
		dataProcessor.load();
		System.out.println("TaskTitle: ");
		String taskTitle = taskContentParser.readTaskContent();
		task.setTitle(taskTitle);
		System.out.println("DueDate: YYYY--MM-DD");
		boolean isDate=false;
		String dueDate ="";
		while(!isDate) {
			dueDate=taskContentParser.readTaskContent();
			isDate=DataUtil.isDate(dueDate);
		}
		task.setDuedate(dueDate);
		System.out.println("Project Name: ");
		String projectName = taskContentParser.readTaskContent();
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
			dataProcessor.load();
			dataProcessor.idIncrement();
			task.setId(dataProcessor.getBiggestId());
			dataProcessor.add(task);
			returnToMain();
			wantToQuit = true;
			break;
		case QUITANDRETURN:
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

}

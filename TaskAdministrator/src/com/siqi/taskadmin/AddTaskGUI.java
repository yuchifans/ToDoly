package com.siqi.taskadmin;

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
		Tasks.TaskNumber += 1;
		task.setId(Tasks.TaskNumber);
		System.out.println("TaskTitle: ");
		String taskTitle = taskContentParser.readTaskContent();
		task.setTitle(taskTitle);
		System.out.println("DueDate: ");
		String dueDate = taskContentParser.readTaskContent();
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

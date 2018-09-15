package com.siqi.taskadmin;

public class AddTaskGUI implements DialogGUI {
	
	TaskContentParser taskContentParser;
	TaskDataProcessor dataProcessor;
	Task task;

	
	public AddTaskGUI() {
		taskContentParser = new TaskContentParser();
		dataProcessor = new TaskDataProcessor();
		task=new Task();
	}

	public void start() {	
		Tasks.TaskNumber+=1;
		task.setId(Tasks.TaskNumber);
		System.out.println("TaskTitle: ");
		String taskTitle=taskContentParser.readTaskContent();
		task.setTitle(taskTitle);
		System.out.println("DueDate: ");
		String dueDate=taskContentParser.readTaskContent();
		task.setDuedate(dueDate);
		System.out.println("Project Name: ");
		String projectName=taskContentParser.readTaskContent();
		task.setProject(projectName);	
		dataProcessor.load();
		dataProcessor.add(task);
	}
	
	public boolean processCommand(Command command) {
		
		return false;
	}

}

package com.siqi.taskadmin.controller;

import com.siqi.taskadmin.model.Task;
import com.siqi.taskadmin.model.Tasks;
import com.siqi.taskadmin.data.TaskDataProcessor;;

public class TasksAdmin {
	private Task task;
	private Tasks tasks;
	private TaskDataProcessor taskDataProcessor;
	
	public TasksAdmin() {
		task= new Task();
		tasks = new Tasks();
		taskDataProcessor = new TaskDataProcessor();
	}
	
	public void addTask(Task task) {
		taskDataProcessor.load();
		taskDataProcessor.idIncrement();
		taskDataProcessor.add(task);
	}
	
	public int getCurrentId() {
		taskDataProcessor.load();
		int currentId=taskDataProcessor.getCurrentId();
		return currentId;
	}
}

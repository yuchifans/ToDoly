package com.siqi.taskadmin.controller;

import com.siqi.taskadmin.model.Task;
import com.siqi.taskadmin.model.Tasks;
import com.siqi.taskadmin.data.TaskDataProcessor;;

public class TasksAdmin {
	private TaskDataProcessor taskDataProcessor;
	
	public TasksAdmin() {
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
	
	public Tasks getTasksSortByDate() {
		taskDataProcessor.load();
		Tasks tasks = taskDataProcessor.sortByDate();
		return tasks;
	}
	
	public Tasks getTasksFilterByProject(String projectName) {
		taskDataProcessor.load();
		Tasks tasks = taskDataProcessor.filterByProject(projectName);
		return tasks;
	}
}

package com.siqi.taskadmin.controller;

import com.siqi.taskadmin.model.Task;
import com.siqi.taskadmin.model.Tasks;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import com.siqi.taskadmin.data.TaskDataProcessor;


public class TasksAdmin {
	private TaskDataProcessor taskDataProcessor;
	private Tasks tasks;

	public TasksAdmin() {
		taskDataProcessor = new TaskDataProcessor();
	}

	public Tasks loadAllTasks() {
		tasks = taskDataProcessor.read();
		return tasks;
	}
	
	public void saveAllTasks(Tasks tasks) {
		taskDataProcessor.write(tasks);
	}
	
	public Tasks updateTask(Tasks tasks,Task task) {
		tasks.removeById(task.getId());
		tasks.add(task);
		return tasks;
	}
	
	public Tasks getTasksSortByDate(Tasks tasks) {
		if (tasks != null) {
			tasks.getTasks().sort(Comparator.comparing(Task::isStatus).thenComparing(Task::getDuedate));
			return tasks;
		} else {
			return null;
		}
	}

	public Tasks getTasksFilterByProject(String projectName,Tasks tasks) {
		Tasks filteredTasks = new Tasks();
		getTasksSortByDate(tasks);
		ArrayList<Task> filteredTasksList = tasks.getTasks().stream().filter(t -> t.getProject().contains(projectName))
				.collect(Collectors.toCollection(ArrayList::new));
		filteredTasks.setTasks(filteredTasksList);
		return filteredTasks;
	}
	

}

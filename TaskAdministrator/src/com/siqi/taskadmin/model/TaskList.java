package com.siqi.taskadmin.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TaskList {
	private List<Task> tasksList;

	public TaskList() {
		tasksList = new ArrayList<>();
	}

	public ArrayList<Task> getTasks() {
		return (ArrayList<Task>) tasksList;
	}

	public void setTasks(ArrayList<Task> tasksList) {
		this.tasksList = tasksList;
	}

	public void addTask(Task task) {
		tasksList.add(task);
	}

	public Task getTaskById(int taskId) {
		if (taskId <= tasksList.size()) {
			return tasksList.get(taskId - 1);
		}
		return null;
	}

	public void removeTask(Task task) {
		if (tasksList.contains(task)) {
			tasksList.remove(task);
		} else {
			System.out.println("Task does not exist!");
		}
	}

	public int getNumberOfTasks() {
		return tasksList.size();
	}

	public void showAllTasks() {
		System.out.println("Task Detail:");
		for (Task task : tasksList) {
			System.out.println("***************************************** ");
			System.out.println(" taskId:" + (tasksList.indexOf(task) + 1) + "\r" + task.getDetail());
		}
	}

	public boolean containTask(int taskId) {
		boolean contain = false;
		if (taskId > 0 && taskId <= tasksList.size()) {
			contain = true;
		} else {
			System.out.println("Sorry, taskId does not exist!");
		}
		return contain;
	}

	public void getTasksSortByDate() {
		tasksList.sort(Comparator.comparing(Task::isStatus).thenComparing(Task::getDuedate));
	}

	public TaskList getTasksFilterByProject(String projectName) {
		TaskList filteredTasks = new TaskList();
		getTasksSortByDate();
		ArrayList<Task> filteredTasksList = tasksList.stream().filter(t -> t.getProject().equals(projectName))
				.collect(Collectors.toCollection(ArrayList::new));
		filteredTasks.setTasks(filteredTasksList);
		return filteredTasks;
	}

	public int[] getNumberOfTasksByStatus() {
		int[] taskNumber = new int[2];
		for (Task task : tasksList) {
			if (task.isStatus() == false) {
				taskNumber[0]++;
			} else {
				taskNumber[1]++;
			}
		}
		return taskNumber;
	}

}

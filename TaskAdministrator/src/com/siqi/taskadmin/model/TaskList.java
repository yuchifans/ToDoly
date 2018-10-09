package com.siqi.taskadmin.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @author Siqi Qian
 * @version 2018.10.04
 */

public class TaskList {
	private List<Task> tasksList;

	/**
	 * Constructor. Create a new TaskList.
	 */
	public TaskList() {
		tasksList = new ArrayList<>();
	}

	/**
	 * Get the whole collection of task list.
	 * 
	 * @return an ArrayList of Task.
	 */
	public ArrayList<Task> getTasks() {
		return (ArrayList<Task>) tasksList;
	}

	/**
	 * Update the collection of task list by given ArrayList of Task.
	 * 
	 * @param tasksList an ArrayList of Task to be assigned.
	 */
	public void setTasks(ArrayList<Task> tasksList) {
		this.tasksList = tasksList;
	}

	/**
	 * Add a task to the collection of tasklist.
	 * 
	 * @param task a task to be added
	 */
	public void addTask(Task task) {
		tasksList.add(task);
	}

	/**
	 * Get a specific task to the collection of tasklist, return null if a task with
	 * this taskId does not exist.
	 * 
	 * @param taskId the Id of task to be returned
	 * @return a certain task corresponding to the taskId.
	 */
	public Task getTaskById(int taskId) {
		if (taskId <= tasksList.size()) {
			return tasksList.get(taskId - 1);
		}
		return null;
	}

	/**
	 * Remove a task from the collection of tasklist, print a notification if the
	 * task is does not exist in the collection.
	 * 
	 * @param task the task to be removed.
	 */
	public void removeTask(Task task) {
		if (tasksList.contains(task)) {
			tasksList.remove(task);
		} else {
			System.out.println("Task does not exist!");
		}
	}

	/**
	 * Get a total number of tasks in the collection.
	 * 
	 * @return total number of tasks in the collection.
	 */
	public int getNumberOfTasks() {
		return tasksList.size();
	}

	/**
	 * Show the full list of tasks in the collection.
	 */
	public void showAllTasks() {
		System.out.println("Task Detail:");
		for (Task task : tasksList) {
			System.out.println("***************************************** ");
			System.out.println(" taskId:" + (tasksList.indexOf(task) + 1) + "\r" + task.toString());
		}
	}

	/**
	 * Check whether the task with the given taskId exists in the collection of
	 * taskList.
	 * 
	 * @param taskId taskId of a task needs to be checked.
	 * @return true if the task with the given id is in the collection, false
	 *         otherwise.
	 */
	public boolean containTask(int taskId) {
		boolean contain = false;
		if (taskId > 0 && taskId <= tasksList.size()) {
			contain = true;
		} else {
			System.out.println("Sorry, taskId does not exist!");
		}
		return contain;
	}

	/**
	 * Sort the collection of task by status and duedate, status in priority.
	 */
	public void getTasksSortByDate() {
		tasksList.sort(Comparator.comparing(Task::getStatus).thenComparing(Task::getDuedate));
	}

	/**
	 * Filter the colleciton of task by a projectname,return a new object of
	 * TaskList with a new collection.
	 * 
	 * @param projectName a project name to filter the collection
	 * @return a new taskList which matches the condition that project name of the
	 *         task in this taskList equals the given project name
	 */
	public TaskList getTasksFilterByProject(String projectName) {
		TaskList filteredTasks = new TaskList();
		getTasksSortByDate();
		ArrayList<Task> filteredTasksList = tasksList.stream().filter(t -> t.getProject().equals(projectName))
				.collect(Collectors.toCollection(ArrayList::new));
		filteredTasks.setTasks(filteredTasksList);
		return filteredTasks;
	}

	/**
	 * Get both numbers of tasks which are in progress and completed.
	 * 
	 * @return an integer array which contains two elements.
	 */
	public int[] getNumberOfTasksByStatus() {
		int[] taskNumber = new int[2];
		for (Task task : tasksList) {
			if (task.getStatus() == false) {
				taskNumber[0]++;
			} else {
				taskNumber[1]++;
			}
		}
		return taskNumber;
	}

}

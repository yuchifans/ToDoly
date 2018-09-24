package com.siqi.taskadmin.controller;

import com.siqi.taskadmin.model.Task;
import com.siqi.taskadmin.model.Tasks;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;
import com.siqi.taskadmin.data.TaskDataProcessor;;

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
			try {
				Collections.sort(tasks.getTasks(), new Comparator<Task>() {
					public int compare(Task task1, Task task2) {
						String[] tempDate1 = task1.getDuedate().split("-");
						String[] tempDate2 = task2.getDuedate().split("-");
						int[] date1 = new int[tempDate1.length];
						int[] date2 = new int[tempDate2.length];

						for (int i = 0; i < date1.length; i++) {
							date1[i] = Integer.parseInt(tempDate1[i]);
						}
						for (int i = 0; i < date1.length; i++) {
							date2[i] = Integer.parseInt(tempDate2[i]);
						}

						if (date1[0] > date2[0]) {
							return -1;
						} else if (date1[0] == date2[0]) {
							if (date1[1] > date2[1]) {
								return -1;
							} else if (date1[1] == date2[1]) {
								if (date1[2] > date2[2]) {
									return -1;
								} else if (date1[2] == date2[2]) {
									return 0;
								} else {
									return 1;
								}
							} else {
								return 1;
							}
						} else {
							return 1;
						}
					}
				});

			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			return tasks;
		} else {
			return null;
		}
	}
	
	public Tasks getTasksFilterByProject(String projectName,Tasks tasks) {
		Tasks filteredTasks = getTasksSortByDate(tasks);
		ArrayList<Task> filteredTasksList = tasks.getTasks().stream().filter(t -> t.getProject().contains(projectName))
				.collect(Collectors.toCollection(ArrayList::new));
		filteredTasks.setTasks(filteredTasksList);
		return filteredTasks;
	}
	

}

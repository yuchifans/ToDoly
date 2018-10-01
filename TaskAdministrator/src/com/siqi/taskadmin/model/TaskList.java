package com.siqi.taskadmin.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class TaskList {
	private static int biggestId;
	private List<Task> tasksList;

	public TaskList() {
		tasksList=new ArrayList<>();
	}
	
	public static void  setBiggestId(int id) {
		biggestId=id;
	}
	
	public static int getBiggestId() {
		return biggestId;
	}

	public ArrayList<Task> getTasks() {
		return (ArrayList<Task>)tasksList;
	}

	public void setTasks(ArrayList<Task> tasksList) {
		this.tasksList = tasksList;
	}
	
	public void add(Task task) {
		tasksList.add(task);
	}
	
	public Task getById(int taskId) {
		for(Task task:tasksList) {
			if(task.getId()==taskId) {
				return task;
			}
		}
		return null;
	}
	
	public void updateTask(Task task) {
		removeById(task.getId());
		add(task);
	}
	
	public void removeById(int taskId) {
		for(Task task:tasksList) {
			if(task.getId()==taskId) {
				tasksList.remove(task);
				break;
			}
		}
	}
	
	public int getNumberOfTask() {
		return tasksList.size();
	}
	
	public void showAllTheTask() {
		System.out.println("Task Detail:");
		for(Task task: tasksList) {
			System.out.println(task.getDetail());
		}	
	}
	
	public boolean containTask(int taskId) {
		boolean contain = false;
		Iterator<Task> it = tasksList.iterator();
		while (it.hasNext()) {
			Task task = (Task) it.next();
			if (task.getId() == taskId) {
				contain = true;
				return contain;
			}
		}
		System.out.println("Sorry, taskId does not exist!");
		return contain;
	}
	
	public void getTasksSortByDate() {
		
			tasksList.sort(Comparator.comparing(Task::isStatus).thenComparing(Task::getDuedate));	
		
	}

	public TaskList getTasksFilterByProject(String projectName,TaskList tasks) {
		TaskList filteredTasks = new TaskList();
		getTasksSortByDate();
		ArrayList<Task> filteredTasksList = tasks.getTasks().stream().filter(t -> t.getProject().contains(projectName))
				.collect(Collectors.toCollection(ArrayList::new));
		filteredTasks.setTasks(filteredTasksList);
		return filteredTasks;
	}
		
	public int[] getNumberOfTasksByStatus() {
		int[] taskNumber=new int[2];
		for(Task task: tasksList) {
			if(task.isStatus()==false) {
				taskNumber[0]++;
			}else {
				taskNumber[1]++;
			}
		}	
		return taskNumber;
	}
	
}

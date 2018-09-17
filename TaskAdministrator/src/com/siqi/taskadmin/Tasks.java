/**
 * 
 */
package com.siqi.taskadmin;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tmp-sda-1162
 *
 */
public class Tasks {
	private List<Task> tasksList;
	public static int TaskNumber;

	public Tasks() {
		tasksList=new ArrayList<>();
	}

	public List<Task> getTasks() {
		return tasksList;
	}

	public void setTasks(ArrayList<Task> tasksList) {
		this.tasksList = tasksList;
	}
	
	public void add(Task task) {
		tasksList.add(task);
	}
	
	public void showAllTheTask() {
		System.out.println("Task Detail:");
		for(Task task: tasksList) {
			System.out.println(task.getDetail());
		}	
	}
}

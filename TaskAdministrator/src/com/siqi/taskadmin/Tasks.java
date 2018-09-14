/**
 * 
 */
package com.siqi.taskadmin;

import java.util.ArrayList;

/**
 * @author tmp-sda-1162
 *
 */
public class Tasks {
	private ArrayList<Task> tasksList;

	
	
	public Tasks() {
		tasksList=new ArrayList<>();
	}

	public ArrayList<Task> getTasks() {
		return tasksList;
	}

	public void setTasks(ArrayList<Task> tasksList) {
		this.tasksList = tasksList;
	}
	
	public void add(Task task) {
		tasksList.add(task);
	}
	
	public void showAllTheTask() {
		for(Task task: tasksList) {
			System.out.println(task.getDetail());
		}	
	}
}

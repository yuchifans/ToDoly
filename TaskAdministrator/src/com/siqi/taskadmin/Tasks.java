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
	
	public void removeById(int taskId) {
		
		for(Task task:tasksList) {
			if(task.getId()==taskId) {
				tasksList.remove(task);
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
	
	public int[] getNumberOfTaskByStatus() {
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

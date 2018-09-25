/**
 * 
 */
package com.siqi.taskadmin.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tmp-sda-1162
 *
 */
public class Tasks {
	private static int biggestId;
	private List<Task> tasksList;

	public Tasks() {
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

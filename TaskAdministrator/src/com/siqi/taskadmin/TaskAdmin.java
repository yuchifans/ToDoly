
package com.siqi.taskadmin;

import java.util.ArrayList;

/**
 * This class is main class of the "ToDoly" application. "ToDoly" is a small
 * application which is used to administrate all the tasks owned by user. Task
 * can be added,edited and removed. To open this application, create an object
 * of this class and call the "work" method
 * 
 * @author Siqi Qian
 * @version 2018-09-12
 *
 */
public class TaskAdmin {
	private Task task;
	
	public TaskAdmin(){}
	
	public void showAllTheTask() {
		Tasks allTasks = new Tasks();
		allTasks.loadTask();
		ArrayList<Task> tasks=allTasks.getTasks();
		for(Task task: tasks) {
			System.out.println(task.getDetail());
		}	
	}

}

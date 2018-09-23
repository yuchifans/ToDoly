package com.siqi.taskadmin.GUI;

import java.util.ArrayList;
import java.util.Iterator;

import com.siqi.taskadmin.ToDolyMainEntry;
import com.siqi.taskadmin.data.TaskDataProcessor;
import com.siqi.taskadmin.model.Task;
import com.siqi.taskadmin.parser.TaskIndexParser;
import com.siqi.taskadmin.util.DataUtil;

public class RemoveTaskGUI implements DialogGUI {
	private TaskDataProcessor taskDataProccessor;
	private TaskIndexParser taskIndexParser;
	private ArrayList<Task> tasks;
 	private int taskId;
	
	public RemoveTaskGUI(ArrayList<Task> tasks) {
		taskDataProccessor=new TaskDataProcessor();
		taskIndexParser = new TaskIndexParser();
		this.tasks=tasks;
		taskId=0;
	}


	public void start() {
		System.out.println("Please input an id of task you want to remove.");
		boolean finished = false;
		while (!finished) {
			finished = removeTask();
		}
		returnToMain();
	}
	
	
	public boolean removeTask() {
		boolean isSuccess = false;
		if(getOperateItem()) {
			if(canOperate()) {
				taskDataProccessor.load();
				isSuccess=taskDataProccessor.remove(taskId);
				return isSuccess;
			}
		}
		return isSuccess;
	}
	
	private boolean canOperate() {
		boolean canOperate=false;
		Iterator it=tasks.iterator();
		while(it.hasNext()) {
			Task task=(Task)it.next();
			if(task.getId()==taskId) {
				canOperate=true;
				return canOperate;
			}	
		}
		System.out.println("Sorry, taskId does not exist!");
		return canOperate;	
	}
	
	private boolean getOperateItem() {
		
		String idStr=taskIndexParser.readTaskId();
		if(!idStr.equals("")&&DataUtil.isInteger(idStr)) {
			taskId = Integer.parseInt(idStr);
			return true;
		}else {
			System.out.println("Please input a proper id number.");
			return false;
		}
	}
	
	private void returnToMain() {
		System.out.println("------------------------------------------------------------------------------");
		ToDolyMainEntry main = new ToDolyMainEntry();
		main.start();
	}

}

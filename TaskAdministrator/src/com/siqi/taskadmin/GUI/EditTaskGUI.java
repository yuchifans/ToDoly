package com.siqi.taskadmin.GUI;

import java.util.ArrayList;

import com.siqi.taskadmin.data.TaskDataProcessor;
import com.siqi.taskadmin.model.Task;
import com.siqi.taskadmin.parser.EditTaskParser;

public class EditTaskGUI implements DialogGUI {
	private TaskDataProcessor taskDataProccessor;
	private EditTaskParser editTaskParser;
	private ArrayList<Task> tasks;
 	private int taskId;
	
	public EditTaskGUI(ArrayList<Task> tasks) {
		taskDataProccessor=new TaskDataProcessor();
		editTaskParser = new EditTaskParser();
		this.tasks=tasks;
		taskId=0;
	}
	public void start() {
		

	}

}

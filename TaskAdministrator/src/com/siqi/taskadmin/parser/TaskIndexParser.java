package com.siqi.taskadmin.parser;

import java.util.Scanner;

public class TaskIndexParser {
	private Scanner reader; 
	private String taskId;
	public TaskIndexParser() {
		reader = new Scanner(System.in);
	}
	
	public String readTaskId() {
		System.out.print("> "); 
		taskId = reader.nextLine();
		return taskId;
	}
}

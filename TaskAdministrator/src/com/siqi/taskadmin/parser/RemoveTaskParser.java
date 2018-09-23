package com.siqi.taskadmin.parser;

import java.util.Scanner;

public class RemoveTaskParser {
	private Scanner reader; 
	private String taskId;
	public RemoveTaskParser() {
		reader = new Scanner(System.in);
	}
	
	public String readTaskId() {
		System.out.print("> "); 
		taskId = reader.nextLine();
		return taskId;
	}

}

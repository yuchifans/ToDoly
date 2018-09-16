package com.siqi.taskadmin;

import java.util.Scanner;

public class TaskContentParser {
	private Scanner reader; 
	private String inputContent;
	public TaskContentParser() {
		reader = new Scanner(System.in);
	}
	public String readTaskContent() {
		System.out.print("> "); 
		inputContent = reader.nextLine();
		return inputContent;
	}
}

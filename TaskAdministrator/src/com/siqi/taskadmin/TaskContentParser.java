package com.siqi.taskadmin;

import java.util.Scanner;

public class TaskContentParser {
	private Scanner reader; 
	private String inputContent;
	public TaskContentParser() {
		reader = new Scanner(System.in);
	}
	public String readTaskContent() {
		 // will hold the full input line
		System.out.print("> "); // print prompt
		inputContent = reader.nextLine();
		return inputContent;
	}
}

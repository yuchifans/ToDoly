package com.siqi.taskadmin;

import java.util.Scanner;

public class ProjectNameParser {
	private Scanner reader; 
	private String projectName;
	public ProjectNameParser() {
		reader = new Scanner(System.in);
	}
	public String readProjectName() {
		System.out.print("> "); 
		projectName = reader.nextLine();
		return projectName;
	}
}

package com.siqi.taskadmin.model;
import java.util.Date;

public class Task {
	private int id;
	private String title;
	private String dueDate;
	private String projectName;
	private boolean status;
	
	public Task() {
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDuedate() {
		return dueDate;
	}
	public void setDuedate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getProject() {
		return projectName;
	}
	public void setProject(String projectName) {
		this.projectName = projectName;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getDetail() {
		return "*****************************************\r "
				+ "TaskId: "+id+"\r Task title:"+title+"\r Duedate:"+dueDate+"\r Project name:"
				+projectName+"\r Status:"+(status==true?"Completed" :"In progress \r"
				+ "*****************************************" );
	}
}
package com.siqi.taskadmin;
import java.util.Date;

public class Task {
	private String title;
	private Date dueDate;
	private Project project;
	private boolean status;
	
	public Task() {
		project=new Project();
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getDuedate() {
		return dueDate;
	}
	public void setDuedate(Date duedate) {
		this.dueDate = duedate;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getDetail() {
		return "Task title:"+title+"Duedate:"+dueDate+"Project name:"+project.getProjectName();
	}
}

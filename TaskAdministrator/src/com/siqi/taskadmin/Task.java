package com.siqi.taskadmin;
import java.util.Date;

public class Task {
	private String title;
	private String dueDate;
	private String projectName;
	private boolean status;
	
	public Task() {
		
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
	public void setDuedate(String duedate) {
		this.dueDate = duedate;
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
		return "Task title:"+title+" Duedate:"+dueDate+" Project name:"+projectName+" Status:"+(status==true?"Completed" :"In progress" );
	}
}

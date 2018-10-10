package com.siqi.taskadmin.model;

import java.util.Date;

/**
 * Class Task-a task can be operated in "ToDoLy".
 * 
 * This class is a basic model of "ToDoLy" application. "TodoLy" is a simple
 * task administration system.
 * 
 * A "Task" have items of title, due date, project name and status.
 * 
 * @author Siqi Qian
 * @version 2018.10.04
 */

public class Task {
	private String title;
	private Date dueDate; // Format: DD-MM-YYYY
	private String projectName;
	private boolean status; // false: in progress; true: completed

	/**
	 * Constructor
	 */
	public Task() {

	}

	/**
	 * Return a string which represents title of the task.
	 * 
	 * @return The title of the task. if the task is empty, return null
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Define a title of this task.
	 * 
	 * @param title The title of the task.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Return a Date which represents the dealine of the task
	 * 
	 * @return The deadline of the task. if the deadline is empty, return null
	 */
	public Date getDuedate() {
		return dueDate;
	}

	/**
	 * Define a deadline of this task.
	 * 
	 * @param dueDate The deadline of the task.
	 */
	public void setDuedate(Date dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * Return a string which represents the project name of the task.
	 * 
	 * @return The project name of the task. if the project name is empty, return
	 *         null
	 */
	public String getProject() {
		return projectName;
	}

	/**
	 * Define a project name which is related to this task.
	 * 
	 * @param projectName The project name which is assigned to the task.
	 */
	public void setProject(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * Return a boolean variable which represents the status of the task.
	 * 
	 * @return The status of the task. default status is false(in progress)
	 */
	public boolean getStatus() {
		return status;
	}

	/**
	 * Set status of this task.
	 * 
	 * @param status The status of the task.("true" represents "completed", "false"
	 *               represents "in progress".
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

}

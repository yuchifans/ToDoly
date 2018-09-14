package com.siqi.taskadmin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TaskDataProcessor {
	private File file;
	private JSONObject json;
	private JSONArray jsonMember;
	private FileReader fr;
	private BufferedReader br;
	private StringBuilder jsonStr;
	private Task task;
	private Tasks tasks;

	public TaskDataProcessor() {
		jsonStr = new StringBuilder();
	}

	private boolean getFile() {
		file = new File("TasksDetails.txt");
		if (file.exists()) {
			return true;
		} else {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
	}

	public void load() {
		if (getFile()) {
			try {
				fr = new FileReader(file);
				br = new BufferedReader(fr);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			String temp = "";
			try {
				while ((temp = br.readLine()) != null) {
					jsonStr.append(temp);
				}
				fr.close();
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Tasks read() {
		task = new Task();
		tasks = new Tasks();
		if (!jsonStr.toString().equals("")) {
			try {
				JSONObject json = new JSONObject(jsonStr.toString());
				JSONArray jsonMembers = json.getJSONArray("tasks");
				for (int i = 0; i < jsonMembers.length(); i++) {
					JSONObject taskJson = (JSONObject) jsonMembers.get(i);
					task=new Task();
					task.setTitle((String) taskJson.get("title"));
					task.setDuedate((String) taskJson.get("dueDate"));
					task.setProject((String) taskJson.get("projectName"));
					if (((String) taskJson.get("status")).equals("true")) {
						task.setStatus(true);
					} else {
						task.setStatus(false);
					}
					tasks.add(task);
				}
			} catch (JSONException ex) {
				ex.printStackTrace();
				return null;
			}
			return tasks;
		}else {
			return null;
		}
	}

	public void write() {

	}
}

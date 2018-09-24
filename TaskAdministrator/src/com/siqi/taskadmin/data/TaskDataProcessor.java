package com.siqi.taskadmin.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.siqi.taskadmin.model.Task;
import com.siqi.taskadmin.model.Tasks;

public class TaskDataProcessor {
	private int biggestId;
	private File file;
	private JSONObject json;
	private JSONArray jsonMembers;
	private FileReader fr;
	private BufferedReader br;
	private StringBuilder jsonStr;
	private Task task;
	private Tasks tasks;

	public TaskDataProcessor() {
		jsonStr = new StringBuilder();
		json = new JSONObject();
		jsonMembers = new JSONArray();
		biggestId = 0;
		tasks = new Tasks();
		task = new Task();
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

	private void load() {
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
			if (!jsonStr.toString().equals("")) {
				try {
					json = new JSONObject(jsonStr.toString());
					if (json.has("tasks")) {
						jsonMembers = json.getJSONArray("tasks");
					}
					if (json.has("biggestID")) {
						biggestId = json.getInt("biggestID");
					}
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	public Tasks read() {
		load();
		task = new Task();
		tasks = new Tasks();
		for (int i = 0; i < jsonMembers.length(); i++) {
			JSONObject taskJson = new JSONObject();
			try {
				taskJson = (JSONObject) jsonMembers.get(i);
				task = new Task();
				task.setId(taskJson.getInt("taskId"));
				task.setTitle((String) taskJson.get("title"));
				task.setDuedate((String) taskJson.get("dueDate"));
				task.setProject((String) taskJson.get("projectName"));
				if (((String) taskJson.get("status")).equals("1")) {
					task.setStatus(true);
				} else {
					task.setStatus(false);
				}
				tasks.add(task);
				Tasks.setBiggestId(biggestId);

			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		}
		return tasks;
	}

	public void write(Tasks tasks) {
		getFile();
		jsonMembers = new JSONArray();
		if (tasks.getNumberOfTask() != 0) {
			for (Task task : tasks.getTasks()) {
				try {
					JSONObject member = new JSONObject();
					member.put("taskId", task.getId());
					member.put("title", task.getTitle());
					member.put("dueDate", task.getDuedate());
					member.put("projectName", task.getProject());
					member.put("status", task.isStatus() == true ? "1" : "0");
					jsonMembers.put(member);

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			try {
				json.put("tasks", jsonMembers);
				json.put("biggestID", Tasks.getBiggestId());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			try {
				json.put("biggestID", 0);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		String jsonStr = json.toString();
		try {
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			writer.write(jsonStr);
			writer.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public int getCurrentId() {
		return biggestId;
	}

}

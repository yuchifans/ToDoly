package com.siqi.taskadmin.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.siqi.taskadmin.model.Task;
import com.siqi.taskadmin.model.TaskList;
import com.siqi.taskadmin.util.DataUtil;

/**
 * Class FileDataProcessor-a processor which is used to read data of tasklist
 * from a file and write the data of tasklist into the certain file. JSON is
 * used as startard model to transmit the data.
 * 
 * This "FileDataProcessor" contains four behaviours of getFile, load, read and
 * write.
 * 
 * @author Siqi Qian
 * @version 2018.10.05
 */

public class FileDataProcessor {
	static private JSONObject json;
	static private JSONArray jsonMembers;

	public FileDataProcessor() {
		json = new JSONObject();
		jsonMembers = new JSONArray();
	}

	/**
	 * Try to get a file of "TasksDetails.txt", if it does not exit, create a new
	 * file by using the same name.
	 */
	private static File getFile() {
		File file = new File("TasksDetails.txt");
		if (file.exists()) {
			return file;
		} else {
			try {
				file.createNewFile();
				return file;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * Try to load the file data and use a string to catch all the data from the
	 * file, then cast the string to a JsonObject. Obtain the JsonArray of task by
	 * parsing the JsonObject by using key of "tasks".
	 */
	private static void load() {
		if (getFile() != null) {
			File file = getFile();
			StringBuilder jsonStr = new StringBuilder();
			String temp = "";
			BufferedReader br = null;
			try {
				FileReader fr = new FileReader(file);
				br = new BufferedReader(fr);
				while ((temp = br.readLine()) != null) {
					jsonStr.append(temp);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (!jsonStr.toString().equals("")) {
				try {
					json = new JSONObject(jsonStr.toString());
					if (json.has("tasks")) {
						jsonMembers = json.getJSONArray("tasks");
					}
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	/**
	 * After loading, loops in the JsonArray to get the all the tasks one by one.
	 * All the tasks are stored in a object of "TaskList" and it will be returned at
	 * last.
	 * 
	 * @return TaskList a tasklist obtained from the file.
	 */
	public static TaskList read() {
		load();
		TaskList tasks = new TaskList();
		for (int i = 0; i < jsonMembers.length(); i++) {
			JSONObject taskJson = new JSONObject();
			try {
				Task task = new Task();
				taskJson = (JSONObject) jsonMembers.get(i);
				task.setTitle((String) taskJson.get("title"));
				String dueDateStr = (String) taskJson.get("dueDate");
				task.setDuedate(new SimpleDateFormat("dd-MM-yyyy").parse(dueDateStr));
				task.setProject((String) taskJson.get("projectName"));
				if (((String) taskJson.get("status")).equals("1")) {
					task.setStatus(true);
				} else {
					task.setStatus(false);
				}
				tasks.addTask(task);

			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}
		return tasks;
	}

	/**
	 * Transform each "Task" of "TaskList" to JsonObject and combine all the "Task"s
	 * into JsonArray. Put the JsonArray into a JsonObject and cast it to a string.
	 * Write the string to the certain file finally.
	 * 
	 * @param tasks a tasklist need to be saved in the file.
	 */
	public static void write(TaskList tasks) {
		jsonMembers = new JSONArray();
		if (tasks.getNumberOfTasks() != 0) {
			for (Task task : tasks.getTasks()) {
				try {
					JSONObject member = new JSONObject();
					member.put("title", task.getTitle());
					member.put("dueDate", DataUtil.dateToString(task.getDuedate()));
					member.put("projectName", task.getProject());
					member.put("status", task.getStatus() == true ? "1" : "0");
					jsonMembers.put(member);

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			try {
				json.put("tasks", jsonMembers);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		String jsonStr = json.toString();
		try {
			File file = getFile();
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			writer.write(jsonStr);
			writer.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}

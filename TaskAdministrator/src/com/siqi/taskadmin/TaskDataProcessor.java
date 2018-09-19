package com.siqi.taskadmin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
			if (!jsonStr.toString().equals("")) {
				try {
					json = new JSONObject(jsonStr.toString());
					jsonMembers = json.getJSONArray("tasks");
					if (json.has("biggestID")) {
						biggestId = json.getInt("biggestID");
					}
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	private void read() {
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
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public Tasks sortByDate() {
		read();
		if (tasks != null) {
			try {
				Collections.sort(tasks.getTasks(), new Comparator<Task>() {
					public int compare(Task task1, Task task2) {
						String[] tempDate1 = task1.getDuedate().split("-");
						String[] tempDate2 = task2.getDuedate().split("-");
						int[] date1 = new int[tempDate1.length];
						int[] date2 = new int[tempDate2.length];

						for (int i = 0; i < date1.length; i++) {
							date1[i] = Integer.parseInt(tempDate1[i]);
						}
						for (int i = 0; i < date1.length; i++) {
							date2[i] = Integer.parseInt(tempDate2[i]);
						}

						if (date1[0] > date2[0]) {
							return -1;
						} else if (date1[0] == date2[0]) {
							if (date1[1] > date2[1]) {
								return -1;
							} else if (date1[1] == date2[1]) {
								if (date1[2] > date2[2]) {
									return -1;
								} else if (date1[2] == date2[2]) {
									return 0;
								} else {
									return 1;
								}
							} else {
								return 1;
							}
						} else {
							return 1;
						}
					}
				});

			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			return tasks;
		} else {
			return null;
		}
	}

	public int[] getNumberOfTasksByStatus() {
		int[] tasksNumber = new int[2];
		JSONObject task;
		for (int i = 0; i < jsonMembers.length(); i++) {
			try {
				task = (JSONObject) jsonMembers.get(i);
				if (task.get("status").equals("0")) {
					tasksNumber[0]++;
				} else if (task.get("status").equals("1")) {
					tasksNumber[1]++;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		return tasksNumber;
	}

	public boolean remove(int taskId) {
		boolean isSuccess = false;
		read();
		for (int i = 0; i < jsonMembers.length(); i++) {
			try {
				JSONObject member = (JSONObject) jsonMembers.get(i);
				if (member.getInt("taskId") == taskId) {
					jsonMembers.remove(i);
					isSuccess = true;
					String jsonStr = json.toString();
					write(jsonStr);
					System.out.println("Task has been removed!");
					return isSuccess;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return isSuccess;
	}

	public void add(Task task) {
		try {

			JSONObject member = new JSONObject();
			member.put("taskId", task.getId());
			member.put("title", task.getTitle());
			member.put("dueDate", task.getDuedate());
			member.put("projectName", task.getProject());
			member.put("status", task.isStatus() == true ? "1" : "0");
			jsonMembers.put(member);
			json.put("tasks", jsonMembers);
			json.put("biggestID", biggestId);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		String jsonStr = json.toString();
		write(jsonStr);

	}

	private void write(String jsonStr) {
		try {
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			writer.write(jsonStr);
			writer.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public Tasks filterByProject(String projectName) {
		read();
		tasks = sortByDate();
		ArrayList<Task> filteredTasks = tasks.getTasks().stream().filter(t -> t.getProject().contains(projectName))
				.collect(Collectors.toCollection(ArrayList::new));
		tasks.setTasks(filteredTasks);
		return tasks;
	}

	public void idIncrement() {
		biggestId += 1;
	}

	public int getBiggestId() {
		System.out.println(biggestId);
		return biggestId;
	}

}

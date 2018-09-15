package com.siqi.taskadmin;

import java.util.HashMap;

public enum CommandWord {

	SHOW(0, 0, "Show task List(By date or project)"), ADD(0, 1, "Add new task"), EDIT(0, 2, "Edit task"),
	SAVE(0, 3, "Save and quit"), QUIT(2, 4, "quit"), HELP(2, 5, "help"), UNKNOWN(2, 6, "?"), BYDATE(1, 0, "By date"),
	BYPROJECT(1, 1, "By project");

	private int commandType;
	private int commandIndex;
	private String commandString;
	private HashMap<Integer,CommandWord> childCommandWords;

	CommandWord(int commandType, int commandIndex, String commandString) {
		this.commandType = commandType;
		this.commandIndex = commandIndex;
		this.commandString = commandString;
		this.childCommandWords=new HashMap<Integer,CommandWord>();

	}

	public void addChildCommand(CommandWord commandword) {
		int wordKey=childCommandWords.size()+1;
		childCommandWords.put(wordKey,commandword);
	}

	public String toString() {
		return commandString;
	}

	public int getIndex() {
		return commandIndex;
	}

	public int getType() {
		return commandType;
	}
	
	public HashMap<Integer, CommandWord> getChildCommandWords() {
		return childCommandWords;
	}
	
	public CommandWord getSingleChildCommandWord(int commandIndex) {
		if (childCommandWords.containsKey(commandIndex)) {
			return childCommandWords.get(commandIndex);
		} else {
			return CommandWord.UNKNOWN;
		}
	}

	public void setChildCommandWords(HashMap<Integer, CommandWord> childCommandWords) {
		this.childCommandWords = childCommandWords;
	}

	
}

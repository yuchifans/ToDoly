package com.siqi.taskadmin.menu;

import java.util.HashMap;

public enum CommandWord {

	SHOW(0, 0, "Show all tasks(By date or project)"), ADD(0, 1, "Add new task"), QUIT(0, 2, "Quit and save"),
	BYDATE(1, 0, "By date"),BYPROJECT(1, 1, "By project"),SAVEANDRETURNAFTERADD(1,0,"Save and return"), QUITANDRETURNAFTERADD(1,1,"Quit and return"),
	SAVEANDRETURNAFTEREDIT(1,0,"Save and return"), QUITANDRETURNAFTEREDIT(1,1,"Quit and return"),RETURNTOMAIN(2,2,"Return"), REMOVE(2, 0, "Remove task"),EDITALL(2,1,"Edit task"),UNKNOWN(-1, 6, "?");

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

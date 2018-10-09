package com.siqi.taskadmin.menu;

import java.util.HashMap;

/**
 * This class is part of the "ToDoly" application. "ToDoly" is a small
 * application which is used to administrate all the tasks owned by user.
 * 
 * This class holds an enumeration of all command word known to the application.
 * It is used to recognise commands when they are typed in.
 *
 * @author Siqi Qian
 * @version 2018.10.04
 */
public enum CommandWord {

	SHOW(0, 0, "Show all tasks(By date or project)"), ADD(0, 1, "Add new task"), QUIT(0, 2, "Quit and save"),
	BYDATE(1, 0, "By date"), BYPROJECT(1, 1, "By project"), SAVEANDRETURNAFTERADD(1, 0, "Save and return"),
	QUITANDRETURNAFTERADD(1, 1, "Quit and return"), SAVEANDRETURNAFTEREDIT(1, 0, "Save and return"),
	QUITANDRETURNAFTEREDIT(1, 1, "Quit and return"), RETURNTOMAIN(2, 2, "Return"), REMOVE(2, 0, "Remove task"),
	EDIT(2, 1, "Edit task"), UNKNOWN(-1, 6, "?");

	@SuppressWarnings("unused")
	private int commandType;
	@SuppressWarnings("unused")
	private int commandIndex;
	@SuppressWarnings("unused")
	private String commandString;
	private HashMap<Integer, CommandWord> childCommandWords;

	CommandWord(int commandType, int commandIndex, String commandString) {
		this.commandType = commandType;
		this.commandIndex = commandIndex;
		this.commandString = commandString;
		this.childCommandWords = new HashMap<Integer, CommandWord>();
	}

	/**
	 * Add a CommandWord as child CommandWord to parent CommandWord.
	 * 
	 * @param commandword a CommandWord to be added.
	 */
	public void addChildCommand(CommandWord commandword) {
		int wordKey = childCommandWords.size() + 1;
		childCommandWords.put(wordKey, commandword);
	}

	/**
	 * Get all the child CommandWords corresponding to parent CommandWord.
	 * 
	 * @return a HashMap which contains all the child CommandWords
	 */
	public HashMap<Integer, CommandWord> getChildCommandWords() {
		return childCommandWords;
	}

	/**
	 * Find the CommandWord in childCommandWords associated with an index.
	 * 
	 * @param commandIndex The index of CommandWord to look up.
	 * @return The CommandWord correspondng to commandIndex, or UNKNOWN if it is not
	 *         a valid command word.
	 */
	public CommandWord getSingleChildCommandWord(int commandIndex) {
		if (childCommandWords.containsKey(commandIndex)) {
			return childCommandWords.get(commandIndex);
		} else {
			return CommandWord.UNKNOWN;
		}
	}

}

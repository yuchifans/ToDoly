/**
 * 
 */
package com.siqi.taskadmin;

import java.util.HashMap;

/**
 * This class is part of the "ToDoly" application. "ToDoly" is a small
 * application which is used to administrate all the tasks owned by user.
 * 
 * This class holds an enumeration of all command menu known to the application.
 * It is used to recognise commands as they are typed in.
 *
 * @author Siqi Qian
 * @version 2018.09.12
 */
public class CommandMenu {

	private HashMap<Integer, CommandWord> commandMenu;

	CommandMenu() {
		commandMenu = new HashMap<>();
		
		commandMenu.put(1, CommandWord.SHOW);
		CommandWord.SHOW.getChildCommandWords().clear();
		CommandWord.SHOW.addChildCommand(CommandWord.BYDATE);
		CommandWord.SHOW.addChildCommand(CommandWord.BYPROJECT);
		commandMenu.put(2, CommandWord.ADD);
		commandMenu.put(3, CommandWord.EDIT);
		commandMenu.put(4, CommandWord.SAVE);
	}

	/**
	 * Find the CommandWord associated with a command word.
	 * 
	 * @param commandWord The word to look up.
	 * @return The CommandWord correspondng to commandWord, or UNKNOWN if it is not
	 *         a valid command word.
	 */
	public CommandWord getCommandWord(int commandIndex) {
		if (commandMenu.containsKey(commandIndex)) {
			return commandMenu.get(commandIndex);
		} else {
			return CommandWord.UNKNOWN;
		}
	}

	public HashMap<Integer, CommandWord> getCommandMenu() {
		return commandMenu;
	}

	public void addCommandToMenu(CommandWord commandword) {
		int commandKey = commandMenu.size() + 1;
		commandMenu.put(commandKey, commandword);
	}

	public void printTopMenu() {
		for (int commandIndex : commandMenu.keySet()) {
			System.out.println("(" + commandIndex + ") " + commandMenu.get(commandIndex).toString());
		}
		System.out.println("Just input a number from 1 to " + commandMenu.size());
	}

	public void printSecondLevelMenu(CommandWord commandWord) {
		HashMap<Integer,CommandWord> childMenu= commandWord.getChildCommandWords();
		for (int commandIndex : childMenu.keySet()) {
			System.out.println("(" + commandIndex + ") " + childMenu.get(commandIndex).toString());
		}
		System.out.println("Just input a number of from 1 to "+ childMenu.size());
	}

}

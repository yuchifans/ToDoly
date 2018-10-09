
package com.siqi.taskadmin.menu;

import java.util.HashMap;

public class CommandMenu {

	private HashMap<Integer, CommandWord> commandMenu;

	/**
	 * Constructor. Create a menu of CommandWord in a tree structure. Top menu is
	 * "Show all tasks(By date or project)", "Add new task", and "Quit and save".
	 * Child menu of "Show all tasks(By date or project)" is "By date" and "By
	 * project". Child menu of "By date" is "Remove task", "Edit task" and "Return".
	 * Child menu of "By project" is "Remove task", "Edit task" and "Return". Child
	 * menu of "Edit task" is "Save and return" and "Quit and return". Child menu of
	 * "Add new task" is "Save and return" and "Quit and return".
	 */
	public CommandMenu() {

		commandMenu = new HashMap<>();
		commandMenu.put(1, CommandWord.SHOW);
		CommandWord.SHOW.getChildCommandWords().clear();
		CommandWord.SHOW.addChildCommand(CommandWord.BYDATE);
		CommandWord.SHOW.addChildCommand(CommandWord.BYPROJECT);
		CommandWord.BYDATE.getChildCommandWords().clear();
		CommandWord.BYDATE.addChildCommand(CommandWord.REMOVE);
		CommandWord.BYDATE.addChildCommand(CommandWord.EDIT);
		CommandWord.BYDATE.addChildCommand(CommandWord.RETURNTOMAIN);
		CommandWord.EDIT.getChildCommandWords().clear();
		CommandWord.EDIT.addChildCommand(CommandWord.SAVEANDRETURNAFTEREDIT);
		CommandWord.EDIT.addChildCommand(CommandWord.QUITANDRETURNAFTEREDIT);
		CommandWord.BYPROJECT.getChildCommandWords().clear();
		CommandWord.BYPROJECT.addChildCommand(CommandWord.REMOVE);
		CommandWord.BYPROJECT.addChildCommand(CommandWord.EDIT);
		CommandWord.BYPROJECT.addChildCommand(CommandWord.RETURNTOMAIN);
		commandMenu.put(2, CommandWord.ADD);
		CommandWord.ADD.getChildCommandWords().clear();
		CommandWord.ADD.addChildCommand(CommandWord.SAVEANDRETURNAFTERADD);
		CommandWord.ADD.addChildCommand(CommandWord.QUITANDRETURNAFTERADD);
		commandMenu.put(3, CommandWord.QUIT);
	}

	/**
	 * Find the CommandWord in the top menu associated with a index.
	 * 
	 * @param commandIndex The index of commandWord to look up.
	 * @return The CommandWord correspondng to commandIndex, or UNKNOWN if it is not
	 *         a valid command word.
	 */
	public CommandWord getCommandWord(int commandIndex) {
		if (commandMenu.containsKey(commandIndex)) {
			return commandMenu.get(commandIndex);
		} else {
			return CommandWord.UNKNOWN;
		}
	}

	/**
	 * Print out the top menu.
	 */
	public void printTopMenu() {
		System.out.println("Pick an option:");
		for (int commandIndex : commandMenu.keySet()) {
			System.out.println("(" + commandIndex + ") " + commandMenu.get(commandIndex).toString());
		}
		System.out.println("Hint: Just input a number from 1 to " + commandMenu.size());
	}

	/**
	 * Print out the child menu corresponding to a parent CommandWord.
	 * @param parentCommandWord a parentCommandWord whose child commands to be printed
	 */
	public void printChildMenu(CommandWord parentCommandWord) {
		System.out.println("Pick an option:");
		HashMap<Integer, CommandWord> childMenu = parentCommandWord.getChildCommandWords();
		for (int commandIndex : childMenu.keySet()) {
			System.out.println("(" + commandIndex + ") " + childMenu.get(commandIndex).toString());
		}
		System.out.println("Hint: Just input a number of from 1 to " + childMenu.size());
	}

}

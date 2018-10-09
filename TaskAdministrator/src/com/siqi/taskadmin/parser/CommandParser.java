package com.siqi.taskadmin.parser;

import com.siqi.taskadmin.menu.CommandMenu;
import com.siqi.taskadmin.menu.CommandWord;
import com.siqi.taskadmin.util.DataUtil;
import java.util.Scanner;

/**
 * This class is part of the "ToDoly" application.
 * 
 * This parser reads user input and tries to interpret it as a valid command.
 * Every time it is called it reads a line from the terminal and tries to
 * interpret the line as command.
 *
 * The parser has a set of known command words. It checks user input against the
 * known commands, and if the input is not one of the known commands, it returns
 * a command object that is marked as an unknown command.
 * 
 * @author Siqi Qian
 * @version 2018.10.07
 */
public class CommandParser {

	private Scanner reader;
	private String inputCommandWord;

	public CommandParser() {
		reader = new Scanner(System.in);
	}

	/**
	 * Get a command from user.
	 * 
	 * @return a string of command
	 */
	public String readCommand() {
		System.out.print("> ");
		inputCommandWord = reader.nextLine();
		return inputCommandWord;
	}

	/**
	 * Get a command of number from user. First check whether the command is format
	 * of an integer(index), if yes, try to get a CommandWord of certain index,
	 * return CommandWord "UNKNOWN" otherwise.
	 * 
	 * @return The CommandWord correspondng to the command, or UNKNOWN if it is not
	 *         a valid command word.
	 */
	public CommandWord getTopMenuCommand() {
		readCommand();
		CommandMenu commandMenu = new CommandMenu();
		if (DataUtil.isInteger(inputCommandWord) && !inputCommandWord.equals("")) {
			return commandMenu.getCommandWord(Integer.parseInt(inputCommandWord));
		}
		return CommandWord.UNKNOWN;
	}

	/**
	 * Get a command of number from user. First check whether the command is format
	 * of an integer(index), if yes, try to get a CommandWord of certain index from
	 * its parent CommandWord, return CommandWord "UNKNOWN" otherwise.
	 * 
	 * @param parentCommandWord a parent CommandWord
	 * @return a certain child commandWord under parent CommandWord corresponding to
	 *         the valid command which is in number format
	 */
	public CommandWord getChildMenuCommand(CommandWord parentCommandWord) {
		readCommand();
		if (DataUtil.isInteger(inputCommandWord) && !inputCommandWord.equals("")) {
			return parentCommandWord.getSingleChildCommandWord(Integer.parseInt(inputCommandWord));
		}
		return CommandWord.UNKNOWN;
	}

}

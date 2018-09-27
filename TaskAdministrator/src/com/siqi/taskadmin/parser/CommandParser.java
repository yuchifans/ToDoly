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
 * interpret the line as a two-word command. It returns the command as an object
 * of class Command.
 *
 * The parser has a set of known command words. It checks user input against the
 * known commands, and if the input is not one of the known commands, it returns
 * a command object that is marked as an unknown command.
 * 
 * @author Siqi Qian
 * @version 2018.9.13
 */
public class CommandParser {
	private CommandMenu commandMenu; 
	private Scanner reader; 
	private String inputCommandWord;
	
	public CommandParser() {
		commandMenu = new CommandMenu();
		reader = new Scanner(System.in);
	}
	
	public String readCommand() {
		System.out.print("> "); 
		inputCommandWord = reader.nextLine();
		return inputCommandWord;
	}
	
	public CommandWord getTopMenuCommand() {
		readCommand();
		if (DataUtil.isInteger(inputCommandWord) && !inputCommandWord.equals("")) {
			return commandMenu.getCommandWord(Integer.parseInt(inputCommandWord));
		}
		return CommandWord.UNKNOWN;
	}
	
	public CommandWord getChildMenuCommand(CommandWord commandWord) {
		readCommand();
		if (DataUtil.isInteger(inputCommandWord) && !inputCommandWord.equals("")) {
			return commandWord.getSingleChildCommandWord(Integer.parseInt(inputCommandWord));
		}
		return CommandWord.UNKNOWN;
	}
	
}

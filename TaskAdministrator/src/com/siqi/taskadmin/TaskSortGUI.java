package com.siqi.taskadmin;

import java.util.ArrayList;

public class TaskSortGUI implements DialogGUI {
	private CommandMenu secondLevelMenuOfShow;
	private CommandParser commandParser;
	private TaskDataProcessor dataProcessor;

	public TaskSortGUI() {
		commandParser = new CommandParser();
		secondLevelMenuOfShow = new CommandMenu();
		dataProcessor = new TaskDataProcessor();
	}

	public void start() {
		secondLevelMenuOfShow.printSecondLevelMenu(CommandWord.SHOW);
		boolean finished = false;
		while (!finished) {
			Command command = commandParser.getChildMenuCommand(CommandWord.SHOW);
			finished = processCommand(command);
		}
	}

	public boolean processCommand(Command command) {
		boolean wantToQuit = false;
		CommandWord commandWord = command.getCommandWord();
		switch (commandWord) {
		case UNKNOWN:
			System.out.println("Please type in a proper number...");
			break;
		case BYDATE:
			dataProcessor.load();
			Tasks tasks = dataProcessor.read();
			if (tasks != null) {
				tasks.showAllTheTask();
			} else {
				System.out.println("There is no task currently!");
			}
				wantToQuit = true;
			break;
		case BYPROJECT:
			System.out.println("BYPROJECT");
			wantToQuit = true;
			break;
		}
		return wantToQuit;
	}

}

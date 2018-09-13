package com.siqi.taskadmin;

public class TaskSortGUI implements DialogGUI {
	private CommandMenu secondLevelMenuOfShow;
	private CommandParser commandParser;
	
	public TaskSortGUI() {
		commandParser=new CommandParser();
		secondLevelMenuOfShow=new CommandMenu();
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
			System.out.println("BYDATE");
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

package com.siqi.taskadmin.GUI;

import com.siqi.taskadmin.menu.CommandMenu;
import com.siqi.taskadmin.parser.Command;
import com.siqi.taskadmin.parser.CommandParser;
import com.siqi.taskadmin.parser.CommandWord;
import com.siqi.taskadmin.model.Tasks;

public class TaskSortGUI implements DialogGUI {
	private CommandMenu childMenuOfShow;
	private CommandParser commandParser;
	private Tasks tasks;

	public TaskSortGUI(Tasks tasks) {
		commandParser = new CommandParser();
		childMenuOfShow = new CommandMenu();
		this.tasks=tasks;
	}

	public void start() {
		childMenuOfShow.printChildMenu(CommandWord.SHOW);
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
			showTaskBydate();
			wantToQuit = true;
			break;
		case BYPROJECT:
			filterTaskByProject();
			wantToQuit = true;
			break;
		}
		return wantToQuit;
	}
	
	private void showTaskBydate() 
	{
		System.out.println("------------------------------------------------------------------------------");
		ShowTaskByDateGUI showTaskByDate=new ShowTaskByDateGUI(tasks);
		showTaskByDate.start();
	}
	
	private void filterTaskByProject() {
		System.out.println("------------------------------------------------------------------------------");
		FilterTaskByProjectGUI showTaskByProject=new FilterTaskByProjectGUI(tasks);
		showTaskByProject.start();
	}

}

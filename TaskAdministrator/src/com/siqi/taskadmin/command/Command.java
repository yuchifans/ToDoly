package com.siqi.taskadmin.command;

import com.siqi.taskadmin.menu.CommandWord;

public class Command {
	private CommandWord commandWord;

	public Command(CommandWord commandWord) {

		this.commandWord = commandWord;
	}

	public CommandWord getCommandWord() {
		return commandWord;
	}

}

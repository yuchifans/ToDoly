package com.siqi.taskadmin;

public class Command {
	private CommandWord commandWord;

	Command(CommandWord commandWord) {

		this.commandWord = commandWord;
	}

	public CommandWord getCommandWord() {
		return commandWord;
	}

}

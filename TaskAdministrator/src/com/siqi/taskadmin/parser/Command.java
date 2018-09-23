package com.siqi.taskadmin.parser;

public class Command {
	private CommandWord commandWord;

	Command(CommandWord commandWord) {

		this.commandWord = commandWord;
	}

	public CommandWord getCommandWord() {
		return commandWord;
	}

}

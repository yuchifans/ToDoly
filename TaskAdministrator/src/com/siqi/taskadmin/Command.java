package com.siqi.taskadmin;

public class Command {
	private CommandWord commandWord;

	Command(CommandWord commandWord) {

		this.commandWord = commandWord;
	}

	/**
	 * Return the command word (the first word) of this command.
	 * 
	 * @return The command word.
	 */
	public CommandWord getCommandWord() {
		return commandWord;
	}

}

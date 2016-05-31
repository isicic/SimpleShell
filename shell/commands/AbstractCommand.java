package hr.fer.oop.lab4.shell.commands;

import hr.fer.oop.lab4.shell.ShellCommand;

/**
 * Abstract shell command class
 * @author Ivan SIcic
 * @version 1.0
 *
 */

public abstract class AbstractCommand implements ShellCommand {
	private String commandName;
	private String commandDescription;
	
	/**
	 * Constructor method
	 * @param Name - Command name
	 * @param Description - command description
	 */

	public AbstractCommand(String Name, String Description) {
		commandName = Name;
		commandDescription = Description;
	}

	public String getCommandName() {
		return commandName;
	}

	public String getCommandDescription() {
		return commandDescription;
	}

}

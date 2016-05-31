package hr.fer.oop.lab4.shell.commands;

import hr.fer.oop.lab4.shell.CommandStatus;
import hr.fer.oop.lab4.shell.Environment;
/**
 * Shell command class
 * @author Ivan Sicic
 * @version 1.0
 */
public class QuitCommand extends AbstractCommand {

	public QuitCommand() {
		super("QUIT", "Quits the shell.");
	}
	/**
	 * @param env - shell environment
	 * @param s - arguments
	 * @return CommandStatus
	 */
	@Override
	public CommandStatus execute(Environment env, String s) {
		return CommandStatus.EXIT;
	}

}

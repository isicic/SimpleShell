package hr.fer.oop.lab4.shell.commands;

import java.security.InvalidParameterException;

import hr.fer.oop.lab4.shell.CommandStatus;
import hr.fer.oop.lab4.shell.Environment;

/**
 * Shell command class
 * 
 * @author Ivan Sicic
 * @version 1.0
 */
public class TerminalCommand extends AbstractCommand {

	public TerminalCommand() {
		super("TERMINAL",
				"Changes current terminal to the one with specified ID. "
						+ "If that terminal does not exist, it creates one.");
	}

	/**
	 * @param env
	 *            - shell environment
	 * @param s
	 *            - arguments
	 * @return CommandStatus
	 */
	@Override
	public CommandStatus execute(Environment env, String s) {
		if (s.isEmpty())
			throw new InvalidParameterException("Parameters must be provided.");
		env.setActiveTerminal(env.getOrCreateTerminal(Integer.valueOf(s)));

		return CommandStatus.CONTINUE;
	}

}

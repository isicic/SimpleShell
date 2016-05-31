package hr.fer.oop.lab4.shell;

import java.io.IOException;

/**
 * Shell command interface
 * 
 * @author Ivan Sicic
 * @version 1.0
 *
 */
public interface ShellCommand {

	public String getCommandName();

	public String getCommandDescription();

	public CommandStatus execute(Environment env, String s) throws IOException;

}

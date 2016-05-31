package hr.fer.oop.lab4.shell.commands;


import hr.fer.oop.lab4.shell.CommandStatus;
import hr.fer.oop.lab4.shell.Environment;
import hr.fer.oop.lab4.shell.ShellCommand;
/**
 * Shell command class
 * @author Ivan Sicic
 * @version 1.0
 */
public class HelpCommand extends AbstractCommand {

	public HelpCommand() {
		super("HELP", "Displays all commands and their descriptions.");
	}
	/**
	 * @param env - shell environment
	 * @param s - arguments
	 * @return CommandStatus
	 */
	@Override
	public CommandStatus execute(Environment env, String s) {
		Iterable<ShellCommand> iter = env.commands();
		
		env.writeln("\n");
		for (ShellCommand comm : iter) {
			env.writeln(comm.getCommandName() + " " + comm.getCommandDescription());
		}
		env.writeln("\n");

		return CommandStatus.CONTINUE;
	}

}

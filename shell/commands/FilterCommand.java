package hr.fer.oop.lab4.shell.commands;

import java.io.File;

import hr.fer.oop.lab4.shell.CommandStatus;
import hr.fer.oop.lab4.shell.Environment;
import hr.fer.oop.lab4.shell.Terminal;

/**
 * Shell command class
 * 
 * @author Ivan Sicic
 * @version 1.0
 */
public class FilterCommand extends AbstractCommand {

	public FilterCommand() {
		super("FILTER", "Searches current directory and its subdirectories "
				+ "and displays paths to files having a name "
				+ "that matches the pattern.");
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
		Terminal term = env.getActiveTerminal();
		File file = term.getCurrentPath().toFile();

		s = s.trim();
		if (s.isEmpty()) {
			env.writeln("No arguments provided.");
			return CommandStatus.CONTINUE;
		}

		filter(file, s, env);

		return CommandStatus.CONTINUE;
	}

	private void filter(File dir, String s, Environment env) {
		File[] files = dir.listFiles();

		for (File file : files) {
			if (file.isDirectory())
				filter(file, s, env);
			else {
				String name = file.getName().toUpperCase();
				String pattern[] = new String[2];
				String[] split = s.split("\\*");

				if (s.startsWith("*") && s.endsWith("*")) {
					pattern[0] = "";
					pattern[1] = "";

				} else if (s.startsWith("*")) {
					pattern[0] = "";
					pattern[1] = split[1];
				} else if (s.endsWith("*")) {
					pattern[0] = split[0];
					pattern[1] = "";
				} else if (!s.endsWith("*") && !s.startsWith("*")
						&& s.contains("*")) {

					pattern[0] = split[0];
					pattern[1] = split[1];
				}
				if (!(s.contains("*"))) {
					if (name.equals(split[0].toUpperCase()))
						env.writeln(file.toPath().toString());
				} else {
					if (name.startsWith(pattern[0].toUpperCase())
							&& name.endsWith(pattern[1].toUpperCase()))
						env.writeln(file.toPath().toString());
				}
			}
		}

	}
}

package hr.fer.oop.lab4.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import hr.fer.oop.lab4.shell.CommandStatus;
import hr.fer.oop.lab4.shell.Environment;

/**
 * Shell command class
 * 
 * @author Ivan Sicic
 * @version 1.0
 */
public class CdCommand extends AbstractCommand {

	public CdCommand() {
		super("CD", "Changes the current directory to provided path.");
	}

	/**
	 * @param env
	 *            - shell environment
	 * @param s
	 *            - arguments
	 * @return CommandStatus
	 */
	@Override
	public CommandStatus execute(Environment currentEnviroment, String line)
			throws IOException {
		line.trim();

		Path newPath;

		try {
			newPath = Paths.get(line);
		} catch (InvalidPathException e) {
			currentEnviroment.writeln("You entered invalid path.");
			return CommandStatus.CONTINUE;
		}

		if (newPath.toString().equals("..")) {
			currentEnviroment.getActiveTerminal().setCurrentPath(
					currentEnviroment.getActiveTerminal().getCurrentPath()
							.getParent());
		} else if (newPath.toString().equals(".")) {
		}

		else if (newPath.toString().equals("\\")) {
			currentEnviroment.getActiveTerminal().setCurrentPath(
					currentEnviroment.getActiveTerminal().getCurrentPath()
							.getRoot());
		} else {
			newPath = currentEnviroment.getActiveTerminal().getCurrentPath()
					.resolve(newPath);

			if (Files.exists(newPath) && Files.isDirectory(newPath))
				currentEnviroment.getActiveTerminal().setCurrentPath(
						newPath.toRealPath(LinkOption.NOFOLLOW_LINKS));
			else {
				currentEnviroment.writeln("Invalid path");
			}
		}

		currentEnviroment.writeln("Your new path is set to: "
				+ currentEnviroment.getActiveTerminal().getCurrentPath());

		return CommandStatus.CONTINUE;
	}
}

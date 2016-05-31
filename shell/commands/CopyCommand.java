package hr.fer.oop.lab4.shell.commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidParameterException;

import hr.fer.oop.lab4.shell.CommandStatus;
import hr.fer.oop.lab4.shell.Environment;
import hr.fer.oop.lab4.shell.Terminal;

/**
 * Shell command class
 * 
 * @author Ivan Sicic
 * @version 1.0
 */
public class CopyCommand extends AbstractCommand {

	public CopyCommand() {
		super("COPY", "Copies a file source a specified path to a directory.");

	}

	/**
	 * @param env
	 *            - shell environment
	 * @param s
	 *            - arguments
	 */
	@Override
	public CommandStatus execute(Environment env, String s) {
		Terminal term = env.getActiveTerminal();
		String[] param = s.split(" ");

		if (param.length != 2)
			throw new InvalidParameterException(
					"Invalid number of parameters parameters.");

		Path source = Paths.get(param[0]);
		if (source.isAbsolute() != true)
			source = term.getCurrentPath().resolve(source);
		if (source.toFile().isDirectory()) {
			env.writeln("System can't copy directory.");
			throw new InvalidParameterException("Wrong first parameter.");
		}

		Path dest = Paths.get(param[1]);
		if (dest.isAbsolute() != true)
			dest = term.getCurrentPath().resolve(dest);
		if (dest.toFile().isDirectory()) {
			if (dest.toFile().exists() != true) {
				env.writeln("Parent directory doesn't exist.");
				throw new InvalidParameterException("Wrong second parameter.");
			}

			dest = dest.resolve(source.getFileName());
		}

		FileInputStream sourceStream = null;
		try {
			sourceStream = new FileInputStream(source.toFile());
		} catch (FileNotFoundException e) {
			env.writeln("Can't open the stream.");
			return CommandStatus.CONTINUE;
		}
		FileOutputStream destStream = null;
		try {
			destStream = new FileOutputStream(dest.toFile());
		} catch (FileNotFoundException e) {
			env.writeln("Can't open the stream.");
			try {
				sourceStream.close();
			} catch (IOException e1) {

			}
			return CommandStatus.CONTINUE;
		}

		byte[] buff = new byte[1024];
		int byteNum;
		try {
			while ((byteNum = sourceStream.read(buff)) != -1)
				try {
					destStream.write(buff, 0, byteNum);
				} catch (IOException e) {
					e.printStackTrace();
				}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			sourceStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			destStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return CommandStatus.CONTINUE;
	}
}

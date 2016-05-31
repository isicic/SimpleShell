package hr.fer.oop.lab4.shell.commands;

import java.io.File;

import java.io.FileInputStream;
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

public class XCopyCommand extends AbstractCommand {

	public XCopyCommand() {
		super("XCOPY", "Recursively copies a whole directory to another.");
	}

	/**
	 * @param env
	 *            - shell environment
	 * @param s
	 *            - arguments
	 * @return CommandStatus
	 */
	@Override
	public CommandStatus execute(Environment env, String s) throws IOException {

		Terminal term = env.getActiveTerminal();
		String[] param = s.split(" ");

		if (param.length != 2)
			throw new InvalidParameterException(
					"Invalid number of parameters parameters.");

		Path source = Paths.get(param[0]);
		if (source.isAbsolute() != true)
			source = term.getCurrentPath().resolve(source);
		if (source.toFile().isDirectory() != true) {
			env.writeln("Source path must be a directory.");
			throw new InvalidParameterException("Wrong first parameter.");
		}

		Path dest = Paths.get(param[1]);
		if (dest.isAbsolute() != true)
			dest = term.getCurrentPath().resolve(dest);
		if (dest.toFile().exists()) {
			if (dest.toFile().isDirectory())
				dest = dest.resolve(source.getFileName());
			else
				dest = dest.getParent();
		} else if (dest.getParent().toFile().exists() != true) {
			env.writeln("Parent directory doesn't exist.");
			throw new InvalidParameterException("Wrong second parameter.");
		}

		xcopy(source.toFile(), dest.toFile());
		return CommandStatus.CONTINUE;
	}

	/**
	 * Recursice function copying a directory
	 * 
	 * @param source
	 *            - source directory
	 * @param dest
	 *            - destination directory
	 * @throws IOException
	 *             - throwing input/output exception
	 */
	private void xcopy(File source, File dest) throws IOException {
		File[] files = source.listFiles();
		dest.mkdirs();

		for (File file : files) {
			if (file.isDirectory())
				xcopy(file, dest.toPath().resolve(file.getName()).toFile());
			else
				copyFile(file, dest.toPath().resolve(file.getName()).toFile());
		}

	}

	/**
	 * Method copying just a file
	 * 
	 * @param file
	 *            - source file
	 * @param file2
	 *            - dest file
	 * @throws IOException
	 *             - throwing input/output
	 */
	private void copyFile(File file, File file2) throws IOException {
		FileInputStream sourceStream = new FileInputStream(file);
		FileOutputStream destStream = new FileOutputStream(file2);

		byte[] buffer = new byte[4096];
		int bytesNum;
		while ((bytesNum = sourceStream.read(buffer)) != -1)
			destStream.write(buffer, 0, bytesNum);

		sourceStream.close();
		destStream.close();

	}
}

package hr.fer.oop.lab4.shell.commands;

import hr.fer.oop.lab4.shell.CommandStatus;
import hr.fer.oop.lab4.shell.Environment;
import hr.fer.oop.lab4.sorts.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import hr.fer.oop.lab4.filter.FiltererByMask;
import hr.fer.oop.lab4.filter.IFileFilterer;

/**
 * ShellComand that sorts and lists filtered files in given directory
 * @author Ivan Sicic
 * @version 1.1
 *
 */
public class DirCommand extends AbstractCommand {

	String type;

	public DirCommand() {
		super("DIR", "Sorts and lists filtered files in given directory");
	}

	@Override
	public CommandStatus execute(Environment env, String s) {
		String arguments[] = s.split(" ");
		Path path;
		String mask = "*";
		type = "";
		String sort = "";
		
		IFileFilterer fileFilterer = new FiltererByMask();

		// obtaining path
		if (arguments[0].startsWith("/sort")
				|| arguments[0].startsWith("/type")
				|| arguments[0].startsWith("/filter") || arguments[0] == "")
			path = env.getActiveTerminal().getCurrentPath();
		else {
			path = env.getActiveTerminal().getCurrentPath()
					.resolve(Paths.get(arguments[0])).normalize();
			if (!path.toFile().exists()) {
				env.writeln("Invalid path.");
				return CommandStatus.CONTINUE;
			}
		}
		System.out.println(path.toString());
		String temp;
		for (String argument : arguments) {
			if (!argument.contains("/") || !argument.contains("="))
				continue;
			temp = argument.substring(argument.indexOf("/"),
					argument.indexOf("="));

			switch (temp) {
			case "/filter":
				mask = argument.substring(argument.indexOf("=") + 1,
						argument.length());
				if (mask.equals(""))
					mask = "*";
				break;
			case "/type":
				type = argument.substring(argument.indexOf("=") + 1,
						argument.length());
				break;
			case "/sort":
				sort = argument.substring(argument.indexOf("=") + 1,
						argument.length());
			}
		}

		File files[] = path.toFile().listFiles();
		List<File> lFiles = new LinkedList<File>();

		for (File f : files) {
			if (fileFilterer.filter(f, mask) && rightType(f))
				lFiles.add(f);
		}

		List<Comparator<File>> sortList = new ArrayList<>();
		sortList.add(new TypeSort());

		Collections.sort(lFiles, new DirSort(sort));

		for (File f : lFiles) {
			System.out.println(f.getName());
		}

		return CommandStatus.CONTINUE;
	}

	private boolean rightType(File f) {
		switch (type) {
		case "d":
			return f.isDirectory();
		case "f":
			return f.isFile();
		case "":
			return true;
		}
		return false;
	}
}

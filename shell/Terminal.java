package hr.fer.oop.lab4.shell;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Terminal class
 * 
 * @author Ivan Sicic
 * @version 1.0
 *
 */
public class Terminal {

	private int terminalId;
	private Path currentPath;

	/**
	 * Constructor method
	 * 
	 * @param id
	 *            - terminal id
	 */
	public Terminal(int id) {
		this.terminalId = id;
		setCurrentPath(Paths.get(".").normalize().toAbsolutePath());
	}

	/**
	 * getter method
	 * 
	 * @return terminal ID
	 */
	public int getId() {
		return terminalId;
	}

	/**
	 * Path getter method
	 * 
	 * @return terminal current path
	 */
	public Path getCurrentPath() {
		return currentPath;
	}

	/**
	 * Path setter method
	 * 
	 * @param newPath
	 *            terminal path to be set
	 */
	public void setCurrentPath(Path newPath) {
		if (newPath.toFile().exists())
			this.currentPath = newPath;
		else
			throw new IllegalArgumentException("Invalid path.");
	}

}

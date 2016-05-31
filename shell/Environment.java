package hr.fer.oop.lab4.shell;

import java.io.IOException;

/**
 * Shell environment interface
 * 
 * @author Ivan Sicic
 * @version 1.0
 *
 */
public interface Environment {

	public String readLine() throws IOException;

	public void write(String s) throws IOException;

	public void writeln(String s);

	public Terminal getActiveTerminal();

	public void setActiveTerminal(Terminal t);

	public Terminal getOrCreateTerminal(int i);

	public Terminal[] listTerminals();

	public Iterable<ShellCommand> commands();

}

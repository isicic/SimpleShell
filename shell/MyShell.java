package hr.fer.oop.lab4.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import hr.fer.oop.lab4.simplehash.SimpleHashtable;
import hr.fer.oop.lab4.simplehash.SimpleHashtable.TableEntry;
import hr.fer.oop.lab4.shell.commands.*;

/**
 * Shell class
 * 
 * @author Ivan Sicic
 * @version 1.0
 *
 */
public class MyShell {
	private static SimpleHashtable<String, ShellCommand> commands;

	/**
	 * adding commands to the table
	 */
	static {
		commands = new SimpleHashtable<String, ShellCommand>();
		ShellCommand[] cc = { new HelpCommand(), new QuitCommand(),
				new CdCommand(), new TerminalCommand(), new TypeCommand(),
				new FilterCommand(), new CopyCommand(), new XCopyCommand(),
				new DirCommand() };

		for (ShellCommand c : cc) {
			commands.put(c.getCommandName(), c);
		}
	}

	/**
	 * Nested class implementing Environment interface and simulating shell
	 * environment
	 * 
	 * @author Ivan Sicic
	 * @version 1.0
	 *
	 */
	public static class EnvironmentImpl implements Environment {

		private SimpleHashtable<Integer, Terminal> terminals = new SimpleHashtable<>();

		private Terminal activeTerminal;

		private BufferedReader reader = new BufferedReader(
				new InputStreamReader(System.in));

		private BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(System.out));

		/**
		 * Method reading input from a standard keyboard
		 */
		@Override
		public String readLine() {
			try {
				return reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		/**
		 * Method displaying on the screen
		 * 
		 * @param s
		 *            - string to be displayed
		 */
		@Override
		public void write(String s) {
			try {
				writer.write(s);
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		/**
		 * Displaying method, moving to new line after writing
		 * 
		 * @param s
		 *            - String to be displayed
		 */

		@Override
		public void writeln(String s) {
			try {
				writer.write(s + "\n");
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		/**
		 * active terminal getter
		 * 
		 * @return - active terminal
		 */
		@Override
		public Terminal getActiveTerminal() {
			return activeTerminal;
		}

		/**
		 * Method setting active terminal
		 * 
		 * @param t
		 *            - terminal to be set
		 */
		@Override
		public void setActiveTerminal(Terminal t) {
			activeTerminal = t;
		}

		/**
		 * Method for getting or creating a terminal
		 * 
		 * @param i
		 *            - terminal ID
		 * @return - a new terminal
		 */
		@Override
		public Terminal getOrCreateTerminal(int i) {
			if (terminals.containsKey(i))
				return (Terminal) terminals.get(i);

			Terminal newTerminal = new Terminal(i);
			terminals.put(i, newTerminal);
			return newTerminal;
		}

		/**
		 * Method listing terminals
		 * 
		 * @return list of terminals
		 */
		@Override
		public Terminal[] listTerminals() {
			Terminal[] listOfTerms = new Terminal[terminals.size()];
			int i = 0;
			for (TableEntry<Integer, Terminal> t : terminals)
				listOfTerms[i++] = t.getValue();

			return listOfTerms;
		}

		/**
		 * Method providing Iterable
		 * 
		 * @return Iterable commands
		 */

		@Override
		public Iterable<ShellCommand> commands() {
			return commands.values();
		}
	}

	private static Environment environment = new EnvironmentImpl();

	/**
	 * Main method
	 * 
	 * @param args
	 *            command line arguments
	 * 
	 */
	public static void main(String[] args) {
		CommandStatus status;
		environment.writeln("Welcome to MyShell! You may enter commands.");

		environment.setActiveTerminal(environment.getOrCreateTerminal(1));

		/**
		 * Looping and providing shell
		 */
		while (true) {

			try {
				environment.write(environment.getActiveTerminal().getId() + "$"
						+ environment.getActiveTerminal().getCurrentPath()
						+ "> ");
			} catch (IOException e1) {
				e1.printStackTrace();
				continue;
			}

			String line;
			try {
				line = environment.readLine();
			} catch (IOException e1) {
				e1.printStackTrace();
				continue;
			}
			String cmd = line.split(" ")[0];
			String param = line.substring(line.indexOf(' ') + 1);
			if (line.split(" ").length == 1)
				param = "";
			ShellCommand shellCommand = commands.get(cmd.toUpperCase());
			if (shellCommand == null) {
				environment.writeln("Unknown command!");
				continue;
			}
			try {
				status = shellCommand.execute(environment, param);
			} catch (Exception e) {
				environment.writeln(e.getMessage());
				continue;
			}
			if (status == CommandStatus.EXIT)
				break;
		}
		environment.writeln("Thank you for using this shell. Goodbye!");
	}
}
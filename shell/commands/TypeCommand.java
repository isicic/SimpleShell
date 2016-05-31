package hr.fer.oop.lab4.shell.commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import hr.fer.oop.lab4.shell.CommandStatus;
import hr.fer.oop.lab4.shell.Environment;

/**
 * Shell command class
 * @author Ivan Sicic
 * @version 1.0
 */
public class TypeCommand extends AbstractCommand {

	public TypeCommand() {
		super("TYPE", "Prints the contents of specified sfile on the screen.");
	}
	/**
	 * @param env - shell environment
	 * @param s - arguments
	 * @return CommandStatus
	 */
	@Override
	public CommandStatus execute(Environment env, String s) {
		try {
            BufferedReader reader = new BufferedReader(new FileReader(Paths.get(s).toFile()));
            String line;
            while ((line = reader.readLine()) != null)
                env.writeln(line);
            reader.close();
        } catch (IOException ex) {
            env.writeln("The system cannot find the file specified.");
        }
		return CommandStatus.CONTINUE;
	}
	

}

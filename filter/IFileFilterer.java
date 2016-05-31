package hr.fer.oop.lab4.filter;

import java.io.File;

/**
 * Interface for File filters
 * @author Ivan Sicic
 * @version 1.0
 *
 */
public interface IFileFilterer {
	
	public boolean filter (File file, String mask);
}

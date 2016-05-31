package hr.fer.oop.lab4.sorts;

import java.io.File;

/**
 * Comparator class used for sorting by type
 * @author Ivan Sicic
 * @version 1.0
 */
public class TypeSort extends AbstractSort {

	public TypeSort() {
		super("T");
	}
	
	@Override
	public int compare(File o1, File o2) {
		if (o1.isDirectory() && o2.isFile())
			return -1;
		if (o1.isFile() && o2.isDirectory())
			return 1;
		return 0;
	}

}

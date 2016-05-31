package hr.fer.oop.lab4.sorts;

import java.io.File;

/**
 * Comparator class used for sorting by name
 * @author Ivan Sicic
 * @version 1.0
 */
public class NameSort extends AbstractSort {

	public NameSort() {
		super("N");
	}

	@Override
	public int compare(File o1, File o2) {
		return o1.compareTo(o2);
	}

}

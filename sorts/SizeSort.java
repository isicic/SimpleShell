package hr.fer.oop.lab4.sorts;

import java.io.File;

/**
 * Comparator class used for sorting by size
 * @author Ivan Sicic
 * @version 1.0
 */
public class SizeSort extends AbstractSort {

	public SizeSort() {
		super("S");
	}

	@Override
	public int compare(File o1, File o2) {
		if (o1.length() > o2.length())
			return 1;
		if (o1.length() < o2.length())
			return -1;
		return 0;
	}

}

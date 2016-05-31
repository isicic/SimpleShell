package hr.fer.oop.lab4.sorts;

import java.io.File;

/**
 * Comparator class used for sorting by date
 * @author Ivan Sicic
 * @version 1.0
 */
public class DateSort extends AbstractSort {

	public DateSort() {
		super("D");
	}

	@Override
	public int compare(File o1, File o2) {
		if (o1.lastModified() > o2.lastModified())
			return 1;
		if (o1.lastModified() < o2.lastModified())
			return -1;
		return 0;
	}

}

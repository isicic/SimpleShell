package hr.fer.oop.lab4.sorts;

import java.io.File;

/**
 * Comparator class used for sorting by extension
 * @author Ivan Sicic
 * @version 1.0
 */
public class ExtensionSort extends AbstractSort {

	public ExtensionSort() {
		super("E");
	}
	
	@Override
	public int compare(File o1, File o2) {
		if (o1.isDirectory())
			if (o2.isDirectory())
				return 0;
			else
				return -1;
		else if (o2.isFile())
			return (o1.toString().substring(o1.toString().lastIndexOf(".")))
					.compareTo(o2.toString().substring(
							o2.toString().lastIndexOf(".")));
		else
			return 1;
	}

}

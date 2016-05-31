package hr.fer.oop.lab4.sorts;

import java.io.File;
import java.util.Comparator;
/**
 * Abstract sort class
 * @author Ivan Sicic
 * @version 1.1
 *
 */
public abstract class AbstractSort implements Comparator<File> {

	private String sortName;

	public AbstractSort(String sortName) {
		this.sortName = sortName;
	}

	public String getSortName() {
		return sortName;
	}

}

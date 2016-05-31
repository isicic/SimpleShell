package hr.fer.oop.lab4.sorts;

import hr.fer.oop.lab4.simplehash.SimpleHashtable;

import java.io.File;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Composite comaparator class
 * @author Ivan Sicic
 * @version 1.1
 *
 */
public class DirSort implements Comparator<File> {
	List<Comparator<File>> comparators;
	private static SimpleHashtable<String, Comparator<File>> sortHashtable;

	static {
		sortHashtable = new SimpleHashtable<>();
		AbstractSort[] allSorts = { new NameSort(), new DateSort(),
				new ExtensionSort(), new TypeSort(), new SizeSort() };

		for (AbstractSort as : allSorts)
			sortHashtable.put(as.getSortName(), as);
	}

	/**
	 * Constructor method
	 * @param sorts
	 * 			String containing letters that are keys to comparators in map
	 */
	public DirSort(String sorts) {
		System.out.println("Print sorts:" + sorts);
		comparators = new LinkedList<Comparator<File>>();
		for (int i = 0; i < sorts.length(); i++) {
			char c = sorts.charAt(i);
			if (sortHashtable.containsKey(Character.toString(c).toUpperCase())) {
				if (!Character.isLowerCase(c))
					comparators.add(sortHashtable.get(Character.toString(c)));
				else
					comparators.add(sortHashtable.get(Character.toString(c).toUpperCase())
							.reversed());
			}
		}
	}

	@Override
	public int compare(File o1, File o2) {
		for (Comparator<File> c : comparators) {
			int r = c.compare(o1, o2);
			if (r != 0)
				return r;
		}
		return 0;
	}

}

package hr.fer.oop.lab4.filter;

/**
 * Class filtering Strings by name and mask
 * @author Ivan Sicic
 *
 */
public class NameFilterer {

	public boolean filter(String name, String s) {
		String pattern[] = new String[2];
		String[] split = s.split("\\*");

		if (s.startsWith("*") && s.endsWith("*")) {
			pattern[0] = "";
			pattern[1] = "";

		} else if (s.startsWith("*")) {
			pattern[0] = "";
			pattern[1] = split[1];
		} else if (s.endsWith("*")) {
			pattern[0] = split[0];
			pattern[1] = "";
		} else if (!s.endsWith("*") && !s.startsWith("*") && s.contains("*")) {

			pattern[0] = split[0];
			pattern[1] = split[1];
		}
		if (!(s.contains("*"))) {
			if (name.equals(split[0].toUpperCase()))
				return true;
		} else {
			if (name.startsWith(pattern[0].toUpperCase())
					&& name.endsWith(pattern[1].toUpperCase()))
				return true;
		}
		return false;

	}
}

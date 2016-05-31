package hr.fer.oop.lab4.simplehash;

import hr.fer.oop.lab4.simplehash.SimpleHashtable;

public class Main {

	public static void main(String[] args) {
		SimpleHashtable<String, Integer> exams = new SimpleHashtable<>();
		
		exams.put("Ivana", Integer.valueOf(5));
		exams.put("Janko", Integer.valueOf(4));
		for (String name : exams.keys()) {
			System.out.println("Ime = " + name);
		}
		for (Integer grade : exams.values()) {
			System.out.println("Ocjena = " + grade);
		}
		for (SimpleHashtable.TableEntry<String, Integer> pair : exams) {
			System.out.println("(Ime, Ocjena) = (" + pair.getKey() + ", "
					+ pair.getValue() + ")");
		}
	}

}

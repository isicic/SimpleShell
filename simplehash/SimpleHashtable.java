package hr.fer.oop.lab4.simplehash;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * 
 * @author Ivan Sicic
 * @version 1.0 Implementation of hashtable
 */
public class SimpleHashtable<K, V> implements
		Iterable<SimpleHashtable.TableEntry<K, V>> {

	/**
	 * 
	 * @author Ivan Sicic
	 * @version 1.0 Entry in a hashtable
	 */
	public static class TableEntry<K, V> {
		private K key;
		private V value;
		private TableEntry<K, V> next = null;

		/**
		 * Constructor method
		 * 
		 * @param key
		 *            element key
		 * @param value
		 *            element value
		 * @param next
		 *            reference to next entry in a slot
		 * @exception Throws
		 *                NullPointerException if parameter value is null.
		 */
		private TableEntry(K key, V value, TableEntry<K, V> next) {
			if (value == null)
				throw new NullPointerException("Entry value cannot be null.");
			this.key = key;
			this.value = value;
			this.next = next;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		private void setValue(V value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return key.toString() + ": " + value.toString();
		}
	}

	private List<TableEntry<K, V>> table = new LinkedList<TableEntry<K, V>>();
	private int size;
	private int exp;

	/**
	 * default constructor method
	 */
	public SimpleHashtable() {
		for (int i = 0; i < 16; i++) {
			table.add(null);
		}
		size = 0;
	}

	/**
	 * Constructor method with defined capacity
	 * 
	 * @param capacity
	 *            number of slots in a hashtable, if less than first greater
	 *            power of two, it is set to that number
	 * @exception Throws
	 *                IllegalArgumentException if parameter capacity is not
	 *                positive.
	 */
	public SimpleHashtable(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException(
					"Capacity must be positive number");
		}
		;

		if (isPowerOfTwo(capacity)) {
			for (int i = 0; i < capacity; i++) {
				table.add(null);
			}
		} else {
			exp = calcExp(capacity);
			for (int i = 0; i < (int) Math.pow(2, exp); i++) {
				table.add(null);
			}
		}
		size = 0;
	}

	/**
	 * 
	 * @param number
	 *            int number to be checked
	 * @return is number a power of two
	 */
	private boolean isPowerOfTwo(int number) {
		return (number != 0) && ((number & (~number + 1)) == number);
	}

	/**
	 * 
	 * @param number
	 * @return base 2 logarithm of a number, ceiled
	 */
	private int calcExp(int number) {
		return (int) Math.ceil(Math.log(number) / Math.log(2));
	}

	/**
	 * Putting element in a hashtable
	 * 
	 * @param key
	 *            element key
	 * @param value
	 *            element value
	 * @exception Throws
	 *                IllegalArgumentException if parameter key is null.
	 */
	public void put(K key, V value) {
		if (key == null) {
			throw new IllegalArgumentException("Key is null.");
		}

		TableEntry<K, V> newEntry = new TableEntry<>(key, value, null);

		int slot = Math.abs(key.hashCode() % table.size());
		if (table.get(slot) == null) {
			table.set(slot, newEntry);
			size++;
		} else {
			TableEntry<K, V> te = table.get(slot);
			do {
				if (te.getKey().equals(key)) {
					te.setValue(value);
					return;
				}
				if (te.next != null) {
					te = te.next;
				}
			} while (te.next != null);
			te.next = newEntry;
			size++;
		}
	}

	/**
	 * Getting element with specified key
	 * 
	 * @param key
	 *            element key
	 * @return element from hashtable or null if element is missing
	 */
	public V get(K key) {
		if (this.containsKey(key)) {
			int slot = Math.abs(key.hashCode() % table.size());
			TableEntry<K, V> te = table.get(slot);
			while (te != null) {
				if (te.key.equals(key))
					return te.value;
				te = te.next;
			}
		}
		return null;
	}

	public int size() {
		return size;
	}

	/**
	 * 
	 * @param key
	 *            elements key
	 * @return is the element with specified key present in the table
	 * 
	 * @exception Throws
	 *                IllegalArgumentException if parameter key is null.
	 */
	public boolean containsKey(K key) {
		if (key == null) {
			throw new IllegalArgumentException("Key is null.");
		}
		int slot = Math.abs(key.hashCode() % table.size());
		if (table.get(slot) == null)
			return false;

		TableEntry<K, V> te = table.get(slot);
		while (te != null) {
			if (te.key.equals(key))
				return true;
			te = te.next;
		}
		return false;
	}

	/**
	 * 
	 * @param value
	 *            elements value
	 * @return is the element with specified value present in the table
	 */
	public boolean containsValue(V value) {
		for (TableEntry<K, V> te : table) {
			while (te != null) {
				if (te.value == value)
					return true;
				te = te.next;
			}
		}
		return false;
	}

	/**
	 * Removing an element with specified key from the hashtable
	 * 
	 * @param key
	 *            elements key
	 */
	public void remove(K key) {
		if (this.containsKey(key)) {
			int slot = Math.abs(key.hashCode() % table.size());
			TableEntry<K, V> current = table.get(slot);
			TableEntry<K, V> previous;

			if (current.getKey().equals(key)) {
				table.set(slot, current.next);
				current.next = null;
				size--;
			}

			while (current.next != null) {
				previous = current;
				current = current.next;
				if (current.getKey().equals(key)) {
					previous.next = current.next;
					current.next = null;
					size--;
				}
			}
		}
	}

	/**
	 * 
	 * @return whether the hashtable is empty or not
	 */
	public boolean isEmpty() {
		return (size == 0);
	}

	/**
	 * @return registers value
	 */
	public String toString() {
		String string = "";
		for (TableEntry<K, V> te : table) {
			while (te != null) {
				string += te.toString() + "\n";
				te = te.next;
			}
		}
		return string;
	}

	/**
	 * Iterator factory method
	 * @return
	 * 		iterator by keys
	 */
	public Iterable<K> keys() {
		return () -> {
			return new Iterator<K>() {
				private Iterator<TableEntry<K, V>> iter = iterator();

				@Override
				public boolean hasNext() {
					return iter.hasNext();
				}

				@Override
				public K next() {
					return iter.next().getKey();
				}
			};
		};

	}

	/**
	 * Iterator factory method
	 * @return iterator by values
	 */
	public Iterable<V> values() {
		return () ->{
			return new Iterator<V>() {
				private Iterator<TableEntry<K, V>> iter = iterator();

				@Override
				public boolean hasNext() {
					return iter.hasNext();
				}

				@Override
				public V next() {
					return iter.next().getValue();
				}
			};
		
		};
	}

	/**
	 * Iterator factory method
	 * 
	 * @return iterator
	 */

	public Iterator<TableEntry<K, V>> iterator() {
		return new TableIterator(this);
	}

	/**
	 * Iterator class for SimpleHashtable
	 * 
	 * @author Ivan Sicic
	 *
	 */

	private class TableIterator implements Iterator<TableEntry<K, V>> {
		private int slot = -1, maxSlot;
		private TableEntry<K, V> entry;
		private SimpleHashtable<K, V> hashTable;

		/**
		 * Constructor method for iterator
		 * 
		 * @param hashTable
		 *            hashtable that iterator is being made for
		 */

		public TableIterator(SimpleHashtable<K, V> hashTable) {
			maxSlot = hashTable.table.size();
			this.hashTable = hashTable;

			for (TableEntry<K, V> forEntry : hashTable.table) {
				entry = forEntry;
				slot++;
				if (entry != null)
					break;
			}
			if (entry == null)
				throw new NoSuchElementException("Empty table");

		}

		/**
		 * Iterator interface method, checking if there are elements left
		 */
		public boolean hasNext() {
			return entry != null;
		}

		/**
		 * Iterator interface method
		 * 
		 * @return next table entry
		 */
		public TableEntry<K, V> next() {
			TableEntry<K, V> returnEntry = entry;

			if (entry.next != null) {
				entry = entry.next;
			} else {
				entry = null;
				for (slot = slot + 1; slot < maxSlot; slot++) {
					if (hashTable.table.get(slot) != null) {
						entry = hashTable.table.get(slot);
						break;
					}
				}
			}
			return returnEntry;
		}

		/**
		 * Unsupported method
		 */
		public void remove() {
			throw new UnsupportedOperationException("Unsupported operation.");

		}

	}

}

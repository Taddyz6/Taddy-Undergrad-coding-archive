
/**
 * Represents an item in linked list.
 */
public class ItemType {
	/**
	 * Value of this item.
	 */
	private int value;

	/**
	 * Constructor.
	 *
	 * @param num the value to set
	 */
	public ItemType(int num) {
		value = num;
	}

	/**
	 * Compares the value of item with the current object's value and return -1 if
	 * value of the current object is less than value in item, 0 if equal and 1 if
	 * greater.
	 *
	 * @param item another object.
	 * @return -1 if value of the current object is less than value in item, 0 if
	 *         equal and 1 if greater.
	 */
	public int compareTo(ItemType item) {
		return Integer.compare(value, item.value);
	}

	/**
	 * Returns the value of instance variable.
	 *
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Initializes the data member by variable num.
	 *
	 * @param num the value to set
	 */
	public void initialize(int num) {
		value = num;
	}
}


/**
 * An linked list in which the elements are sorted in ascending order.
 */
public class SortedLinkedList {
	/**
	 * The head node.
	 */
	private NodeType head;

	/**
	 * The current position
	 */
	private NodeType currentPos;

	/**
	 * Initialize a sorted linked list object.
	 */
	public SortedLinkedList() {
		head = null;
		currentPos = null;
	}

	/**
	 * Return the length of the linked list.
	 *
	 * @return the length of the linked list
	 */
	public int getLength() {
		int length = 0;
		NodeType node = head;

		// iterates all nodes
		while (node != null) {
			length++;
			node = node.next;
		}
		return length;
	}

	/**
	 * Insert item to the linked list maintaining the ascending sorted order.
	 *
	 * @param item the item to insert
	 */
	public void insertItem(ItemType item) {
		NodeType node = new NodeType();
		node.info = item;

		if (head == null) {
			// insert as head
			head = node;
		} else if (node.info.compareTo(head.info) < 0) {
			// insert before head
			node.next = head;
			head = node;
		} else {
			// find the correct position to insert.
			NodeType pre = null;
			NodeType cur = head;
			while (cur != null && node.info.compareTo(cur.info) >= 0) {
				if (node.info.compareTo(cur.info) == 0) {
					System.out.println("Sorry. You cannot insert the duplicate item");
					return;
				}

				pre = cur;
				cur = cur.next;
			}

			node.next = cur;
			pre.next = node;
		}
	}

	/**
	 * Delete an item from linked list.
	 *
	 * @param item the item to delete
	 */
	public void deleteItem(ItemType item) {
		// check if list is empty
		if (head == null) {
			System.out.println("You cannot delete from an empty list");
			return;
		}

		// check if item is equals to head
		if (item.compareTo(head.info) == 0) {
			head = head.next;
			return;
		}

		// find item from list
		NodeType cur = head;
		NodeType pre = null;
		while (cur != null && item.compareTo(cur.info) != 0) {
			pre = cur;
			cur = cur.next;
		}

		// not found item
		if (cur == null) {
			System.out.println("Item not found");
			return;
		}

		// delete cur node
		pre.next = cur.next;
	}

	/**
	 * Search the linked list that contains an item equal to the parameter item and
	 * return its index.
	 *
	 * @param item the item to search
	 * @return index of item found.
	 */
	public int searchItem(ItemType item) {
		if (head == null) {
			System.out.println("The list is empty");
			return -1;
		}
		int index = 0;
		NodeType cur = head;

		// find item
		while (cur != null) {
			if (cur.info.compareTo(item) == 0) {
				// found
				return index + 1;
			}
			cur = cur.next;
			index++;
		}

		// not found
		System.out.println("Item is not present in the list");
		return -1;
	}

	/**
	 * returns the next item in the list pointed by the currentPos.
	 *
	 * @return the next item.
	 */
	public ItemType getNextItem() {
		if (head == null) {
			System.out.println("The list is empty");
			return null;
		}

		if (currentPos == null) {
			// Start iterating from the beginning of the list again.
			currentPos = head;
		}

		ItemType item = currentPos.info;
		currentPos = currentPos.next;
		return item;
	}

	/**
	 * initialize the currentPos variable to null.
	 */
	public void resetList() {
		currentPos = null;
	}

	/**
	 * Merge two linked list.
	 *
	 * @param otherList linked list to merge.
	 * @return the merged linked list.
	 */
	public SortedLinkedList mergeList(SortedLinkedList otherList) {
		NodeType head = null;
		NodeType tail = null;

		NodeType a = this.head;
		NodeType b = otherList.head;

		// choose an element from a or b until a is null or b is null.
		while (a != null && b != null) {
			NodeType node = new NodeType();

			if (a.info.compareTo(b.info) <= 0) {
				node.info = new ItemType(a.info.getValue());

				if(a.info.compareTo(b.info) == 0) {
					b = b.next;
				}

				a = a.next;
			} else {
				node.info = new ItemType(b.info.getValue());
				b = b.next;
			}

			if (head == null) {
				head = tail = node;
			} else {
				tail.next = node;
				tail = node;
			}
		}

		// add the elements remain in a
		while (a != null) {
			NodeType node = new NodeType();
			node.info = new ItemType(a.info.getValue());
			a = a.next;

			if (head == null) {
				head = tail = node;
			} else {
				tail.next = node;
				tail = node;
			}
		}

		// add the elements remain in b
		while (b != null) {
			NodeType node = new NodeType();
			node.info = new ItemType(b.info.getValue());
			b = b.next;

			if (head == null) {
				head = tail = node;
			} else {
				tail.next = node;
				tail = node;
			}
		}

		SortedLinkedList list = new SortedLinkedList();
		list.head = head;
		return list;
	}

	/**
	 * delete alternate nodes from the list.
	 */
	public void deleteAlternateNodes() {
		if (head == null) {
			System.out.println("The list is empty");
			return;
		}

		if (head == null || head.next == null) {
			return;
		}

		// delete alternate nodes
		NodeType pre = head;
		NodeType cur = head.next;
		while (cur != null) {
			pre.next = cur.next; // remove cur node
			cur = cur.next;
			if (cur != null) {// to next node
				pre = cur;
				cur = cur.next;
			}
		}
	}

	/**
	 * Get intersection of two linked list.
	 *
	 * @param otherList linked list to intersect.
	 * @return the intersected linked list.
	 */
	public SortedLinkedList intersection(SortedLinkedList otherList) {
		NodeType head = null;
		NodeType tail = null;

		NodeType a = this.head;
		NodeType b = otherList.head;

		// choose the same element in a and b until a is null or b is null.
		while (a != null && b != null) {
			NodeType node = new NodeType();

			if (a.info.compareTo(b.info) == 0) {
				// a and b has same head
				node.info = new ItemType(a.info.getValue());

				a = a.next;
				b = b.next;

				if (head == null) {
					head = tail = node;
				} else {
					tail.next = node;
					tail = node;
				}
			} else if (a.info.compareTo(b.info) < 0) {
				a = a.next;
			} else {
				b = b.next;
			}
		}

		SortedLinkedList list = new SortedLinkedList();
		list.head = head;
		return list;
	}

	/**
	 * Get the string of this list.
	 *
	 * @return string of list.
	 */
	public String toString() {
		String s = "";
		NodeType node = head;
		while (node != null) {
			s += " " + node.info.getValue();
			node = node.next;
		}
		return s;
	}
}

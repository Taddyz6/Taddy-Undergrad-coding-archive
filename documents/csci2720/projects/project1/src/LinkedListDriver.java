import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The driver to test SortedLinkedList.
 */
public class LinkedListDriver {
	/**
	 * Scanner for user input.
	 */
	private static Scanner input = new Scanner(System.in);

	/**
	 * Main function.
	 *
	 * @param args the command line arguments.
	 */
	public static void main(String[] args) {
		// check if the filename is in command-line arguments
		if (args.length != 1) {
			System.out.println("Invalid command-line format.");
			return;
		}

		SortedLinkedList listA = new SortedLinkedList();

		String filename = args[0];

		// read input file
		try {
			readNums(filename, listA);
		} catch (FileNotFoundException e) {
			System.out.println("file not found.");
			return;
		}

		showMenu(listA);
	}

	/**
	 * Show system menu and accept user commands.
	 *
	 * @param list the linked list to operate.
	 */
	private static void showMenu(SortedLinkedList list) {

		// print menu
		System.out.println("Commands:");
		System.out.println("(i) - Insert value");
		System.out.println("(d) - Delete value");
		System.out.println("(s) - Search value");
		System.out.println("(n) - Print next iterator value");
		System.out.println("(r) - Reset iterator");
		System.out.println("(a) - Delete alternate nodes");
		System.out.println("(m) - Merge lists");
		System.out.println("(t) - Find intersection");
		System.out.println("(p) - Print list");
		System.out.println("(l) - Print length");
		System.out.println("(q) - Quit program");
		System.out.println();

		String option = "";
		// read and process user commands
		while (true) {
			if (option != null) {
				System.out.print("Enter a command: ");
			}
			option = input.next().toLowerCase();

			if (option.equals("p")) {
				System.out.println("The list is: " + list.toString());
			} else if (option.equals("l")) {
				System.out.println("The length of the list is " + list.getLength());
			} else if (option.equals("i")) {
				System.out.print("Enter a number to insert: ");
				int num = input.nextInt();

				System.out.println("Original list : " + list.toString());
				list.insertItem(new ItemType(num));
				System.out.println("New list : " + list.toString());
			} else if (option.equals("d")) {
				System.out.print("Enter a number to delete: ");
				int num = input.nextInt();

				System.out.println("Original list : " + list.toString());
				list.deleteItem(new ItemType(num));
				System.out.println("New list : " + list.toString());
			} else if (option.equals("s")) {
				System.out.print("Enter a number to search: ");
				int num = input.nextInt();

				System.out.println("Original list : " + list.toString());
				int index = list.searchItem(new ItemType(num));
				if (index >= 0) {
					System.out.println("The item is present at index " + index);
				}
			} else if (option.equals("r")) {
				list.resetList();
				System.out.println("Iterator is reset");
			} else if (option.equals("n")) {
				ItemType item = list.getNextItem();
				if (item != null) {
					System.out.println(item.getValue());
				}
			} else if (option.equals("a")) {
				System.out.println("Original list : " + list.toString());
				list.deleteAlternateNodes();
				System.out.println("Modified list : " + list.toString());
			} else if (option.equals("m")) {
				System.out.print("Enter the length of the new list: ");
				int length = input.nextInt();

				SortedLinkedList otherList = new SortedLinkedList();
				System.out.print("Enter the numbers: ");
				for (int i = 0; i < length; i++) {
					int num = input.nextInt();
					otherList.insertItem(new ItemType(num));
				}

				System.out.println("The list 1: " + list.toString().trim());
				System.out.println("The list 2: " + otherList.toString().trim());
				System.out.println("Merged list: " + list.mergeList(otherList).toString().trim());
			} else if (option.equals("t")) {
				System.out.print("Enter the length of the new list: ");
				int length = input.nextInt();

				SortedLinkedList otherList = new SortedLinkedList();
				System.out.print("Enter the numbers: ");
				for (int i = 0; i < length; i++) {
					int num = input.nextInt();
					otherList.insertItem(new ItemType(num));
				}

				System.out.println("The list 1: " + list.toString().trim());
				System.out.println("The list 2: " + otherList.toString().trim());
				System.out.println("Intersection of lists: " + list.intersection(otherList).toString().trim());
			} else if (option.equals("q")) {
				System.out.println("Exiting the program...");
				break;
			} else {
				System.out.print("Invalid command try again: ");
				option = null;
			}
		}
	}

	/**
	 * Read numbers in file and insert them to list.
	 *
	 * @param filename name of data file.
	 * @param list     list to hold numbers
	 * @throws FileNotFoundException
	 */
	private static void readNums(String filename, SortedLinkedList list) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(filename));
		while (scanner.hasNextInt()) {
			int num = scanner.nextInt();
			list.insertItem(new ItemType(num));
		}
	}
}

package cs1302.hw05;

import cs1302.adt.Node;

/**
 * This is a mian class that print node.
 */
public class Driver {

    public static void main(String[] args) {

        // CODE BLOCK 1
        Node a = new Node("Joe");
        a.setItem("Billy");

        // CODE BLOCK 2
        new Node("Linda", a);

        // CODE BLOCK 3
        Node b = new Node("Juno");
        b.setNext(a);
        a.setNext(new Node("Sally"));
        b.getNext().getNext().setNext(new Node("Barry"));

        // CODE BLOCK 4
        Node c = b.getNext().getNext();
        c.getNext().setNext(new Node("Simon"));

        // CODE BLOCK 5
        System.out.println(c.getNext().getNext().getNext());
        System.out.println(b.getItem());
        System.out.println(a.getNext().getNext().getItem());
        System.out.println(c.getNext().getNext().getItem());

        printNode(a, 1);
        printNode(b, 2);
        printNode(c, 0);
        printNode(a.getNext(), 0);
        printNode(c.getNext(), 3);

    } // main

        /**
         * Prints the string value for a node. Starting with {@code node}, this method attempts
         * to traverse {@code numLinks}-many {@code getNext()} links, then prints the result of
         * calling {@code getItem()} on the very last node in its traversal.
         *
         * @param node      starting node
         * @param numLinks  number of links to traverse
         * @throws NullPointerException when an attempt is made to call {@code getItem()} or
         *         {@code getNext()} using a {@code null} reference.
         */
    public static void printNode(Node node, int numLinks) {
        Node nodes = node;
        for (int x = 0; x < numLinks; x++) {
            nodes = nodes.getNext();
        } // for
        System.out.println(nodes.getItem());

    } // printNode

} // Driver

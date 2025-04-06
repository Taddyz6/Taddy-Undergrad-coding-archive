package cs1302.p2;

import cs1302.adt.FancyStringList;
import cs1302.adt.StringList;
import cs1302.oracle.FancyOracleStringList;

/**
 * This is main class to test the method.
 */
public class StringListMain {

    public static void main(String[] args) {

        ArrayStringList list = new ArrayStringList();
        list.append("A"); // 0
        list.append("B"); // 1
        list.append("C"); // 2
        list.append("D"); // 3
        list.append("E"); // 4
        System.out.println("Start testing: ");
        StringList slice0 = list.slice(0, 2);
        slice0.prepend("G");
        System.out.println(slice0.size());
        System.out.println(slice0.makeString("[", "~", "]"));
        slice0.remove(0);
        System.out.println(slice0.size());
        System.out.println(slice0.makeString("[", "~", "]"));

        System.out.println("Testing linked list");
        LinkedStringList linked_list = new LinkedStringList();

        linked_list.append("A"); // 0
        linked_list.append("B"); // 1
        linked_list.append("C"); // 2
        linked_list.append("D"); // 3
        linked_list.append("E"); // 4
        linked_list.prepend("F");

        System.out.println(linked_list.makeString("[", "~", "]"));

        System.out.println(linked_list.get(1));

        linked_list.add(0, "H");
        linked_list.add(1, "J");
        linked_list.add(7, "K");
        System.out.println(linked_list.makeString("[", "~", "]"));

        linked_list.remove(0);
        linked_list.remove(1);
        System.out.println(linked_list.makeString("[", "~", "]"));

//      StringList linkedSlice = linked_list.slice(1, 3);
//      System.out.println(linkedSlice);

    }

}

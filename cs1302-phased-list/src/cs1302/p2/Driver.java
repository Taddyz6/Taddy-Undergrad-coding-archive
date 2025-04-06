package cs1302.p2;

import cs1302.adt.StringList;
import cs1302.oracle.OracleStringList;

/**
 * This is class represents a testing class.
 */
public class Driver {

    /**
     * This is the main method.
     * @param args the string args
     */
    public static void main(String [] args) {
        StringList s1 = new ArrayStringList();
        // StringList s1 = new LinkedStringList();
        s1 = new OracleStringList();
        if (s1.isEmpty()) {
            System.out.println("isEmpty: Test Passes");
        } else {
            System.out.println("isEmpty: Test Failed");
            System.exit(0);
        }

        if (s1.size() == 0) {
            System.out.println("size: passed");
        } else {
            System.out.println("size: failed");
            System.exit(0);
        }

        test1(s1);
        test2(s1);
        s1.remove(1);
        System.out.println("The size is: " + s1.size());
        StringList s2 = s1.slice(1,1);
        System.out.println("size: " + s2.size());
        s1.add(4,"ff");
        System.out.println(s1);
    }

    /**
     * Test case 1.
     * @param s1 StringList object
     */
    private static void test1 (StringList s1) {
        addToTen(s1);
        s1.append("1000");
        System.out.println(s1.get(0));
        System.out.println(s1.size());
        s1.prepend("9999");
        System.out.println(s1.get(0));
        System.out.println(s1.size());
        System.out.println(s1.get(s1.size() - 2)); // get the second to last
        System.out.println(s1.get(s1.size() - 1)); // get the last
        //System.out.println(s1.get(s1.size())); // get the one on size() position,which is error
        System.out.println(s1.slice(2,5).get(2));
        System.out.println(s1.remove(0));
        System.out.println(s1.remove(1));
        System.out.println(s1.remove(2));
        System.out.println(s1.remove(3));
        System.out.println(s1.size());
        s1.clear();
        System.out.println(s1.size());

    }

    /**
     * Test case 2 for LinkedStringList.
     * @param s1 StringList object
     */
    private static void test2 (StringList s1) {
        s1.add(0, "a");
        System.out.println("size: "  + s1.size());
        s1.add(1, "b");
        System.out.println("size: " + s1.size());
        s1.add(2, "c");
        System.out.println("size: " + s1.size());
        s1.add(0, "d");
        System.out.println("size: " + s1.size());
        s1.add(2, "e");
        System.out.println("size: " + s1.size());
        System.out.println(s1);
        //s1.add(6,"error");
    }

    /**
     * add 1 - 10 to string list.
     * @param s1 StringList object
     */
    private static void addToTen (StringList s1) {
        for (int i = 0; i < 10; i ++) {
            s1.add(i, String.valueOf(i + 1));
            System.out.println(s1.get(i));
        }
    }

}

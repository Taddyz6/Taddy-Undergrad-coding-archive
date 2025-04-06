package cs1302.hw06;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Function;

/**
 * This class contains methods related to {@code cs1302-hw06}.
 */
public class LambdaFun {

    /** Standard Input scanner. */
    private static Scanner input = new Scanner(System.in);

    /**
     * Main entry-point into the application.
     * @param args the command-line arguments.
     */
    public static void main(String[] args) {

        String[] myStrings = new String[] {
            "CSCI",        "1302",    "is", "an", "awesome", "course!",
            "Lambda", "expressions", "scare", "me",     "but",       "I",
            "will",   "persevere"
        };

        Email[] inbox = new Email[] {
            new Email("bjb211@uga.edu", "yellowjacket@gatech.edu",
                      LocalDate.of(2019, 2, 4), "Go GA Tech!"),
            new Email("bjb211@uga.edu", "mom@aol.com",
                      LocalDate.of(2019, 2, 5), "Have a good day!"),
            new Email("bjb211@uga.edu", "steve@anyotherschool.edu",
                      LocalDate.of(2019, 2, 6), "I wish I would've chosen UGA"),
            new Email("bjb211@uga.edu", "student1@uga.edu",
                      LocalDate.of(2019, 2, 7), "Thanks for teaching us!"),
            new Email("bjb211@uga.edu", "yellowjacket@gatech.edu",
                      LocalDate.of(2019, 2, 8), "Go GA Tech!")
        };

        Predicate<String> p = (String t) -> t.contains("a");
        LambdaFun.printlnMatches(myStrings, p);
        System.out.println("Test 1: outputs all strings that end with \"e\"");
        p = t -> t.endsWith("e");
        LambdaFun.printlnMatches(myStrings, p);
        System.out.println("Test 2: outputs all strings that have a length greater than 4");
        p = t -> t.length() > 4;
        LambdaFun.printlnMatches(myStrings, p);
        System.out.println("Test 3: outputs all strings that have least 2 \"e\"");
        p = t -> {
            int count = 0;
            for (int i = 0; i < t.length(); i ++) {
                if (t.charAt(i) == 'e') {
                    count ++;
                }
            }
            if (count >= 2) {
                return true;
            }
            return false;
        };
        LambdaFun.printlnMatches(myStrings, p);

        System.out.println();
        Predicate<Email> isTech = email -> email.getSender().contains("gatech.edu");
        Function<Email, String> printEmail = email -> email.getContents();
        System.out.println("Filtered out emails from GT");
        LambdaFun.printlnMappedMatches(inbox, isTech, printEmail);

        System.out.println("Test 1: Filtered out emails from GT, and provided the send date");
        LambdaFun.printlnMappedMatches(inbox, isTech, email -> email.getContents() + " (Sent on " +
            email.getDateSent() + ")");

        System.out.println("Test 2: Filtered out emails from GT, and provided sender address");
        LambdaFun.printlnMappedMatches(inbox, isTech, email -> email.getContents() + "(Sent by " +
            email.getSender() + ")");

    } // main

    /**
     * Prints the elements of the array that pass the test specified by the given predicate.
     * More formally, this method prints all elements {@code e} in the array referred to by
     * {@code t} such that {@code p.test(e)}. Each element will be printed on its own line.
     *
     * @param <T> the type of the array elements
     * @param t the specified array
     * @param p the specified predicate
     * @throws NullPointerException if the specified predicate is {@code null}
     */
    private static <T> void printlnMatches(T[] t, Predicate<T> p) {
        for (T e : t) {
            if (p.test(e)) {
                System.out.println(e);
            }
        }
        System.out.println();
    } // printlnMatches

    /**
     * Prints the elements of the array that pass the test specified by the given predicate
     * using a string mapper. More formally, this method prints the string mapped elements
     * {@code f.apply(e)} in the array referred to by {@code t} for each {@code e} such that
     * {@code p.test(e)}. Each string mapped element will be printed on its own line.
     *
     * @param <T> the type of the array elements
     * @param t the specified array
     * @param p the specified predicate
     * @param f the specified string mapper
     * @throws NullPointerException if the specified predicate or string mapper is {@code null}
     */
    private static <T> void printlnMappedMatches(T[] t, Predicate<T> p, Function<T, String> f) {
        for (T e : t) {
            if (!p.test(e)) {
                String ans = f.apply(e);
                System.out.println(ans);
            }
        }
        System.out.println();
    } // printlnMappedMatches

} // LambdaFun

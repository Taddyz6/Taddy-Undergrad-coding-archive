package cs1302.hw03;

import cs1302.hw03.contract.Drivable;
import cs1302.hw03.impl.Car;
import cs1302.hw03.impl.Train;

/**
 * A driver program to test the functionality of.
 * {@code cs1302.hw03.impl.Car} which implements
 * {@code cs1302.hw03.contract.Drivable}
 */
public class Driver {

    /**
     * Attempts to speedup and slowdown the {@link cs1302.hw03.impl.Car} object
     * by a specified amount. If the {@link cs1302.hw03.impl.Car} object is unable
     * to speedup or slowdown by the specified amount, an appropriate message is
     * printed.
     *
     * @param drivable the car and train to test
     * @param speedupAmount the amount to speedup the car
     * @param slowdownAmount the amount to slowdown the car
     */
    public static void test(Drivable drivable, double speedupAmount, double slowdownAmount) {

        System.out.println(drivable);
        if (drivable.speedUp(speedupAmount)) {
            System.out.println("The car speed up by " + speedupAmount + " mph");
        } else {
            System.out.println("The car cannot go that fast");
        } // if

        if (drivable.slowDown(slowdownAmount)) {
            System.out.println("The car slowed down by " + slowdownAmount + " mph");
        } else {
            System.out.println("The car is cannot slow down by that amount");
        } // if

        System.out.println(drivable);
    } // test

    /**
     *this is the Main method.
     * @param args the command-line arguments to the program
     */
    public static void main(String[] args) {
        Drivable Car = new Car(185.5);
        Drivable Train = new Train(75.0);

        System.out.println("--------------------");

        test(Car, 200.5, 20);
        System.out.println("--------------------");

        test(Train, 125, 20);
        System.out.println("--------------------");

        test(Car, 85, 20);
        System.out.println("--------------------");

        test(Train, 65, 65);
        System.out.println("--------------------");
    } // main
} // Driver

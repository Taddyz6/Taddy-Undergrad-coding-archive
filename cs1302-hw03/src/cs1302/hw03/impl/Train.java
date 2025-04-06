package cs1302.hw03.impl;

import cs1302.hw03.contract.Drivable;

/**
 * This class represents a train. It implements
 * the {@link cs1302.hw03.contract.Drivable} interface.
 *
 */
public class Train implements Drivable {
    private double speed; // in mph
    private double maxSpeed; // in mph

   /**
    * Constructs a {@code Train} object with a specified
    * maximum speed. The maximum speed of the object
    * will default to zero if a negative value is given.
    * @param maxSpeed the maximum speed of the {@code Train}
    */
    public Train(double maxSpeed) {
        speed = 0;
        if (maxSpeed >= 0) {
            this.maxSpeed = maxSpeed;
        } // if
    } //Train

    /** {@inheritDoc} */
    public boolean speedUp(double amount) {
        boolean success = false;
        if (speed + amount <= maxSpeed) {
            speed += amount;
            success = true;
        } // if
        return success;
    } // speedUp

    /** {@inheritDoc}  */
    public boolean slowDown(double amount) {
        boolean success = false;
        if (speed - amount >= 0) {
            speed -= amount;
            success = true;
        }
        return success;
    } // slowDown

    /** {@inheritDoc}  */
    public boolean stop(double amount) {
        boolean success = false;
        if (speed + amount >= maxSpeed) {
            speed += amount;
            success = true;
        } // if
        return success;
    } // stop

     /**
      * Returns a {@code String} representation of this {@code Train}
      * in the format Train(speed: speed, maxspeed: maxSpeed).
      * @return the {@code String} representation of this object.
      */
    public String toString() {
        return String.format("Train(speed: %.2f, maxspeed: %.2f)",speed, maxSpeed);
    }
} // Train

package cs1302.hw03.contract;

/**
 * Represents the interface for an object that is drivable.
 * Drivable objects can {@code speedUp} and {@code slowDown}.
 */
public interface Drivable {

    /**
     * Increases the speed of the object by the specified amount.
     * @param amount the specified amount in mph
     * @return true when the operation was successful; false otherwise
     */
    public boolean speedUp(double amount);

    /**
     * Decreases the speed of the object by the specified amount.
     * @param amount the specified amount in mph
     * @return true when the operation was successful; false otherwise
     */
    public boolean slowDown(double amount);

    /**
     *This is a stop method that make the test stop.
     *@param amount the specified amount in mph
     *@return true when the car move; false otherwise
     */
    public boolean stop(double amount);

} // Drivable

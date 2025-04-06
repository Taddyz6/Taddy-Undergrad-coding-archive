package cs1302.shapes;

/**
 * A square is an {@link Rectangle} where both docul points are in the same location.
 * This is the square that we need give the same length
 * The four side length are equal.
 */
public class Square extends Rectangle {

    /**
     * Constructs a {@link Square} objects with the specific side length.
     *
     * @param length the side length of
     */
    public Square(double length) {

        super(length, length);
        setName("Square");

    } // Square

    /**
     * Returns the exact perimeter length of this {@link Square}. This method is implemented by
     * calling the {@link #getArea()} method of this {@code Square}.
     *
     * @return the area of this {@code Square}
     */
    @Override
    public double getArea() {
        return getLength() * getLength();
    } // get area

    /**
     * Returns the exact perimeter length of this {@link Square}. This method is implemented by
     * calling the {@link #getPerimeter()} method of this {@code Square}.
     *
     * @return the perimeter of this {@code Square}
     */
    @Override
    public double getPerimeter() {
        return 4 * getLength();
    } // get perimeter

    /**
     * Return the length of this {@code Square}.
     *
     * @return the length of this {@code Square}
     */
    public double getLength() {
        return getLengthLength();
    } // get length


} // Square

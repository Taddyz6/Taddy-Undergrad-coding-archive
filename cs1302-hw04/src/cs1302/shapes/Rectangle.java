package cs1302.shapes;

/**
 * A rectangle is a shape that by using four sides and have two same length and width
 * so if we know the length and width.
 */
public class Rectangle extends Shape {

    /** Length of the length. */
    private double a;

    /** Length of the width. */
    private double b;

    /**
     * Contructure an {@link Rectangle} object with the sepcific length and width.
     *
     * @param a the length of the length
     * @param b the length of the width
     */
    public Rectangle(double a, double b) {
        setName("Rectangle");
        this.a = a;
        this.b = b;
    } // Rectangle

    /**
     * Returns the length of the length.
     *
     * @return the length of the length.
     */
    public double getLengthLength() {
        return a;
    } // get length Length

    /**
     * Returns the length of the width.
     *
     * @return the length of the width.
     */
    public double getWidthLength() {
        return b;
    } // get width Length

    /**
     * Returns the area of the rectangle.
     *
     * @return the area of the rectangle
     */
    @Override
    public double getArea() {
        return a * b;
    } // get area

    /**
     * Return an approximation of the perimeter.
     *
     * @return an approximation of the perimeter
     */
    @Override
    public double getPerimeter() {
        double p = 2 * a + 2 * b;
        return p;
    } // get perimeter

} // Rectangle

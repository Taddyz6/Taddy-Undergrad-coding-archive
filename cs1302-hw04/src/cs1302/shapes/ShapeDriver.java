package cs1302.shapes;

/**
 * This is a Driver class that print all the shape.
 *
 */
public  class ShapeDriver {

    /**
     * This is main in order to print the shape.
     *
     * @param args the array include the shape.
     */
    public static void main(String[] args) {
        Shape[] shapes = new Shape[] {
            new Ellipse(1.1, 2.5),
            new Circle(1.5),
            new Rectangle(1.5, 2.5),
            new Square(2.5)
            };

        for (int x = 0; x < shapes.length; x++) {
            double area = Math.round(shapes[x].getArea());
            double p = Math.round(shapes[x].getPerimeter());
            System.out.println("The area of the " + shapes[x].getName() + " is " + area);
            System.out.println("The perimeter of the " + shapes[x].getPerimeter() + " is " + p);
        } // for

    } // main class

} // shape driver

package cs1302.generics;

public class ShippingContainer <ContentType> {

    private ContentTpye contents;
    private double weight;

    public ShippingContainer(ContentType contents, double weight) {
        setWeight(weight);
        setContents(contents);
    } // ShippingContainer

    public void setWeight(double weight) {
        if (weight > 0.0) {
            this.weight = weight;
        } else {
            this.weight = 1.0;
        } // if
    } // setWeight

    public void setContents(ContentType contents) {
        this.contents = contents;
    } // setContents

} // ShippingContainer

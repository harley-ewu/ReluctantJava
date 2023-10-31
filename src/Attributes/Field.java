package Attributes;

public class Field extends Attribute{
    String primitive;
    public Field(String name, String primitive) {
        super(name, primitive);
        primitive = primitive.replaceAll("[\\[\\]]", "");
    }

    @Override
    public String toString() {
        return (super.toString() + ": " + this.primitive + "\n");
    }
}

package Attributes;

public class Field extends Attribute{
    private String primitive;
    public Field(String name, String primitive) {
        super(name, primitive);
        this.primitive = primitive.replaceAll("[\\[\\]]", "");
    }

    @Override
    public String toString() {
        return super.toString() + ": " + primitive;
    }
}

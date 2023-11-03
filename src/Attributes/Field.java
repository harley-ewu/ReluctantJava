package Attributes;

public class Field extends Attribute{
    private String primitive;
    public Field(String name, String primitive) {
        super(name);
        this.primitive = primitive;
    }

    @Override
    public String toString() {
        return (super.toString() + ": " + this.primitive.replaceAll("[\\[\\]]", "") + "\n");
    }
}

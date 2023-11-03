package Attributes;

public class Field extends Attribute{
    private String primitive;
    private Type type;
    public Field(String name, String primitive) {
        super(name);
        this.primitive = primitive;
        this.setType();
    }



    @Override
    public Type getType() {
        return this.type;
    }


    public void setType() {
        this.type = Type.FIELD;
    }

    @Override
    public String toString() {
        return (super.toString() + ": " + this.primitive.replaceAll("[\\[\\]]", ""));
    }
}

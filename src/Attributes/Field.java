package Attributes;

import java.util.Objects;

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

    @Override
    public String getPrimitive() {
        return this.primitive.replaceAll("[\\[\\]]", "");
    }
    @Override
    public void setPrimitive(String primitive) {
        this.primitive = primitive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Field field = (Field) o;
        return primitive.equals(field.primitive);
    }

}
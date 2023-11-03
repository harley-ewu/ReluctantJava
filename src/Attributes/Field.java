package Attributes;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Field field = (Field) o;
        return primitive.equals(field.primitive);
    }
}
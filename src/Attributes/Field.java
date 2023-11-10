package Attributes;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public class Field{
    @Expose
    private String primitive;

    @Expose
    private String name;

    public Field(String name, String primitive) {
        this.name = name;
        this.primitive = primitive;
    }

    public String getPrimitive() {
        return this.primitive.replaceAll("[\\[\\]]", "");
    }

    public void setPrimitive(String primitive) {
        this.primitive = primitive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return this.primitive.equals(field.primitive) && this.name.equals(field.name);
    }

    @Override
    public String toString() {
        return ( this.name + ": " + this.primitive.replaceAll("[\\[\\]]", ""));
    }
}
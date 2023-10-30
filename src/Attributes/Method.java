package Attributes;

import java.util.ArrayList;
import java.util.List;

public class Method extends Attribute{
    ArrayList<String> parameter = new ArrayList<>();

    public Method(String name, String primitive) {
        super(name, primitive);
        this.parameter = parameter;
    }

    public ArrayList<String> getParameter() {
        return this.parameter;
    }

    @Override
    public String toString() {
        return (super.toString() + "(" + parameter.toString() + ")" + "\n");
    }
}

package Attributes;

import java.util.ArrayList;
import java.util.List;

public class Method extends Attribute{
    ArrayList<String> parameter;

    public Method(String name, ArrayList<String> parameters) {
        super(name, parameters);
        this.parameter = parameters;
    }

    @Override
    public String toString() {
        return (super.toString() + "(" + parameter.toString() + ")" + "\n");
    }
}

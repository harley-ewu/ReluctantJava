package Attributes;

import java.util.ArrayList;
import java.util.List;

public class Method extends Attribute{
    ArrayList<String> parameter = new ArrayList<>();

    public Method(String name, List<String> parameters) {
        super(name, parameters);
    }
}

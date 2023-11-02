package Attributes;

import java.util.ArrayList;
import java.util.List;

public class Method extends Attribute{
    ArrayList<String> parameters = new ArrayList<>();

    public Method(String name) {
        super(name);
    }

    public Method(String name, ArrayList<String> parameters) {
        super(name);
        this.parameters = parameters;
    }

    /**
     * this method will replace the current parameter arraylist with a new one
     * (any existing parameter will be cleared from the list)
     * @param parameter - a new array list of params
     */

    public void setParameters(ArrayList<String> parameter) {
        this.parameters.clear();
        this.parameters.addAll(parameter);
    }

    public void addParameter(String parameter) {
        this.parameters.add(parameter);
    }

    public void removeParameter(int input) {
        if (input < 1 || input > this.parameters.size()+1) {
            return;
        }
        this.parameters.remove(input-1);
    }


    @Override
    public String toString() {
        return (super.toString() + "(" + parameters.toString().replaceAll("[\\[\\]]", "") + ")" + "\n");
    }
}

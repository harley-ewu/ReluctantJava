package Attributes;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Method{

    @Expose
    private ArrayList<String> parameters = new ArrayList<>();

    @Expose
    private String name;

    public Method(String name) {
        this.name = name;
    }

    public Method(String name, ArrayList<String> parameters) {
        this.name = name;
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

    public ArrayList<String> getParameters() {
        return parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return (this.name + "(" + parameters.toString().replaceAll("[\\[\\]]", "") + ")");
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Method method = (Method) o;
        return this.parameters.equals(method.parameters) && this.name.equals(method.name);
    }
}

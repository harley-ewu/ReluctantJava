package Attributes;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class Method implements Cloneable{

    @Expose
    private ArrayList<String> parameters = new ArrayList<>();

    @Expose
    private String name;

    public Method(String name) {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("The method name cannot be null or empty");
        }
        this.name = name;
    }

    public Method(String name, ArrayList<String> parameters) {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("The method name cannot be null or empty");
        }else if(parameters == null){
            throw new IllegalArgumentException("The parameters cannot be null");
        }
        this.name = name;
        this.parameters = parameters;
    }

    /**
     * this method will replace the current parameter arraylist with a new one
     * (any existing parameter will be cleared from the list)
     * @param parameter - a new array list of params
     */

    public void setParameters(ArrayList<String> parameter) {
        if(parameter == null){
            throw new IllegalArgumentException("The parameters cannot be null");
        }
        this.parameters.clear();
        this.parameters.addAll(parameter);
    }

    /**
     * Description: Gets the list of params inside the method object.
     * @return - Returns an ArrayList of type string with all the params.
     */
    public ArrayList<String> getParameters() {
        return parameters;
    }

    /**
     * Description: Gets the name of the method object.
     * @return - Returns a string with the name of the method object.
     */
    public String getName() {
        return name;
    }

    /**
     * Description: Sets the name of the method object to a new name.
     * @param name - the new name of the method object.
     */
    public void setName(String name) {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("The method name cannot be null or empty");
        }
        this.name = name;
    }

    /**
     * Description: Adds a param to the list of params for the method object.
     * @param parameter - the name of the new param
     */
    public boolean addParameter(String parameter) {
        if(parameter == null || parameter.isEmpty()){
            throw new IllegalArgumentException("The new parameter cannot be null or empty");
        }
        this.parameters.add(parameter);
        return true;
    }

    /**
     * Description: Removes a param from the list of params in the method object.
     * @param input - The index of the param to be removed.
     */
    public boolean removeParameter(int input) {
        if (input < 1 || input > this.parameters.size()) {
            return false;
        }
        this.parameters.remove(input-1);
        return true;
    }

    /**
     * Description: Compares and object to the calling method object to see if they have the same information.
     * @param o - The object being compared to the method object.
     * @return - Returns true the object being compared is the same exact method object or if
     * the params are the same and the name of the methods are the same, returns false if the
     * object being compared is null or not an instance of method, also returns false if the
     * params are not the same, the name is not the same, or both.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Method method = (Method) o;
        return this.parameters.equals(method.parameters) && this.name.equals(method.name);
    }

    /**
     * Description: Converts the information stored inside the method object into a string.
     * @return - Returns a string of the name and params of the method object.
     */
    @Override
    public String toString() {
        return (this.name + "(" + parameters.toString().replaceAll("[\\[\\]]", "") + ")");
    }

    @Override
    public Method clone() {
        try {
            return (Method) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

package Attributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Attribute {
    // Attribute name and variable type.
    private String name;
    public enum Type {
        FIELD,
        METHOD
    }

    public Attribute() {}
    public Attribute(final String name) {
        this.name = name;
    }

    /**
     * Description: Constructor used to create Attribute objects from a load file.
     * @param name: the name of the attribute.
     * @param parameters: a list of parameters passed to the method class
     * @param type: the type of attribute the user wants
     */
    //TODO: get name, get type, set name, set type
    public Attribute addAttribute(final String name, ArrayList<String> parameters, final Type type) {
        Objects.requireNonNull(name, "Name can't be null.");
        if(!name.isEmpty()){
            if(type == Type.FIELD){
                return new Field(name, String.valueOf(parameters));
            }else {
                Method method = new Method(name);
                if (!parameters.isEmpty()) {
                    method.setParameters(parameters);
                }
                return method;
            }
        } else {
            return null;
        }
    }

    @Override
    public String toString(){
        return name;
    }

}

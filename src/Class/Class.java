package Class;
import Attributes.*;
//import com.github.cliftonlabs.json_simple.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Class {

    //TODO: add array list for relationships
    //TODO: add method for relationships for array list
    //TODO: remove method for relationships array list
    //TODO: add toString representation for relationships
    //TODO: attribute class needs a display

    private String className;
    private Attribute attributes;

    public Class(final String className, final Attribute attributes) {

        if (className == null || className.isEmpty() || attributes == null) {
            throw new IllegalArgumentException("param in class not valid");
        }

        this.className = className;
        this.attributes = attributes;

    }

    //getters
    public String getClassName() {
        return this.className;
    }

    //setters
    public void setClassName(String newClassName) {
        if (newClassName == null) {
            throw new NullPointerException("class name param is null");
        }

        this.className = newClassName;
    }

    @Override
    public String toString() {
        String output;
        //String attributesList;

        //TODO: the attributes array list is set to private ~ needs to either change accessibility level or a getter needs to be created

        output = "Class Name: " + this.getClassName() +"\n"
        +"Attributes: \n\n"
        +"Relationships: \n\n";

        return output;
    }

    /**
     * Description: Converts a Class object into a JsonObject for saving.
     * @return : returns a JsonObject of the Class object.
     */
    /*public JsonObject toJsonObject(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("name", className);
        jsonObject.put("attributes", attributes.toJsonObject());
        return jsonObject;
    }

    *//**
     * Description: Converts a JsonObject from a loaded file back into an Class object.
     * @param jsonObject: the JsonObject read from the file.
     * @return : The Class object that was saved to the file.
     *//*
    public static Class fromJsonObject(JsonObject jsonObject){
        String className = (String) jsonObject.get("name");
        Attribute attributes = Attribute.fromJsonObject((JsonObject) jsonObject.get("attributes"));
        return new Class(className, attributes);
    }*/

}

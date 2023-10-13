package Attributes;

import com.github.cliftonlabs.json_simple.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class Attribute {
    // List of attributes for the class.
    private List<Attribute> attributes = new ArrayList<>();
    // Attribute name and variable type.
    private String name;

    public Attribute() {}
    public Attribute(final String name) {
        this.name = name;
    }

    /**
     * Description: Constructor used to create Attribute objects from a load file.
     * @param name: the name of the attribute.
     * @param attributes: the list of attributes.
     */
    public Attribute(final String name, final List<Attribute> attributes){
        this.name = name;
        this.attributes = attributes;
    }

    public void addAttribute(final String name) {
        // Initializing a new attribute and adding it to the list.
        Attribute newAttribute = new Attribute(name);
        attributes.add(newAttribute);
    }

    public void deleteAttribute(final String name) {
        // Loop to check each attribute in the list.
        for (Attribute attribute : attributes) {
            // Checking if the name of the attribute to be deleted matches with the attribute that was found.
            if (attribute.name.equals(name)) {
                // Removing the attribute and leaving the loop.
                attributes.remove(attribute);
                break;
            }
        }
    }

    public List<Attribute> getAttributes() {
        return new ArrayList<>(this.attributes);
    }

    @Override
    public String toString(){
        StringBuilder builtString = new StringBuilder();
        // Loop to append all the attributes to a built string.
        for (Attribute attribute :attributes) {
            builtString.append(attribute.name).append("\n");
        }
        // Removing the new line character/list is empty error check.
        if(!builtString.isEmpty()) {
            builtString.setLength(builtString.length() - 1);
        }
        return builtString.toString();
    }

    /**
     * Description: Converts an Attribute object into a JsonObject for saving.
     * @return : returns a JsonObject of the Attribute object.
     */
    public JsonObject toJsonObject(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("name", name);
        jsonObject.put("attributes", attributes);
        return jsonObject;
    }

    /**
     * Description: Converts a JsonObject from a loaded file back into an Attribute object.
     * @param jsonObject: the JsonObject read from the file.
     * @return : The Attribute object that was saved to the file.
     */
    public static Attribute fromJsonObject(JsonObject jsonObject){
        String name = (String) jsonObject.get("name");
        ArrayList<Attribute> attributes = (ArrayList<Attribute>) jsonObject.get("attributes");
        return new Attribute(name, attributes);
    }
}

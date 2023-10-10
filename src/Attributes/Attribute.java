package Attributes;
import java.util.ArrayList;
import java.util.List;

public class Attribute {
    // List of attributes for the class.
    private List<Attribute> attributes = new ArrayList<>();
    // Attribute name and variable type.
    private String name;
    private String type;

    public Attribute() {}
    public Attribute(final String name, final String type) {
        this.name = name;
        this.type = type;
    }

    public void addAttribute(final String name, final String type) {
        // Initializing a new attribute and adding it to the list.
        Attribute newAttribute = new Attribute(name, type);
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
            builtString.append(attribute.name).append(": ").append(attribute.type).append("\n");
        }
        // Removing the new line character/list is empty error check.
        if(!builtString.isEmpty()) {
            builtString.setLength(builtString.length() - 1);
        }
        return builtString.toString();
    }
}

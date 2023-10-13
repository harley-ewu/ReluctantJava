package Attributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public void addAttribute(final String name) {
        // Null check.
        Objects.requireNonNull(name, "Name can't be null.");

        //Initializing a variable to check if the attribute is already in the list.
        boolean found = false;

        //Checking if the attribute is already contained within the list.
        for (Attribute attribute : attributes) {
            // Checking if the name of the attribute to be deleted matches with the attribute that was found.
            if (attribute.name.equals(name)) {
                // Removing the attribute and leaving the loop.
                found = true;
                break;
            }
        }

        //Adding if it is already not in the list.
        if (!found) {
            // Initializing a new attribute and adding it to the list.
            Attribute newAttribute = new Attribute(name);
            attributes.add(newAttribute);
            System.out.println("Successfully added attribute " + name);
        } else {
            System.out.println("Attribute is already in the list!");
        }
    }

    public void deleteAttribute(final String name) {
        // Null check.
        Objects.requireNonNull(name, "Name can't be null.");

        // Loop to look for the missing attribute.
        for (int i = 0; i < attributes.size(); i++) {
            Attribute attribute = attributes.get(i);
            if (attribute.name.equals(name)) {
                attributes.remove(i);
                System.out.println("Successfully deleted attribute " + name);
                return;
            }
        }

        System.out.println("Attribute was not found. No changes have occurred.");
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
}

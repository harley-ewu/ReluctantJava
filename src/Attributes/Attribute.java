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
    public Attribute(final String name, final String primitive) {
        this.name = name;
    }

    /**
     * Description: Constructor used to create Attribute objects from a load file.
     * @param name: the name of the attribute.
     */
    public Attribute(final String name, final List<String> parameters){
        this.name = name;
    }

    public Object addAttribute(final String name, ArrayList<String> parameters, final String type) {
        // Null check.
        Objects.requireNonNull(name, "Name can't be null.");

        if(type.equals("FIELD")){
            return new Field(name, String.valueOf(parameters));
        }else {
            return new Method(name, parameters);
        }
    }

    public void deleteAttribute(final String name) {
        // Null check.
        Objects.requireNonNull(name, "Name can't be null.");

        /*// Loop to look for the missing attribute.
        for (int i = 0; i < attributes.size(); i++) {
            Attribute attribute = attributes.get(i);
            if (attribute.name.equals(name)) {
                attributes.remove(i);
                System.out.println("Successfully deleted attribute " + name);
                return;
            }
        }*/

        System.out.println("Attribute was not found. No changes have occurred.");
    }

    public void renameAttribute(final String name, final String newName) {
        Objects.requireNonNull(name, "Name can't be null.");
        Objects.requireNonNull(newName, "New name can't be null.");

        /*//Checking if the attribute is already contained within the list.
        for (int i = 0; i < attributes.size(); i++) {
            Attribute attribute = attributes.get(i);
            if (attribute.name.equals(name)) {
                Attribute newAttribute = new Attribute(newName);
                attributes.set(i, newAttribute);
                System.out.println("Successfully changed " + name + " to " + newName);
                return;
            }
        }*/
        System.out.println("Attribute was not contained in the list.");
    }

    public List<Attribute> getAttributes() {
        //return new ArrayList<>(this.attributes);
        return null;
    }

    @Override
    public String toString(){
        return name;
    }

}

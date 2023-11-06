package Class;

import Attributes.Attribute;
import com.google.gson.annotations.Expose;
import java.util.*;

public class Class {

    @Expose
    private String className;
    @Expose
    private ArrayList<Attribute> attributes = new ArrayList<>();

    public Class(final String className) {
        if (className == null) {
            throw new NullPointerException("Class name is null.");
        }
        this.className = className;
    }

    /**
     * returns the current name of the class
     * @return
     */
    //getters
    public String getClassName() {
        return this.className;

    }

    /**
     * returns the attributes object attached to the Class class
     * @return
     */

    public ArrayList<Attribute> getAttributes() {
        return this.attributes;
    }

    /*
    public ArrayList getRelationships(){
        return new ArrayList<>(this.relationships);
    }
    */
    /**
     * description: used to set a name for the class or rename a class
     * @param newClassName - the name of the new class a user wants to rename a class to
     */
    //setters
    public void setClassName(final String newClassName) {
        if (newClassName == null) {
            throw new NullPointerException("Class name parameter is null.");
        }

        this.className = newClassName;
    }

    /**
     * description: createAttribute is a menu option method, prompting the user to enter a name for an attribute
     * @param name - the name of the class
     * @param parameters - a list of parameters for a method (will be a single
     * @param input - the number the user inputted in the menu indicating type.
     */
    public void createAttribute(String name, ArrayList<String> parameters, int input) {
        if (name.isEmpty() || parameters == null || input > 2 || input < 1) {
            return;
        }
        Attribute attribute = new Attribute();
        // Depending on the input taken from the user, construct either a field or method attribute.
        if (input == 1) {
            attribute = (Attribute) attribute.addAttribute(name, parameters, Attribute.Type.FIELD);
        } else if (input == 2) {
            attribute = (Attribute) attribute.addAttribute(name, parameters,  Attribute.Type.METHOD);
        } else {
            throw new IllegalArgumentException("Invalid input. Please enter 1 for field or 2 for method.");
        }
        if (attribute != null && !duplicateAttributeCheck(attribute)) {
            attributes.add(attribute);
        }

    }

    /**
     * description: deleteAttribute is a menu option method, prompting the user to enter an attribute to delete
     */
    public void deleteAttribute(int input) {
        if (input < 1 || input > this.attributes.size()+1) {
            return;
        }

        this.attributes.remove(input-1);
    }

    /**
     * description: returns a string of all attributes currently within the class
     * @return
     */
    public String displayAttributes() {
        sortArrayList(this.attributes);
        StringBuilder display = new StringBuilder();

        display.append("Available Fields and Methods: \n");

        int i = 0;

        for (Attribute attribute : this.attributes) {
            display.append((i+1)+". "+ attribute.toString().replaceAll("[\\[\\]]", "")+"\n");
            i++;
        }

        return display.toString();
    }

    public boolean duplicateAttributeCheck(Attribute newAttribute) {
        boolean found = false;
        for (int i = 0; i < this.attributes.size(); i++) {
            if (newAttribute.equals(this.attributes.get(i))) {
                found = true;
                break;
            }
        }
        return found;
    }

    /**
     * description: method that handles renaming an attribute
     * @param input - The index of the attribute in the list to be changed.
     * @param newName - The new name to for the attribute.
     * @param parameters - List of new parameters.
     * @param type - Indicates whether the attribute is a field or method.
     */
    public void renameAttribute(int input, String newName, ArrayList<String> parameters, Attribute.Type type) {
        if(input <= this.attributes.size() && input >= 1  && !newName.isEmpty()) {
            Attribute newAttribute = new Attribute();
            newAttribute = newAttribute.addAttribute(newName, parameters, type);
            if (!duplicateAttributeCheck(newAttribute)) {
                this.attributes.set(input - 1, newAttribute);
            }
        }
    }

    /**
     * description: method that handles renaming an attribute
     * @param newParameters - List of new parameters.
     * @param index - Index of attribute to be changed.
     */
    public void renameAttributeParameters(ArrayList<String> newParameters, int index) {
        if (index <= this.attributes.size() && index >= 1) {
            Attribute oldAttribute = this.attributes.get(index - 1);
            Attribute newAttribute = new Attribute(oldAttribute.getName());
            newAttribute = newAttribute.addAttribute(oldAttribute.getName(), newParameters, Attribute.Type.METHOD);
            if (!duplicateAttributeCheck(newAttribute)) {
                this.attributes.set(index - 1, newAttribute);
            }
        }
    }

    /**
     * description: This method sorts the array list by either field or method.
     * @param unsortedList - The list to be sorted.
     */
    public void sortArrayList(ArrayList<Attribute> unsortedList) {
        Comparator<Attribute> arrayListComparator = new Comparator<Attribute>() {
            @Override
            public int compare(Attribute s1, Attribute s2) {
                char lastChar1 = s1.toString().charAt(s1.toString().length() - 3);
                char lastChar2 = s2.toString().charAt(s2.toString().length() - 3);
                return Character.compare(lastChar1, lastChar2);
            }
        };
        Collections.sort(unsortedList, arrayListComparator);
    }

    /*public String help() {
        String help = "How to use this menu:\n" +
                "1. Add attribute -- prompts user to enter a name for a new attribute\n" +
                "2. Delete attribute -- prompts user to enter a name of an (existing) attribute to delete\n" +
                "3. Rename attriubte -- prompts the user to enter a name of an existing attribute, then prompts user to enter a new name for that attribute\n" +
                "4. Display relationships -- displays all relationships assigned to the class\n" +
                "5. Display all contents -- displays the contents of the class including: name, attributes, and relationships\n" +
                "6. Return to Diagram Menu -- returns the user to the diagram menu holding the class\n";

        return help;
    }*/
    

    /**
     * description: toString will display all contents of the class object including: name, attributes, and relationships
     * @return
     */
    @Override
    public String toString() {
        StringBuilder relationships = new StringBuilder();
        StringBuilder attributeString = new StringBuilder();
        sortArrayList(this.attributes);

        for (Attribute attribute : this.attributes) {
            attributeString.append(attribute.toString().replaceAll("[\\[\\]]", "") +"\n");
        }

        return "Class Name: " + this.getClassName() + "\n"
                +"---------------------\n"
                + "Attributes: \n" + attributeString;
    }

}

package Class;

import Attributes.Attribute;
import Attributes.Field;
import Relationships.Relationship;
import com.google.gson.annotations.Expose;

import java.util.*;

public class Class {

    @Expose
    private String className;
    private Scanner scanner = new Scanner(System.in);
    @Expose
    private List<Relationship> relationships = new ArrayList<>();

    ArrayList<String> attributes = new ArrayList<>();


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

    public ArrayList<String> getAttributes() {
        return this.attributes;
    }

    public ArrayList getRelationships(){
        return new ArrayList<>(this.relationships);
    }

    /**
     * description: used to set a name for the class or rename a class
     * @param newClassName
     */
    //setters
    public void setClassName(final String newClassName) {
        if (newClassName == null) {
            throw new NullPointerException("Class name parameter is null.");
        }

        this.className = newClassName;
    }

    /**
     * description: addRelationship will create a new relationship between two classes and add it to the relationships array list
     * addRelationship will search the array list and check to see if a relationship between two classes exists, if not, the user will be notified
     * and the attribute will not be added
     * @param relationshipType
     * @param otherClassName
     * @param thisClassCardinality
     * @param otherClassCardinality
     * @param owner
     */
    public void addRelationship(final Relationship.RelationshipType relationshipType, final Class otherClassName, final int thisClassCardinality,
                                final int otherClassCardinality, final boolean owner) {
        Relationship newRelationship = new Relationship(relationshipType, otherClassName, thisClassCardinality, otherClassCardinality, owner);

        if (this.relationships == null) {
            throw new NullPointerException("Relationships list is null.");
        }
        else if (this.relationships.contains(newRelationship)) {
                System.out.println("There is already a relationship between these two classes.");
                return;
        }
        relationships.add(newRelationship);
    }

    /**
     * description: delete relationship will take in a relationship object and search the relationship array list to see if the relationship exists
     * if not, the user will be notified and nothing will be deleted
     * @param relationship
     */

    public void deleteRelationship(final Relationship relationship) {
        if (relationships.isEmpty()) {
            System.out.println("There are no relationships assigned to this class.");
        }
        //checks if given relationship is null and if it is contained within the relationship list
        else if (relationship != null && relationships.contains(relationship)) {
            relationships.remove(relationship);
        } else {
            System.out.println("This relationship is not assigned to this class.");
        }
    }

    /**
     * description: getRelationship will search the relationship list for a desired related class, if found it will return the class to the user
     * @param otherClass
     * @return
     */

    public Relationship getRelationship(final Class otherClass){
        Relationship relationship = null;
        for(int i = 0; i < relationships.size(); i++){
            if(relationships.get(i).getOtherClassName() == otherClass){
                relationship = relationships.get(i);
                break;
            }
        }

        return relationship;
    }

    public void setRelationships(ArrayList<Relationship> relationship){
        this.relationships = relationship;
    }

    /**
     * description: addAttribute is a menu option method, prompting the user to enter a name for an attribute
     */
    public void addAttribute(String name, ArrayList<String> parameters, int input) {
        if (name.isEmpty() || parameters.isEmpty() || input > 2 || input < 1) {
            return;
        }
        Attribute attribute = new Attribute();
        // Depending on the input taken from the user, construct either a field or method attribute.
        if (input == 1) {
            attribute = (Attribute) attribute.addAttribute(name, parameters, Attribute.Type.FIELD.toString());
        } else if (input == 2) {
            attribute = (Attribute) attribute.addAttribute(name, parameters,  Attribute.Type.METHOD.toString());
        } else {
            throw new IllegalArgumentException("Invalid input. Please enter 1 for field or 2 for method.");
        }
        if (attribute != null) {
            attributes.add(attribute.toString());
        }

    }

    /**
     * description: deleteAttribute is a menu option method, prompting the user to enter an attribute to delete
     */
    public void deleteAttribute() {

    }

    /**
     * description: returns a string of all attributes currently within the class
     * @return
     */
    public String displayAttributes() {
        /*if (this.attributes.getAttributes().isEmpty()) {
            return "There are no attributes in this class";
        } else {
            return "Attributes in the " + this.getClassName() + " class:\n" + this.attributes.toString();
        }*/
        return null;

    }

    /**
     * description: menu option prompting the user to rename an attribute. If the attribute exists in the list,
     * the attribute will be successfully named, if not, the user will be prompted so.
     */

    public void renameAttribute() {
        /*String attribute;
        String newName;
        System.out.println("Please enter an attribute to rename: ");
        attribute = this.scanner.nextLine();
        System.out.println("Please enter a new name for the attribute: ");
        newName = this.scanner.nextLine();

        this.attributes.renameAttribute(attribute, newName);*/

    }

    /**
     * description: This method sorts the array list by either field or method.
     *
     */
    private void sortArrayList(ArrayList<String> unsortedList) {
        Comparator<String> arrayListComparator = new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                char lastChar1 = s1.charAt(s1.length() - 3);
                char lastChar2 = s2.charAt(s2.length() - 3);
                return Character.compare(lastChar1, lastChar2);
            }
        };
        Collections.sort(unsortedList, arrayListComparator);
    }

    /**
     * description: returns a string of all relationships attached to the class
     */
    public String displayRelationships() {
        if (this.relationships.isEmpty()) {
            return "There are no relationships assigned to this class.";
        } else {
            StringBuilder relationships = new StringBuilder();

            for (Relationship relationship: this.relationships) {
                relationships.append(relationship.toString()).append("\n");
            }
            return "Relationships in the " + this.getClassName() + " class: \n" + relationships;
        }

    }

    public String help() {
        String help = "How to use this menu:\n" +
                "1. Add attribute -- prompts user to enter a name for a new attribute\n" +
                "2. Delete attribute -- prompts user to enter a name of an (existing) attribute to delete\n" +
                "3. Rename attriubte -- prompts the user to enter a name of an existing attribute, then prompts user to enter a new name for that attribute\n" +
                "4. Display relationships -- displays all relationships assigned to the class\n" +
                "5. Display all contents -- displays the contents of the class including: name, attributes, and relationships\n" +
                "6. Return to Diagram Menu -- returns the user to the diagram menu holding the class\n";

        return help;
    }
    
    /**
     * description: subMenu is a built-in sub menu to a class object, this can be accessed in the diagram menu by selecting the "edit class" option
     * the user can use this menu to edit attributes and relationships (wip)
     */
    public void subMenu() {
        boolean on = true;
        //the sub menu will loop until the user is done making necessary changes, they can step back to the previous menu
        while (on) {
            int choice = -99;
            do {
                System.out.println("\nEdit menu for the " + this.getClassName() + " class\n");
                System.out.println("\n1.Add attribute\n2.Delete attribute\n3.Rename Attribute" +
                        "\n4.Display attributes\n5.Display relationships\n6.Display all contents\n7.Return to Diagram Menu\n8.Help");
                String op = scanner.nextLine();
                if (!op.isEmpty() && Character.isDigit(op.charAt(0)) && op.length() == 1) {
                    choice = Integer.parseInt(op);
                } else {
                    choice = -99;
                    System.out.println("Please enter a valid option, 1-7");
                }

            } while (choice < 1 || choice > 8);

            switch (choice) {

                case 1: //add attribute
                    //this.addAttribute();
                    break;
                case 2: //delete attribute
                    this.deleteAttribute();
                    break;
                case 3: //rename attribute
                    this.renameAttribute();
                    break;
                case 4: //display attributes
                    System.out.println(this.displayAttributes());
                    break;
                case 5: //display relationships
                    System.out.println(this.displayRelationships());
                    break;
                case 6: //display all contents
                    System.out.println(this);
                    break;
                case 7: //return to diagram menu
                    on = false;
                    break;
                case 8: //help
                    System.out.println(this.help());
                default:
                    break;
            }
        }
    }



    /**
     * description: toString will display all contents of the class object including: name, attributes, and relationships
     * @return
     */
    @Override
    public String toString() {
        StringBuilder relationships = new StringBuilder();
        StringBuilder attributeString = new StringBuilder();
        sortArrayList(attributes);

        for (Relationship relationship: this.relationships) {
            relationships.append(relationship.toString()).append("\n");
        }

        for (String attribute : attributes) {
            attributeString.append(attribute.replaceAll("[\\[\\]]", ""));
        }

        return "Class Name: " + this.getClassName() + "\n"
                +"---------------------\n"
                + "Attributes: \n" + attributeString +
                "\n\n" + "Relationships: \n\n" + relationships;
    }

}

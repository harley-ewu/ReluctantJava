package Class;

import Attributes.Attribute;
import Diagram.Diagram;
import Relationships.Relationship;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//import com.github.cliftonlabs.json_simple.JsonObject;

public class Class {

    private String className;
    private Attribute attributes;
    private Scanner scanner = new Scanner(System.in);
    private List<Relationship> relationships = new ArrayList();


    private Diagram diagram;
    public Class(final String className) {
        if (className == null) {
            throw new NullPointerException("Class name is null.");
        }
        this.attributes = new Attribute();
        this.className = className;
        this.diagram = diagram;
    }

    /**
     * Description: Converts a Class object into a JsonObject for saving.
     * @return : returns a JsonObject of the Class object.
     */

    public JsonObject toJsonObject(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("name", className);
        jsonObject.put("attributes", attributes.toJsonObject());
        return jsonObject;
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

    public Attribute getAttributes() {
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
     * description: this method will set the attributes variable to a new attribute object
     * @param attribute
     */
    public void setAttributes(final Attribute attribute) {
        this.attributes = new Attribute();
    }
//----------------------------------------------------------------------------------------
    //note: add and delete methods for attributes are handled in the attributes class

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

    /**
     * description: addAttribute is a menu option method, prompting the user to enter a name for an attribute
     */
    public void addAttribute() {
        String newAttribute;
        System.out.print("\nPlease enter a name for the attribute: ");
        newAttribute = this.scanner.nextLine();

        if (newAttribute.isEmpty()) {
            System.out.println("\nNothing was typed, please enter a name for the attribute.");

        }else {
            this.attributes.addAttribute(newAttribute); //will eventually contain messages for the user
        }
    }

    /**
     * description: deleteAttribute is a menu option method, prompting the user to enter an attribute to delete
     */
    public void deleteAttribute() {
        String attribute;
        System.out.print("\nPlease enter an attribute to remove: ");
        attribute = this.scanner.nextLine();

        if (attribute.isEmpty()) {
            System.out.println("\nNothing was typed, please enter a name for the attribute that you want to remove.");
        } else {
            this.attributes.deleteAttribute(attribute); //this will eventually include messages for the user

        }
    }

    /**
     * description: returns a string of all attributes currently within the class
     * @return
     */
    public String displayAttributes() {
        if (this.attributes.getAttributes().isEmpty()) {
            return "There are no attributes in this class";
        } else {
            return "Attributes in the " + this.getClassName() + " class:\n" + this.attributes.toString();
        }

    }

    /**
     * description: menu option prompting the user to rename an attribute. If the attribute exists in the list,
     * the attribute will be successfully named, if not, the user will be prompted so.
     */

    public void renameAttribute() {
        String attribute;
        String newName;
        System.out.println("Please enter an attribute to rename: ");
        attribute = this.scanner.nextLine();
        System.out.println("Please enter a new name for the attribute: ");
        newName = this.scanner.nextLine();

        this.attributes.renameAttribute(attribute, newName);

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
                        "\n4.Display attributes\n5.Display relationships\n6.Display all contents\n7.Return to Diagram Menu");
                String op = scanner.nextLine();
                if (!op.isEmpty() && Character.isDigit(op.charAt(0)) && op.length() == 1) {
                    choice = Integer.parseInt(op);
                } else {
                    choice = -99;
                }

            } while (choice < 1 || choice > 7);

            switch (choice) {

                case 1: //add attribute
                    this.addAttribute();
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
                default:
                    System.out.println("Please enter a number between 1 and 7");
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

        for (Relationship relationship: this.relationships) {
            relationships.append(relationship.toString()).append("\n");
        }

        return "Class Name: " + this.getClassName() + "\n"
                +"---------------------\n"
                + "Attributes: \n" + this.attributes.toString() +
                "\n\n" + "Relationships: \n\n" + relationships;
    }

}

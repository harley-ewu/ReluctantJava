package Class;

import Attributes.Attribute;
import Diagram.Diagram;
import Relationships.Relationship;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Class {

    //TODO: write toString representation for relationships
    //TODO: relationships needs a toString
    //TODO: add relationship menu init function
    //TODO: update add relationship to prompt for relationship info
    private String className;
    private Attribute attributes;
    private Scanner scanner = new Scanner(System.in);
    private List<Relationship> relationships = new ArrayList();

    private Diagram diagram;
    public Class(final String className, final Diagram diagram) {
        if (className == null) {
            throw new NullPointerException("Class name is null.");
        }
        this.attributes = new Attribute();
        this.className = className;
        this.diagram = diagram;
    }
    //getters
    public String getClassName() {
        return this.className;

    }
    //setters
    public void setClassName(final String newClassName) {
        if (newClassName == null) {
            throw new NullPointerException("Class name parameter is null.");
        }

        this.className = newClassName;
    }
//----------------------------------------------------------------------------------------
    //note: add and delete methods for attributes are handled in the attributes class

    public void addRelationship(final Relationship.RelationshipType relationshipType, final Class otherClassName, final int thisClassCardinality, final int otherClassCardinality, final boolean owner) {
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
    //TODO: To be moved to diagram class
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

    public String displayAttributes() {
       return "Attributes in the " + this.getClassName() + " class: \n" + this.attributes.toString();

    }

    public String displayRelationships() {
        StringBuilder relationships = new StringBuilder();

        for (Relationship relationship: this.relationships) {
            relationships.append(relationship.toString()).append("\n");
        }
        return "Relationships in the " + this.getClassName() + " class: \n" + relationships;

    }

    public void subMenu() {
        boolean on = true;
        //the sub menu will loop until the user is done making necessary changes, they can step back to the previous menu
        while (on) {
            int choice = -99;
            do {
                System.out.println("\nEdit menu for the " + this.getClassName() + " class\n");
                System.out.println("\n1.Add attribute\n2.Delete attribute\n3.Display attributes" +
                        "\n4.Display relationships\n5.Display all contents\n6.Return to Diagram Menu");
                choice = Integer.parseInt(scanner.nextLine());

            } while (choice < 0 || choice > 6);

            switch (choice) {

                case 1: //add attribute
                    this.addAttribute();
                    break;
                case 2: //delete attribute
                    this.deleteAttribute();
                    break;
                case 3: //display attributes
                    this.displayAttributes();
                    break;
                case 4: //display relationships
                    this.displayRelationships();
                    break;
                case 5: //display all contents
                    System.out.println(this);
                    break;
                case 6: //return to diagram menu
                    on = false;
                    this.diagram.menu();
                    break;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder relationships = new StringBuilder();

        for (Relationship relationship: this.relationships) {
            relationships.append(relationship.toString()).append("\n");
        }

        return "Class Name: " + this.getClassName() + "\n"
                + "Attributes: \n" + this.attributes.toString() +
                "\n" + "Relationships: \n" + relationships;
    }

}

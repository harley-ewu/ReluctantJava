package Class;
import Attributes.Attribute;
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
    public Class(final String className) {
        if (className == null) {
            throw new NullPointerException("class name is null");
        }
        this.attributes = new Attribute();
        this.className = className;
        //initializing constructor will automatically prompt the user to enter desired attributes and relationships
        //displays the newly created class along with its attributes and relationships
        System.out.println("You have created a class with the name: " + this.getClassName()
                +"\n\nWith attributes: \n" + attributes.toString() + "\n\n"
                + "With relationships: ");
    }
    //getters
    public String getClassName() {
        return this.className;

    }
    //setters
    public void setClassName(String newClassName) {
        if (newClassName == null) {
            throw new NullPointerException("Class name param is null.");
        }

        this.className = newClassName;
    }
//----------------------------------------------------------------------------------------
    //note: add and delete methods for attributes are handled in the attributes class
    //pulled from the relationship class
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
    //pulled from the relationship class
    public void deleteRelationship(Relationship relationship) {
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
    public void initAttributes() {
        //asks the user if they want to add an attribute
        int cont = -99, choice = -99;
        /*
            * prompts the user with a menu allowing them to add an attribute
            * this code will loop infinitely until the user decides that they do not want to add anymore attributes
         */
        while (cont < 0) {
            do {
                System.out.println("Do you want to add an attribute?");
                System.out.println("1. Yes\n2. No");
                choice = Integer.parseInt(this.scanner.nextLine());
            } while (choice < 0 || choice > 2);
            switch (choice) {
                case 1:
                    String attributeName;
                    System.out.print("Enter a name for an attribute: ");
                    attributeName = this.scanner.nextLine();
                    this.attributes.addAttribute(attributeName);
                    System.out.println(this.attributes.toString());
                    break;
                case 2:
                    cont = 1;
                default:
                    break;
            }
        }
    }

    public void subMenu() {
        int choice = -99;
        do {
            System.out.println("Edit menu for " + this.getClassName() + " class\n\n");
            System.out.println("1. Add attribute\n2. Delete attribute\n3. Add relationship\n4. Delete relationship \n 5. Go back");
            choice = Integer.parseInt(scanner.nextLine());

        } while (choice < 0 || choice > 5);

        switch(choice) {

            case 1: //add attribute
                break;
            case 2: //delete attribute
                break;
            case 3: //add relationship
                break;
            case 4: //remove relationship
                break;
            case 5: //go back
                break;
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
                "\n\n" + "Relationships: \n" + relationships;
    }


}

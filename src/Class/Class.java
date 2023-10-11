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
        this.initAttributes();
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
            throw new NullPointerException("class name param is null");
        }

        this.className = newClassName;
    }
//----------------------------------------------------------------------------------------
    //note: add and delete methods for attributes are handled in the attributes class
    //pulled from the relationship class
    public void addRelationship(final Relationship.RelationshipType relationshipType, final Class otherClassName, final int thisClassCardinality, final int otherClassCardinality, final boolean owner) {
        Relationship newRelationship = new Relationship(relationshipType, otherClassName, thisClassCardinality, otherClassCardinality, owner);

        if (this.relationships == null) {
            throw new NullPointerException("relationships list is null");

        }
        else if (this.relationships.contains(newRelationship)) {
                System.out.println("There is already a relationship between these two classes");
                return;
        }

        relationships.add(newRelationship);


    }
    //pulled from the relationship class
    public void deleteRelationship(Relationship relationship) {
        if (relationships.isEmpty()) {
            System.out.println("There are no relationships assigned to this class");
        }
        //checks if given relationship is null and if it is contained within the relationship list
        else if (relationship != null && relationships.contains(relationship)) {
            relationships.remove(relationship);
        } else {
            System.out.println("this relationship is not assigned to this class");
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
                System.out.println("Do you want to add an Attribute?");
                System.out.println("1. Yes\n2. No");
                choice = Integer.parseInt(this.scanner.nextLine());
            } while (choice < 0 || choice > 2);
            switch (choice) {
                case 1:
                    String attributeName;
                    System.out.print("enter attribute name: ");
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
    @Override
    public String toString() {
        return null;
    }


}

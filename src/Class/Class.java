package Class;
import Attributes.Attribute;
import Relationships.Relationship;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Class {

    //TODO: finish toString representation for relationships
   //TODO: relationships needs a toString

    private String className;
    private Attribute attributes;
    private Scanner scanner;
    private List<Relationship> relationships = new ArrayList<Relationship>();

    public Class(final String className) {
        if (className == null) {
            throw new NullPointerException("class name is null");
        }

        this.className = className;

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
    public void addAttribute(final String attributeName) {
        if (this.attributes != null && !attributeName.isEmpty()) {
            this.attributes.addAttribute(attributeName);
        } else {
            System.out.println("Please enter a name for the attribute");
        }
    }

    public void deleteAttribute(final String attributeName) {
        if (this.attributes != null || this.attributes.getAttributes().contains(attributeName)) {
            this.attributes.deleteAttribute(attributeName);
        } else {
            System.out.println("There are no attributes assigned to this class");
        }

    }
    //pulled from the relationship class
    public void addRelationship(final Relationship.RelationshipType relationshipType, final String className, final String otherClassName, final int thisClassCardinality, final int otherClassCardinality, final boolean owner) {
        Relationship newRelationship = new Relationship(relationshipType, className, otherClassName, thisClassCardinality, otherClassCardinality, owner);

        if (this.relationships != null) {
            if (this.relationships.contains(newRelationship)) {
                System.out.println("There is already a relationship between these two classes");
                return;
            }
            relationships.add(newRelationship);
        }

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
        int choice = -99;
        do {
            System.out.println("Do you want to add an Attribute?");
            System.out.println("1. Yes\n2. No");
            choice = Integer.parseInt(this.scanner.nextLine());

        }while (choice < 0);

        switch(choice) {
            case 1:
                String attributeName;
                System.out.print("enter attribute name: ");
                attributeName = this.scanner.nextLine();
                this.addAttribute(attributeName);
                initAttributes();
            case 2:
                break;
            default:
                initAttributes();
        }

        this.attributes.toString();
    }

    public void initRelationships() {
        //asks the user if they want to add a relationship
        int choice = -99;
        do {
            System.out.println("Do you want to add a relationship?");
            System.out.println("1. Yes\n2. No");
            choice = Integer.parseInt(this.scanner.nextLine());

        }while (choice < 0);

        switch(choice) {
            case 1:
                String relationshipName;
                System.out.print("enter relationship name: ");
                relationshipName = this.scanner.nextLine();
                this.addAttribute(relationshipName);
                initRelationships();
            case 2:
                break;
            default:
                initRelationships();
        }

        this.relationships.toString();
    }




    /*
    @Override
    public String toString() {
        String output;
        //String attributesList;

        //TODO: the attributes array list is set to private ~ needs to either change accessibility level or a getter needs to be created

        output = "Class Name: " + this.getClassName() +"\n"
        +"Attributes: \n\n"
        +"Relationships: \n\n";

        return output;
    }
    */

}

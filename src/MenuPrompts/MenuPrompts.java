package MenuPrompts;
import java.util.*;
import Diagram.Diagram;
import Relationships.Relationship;
import Class.Class;


public class MenuPrompts {
    //private static Diagram diagram;
    private static Scanner scanner = new Scanner(System.in);

    //Prompts the user for name of class to be added to the diagram
    public static String addClassPrompt() {
        System.out.println("Enter a class name to add: ");
        String className = scanner.nextLine();
        while (className.isEmpty()) {
           System.out.println("Please enter a name between 1 and 50 characters inclusive");
           className = scanner.nextLine();
        }
        return className;
     }

     //Prompts the user for the name of class to be deleted from the diagram
     public static Class deleteClassPrompt(final Diagram diagram) {
        System.out.println("Enter a class name to delete: ");
        String className = scanner.nextLine();
        while (className.isEmpty()) {
            System.out.println("Please enter a name between 1 and 50 characters inclusive");
            className = scanner.nextLine();
        }
        Class deletedClass = diagram.findSingleClass(className);
        if (deletedClass == null) {
            System.out.println("Class does not exist.");
            return null;
        }
        return deletedClass;
   }

   //Prompts the user for the original class name to be renamed
   public static Class renameClassPromptOriginalName(final Diagram diagram) {
        String oldClassName;
        System.out.println("Enter the original name of the class.");
        oldClassName = scanner.nextLine();
        Class c = diagram.findSingleClass(oldClassName);
        if(c != null){
            System.out.println("Class exists.");
            return c;
        }
        else {
            System.out.println("Class does not exist.");
            return null;
        }
 }

 //Prompts the user for the new class name to rename old class
 public static String renameClassPromptNewName(final Diagram diagram, final Class old) {
        String newClassName = "";
        if(old != null) {
            System.out.println("Enter a new name for the class.");
            newClassName = scanner.nextLine();
        while (newClassName.isEmpty()) {
            System.out.println("Please enter a name between 1 and 50 characters inclusive");
            newClassName = scanner.nextLine();
        }
        }
        return newClassName;
    }


    public static Class printSingleClassPrompt(final Diagram diagram){
        System.out.println("Enter name of class to view: ");
        String className = scanner.nextLine();
        Class c = diagram.findSingleClass(className);
        if(c != null) {
            return c;
        }
        else {
            return null;
        }
    }

    public static Relationship.RelationshipType relationshipTypePrompt() {
        Relationship.RelationshipType relationshipType = null;
        int choice;
        while(relationshipType == null) {
            System.out.println("What Type of Relationship?\n" +
                    "1. Association \n2. Aggregation \n3.Composition \n4.Generalization");
            choice = Integer.parseInt(scanner.nextLine());
            if(choice < 1 || choice > 4){
               System.out.println("Please enter 1 through 4 as your choice");
            }
            else if(choice == 1){
               relationshipType = Relationship.RelationshipType.Realization;
            }
            else if(choice == 2) {
               relationshipType = Relationship.RelationshipType.Aggregation;
            }
            else if (choice == 3){
               relationshipType = Relationship.RelationshipType.Composition;
            }
            else{
               relationshipType = Relationship.RelationshipType.Inheritance;
            }
   
         }
         return relationshipType;
    }

    public static int class1CardinalityPrompt(final Class c1){
        int c1Cardinality = -2;
        System.out.println("What is "+c1.getClassName()+"'s Class Cardinality? (Enter -1 for * Cardinality)");
        c1Cardinality = Integer.parseInt(scanner.nextLine());
        while(c1Cardinality < -1){
            System.out.println("Please enter a valid cardinality");
            c1Cardinality = Integer.parseInt(scanner.nextLine());
        }
        return c1Cardinality;
    }

    public static int class2CardinalityPrompt(final Class c2){
        int c2Cardinality = -2;
        System.out.println("What is "+c2.getClassName()+"'s Class Cardinality? (Enter -1 for * Cardinality)");
        c2Cardinality = Integer.parseInt(scanner.nextLine());
        while(c2Cardinality < -1){
            System.out.println("Please enter a valid cardinality");
            c2Cardinality = Integer.parseInt(scanner.nextLine());
        }
        return c2Cardinality;
    }

    public static boolean whichClassIsOwnerPrompt(final Class c1, final Class c2){
        int choice = 0;
        boolean owner = true;
        while(choice != 1 && choice != 2){
            System.out.println("Which class is the owner of the relationship?\n" +
                    "1. "+c1.getClassName()+"\n2. "+c2.getClassName()+"\n");
            choice = Integer.parseInt(scanner.nextLine());

            if(choice != 1 && choice != 2){
                System.out.println("Please enter 1 or 2 as your choice");
            }
            else if(choice == 1){
                //class 1 is owner
                owner = true;
            }
            else {
                //class 2 is owner
                owner = false;
            }
        }
        return owner;
    }
   
}

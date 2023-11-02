package MenuPrompts;
import java.util.*;
import Diagram.Diagram;
import Relationships.Relationship;
import Class.Class;


public class MenuPrompts {
    //private static Diagram diagram;
    private static Scanner scanner = new Scanner(System.in);
    
    /**
    * Prompts the user to enter a class name. If the user enters an empty string it will prompt the user for a name between 1 and 50 characters inclusive.
    * 
    * 
    * @return the name entered by the user or null if the user chose to enter an empty string in which case the user will be prompted
    */
    public static String addClassPrompt() {
        System.out.println("Enter a class name to add: ");
        String className = scanner.nextLine();
        while (className.isEmpty()) {
           System.out.println("Please enter a name between 1 and 50 characters inclusive");
           className = scanner.nextLine();
        }
        return className;
     }

     /**
     * Deletes a class from a diagram. The user is prompted for a name to delete and then tries to find the class.
     * 
     * @param diagram - The diagram to search. Must be non - null.
     * 
     * @return The deleted class or null if the class could not be found or not deleted by the user in the diagram
     */
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

   /**
   * Prompts the user to rename the class. If the class does not exist in the diagram prints an error message and returns null.
   * 
   * @param diagram - The diagram to use. Must not be null.
   * 
   * @return The class that was renamed or null if user chose to rename the class without input from the user
   */
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

 /**
 * Prompts the user to enter a new name for the class. If the old class is not null the user will be prompted for a new name.
 * 
 * @param diagram - The diagram to be used. This is not used in this method.
 * @param old - The old class or null if none. This is not used in this method.
 * 
 * @return The new name for the class or null if the user chose not to enter a new name. Note that this method does not return null
 */
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


    /**
    * Prints a prompt to ask the user for a name of a class and returns the class. If the user enters nothing null is returned
    * 
    * @param diagram - the diagram to search in
    * 
    * @return the class or null if not found in the diagram or the user cancels the search ( in which case null is returned
    */
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

    /**
    * Asks the user to input the type of relationship. This prompts the user for the type of relationship and returns it.
    * 
    * 
    * @return Relationship. RelationshipType. Realization Relationship. RelationshipType. Aggregation or Relationship. RelationshipType. Composition
    */
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

    /**
    * Prompt the user to enter Cardinality of a Class. This is used for Classes that have multiple Contiguity
    * 
    * @param c1 - the Class that is to be prompts
    * 
    * @return the user's Cardinality or - 2 if they don't enter a valid Cardinality or the Class is
    */
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

    /**
    * Prompt the user to enter Cardinality of a Relationship. 
    *
    * @param c2 - the Class to be examined
    * 
    * @return the Cardinality of the Class or - 2 if the user enters an invalid Cardinality ( in which case the user is prompted again )
    */
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

    /**
    * Prompts the user to select which class is the owner of the relationship.
    * 
    * @param c1 - the first class to check. It must be a class apart of the relationship.
    * @param c2 - the second class to check. It must be a class apart of the relationship.
    * 
    * @return true if the class is the owner of the relationship false otherwise. In other words it returns true if the class is the owner
    */
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

    /**
    * Prompt user to edit class. The name of the class will be entered from the user until it is less than 50 characters or the user enters an empty string
    * 
    * @param diagram - Diagram to search for class
    * 
    * @return class name or " " if class doesn't exist in the diagram or if user cancels the
    */
    public static String editClassPrompt(final Diagram diagram) {
        System.out.println("Enter name of class to edit: ");
        String className = scanner.nextLine();
        while (className.isEmpty() || className.length() > 50) {
            System.out.println("Please enter a name between 1 and 50 characters inclusive");
            className = scanner.nextLine();
        }
        if(diagram.findSingleClass(className) == null){
            System.out.println("Class does not exist.");
            return "";
        }
        return className;
    }

    public static int editRelationshipsMenuChoice() {
        int userInput = -99;
        System.out.println("Relationship Editor");
        System.out.println("""
            
                                1 - Add Relationship
                                2 - Delete Relationship
                                3 - Back to Diagram Menu
                                
                                Enter a number:""");
        
        while (true) {
            try {
                userInput = Integer.parseInt(scanner.nextLine());
                if (userInput >= 1 && userInput <= 3) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 3");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }
        return userInput;
    }

    public static Class promptClass1Relationship(final Diagram diagram) {
        System.out.println("What is the name of the first class?");
        String ownerString = scanner.nextLine();
        Class c1 = diagram.findSingleClass(ownerString);
        if (c1 == null) {
            System.out.println("Class does not exist");
            return null;
        }
        return c1;
    }

    public static Class promptClass2Relationship(final Diagram diagram) {
        System.out.println("What is the name of the second class?");
        String otherString = scanner.nextLine();
        Class c2 = diagram.findSingleClass(otherString);
        if (c2 == null) {
            System.out.println("Class does not exist");
            return null;
        }
        return c2;
    }
   
}

package MenuPrompts;

import Class.Class;
import Diagram.Diagram;
import Relationships.Relationship;

import java.util.ArrayList;
import java.util.Scanner;

//THIS IS A VIEW

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
        System.out.println("Enter a class name to add (or press blank enter to exit): ");
        System.out.print("--> ");
        String className = scanner.nextLine();
        if (className.isEmpty()) {
           /*System.out.println("Please enter a name between 1 and 50 characters inclusive");
           System.out.print("--> ");
           className = scanner.nextLine();*/
            return "";
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
        listClasses(diagram);
        System.out.println("Enter a class name to delete from the list above: ");
        System.out.print("--> ");
        String className = scanner.nextLine();
        while (className.isEmpty()) {
            System.out.println("Please enter a name between 1 and 50 characters inclusive");
            System.out.print("--> ");
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
        listClasses(diagram);
        String oldClassName;
        System.out.println("Enter the original name of the class from the list above.");
        System.out.print("--> ");
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
            System.out.print("--> ");
            newClassName = scanner.nextLine();
            while (newClassName.isEmpty()) {
                System.out.println("Please enter a name between 1 and 50 characters inclusive");
                System.out.print("--> ");
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
        listClasses(diagram);
        System.out.println("Enter name of class to view from list above: ");
        System.out.print("--> ");
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
        System.out.println("What Type of Relationship?\n" +
                "1 - Association \n2 - Aggregation \n3 - Composition \n4 - Generalization");
        System.out.println("Enter a number (or press blank enter to exit):");
        System.out.print("--> ");
        while(relationshipType == null) {

            try {
                choice = Integer.parseInt(scanner.nextLine());
                if(choice < 1 || choice > 4){
                    System.out.println("Please enter 1 through 4 as your choice");
                    System.out.print("--> ");
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
            } catch(NumberFormatException e) {
                //System.out.println("Please enter a valid number");
                //System.out.print("--> ");
                System.out.println("\nExiting...");
                break;
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
        System.out.println("What is "+c1.getClassName()+"'s Class Cardinality? (Enter -1 for * Cardinality or blank enter to exit)");
        System.out.print("--> ");
        while (c1Cardinality < -1) {
            try {
                c1Cardinality = Integer.parseInt(scanner.nextLine());
                while(c1Cardinality < -1){
                    System.out.println("Please enter a valid cardinality");
                    System.out.print("--> ");
                    c1Cardinality = Integer.parseInt(scanner.nextLine());
                }
            }catch(NumberFormatException e){
                //System.out.println("Please enter a valid cardinality");
                //System.out.print("--> ");
                System.out.println("\nExiting...");
                break;
            }
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
        System.out.println("What is "+c2.getClassName()+"'s Class Cardinality? (Enter -1 for * Cardinality or blank enter to exit)");
        System.out.print("--> ");
        while(c2Cardinality < -1) {
            try {
                c2Cardinality = Integer.parseInt(scanner.nextLine());
                while(c2Cardinality < -1){
                    System.out.println("Please enter a valid cardinality");
                    System.out.print("--> ");
                    c2Cardinality = Integer.parseInt(scanner.nextLine());
                }
            }catch(NumberFormatException e){
                //System.out.println("Please enter a valid cardinality");
                //System.out.print("--> ");
                System.out.println("\nExiting...");
                break;
            }
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
        System.out.println("Which class is the owner of the relationship?\n" +
                "1. "+c1.getClassName()+"\n2. "+c2.getClassName()+"\n");
        System.out.println("Please enter 1 or 2 as your choice (or press blank enter to exit)");
        System.out.print("--> ");
        while(choice != 1 && choice != 2){
            try {
                choice = Integer.parseInt(scanner.nextLine());

                if(choice != 1 && choice != 2){
                    System.out.println("Please enter 1 or 2 as your choice");
                    System.out.print("--> ");
                }
                else if(choice == 1){
                    //class 1 is owner
                    owner = true;
                }
                else {
                    //class 2 is owner
                    owner = false;
                }
            }catch(NumberFormatException e){
                System.out.println("Please enter a valid number");
                System.out.print("--> ");
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
    public static Class editClassPrompt(final Diagram diagram) {
        if(diagram.getClassList().size() < 1){
            System.out.println("\nNo classes have been created to edit.");
            return null;
        }
        listClasses(diagram);
        System.out.println("Enter name of class to edit from list above (or press blank enter to exit):");
        System.out.print("--> ");
        String className = scanner.nextLine();
        if(className.isEmpty()){
            return null;
        }
        while (className.isEmpty() || className.length() > 50) {
            System.out.println("Please enter a name between 1 and 50 characters inclusive");
            System.out.print("--> ");
            className = scanner.nextLine();
        }
        Class c1 = diagram.findSingleClass(className);
        if(c1 == null){
            System.out.println("Class does not exist.");
            return null;
        }
        return c1;
    }

    public static int editRelationshipsMenuChoice() {
        int userInput = -99;
        System.out.println("\n--------------------------");
        System.out.println("Relationship Editor");
        System.out.println("""
                            --------------------------
                             1 - Add Relationship
                             2 - Delete Relationship
                             3 - Back to Diagram Menu
                            --------------------------   
                                Enter a number:""");
        System.out.print("--> ");

        while (true) {
            try {
                userInput = Integer.parseInt(scanner.nextLine());
                if (userInput >= 1 && userInput <= 3) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 3");
                    System.out.print("--> ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
                System.out.print("--> ");
            }
        }
        return userInput;
    }

    public static Class promptClass1Relationship(final Diagram diagram) {
        listClasses(diagram);
        System.out.println("What is the name of the first class?");
        System.out.println("Type the name from the list above. (or press blank enter to exit)");
        System.out.print("--> ");
        String ownerString = scanner.nextLine();
        if(ownerString.isEmpty()) {
            System.out.println("\nExiting...");
            return null;
        }
        Class c1 = diagram.findSingleClass(ownerString);
        if (c1 == null) {
            System.out.println("Class does not exist");
            return null;
        }
        return c1;
    }

    public static Class promptClass2Relationship(final Diagram diagram) {
        listClasses(diagram);
        //System.out.print("\nChoose between 1 and " + (diagram.getClassList().size()) + " -> ");
        System.out.println("\nWhat is the name of the class to form the relationship?");
        System.out.println("Type the name from the list above. (or press blank enter to exit)");
        System.out.print("--> ");
        String otherString = scanner.nextLine();
        if(otherString.isEmpty()){
            System.out.println("\nExiting...");
            return null;
        }
        Class c2 = diagram.findSingleClass(otherString);
        if (c2 == null) {
            System.out.println("Class does not exist");
            return null;
        }
        return c2;
    }

    public static int promptAttributeType() {
        int userInput = -99;

        System.out.println("What type of attribute would you like to modify?:\n" +
                "1 - Field\n" +
                "2 - Method\n");
        System.out.println("Enter a number:");
        System.out.print("--> ");
        while (true) {
            try {
                userInput = Integer.parseInt(scanner.nextLine());
                if (userInput >= 1 && userInput <= 2) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 2");
                    System.out.print("--> ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
                System.out.print("--> ");
            }
        }
        return userInput;
    }

    public static String promptParameterType(){
        String parameterType = "";
        while (parameterType.isEmpty() || parameterType == null){
            System.out.println("Please enter a vaild parameter type:");
            System.out.println("(Ex: String, int, double, float)");
            System.out.print("--> ");
            parameterType = scanner.nextLine();
        }
        return parameterType;
    }

    public static String promptParameterName(){
        String name = "";
        while(name == null || name.isEmpty()){
            System.out.println("Please enter a valid name for the attribute:");
            System.out.print("--> ");
            name = scanner.nextLine();
        }
        return name;
    }

    public static int addParameterPrompt() {
        int userInput = -99;
        System.out.println("" +
                "1 - Enter a parameter name\n" +
                "2 - Finished\n");
        System.out.println("Enter a number:");
        System.out.print("--> ");
        while (true) {
            try {
                userInput = Integer.parseInt(scanner.nextLine());
                if (userInput >= 1 && userInput <= 2) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 2");
                    System.out.print("--> ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
                System.out.print("--> ");
            }
        }

        return userInput;

    }

    public static ArrayList<String> renameFieldParameterPrompt() {
        ArrayList<String> newParam = new ArrayList<>();
        String parameter = "";
        while(parameter == null || parameter.isEmpty()){
            System.out.println("Please enter a valid parameter type:\n(Ex: String, int, double, float)");
            System.out.print("--> ");
            parameter = scanner.nextLine();
        }
        newParam.add(0, parameter);
        return newParam;
    }

    public static int deleteFieldPrompts(final Class currentClass) {
        int choice = -99;
        do {
            System.out.println("Delete an attribute:");
            //need to add message if no attributes exist
            System.out.println(currentClass.displayAttributes());
            System.out.print("\nChoose between 1 and " + (currentClass.getFields().size()) + " -> ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
                System.out.print("--> ");
            }

        } while (choice < 1 || choice > currentClass.getFields().size()+1);
        return choice;
    }

    public static int deleteMethodPrompts(final Class currentClass) {
        int choice = -99;
        do {
            System.out.println("Delete an attribute:");
            //need to add message if no attributes exist
            System.out.println(currentClass.displayAttributes());
            System.out.print("\nChoose between 1 and " + (currentClass.getMethods().size()) + " -> ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
                System.out.print("--> ");
            }

        } while (choice < 1 || choice > currentClass.getMethods().size()+1);
        return choice;
    }

    public static int renameFieldPrompt(final Class currentClass) {
        int choice = -99;
        do {
            System.out.println("Rename a field: ");
            System.out.println(currentClass.displayAttributes());
            System.out.print("\nChoose between 1 and " + (currentClass.getFields().size()) + " -> ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
                System.out.print("--> ");
            }
        }while (choice < 1 || choice > currentClass.getFields().size()+1);
        return choice;
    }

    public static int renameMethodPrompt(final Class currentClass) {
        int choice = -99;
        do {
            System.out.println("Rename a method: ");
            System.out.println(currentClass.displayAttributes());
            System.out.print("\nChoose between 1 and " + (currentClass.getMethods().size()) + " -> ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
                System.out.print("--> ");
            }
        }while (choice < 1 || choice > currentClass.getMethods().size()+1);
        return choice;
    }

    public static String renameAttributeNewName(){
        String newName = "";
        while (newName.isEmpty() || newName == null){
            System.out.println("Please enter a new valid name for the attribute");
            System.out.print("--> ");
            newName = scanner.nextLine();
        }
        return newName;
    }

    public static void listClasses (final Diagram diagram) {
        if(diagram.getClassList().size() < 1){
            System.out.println("\nClass list is empty. Please add a class.");
            return;
        }
        System.out.println("\nClass List:");
        System.out.println("---------------------");
        diagram.getClassList().forEach((key, value) -> {
            System.out.println(key);
        });
        System.out.println("\n");
    }
}
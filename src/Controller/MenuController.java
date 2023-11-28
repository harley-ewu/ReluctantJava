package Controller;

import application.CLI.CommandLineInterface;
import Class.Class;
import Diagram.Diagram;
import MenuPrompts.MenuPrompts;
import Relationships.Relationship;

import java.util.*;

public class MenuController {

    /**
    * Menu Control for Diagram
    * 
    * @param shouldTerminate - true if user presses terminate false otherwise
    * @param diagram - Diagram to get menu control for ( used for newClassMenuControl
    */
    public static void diagramMenuControl(boolean shouldTerminate, final Diagram diagram){
            while(!shouldTerminate) {
                String stringChoice = CommandLineInterface.diagramMenuChoice();
                if(stringChoice.isEmpty()){
                    continue;
                }
                if (!Character.isDigit(stringChoice.charAt(0))){
                    //autocomplete methods
                    shouldTerminate = typingDiagramMenuControl(stringChoice.trim(), diagram);
                    continue;
                }
                int choice = Integer.parseInt(stringChoice);
                Class currentClass = null;
                switch (choice) {
                //Add Class - name needed
                case 1:
                    addClass(diagram);
                    break;
                //Delete Class - name needed
                case 2:
                    deleteClass(diagram);
                    break;
                //Rename Class - current and new name needed
                case 3:
                    renameClass(diagram);
                    break;
                //Edit Class - name needed
                case 4:
                    currentClass = MenuPrompts.editClassPrompt(diagram);
                    if(currentClass == null){
                        break;
                    }
                    editClassSubMenu(currentClass, diagram);
                    break;
                //View class - name needed
                case 5:
                    viewSingleClass(diagram);
                    break;
                //View Diagram
                case 6:
                    System.out.println(diagram);
                    break;
                case 7:
                    CommandLineInterface.saveDiagram(diagram);
                    break;
                case 8:
                    CommandLineInterface.diagramHelp();
                    break;
                case 9:
                    shouldTerminate = CommandLineInterface.exit(diagram);
                    break;
                default:
                    break;
               
            }
            }
    }

    public static void addClass(final Diagram diagram){
        String className = MenuPrompts.addClassPrompt();
        if(className.isEmpty()) {
            return;
        }
        diagram.addClass(className);
        System.out.println("\nClass '" + className + "' has been successfully added!");
        newClassMenuControl(diagram.getClassList().get(className), diagram);
    }

    public static void deleteClass(final Diagram diagram) {
        if(diagram.getClassList().size() < 1){
            System.out.println("\nThere are no classes to delete");
            return;
        }
        Class deletedClass = MenuPrompts.deleteClassPrompt(diagram);
        if(deletedClass == null){
            return;
        }
        diagram.deleteClass(deletedClass);
        System.out.println("\nClass '" + deletedClass.getClassName() + "' has been successfully deleted!");
    }

    public static void renameClass(final Diagram diagram) {
        if(diagram.getClassList().size() < 1){
            System.out.println("\nThere are no classes to rename.");
            return;
        }
        Class old = MenuPrompts.renameClassPromptOriginalName(diagram);
        String newName = MenuPrompts.renameClassPromptNewName(diagram, old);
        diagram.renameClass(old, newName);
        System.out.println("\nClass has been successfully renamed!");
    }

    public static void viewSingleClass(final Diagram diagram){
        if(diagram.getClassList().size() < 1){
            System.out.println("\nThere are no classes to display.");
            return;
        }
        Class c = MenuPrompts.printSingleClassPrompt(diagram);
        System.out.println("\n" + diagram.printSingleClass(c));
    }

    /**
    * Displays the menu for adding attributes and relationships to a diagram. This method returns when the user presses enter in the menu or terminates
    *
    * @param currentClass - the class to add or remove attributes to
    * @param diagram - the diagram to add or remove relationships to
    */
    public static void newClassMenuControl(final Class currentClass, final Diagram diagram) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String stringChoice = CommandLineInterface.newClassMenuChoice(currentClass);
            if(stringChoice.isEmpty()){
                    continue;
                }
            if (!Character.isDigit(stringChoice.charAt(0))){
                boolean quit = false;
                //autocomplete methods
                quit = typingNewClassMenuControl(stringChoice.trim(), diagram, currentClass, scanner);
                if(quit) return;
                continue;
            }
            int choice = Integer.parseInt(stringChoice);
            Class c2 = null;
            switch(choice) {
                //Add attribute
                case 1:
                   addAttribute(currentClass, scanner);
                   break;
                //Add relationship
                case 2:
                    if(diagram.getClassList().size() < 2){
                        System.out.println("\nCannot form a relationship with current number of classes existing.\n");
                        break;
                    }
                    c2 = MenuPrompts.promptClass2Relationship(diagram, currentClass);
                    if(c2 == null) {
                        break;
                    }
                   addRelationship(currentClass, c2, diagram);
                   break;
                case 3:
                    CommandLineInterface.saveDiagram(diagram);
                    break;
                case 4:
                   CommandLineInterface.newClassMenuHelp();
                   break;
                case 5:
                    return;
                default:
                   break;
             }
        }
    }

    /*
     * Prompts user for both class names, then prompts for all relevant relationship information
     * and builds relationships between the classes, then adds it to either of their relationship lists
     */
    public static void addRelationship(Class c1, Class c2, Diagram diagram) {
        if (c1 == c2) {
            System.out.println("\nCannot form a relationship with itself.");
            return;
        }
        Relationship.RelationshipType relationshipType = null;
        int c1Cardinality = -2;
        int c2Cardinality = -2;
        Boolean owner = false;

        relationshipType = MenuPrompts.relationshipTypePrompt();
        if (relationshipType == null) {
            //System.out.println("\nExiting relationship editor...");
            return;
        }
        c1Cardinality = MenuPrompts.class1CardinalityPrompt(c1);
        if (c1Cardinality < -1) {
            //System.out.println("\nExiting relationship editor...");
            return;
        }
        c2Cardinality = MenuPrompts.class2CardinalityPrompt(c2);
        if(c2Cardinality < -1){
            return;
        }
        owner = MenuPrompts.whichClassIsOwnerPrompt(c1, c2);

        Relationship relationship = new Relationship(relationshipType, c1, c2, c1Cardinality, c2Cardinality, owner);
        diagram.addRelationship(relationship);
        System.out.println("\nA new relationship has successfully been added!");
    }

    public static void editClassSubMenu(final Class currentClass, final Diagram diagram) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String stringChoice = CommandLineInterface.editClassMenuChoice(currentClass, diagram);
            if(stringChoice.isEmpty()){
                continue;
            }
            if (!Character.isDigit(stringChoice.charAt(0))){
                //autocomplete methods
                boolean quit = false;
                quit = typingEditClassSubMenuControl(stringChoice.trim(), diagram, currentClass, scanner);
                if(quit) return;
                continue;
            }
            int choice = Integer.parseInt(stringChoice);
            switch (choice) {

                case 1: //add attribute
                    addAttribute(currentClass, scanner);
                    break;
                case 2: //delete field
                    deleteField(currentClass, scanner);
                    break;
                case 3: //delete method
                    deleteMethod(currentClass, scanner);
                    break;
                case 4: //rename attribute
                    renameAttribute(currentClass, scanner);
                    break;
                case 5:
                    editRelationshipsControl(diagram, currentClass);
                    break;
                case 6: //display all contents
                    diagram.printSingleClass(currentClass);
                    break;
                case 7:
                    CommandLineInterface.saveDiagram(diagram);
                    break;
                case 8: //help
                    CommandLineInterface.editClassMenuHelp();
                    break;
                case 9: //return to diagram menu
                    return;

                default:
                    break;
            }
        }
    }

    public static void addAttribute(Class currentClass, Scanner scanner) {
        int choice = MenuPrompts.promptAttributeType();
        String name = MenuPrompts.promptParameterName();
        ArrayList<String> parameters = new ArrayList<>();

        if (choice == 1) {
            do {
                String type = MenuPrompts.promptParameterType();
                parameters.add(type);

            }while(parameters == null && parameters.isEmpty());

            currentClass.createField(name, parameters);

        } else {
            int option = -99;
            do {
                option = MenuPrompts.addParameterPrompt();

                if (option == 1) {
                    String parameterName = MenuPrompts.promptParameterName();
                    parameters.add(parameterName);
                }

            }while (option != 2);

            currentClass.createMethod(name, parameters);

        }

        //currentClass.createAttribute(name, parameters, choice);
        System.out.println("\nAn attribute has successfully been added!");
    }

    public static void deleteField(Class currentClass, Scanner scanner) {
        if(currentClass.getFields().size() < 1){
            System.out.println("\nThere are no fields to delete");
            return;
        }
        int choice = MenuPrompts.deleteFieldPrompts(currentClass);
        currentClass.deleteField(choice);
        System.out.println("\nField has sucessfully been deleted!");

    }

    public static void deleteMethod(Class currentClass, Scanner scanner) {
        if(currentClass.getMethods().size() < 1){
            System.out.println("\nThere are no methods to delete");
            return;
        }
        int choice = MenuPrompts.deleteMethodPrompts(currentClass);
        currentClass.deleteMethod(choice);
        System.out.println("\nMethod has successfully been deleted!");

    }

    public static void renameAttribute(Class currentClass, Scanner scanner) {
        if(currentClass.getMethods().size() < 1 && currentClass.getFields().size() < 1){
            System.out.println("\nNo attributes exist to rename.");
            return;
        }
        System.out.println(currentClass.displayAttributes());
        //int input = MenuPrompts.renameAttributePrompt(currentClass);
        String newName = "";
        ArrayList<String> parameters = new ArrayList<>();

        int type = -99;
        do {
            type = MenuPrompts.promptAttributeType();

            if (type == 1) {
                int input = MenuPrompts.renameFieldPrompt(currentClass);
                newName = MenuPrompts.renameAttributeNewName();
                parameters = MenuPrompts.renameFieldParameterPrompt();
                currentClass.renameField(input, newName);
                break;
            }
            int option = -99;
            do {
                int input = MenuPrompts.renameMethodPrompt(currentClass);
                newName = MenuPrompts.renameAttributeNewName();
                option = MenuPrompts.addParameterPrompt();
                if (option == 1) {
                    String parameterName = MenuPrompts.promptParameterName();
                    parameters.add(parameterName);
                }
                currentClass.renameMethod(input, newName, parameters);
            }while (option != 2);

            }while (type != 2);
        System.out.println("\nAttribute has been successfully renamed!");
    }

    public static void deleteRelationship(final Diagram diagram, final Class c1, final Class c2) {
        diagram.deleteRelationship(c1, c2);
        System.out.println("\nSuccessfully deleted a relationship!");
    }
    /**
    * Allows the user to edit relationships in the diagram.
    *
    * @param diagram - Diagram to edit relationships in
    */
    public static void editRelationshipsControl(final Diagram diagram, final Class currentClass){
        if(diagram.getClassList().size() < 2){
            System.out.println("\nCannot modify a relationship with current number of classes existing.\n");
            return;
        }
            Class c2 = null;
            c2 = MenuPrompts.promptClass2Relationship(diagram, currentClass);
            while(currentClass == c2) {
                System.out.println("\nCannot modify a relationship with itself.");
                c2 = MenuPrompts.promptClass2Relationship(diagram, currentClass);
            }
            if(c2 == null) {
                return;
            }
        Scanner scanner = new Scanner(System.in);
        while(true){
            
            String stringChoice = CommandLineInterface.editRelationshipsMenuChoice(diagram, currentClass);
                if(stringChoice.isEmpty()){
                    continue;
                }
                if (!Character.isDigit(stringChoice.charAt(0))){
                    //autocomplete methods
                    boolean quit = false;
                    quit = typingEditRelationshipControl(stringChoice.trim(), diagram, scanner, currentClass, c2);
                    if(quit) return;
                    continue;
                }
                int choice = Integer.parseInt(stringChoice);
            
            switch(choice) {
                case 1:
                    addRelationship(currentClass, c2, diagram);
                    break;
                case 2:
                    diagram.deleteRelationship(currentClass, c2);
                    break;
                case 3:
                    diagram.listAllRelationships();
                    break;
                case 4: 
                    CommandLineInterface.saveDiagram(diagram);
                    break;
                case 5:
                    return;
                default:
                    break;
            }
        }
    }
    public static boolean typingDiagramMenuControl(final String command, final Diagram diagram) {
        Class currentClass = null;
        switch (command) {
            case("add-class"):
                addClass(diagram);
                break;
            case("delete-class"):
                deleteClass(diagram);
                break;
            case("rename-class"):
                renameClass(diagram);
                break;
            case("edit-class"):
                currentClass = MenuPrompts.editClassPrompt(diagram);
                if(currentClass == null){
                    break;
                }
                editClassSubMenu(currentClass, diagram);
                break;
            /*case("edit-relationships"):
                editRelationshipsControl(diagram);
                break;*/
            case("view-class"):
                Class c = MenuPrompts.printSingleClassPrompt(diagram);
                System.out.println(diagram.printSingleClass(c));
                break;
            case("view-diagram"):
                System.out.println(diagram);
                break;   
            case("save"):
                CommandLineInterface.saveDiagram(diagram);
                break;
            case("help"):
                CommandLineInterface.diagramHelp();
                break;
            case("back"):
                return true;
            default:
                System.out.println("Not a recognized command.");
        }
        return false;
    }

    public static boolean typingNewClassMenuControl(final String command, final Diagram diagram, final Class currentClass, final Scanner scanner) {
        Class c2 = null;
        switch(command) {
            case ("add-attribute"):
                addAttribute(currentClass, scanner);
                break;
            case("add-relationship"):
                if(diagram.getClassList().size() < 2){
                    System.out.println("\nCannot form a relationship with only a single class existing.\n");
                    break;
                }
                c2 = MenuPrompts.promptClass2Relationship(diagram, currentClass);
                if(c2 == null) {
                    break;
                }
                addRelationship(currentClass, c2, diagram);
                break;
            case("save"):
                CommandLineInterface.saveDiagram(diagram);
                break;
            case("help"):
                CommandLineInterface.newClassMenuHelp();
                break;
            case("back"):
                return true;
            default:
                System.out.println("Not a recognized command.");
        }
        return false;
    }

    public static boolean typingEditClassSubMenuControl(final String command, final Diagram diagram, final Class currentClass, final Scanner scanner) {
        Class c2 = null;
        switch(command){
            case("add-attribute"):
                addAttribute(currentClass, scanner);
                break;
            case("delete-field"):
                deleteField(currentClass, scanner);
                break;
            case("delete-method"):
                deleteMethod(currentClass, scanner);
                break;
            case("rename-attribute"):
            renameAttribute(currentClass, scanner);
                break;
            case("display-attributes"):
                System.out.println(currentClass.displayAttributes());
                break;
            case("add-relationship"):
                c2 = MenuPrompts.promptClass2Relationship(diagram, currentClass);
                if(c2 == null) {
                    return true;
                }
                addRelationship(currentClass, c2, diagram);
                break;
            case("delete-relationship"):
                c2 = MenuPrompts.promptClass2Relationship(diagram, currentClass);
                if(c2 == null) {
                    return true;
                }
                diagram.deleteRelationship(currentClass, c2);
                break;
            case("edit-relationships"):
                editRelationshipsControl(diagram, currentClass);
                break;
            case("display-relationships"):
                System.out.println(diagram.listAllRelationships());
                break;
            case("display-all"):
                System.out.println(currentClass);
                diagram.getSingleClassRelationships(currentClass);
                break;
            case("add-class"):
                addClass(diagram);
                break;
            case("save"):
                CommandLineInterface.saveDiagram(diagram);
                break;
            case("back"):
                return true;
            case("help"):
                CommandLineInterface.editClassMenuHelp();
                break;
            default:
                System.out.println("Not a recognized command.");
        }

        return false;
    }

    public static boolean typingEditRelationshipControl(final String command, final Diagram diagram, final Scanner scanner, final Class c1, final Class c2){
        switch(command) {
            case("add-relationship"):
                addRelationship(c1, c2, diagram);
                break;
            case("delete-relationship"):
                diagram.deleteRelationship(c1, c2);
                break;
            case("save"):
                CommandLineInterface.saveDiagram(diagram);
                break;
            case("view-relationships"):
                diagram.listAllRelationships();
                break;
            case("back"):
                return true;
            case("exit"):
                return true;
            default:
                System.out.println("Not a recognized command.");
        }
        return false;
    }
}

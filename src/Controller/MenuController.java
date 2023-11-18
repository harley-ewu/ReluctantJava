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
                //edit relationships
                case 5:
                    editRelationshipsControl(diagram);
                    break;
                //View class - name needed
                case 6:
                    Class c = MenuPrompts.printSingleClassPrompt(diagram);
                    System.out.println(diagram.printSingleClass(c));
                    break;
                //View Diagram
                case 7:
                    System.out.println(diagram);
                    break;
                case 8:
                    CommandLineInterface.diagramHelp();
                    break;
                case 9:
                    shouldTerminate = true;
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
        newClassMenuControl(diagram.getClassList().get(className), diagram);
    }

    public static void deleteClass(final Diagram diagram) {
        if(diagram.getClassList().size() < 1){
            System.out.println("There are no classes to delete");
            return;
        }
        Class deletedClass = MenuPrompts.deleteClassPrompt(diagram);
        if(deletedClass == null){
            return;
        }
        diagram.deleteClass(deletedClass);
    }

    public static void renameClass(final Diagram diagram) {
        Class old = MenuPrompts.renameClassPromptOriginalName(diagram);
        String newName = MenuPrompts.renameClassPromptNewName(diagram, old);
        diagram.renameClass(old, newName);
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
            int choice = CommandLineInterface.newClassMenuChoice();
            Class c2 = null;
            switch(choice) {
                //Add attribute
                case 1:
                   addAttribute(currentClass, scanner);
                   break;
                //Add relationship
                case 2:
                    if(diagram.getClassList().size() < 2){
                        System.out.println("\nCannot form a relationship with only a single class existing.\n");
                        break;
                    }
                    c2 = MenuPrompts.promptClass2Relationship(diagram);
                    if(c2 == null) {
                        break;
                    }
                   addRelationship(currentClass, c2, diagram);
                   break;
                case 3:
                   return;
                   
                case 4:
                   CommandLineInterface.newClassMenuHelp();
                   break;
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
        if (c1 == c2) return;

        Relationship.RelationshipType relationshipType = null;
        int c1Cardinality = -2;
        int c2Cardinality = -2;
        Boolean owner = false;

        relationshipType = MenuPrompts.relationshipTypePrompt();
        if (relationshipType == null) {
            return;
        }
        c1Cardinality = MenuPrompts.class1CardinalityPrompt(c1);
        if (c1Cardinality < -1) {
            return;
        }
        c2Cardinality = MenuPrompts.class2CardinalityPrompt(c2);
        owner = MenuPrompts.whichClassIsOwnerPrompt(c1, c2);

        Relationship relationship = new Relationship(relationshipType, c1, c2, c1Cardinality, c2Cardinality, owner);
        diagram.addRelationship(relationship);
    }

    public static void editClassSubMenu(final Class currentClass, final Diagram diagram) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int choice = CommandLineInterface.editClassMenuChoice(currentClass);
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
                case 5: //display attributes
                    System.out.println(currentClass.displayAttributes());
                    break;
                case 6: //display relationships
                    System.out.println(diagram.listAllRelationships());
                    break;
                case 7: //display all contents
                    System.out.println(currentClass);
                    break;
                case 8: //return to diagram menu
                    return;
                case 9: //help
                    CommandLineInterface.editClassMenuHelp();
                    break;
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

    }

    public static void deleteField(Class currentClass, Scanner scanner) {
        int choice = MenuPrompts.deleteFieldPrompts(currentClass);
        currentClass.deleteField(choice);

    }

    public static void deleteMethod(Class currentClass, Scanner scanner) {
        int choice = MenuPrompts.deleteMethodPrompts(currentClass);
        currentClass.deleteMethod(choice);

    }

    public static void renameAttribute(Class currentClass, Scanner scanner) {
        //int input = MenuPrompts.renameAttributePrompt(currentClass);
        String newName = MenuPrompts.renameAttributeNewName();
        ArrayList<String> parameters = new ArrayList<>();

        int type = -99;
        do {
            type = MenuPrompts.promptAttributeType();

            if (type == 1) {
                int input = MenuPrompts.renameFieldPrompt(currentClass);
                parameters = MenuPrompts.renameFieldParameterPrompt();
                currentClass.renameField(input, newName);
                break;
            }
            int option = -99;
            do {
                int input = MenuPrompts.renameMethodPrompt(currentClass);
                option = MenuPrompts.addParameterPrompt();
                if (option == 1) {
                    String parameterName = MenuPrompts.promptParameterName();
                    parameters.add(parameterName);
                }
                currentClass.renameMethod(input, newName, parameters);
            }while (option != 2);

        }while (type != 2);
    }

    /**
    * Allows the user to edit relationships in the diagram.
    *
    * @param diagram - Diagram to edit relationships in
    */
    public static void editRelationshipsControl(final Diagram diagram){
        if(diagram.getClassList().size() < 2){
            System.out.println("\nCannot form a relationship with only a single class existing.\n");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        while(true){
            int choice = MenuPrompts.editRelationshipsMenuChoice();
            Class c1 = null;
            Class c2 = null;
            if (choice != 3){
                c1 = MenuPrompts.promptClass1Relationship(diagram);
                if (c1 == null) {
                    break;
                }
                c2 = MenuPrompts.promptClass2Relationship(diagram);
                if(c2 == null) {
                    break;
                }
            }
            switch(choice) {
                case 1:
                    addRelationship(c1, c2, diagram);
                    break;
                case 2:
                    diagram.deleteRelationship(c1, c2);
                    break;
                case 3: 
                    //shouldTerminate = true;
                    return;
                    //break;
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
            case("edit-relationships"):
                editRelationshipsControl(diagram);
                break;
            case("view-class"):
                Class c = MenuPrompts.printSingleClassPrompt(diagram);
                System.out.println(diagram.printSingleClass(c));
                break;
            case("view-diagram"):
                System.out.println(diagram);
                break;   
            case("help"):
                CommandLineInterface.diagramHelp();
                break;
            case("exit"):
                return true;
            default:
                System.out.println("Not a recognized command.");
        }
        return false;
    }
}

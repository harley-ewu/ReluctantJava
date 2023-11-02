package Controller;

import CLI.CommandLineInterface;
import Class.Class;
import Diagram.Diagram;
import Class.Class;
import CLI.CommandLineInterface;
import MenuPrompts.MenuPrompts;

import java.util.*;

public class MenuController {

    /**
    * Menu Control for Diagram
    * 
    * @param shouldTerminate - true if user presses terminate false otherwise
    * @param diagram - Diagram to get menu control for ( used for newClassMenuControl
    */
    public static void diagramMenuControl(boolean shouldTerminate, final Diagram diagram){

            String className = "";
            while(!shouldTerminate) {
                int choice = CommandLineInterface.diagramMenuChoice();
                Class currentClass = null;
                switch (choice) {
                //Add Class - name needed
                case 1:
                    /*className = MenuPrompts.addClassPrompt();
                    diagram.addClass(className);
                    newClassMenuControl(false, diagram.getClassList().get(className), diagram);*/
                    addClass(diagram);
                    break;
                //Delete Class - name needed
                case 2:
                    /*Class deletedClass = MenuPrompts.deleteClassPrompt(diagram);
                    diagram.deleteClass(deletedClass);*/
                    deleteClass(diagram);
                    break;
                //Rename Class - current and new name needed
                case 3:
                    /*Class old = MenuPrompts.renameClassPromptOriginalName(diagram);
                    String newName = MenuPrompts.renameClassPromptNewName(diagram, old);
                    diagram.renameClass(old, newName);*/
                    renameClass(diagram);
                    break;
                //Edit Class - name needed
                case 4:
                    while(currentClass == null){
                        currentClass = MenuPrompts.editClassPrompt(diagram);
                    }
                    editClassSubMenu(false, currentClass);
                    break;
                //edit relationships
                case 5:
                    editRelationshipsControl(false, diagram);
                    break;
                //View class - name needed
                case 6:
                    Class c = MenuPrompts.printSingleClassPrompt(diagram);
                    diagram.printSingleClass(c);
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
        diagram.addClass(className);
        newClassMenuControl(false, diagram.getClassList().get(className), diagram);
    }

    public static void deleteClass(final Diagram diagram) {
        Class deletedClass = MenuPrompts.deleteClassPrompt(diagram);
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
    * @param shouldTerminate - true if the method should terminate prematurely
    * @param currentClass - the class to add or remove attributes to
    * @param diagram - the diagram to add or remove relationships to
    */
    public static void newClassMenuControl(boolean shouldTerminate, final Class currentClass, final Diagram diagram) {
        Scanner scanner = new Scanner(System.in);
        while(!shouldTerminate) {
            int choice = CommandLineInterface.newClassMenuChoice();
            switch(choice) {
                //Add attribute
                case 1:
                   addAttribute(currentClass, scanner);
                   break;
                //Add relationship
                case 2:
                   Class c2 = null;
                   while(c2 == null){
                        c2 = MenuPrompts.promptClass2Relationship(diagram);
                   }
                   diagram.addRelationship(currentClass, c2);
                   break;
                case 3:
                   shouldTerminate = true;
                   break;
                case 4:
                   CommandLineInterface.newClassMenuHelp();
                   break;
                default:
                   break;
             }
        }
    }

    public static void editClassSubMenu(boolean shouldTerminate, final Class currentClass) {
        Scanner scanner = new Scanner(System.in);
        while (!shouldTerminate) {
            int choice = CommandLineInterface.editClassMenuChoice();
            switch (choice) {

                case 1: //add attribute
                    addAttribute(currentClass, scanner);

                    break;
                case 2: //delete attribute
                    deleteAttribute(currentClass, scanner);
                    break;
                case 3: //rename attribute
                    renameAttribute(currentClass, scanner);
                    break;
                case 4: //display attributes
                    System.out.println(currentClass.displayAttributes());
                    break;
                case 5: //display relationships
                    //System.out.println(this.displayRelationships());
                    break;
                case 6: //display all contents
                    System.out.println(currentClass);
                    break;
                case 7: //return to diagram menu
                    shouldTerminate = true;
                    break;
                case 8: //help
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

        /*do {
        System.out.println("Please choose one of the following options:\n" +
                "1. Field\n" +
                "2. Method\n");
        System.out.print("\nenter: ");
        choice = Integer.parseInt(scanner.nextLine());
        }while (choice != 1 && choice != 2);*/

        /*do {
            System.out.print("\nPlease enter a name:");
            name = scanner.nextLine();

        }while (name == null || name.isEmpty());*/

        if (choice == 1) {
            do {
                String type = MenuPrompts.promptParameterType();
                parameters.add(type);
                //error checking done in prompt method
                /*if (type != null || !type.isEmpty()) {
                    parameters.add(type);
                }*/

            }while(parameters == null && parameters.isEmpty());

        } else {
            int option = -99;
            do {
                option = MenuPrompts.addParameterPrompt();
                /*System.out.print("\nEnter an option 1 or 2:\n" +
                        "1. Enter a parameter name\n" +
                        "2. Finished\n" +
                        "\nEnter -> ");
                option = Integer.parseInt(scanner.nextLine());*/

                if (option == 1) {
                    String parameterName = MenuPrompts.promptParameterName();
                    parameters.add(parameterName);
                    /*System.out.print("\nEnter parameter name: ");
                    parameterName = scanner.nextLine();
                    if (parameterName != null && !parameterName.isEmpty()) {
                        parameters.add(parameterName);
                    }*/
                }

            }while (option != 2);

        }

        currentClass.createAttribute(name, parameters, choice);
    }

    public static void deleteAttribute(Class currentClass, Scanner scanner) {
        /*int choice = -99;
        do {
            System.out.println("Delete an attribute:");
            //need to add message if no attributes exist
            System.out.println(currentClass.displayAttributes());
            System.out.print("\nchoose between 1 and " + (currentClass.getAttributes().size()) + " -> ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }

        } while (choice < 1 || choice > currentClass.getAttributes().size()+1);*/
        int choice = MenuPrompts.deleteAttributePrompts(currentClass);
        currentClass.deleteAttribute(choice);

    }

    public static void renameAttribute(Class currentClass, Scanner scanner) {
        int input = 0;
        String newName = "";
        ArrayList<String> parameters = new ArrayList<>();

        do{
            currentClass.displayAttributes();
            System.out.print("\nPlease choose an attribute to rename:\n->");
        }while(input > currentClass.getAttributes().size() + 1 || input < 1);

        do{
            System.out.println("Please enter a new name:");
            newName = scanner.nextLine();
        }while(newName == null || newName.isEmpty());

        int choice = 0;
        do{
            System.out.println("Please choose an option:\n1.Add a parameter\n2.Continue");
        }while(choice != 2);
    }

    /**
    * Allows the user to edit relationships in the diagram.
    * 
    * @param shouldTerminate - true if the method should terminate prematurely
    * @param diagram - Diagram to edit relationships in
    */
    public static void editRelationshipsControl(boolean shouldTerminate, final Diagram diagram){
        Scanner scanner = new Scanner(System.in);
        while(!shouldTerminate){
            int choice = MenuPrompts.editRelationshipsMenuChoice();
            Class c1 = null;
            Class c2 = null;
            if (choice != 3){
                while(c1 == null){
                    c1 = MenuPrompts.promptClass1Relationship(diagram);
                }
                while(c2 == null) {
                    c2 = MenuPrompts.promptClass2Relationship(diagram);
                }
            }
            switch(choice) {
                case 1:
                    diagram.addRelationship(c1, c2);
                    break;
                case 2:
                    diagram.deleteRelationship(c1, c2);
                    break;
                case 3: 
                    shouldTerminate = true;
                    break;
                default:
                    break;
            }
        }
    }
}

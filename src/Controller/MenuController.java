package Controller;

import java.util.*;
import Diagram.Diagram;
import Class.Class;
import CLI.CommandLineInterface;

public class MenuController {

    public static void diagramMenuControl(boolean shouldTerminate, final Diagram diagram){

            String className = "";
            while(!shouldTerminate) {
                int choice = CommandLineInterface.diagramMenuChoice();
                switch (choice) {
                //Add Class - name needed
                case 1:
                    className = diagram.addClassPrompt();
                    diagram.addClass(className);
                    newClassMenuControl(false, diagram.getClassList().get(className), diagram);
                    break;
                //Delete Class - name needed
                case 2:
                    Class deletedClass = diagram.deleteClassPrompt();
                    diagram.deleteClass(deletedClass);
                    break;
                //Rename Class - current and new name needed
                case 3:
                    Class old = diagram.renameClassPromptOriginalName();
                    String newName = diagram.renameClassPromptNewName(old);
                    diagram.renameClass(old, newName);
                    break;
                //Edit Class - name needed
                case 4:
                    diagram.editClass();
                    break;
                //edit relationships
                case 5:
                    diagram.editRelationships();
                    break;
                //View class - name needed
                case 6:
                    diagram.printSingleClass();
                    break;
                //View Diagram
                case 7:
                    System.out.println(diagram);
                    break;
                case 8:
                    shouldTerminate = true;
                default:
                    break;
               
            }
            }
    }

    public static void newClassMenuControl(boolean shouldTerminate, final Class currentClass, final Diagram diagram) {
        Scanner scanner = new Scanner(System.in);
        while(!shouldTerminate) {
            int choice = CommandLineInterface.newClassMenuChoice();
            switch(choice) {
                //Add attribute
                case 1:
                   currentClass.createAttribute();
                   break;
                //Add relationship
                case 2:
                    //Will need to move to the CLI (view) class
                    System.out.println("Which class do you want to make a relationship with?");
                    System.out.print("Class: ");
                    String input = scanner.nextLine();
                    Class c2 = null;
                   do {
                      for(Class item : diagram.getClassList().values()){
                         if(input.equals(item.getClassName())) {
                            c2 = item;
                         }
                      }
    
                      if(c2 == null) {
                        //Will need to move to the CLI (view) class
                         System.out.println("class does not exist, please enter a valid class");
                         System.out.print("Class: ");
                         input = scanner.nextLine();
                      }
                   }while(c2 == null);
    
                   diagram.addRelationship(currentClass, c2);
                   break;
                case 3:
                   shouldTerminate = true;
                default:
                   break;
             }
        }
    }

    public static void editClassSubMenu(boolean shouldTerminate, final Class currentClass) {
        Scanner scanner = new Scanner(System.in);
        while (!shouldTerminate) {
            int choice = CommandLineInterface.newClassMenuChoice();
            switch (choice) {

                case 1: //add attribute
                    addAttribute(currentClass, scanner);

                    break;
                case 2: //delete attribute
                    deleteAttribute(currentClass, scanner);
                    break;
                case 3: //rename attribute
                    currentClass.renameAttribute();
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
                    shouldTerminate = false;
                    break;
                case 8: //help
                    System.out.println(currentClass.help());
                default:
                    break;
            }
        }
    }

    public static void addAttribute(Class currentClass, Scanner scanner) {
        int choice = -99;
        String name = "";
        ArrayList<String> parameters = new ArrayList<>();

        do {
        System.out.println("Please choose one of the following options:\n" +
                "1. Field\n" +
                "2. Method\n");
        System.out.print("\nenter: ");
        choice = Integer.parseInt(scanner.nextLine());
        }while (choice != 1 || choice != 2);

        do {
            System.out.print("\nPlease enter a name:");
            name = scanner.nextLine();

        }while (name == null || name.isEmpty());

        if (choice == 1) {
            do {
                System.out.println("Please enter a type: ");
                String type = scanner.nextLine();
                if (type != null || !type.isEmpty()) {
                    parameters.add(type);
                }

            }while(parameters == null && parameters.isEmpty());

        } else {
            int option = -99;
            do {
                System.out.print("\nEnter an option 1 or 2:\n" +
                        "1. Enter a parameter name\n" +
                        "2. Finished\n" +
                        "\nEnter -> ");
                option = Integer.parseInt(scanner.nextLine());

                if (option == 1) {
                    String parameterName = "";
                    System.out.print("\nEnter parameter name: ");
                    parameterName = scanner.nextLine();
                    if (parameterName != null && !parameterName.isEmpty()) {
                        parameters.add(parameterName);
                    }
                }

            }while (option != 2);

        }

        currentClass.createAttribute(name, parameters, choice);
    }

    public static void deleteAttribute(Class currentClass, Scanner scanner) {
        int choice = -99;
        do {
            System.out.println("Delete an attribute:");
            currentClass.displayAttributes();
            System.out.print("\nchoose between 1 and " + (currentClass.getAttributes().size()+1) + " -> ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }

        } while (choice < 1 || choice > currentClass.getAttributes().size()+1);

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
}

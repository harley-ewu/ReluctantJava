package Controller;

import CLI.CommandLineInterface;
import Class.Class;
import Diagram.Diagram;
import Class.Class;
import CLI.CommandLineInterface;
import MenuPrompts.MenuPrompts;

import java.util.Scanner;

public class MenuController {

    public static void diagramMenuControl(boolean shouldTerminate, final Diagram diagram){

            String className = "";
            while(!shouldTerminate) {
                int choice = CommandLineInterface.diagramMenuChoice();
                switch (choice) {
                //Add Class - name needed
                case 1:
                    className = MenuPrompts.addClassPrompt();
                    diagram.addClass(className);
                    newClassMenuControl(false, diagram.getClassList().get(className), diagram);
                    break;
                //Delete Class - name needed
                case 2:
                    Class deletedClass = MenuPrompts.deleteClassPrompt(diagram);
                    diagram.deleteClass(deletedClass);
                    break;
                //Rename Class - current and new name needed
                case 3:
                    Class old = MenuPrompts.renameClassPromptOriginalName(diagram);
                    String newName = MenuPrompts.renameClassPromptNewName(diagram, old);
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
                    Class c = MenuPrompts.printSingleClassPrompt(diagram);
                    diagram.printSingleClass(c);
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
                   //currentClass.addAttribute();
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
}

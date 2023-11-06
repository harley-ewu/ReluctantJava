package CLI;

import Class.Class;
import Controller.MenuController;
import Diagram.Diagram;
import GUI.GraphicalUserInterface;
import SaveLoadSystem.SaveLoadSystem;
import Class.Class;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/*
 * Description: This class will contain code related to the CLI
 * Use case: Call the start method from main to start the CLI
 * */
public class CommandLineInterface {

    private static final int MAX_CHOICES = 8;

    private static Diagram currentDiagram;
    public static void main(String[] args){
        boolean shouldTerminate = false;
        startCLI(shouldTerminate);
    }

    /*
     * Description: This method will start the CLI
     * Use Case: Call this from main
     * */


    private static void startCLI(boolean shouldTerminate){

        currentDiagram = null;

        while (!shouldTerminate) {
            int userChoice = getUserChoice();

            switch (userChoice) {
                case 1 -> currentDiagram = createNewDiagram(currentDiagram);
                case 2 -> viewDiagram(currentDiagram);
                case 3 -> editDiagram(currentDiagram);
                case 4 -> saveDiagram(currentDiagram);
                case 5 -> currentDiagram = loadDiagram();
                case 6 -> help();
                case 7 -> new Thread(() -> GraphicalUserInterface.startGUI(new String[0])).start();
                case 8 -> shouldTerminate = exit(currentDiagram);
                default -> System.out.println("There is a bug in getUserChoice");
            }
        }
    }

    /*
     * Description: This method will handle collecting and validating CLI input
     * Use Case: Call this to get a clean user input
     * @return: Int representing the users choice
     * */

    private static int getUserChoice() {
        Scanner scan = new Scanner(System.in);
        int userInput = -1;

        System.out.println("""
                                
                                
                 1 - New Diagram
                 2 - View Existing Diagram
                 3 - Edit Existing Diagram
                 4 - Save Current Diagram
                 5 - Load Diagram
                 6 - Help
                 7 - Open GUI
                 8 - Exit
                
                 Enter a number:
                """);
        System.out.print("--> ");
        while (true) {
            try {
                userInput = Integer.parseInt(scan.nextLine());
                if (isValidUserInput(userInput)) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and " + MAX_CHOICES);
                    System.out.print("--> ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
                System.out.print("--> ");
            }
        }

        return userInput;
    }

    /*
     * Description: Gets a diagram title from the user
     * Use case: Call in create diagram to get a title for the diagram constructor
     * */
    private static String getDiagramName(){
        Scanner scan = new Scanner(System.in);
        String userChoice = "";
        while(true) {
            try {
                System.out.println("Enter a title for the new diagram");
                System.out.print("--> ");
                userChoice = scan.nextLine();

                if(userChoice.length() < 1 || userChoice.length() > 50) {
                    throw new IllegalArgumentException();
                }
                break;
            }
            catch(IllegalArgumentException e){
                System.out.println("Please enter a title between 1 and 50 characters inclusive");
                System.out.print("--> ");
            }
        }
        return userChoice;
    }
    /*
     * Description: Validates user input
     * Use Case: Validate user choice input
     * */
    private static boolean isValidUserInput(int userInput) {
        return userInput >= 1 && userInput <= MAX_CHOICES;
    }

    private static void savePrompt(Diagram diagram) {

        if(diagram == null){
            System.out.println("There is no diagram loaded");
            return;
        }
        char userChoice;
        Scanner scan = new Scanner(System.in);

        System.out.println("""
                    1 - Save to Default Path
                    2 - Save to Custom Path
                    Any other key - Do Not Save
                    """);
        System.out.print("--> ");

        userChoice = scan.next().charAt(0);

        switch (userChoice) {
            case '1' -> saveToDefaultPath(diagram);
            case '2' -> saveToCustomPath(diagram);
            default -> System.out.println("Continuing without saving.");
        }
    }

    private static void saveToDefaultPath(Diagram diagram) {
        SaveLoadSystem.saveDefaultCLI(diagram.getTitle(), diagram);
        System.out.println("Saved to the default path.");
    }

    private static void saveToCustomPath(Diagram diagram) {
        Scanner scan = new Scanner(System.in);
        boolean saveSuccessful = false;

        while (!saveSuccessful) {
            System.out.println("Enter the custom file path:");
            String customPath = scan.nextLine();

            try {
                Path filePath = Paths.get(customPath);
                SaveLoadSystem.saveCustomCLI(filePath.toString(), diagram.getTitle(), diagram);
                System.out.println("Saved to custom path: " + filePath);
                saveSuccessful = true;
            } catch (InvalidPathException e) {
                System.out.println("Invalid file path. Please enter a valid path.");
                char userChoice;

                System.out.println("""
                1 - Retry with a new path
                2 - Save to Default Path
                Any other key - Do Not Save
                """);
                System.out.print("--> ");
                userChoice = scan.next().charAt(0);
                scan.nextLine();

                switch (userChoice) {
                    case '1', '2' -> {
                        if (userChoice == '2') {
                            saveToDefaultPath(diagram);
                        }
                    }
                    default -> {
                        System.out.println("Exiting without saving.");
                        saveSuccessful = true;
                    }
                }
            }
        }
    }



    private static Diagram createNewDiagram(Diagram currentDiagram){
        if(currentDiagram != null){
            savePrompt(currentDiagram);
        }

        String diagramName = getDiagramName();
        return new Diagram(diagramName);
    }
    private static void viewDiagram(Diagram currentDiagram){
        if(currentDiagram == null){
            System.out.println("There is no diagram currently loaded");
            return;
        }

        System.out.println(currentDiagram);
    }
    private static void editDiagram(Diagram currentDiagram){
        if(currentDiagram == null){
            System.out.println("There is no diagram currently loaded");
            return;
        }
        MenuController.diagramMenuControl(false, currentDiagram);
        //currentDiagram.menu();
    }
    private static void saveDiagram(Diagram currentDiagram){
        savePrompt(currentDiagram);
    }
    private static Diagram loadDiagram() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter the path to the file you want to load:");
        System.out.print("--> ");
        String filePath = scan.nextLine();

        System.out.println("Enter the name of the file you want to load (without .json extension):");
        System.out.print("--> ");
        String fileName = scan.nextLine();

        /*
        try {
            //var loadedClasses = SaveLoadSystem.load(filePath);
            System.out.println("Diagram loaded successfully.");
            Diagram diagram = new Diagram("");
            //diagram.setClassList(loadedClasses);
            return new Diagram("");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid file path. Please enter a valid path.");
        } catch (Exception e) {
            System.out.println("An error occurred while loading the diagram.");
        }*/
        Diagram loadedDiagram = SaveLoadSystem.loadDiagramCLI(filePath, fileName);
        if (loadedDiagram == null) {
            System.out.println("Diagram was not found!");
        }
        return loadedDiagram;
    }

    /*
     * Description: Prints description of each command to the console
     * Use Case: Call under 'help' switch block
     * */
    private static void help(){
        System.out.println("""
                 MAIN MENU COMMANDS:

                 Option 1 - New Diagram: Create a new UML Diagram
                
                 Option 2 - View Existing Diagram: View the currently loaded diagram
                
                 Option 3 - Edit Diagram: Edit the currently loaded diagram
                
                 Option 4 - Save Diagram: Saves the currently loaded diagram
                
                 Option 5 - Load Diagram: Loads an existing diagram from a file
                
                 Option 6 - Help: Lists a description of all available commands
                
                 Option 7 - Start GUI: Opens the graphical user interface associated with this program
                
                 Option 8 - Exit: Exit the program
                """);
    }

    /*
     * Description: This method will prompt the user to save, and then close the program
     * Use Case: Call this under 'exit' switch block
     * @param currentDiagram: The active diagram
     * @returns: true (Assign value to menu flag)
     * */

    private static boolean exit(Diagram currentDiagram) {
        savePrompt(currentDiagram);
        return true;
    }

    public static int diagramMenuChoice() {
        int userInput = -99;
        Scanner scan = new Scanner(System.in);
        System.out.println("UML Diagram Editor Menu");
        System.out.println("""
            
                                1 - Add Class
                                2 - Delete Class
                                3 - Rename Class
                                4 - Edit Class
                                5 - Edit Relationships
                                6 - View Class
                                7 - View Diagram
                                8 - Help
                                9 - Exit
                                
                                Enter a number:""");
        System.out.print("--> ");

        while (true) {
            try {
                userInput = Integer.parseInt(scan.nextLine());
                if (userInput >= 1 && userInput <= 9) {
                    break;
                } else {
                    int choices = MAX_CHOICES + 1;
                    System.out.println("Invalid input. Please enter a number between 1 and " + choices);
                    System.out.print("--> ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
                System.out.print("--> ");
            }
        }
        return userInput;

    }

    public static void diagramHelp(){
        System.out.println("""
            DIAGRAM MENU COMMANDS:

            Option 1 - Add Class: Create and add a class to the diagram
           
            Option 2 - Delete Class: Delete existing class from the diagram
           
            Option 3 - Rename Class: Rename existing class in the diagram
           
            Option 4 - Edit Class: Opens up sub-menu with class editing options
           
            Option 5 - Edit Relationships: Opens up sub-menu with relationship add/delete options
           
            Option 6 - View Class: Lists all attributes and relationships of existing class
           
            Option 7 - View Diagram: Lists all classes and their properties in current diagram
           
            Option 8 - Exit: Exit the program
           
            Option 9 - Help: See how each option functions
                """);
    }

    public static int newClassMenuChoice(){
        int userInput = -99;
        Scanner scan = new Scanner(System.in);
        System.out.println("New Class Editor");
        System.out.println("""
                                
                                1 - Add Attribute
                                2 - Add Relationship
                                3 - Back to Diagram Menu
                                4 - Help
                                
                                Enter a number:""");
        System.out.print("--> ");
        while (true) {
            try {
                userInput = Integer.parseInt(scan.nextLine());
                if (isValidUserInput(userInput)) {
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

    public static void newClassMenuHelp(){
        System.out.println("""
            NEW CLASS MENU COMMANDS:

            Option 1 - Add Attribute: Opens up sub-menu for creating an attribute for the current class
           
            Option 2 - Add Relationship: Opens up a sub-menu for adding a relationship between two classes
           
            Option 3 - Back to Diagram Menu: Returns you to Edit Diagram Menu
           
            Option 4 - Help: See how each option functions
                """);
    }

    public static int editClassMenuChoice(final Class currentClass) {
        int userInput = -99;
        Scanner scan = new Scanner(System.in);
        //the sub menu will loop until the user is done making necessary changes, they can step back to the previous menu
        System.out.println("\n" + currentClass.getClassName() + " Class Editor");
        System.out.println("""
                              1 - Add attribute
                              2 - Delete attribute
                              3 - Rename Attribute
                              4 - Display attributes
                              5 - Display relationships
                              6 - Display all contents
                              7 - Return to Diagram Menu
                              8 - Help
                             
                             Enter a number:""");
        System.out.print("--> ");

        while (true) {

            try {
                userInput = Integer.parseInt(scan.nextLine());
                if (isValidUserInput(userInput)) {
                    break;
                } else {
                    System.out.println("Please enter a valid option, 1-" + MAX_CHOICES);
                    System.out.print("--> ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
                System.out.print("--> ");
            }

        }

        return userInput;
    }

    public static void editClassMenuHelp(){
        System.out.println("""
            CLASS MENU COMMANDS:

            Option 1 - Add attribute: Prompts user to enter a name for a new attribute

            Option 2 - Delete attribute: Prompts user to enter a name of an (existing) attribute to delete

            Option 3 - Rename attribute: Prompts the user to enter a name of an existing attribute, then prompts user to enter a new name for that attribute

            Option 4 - Display relationships: Displays all relationships assigned to the class

            Option 5 - Display all contents: displays the contents of the class including name, attributes, and relationships

            Option 6 - Return to Diagram Menu: returns the user to the diagram menu holding the class
                """);
    }

    public static void setCurrentDiagram(Diagram diagram){
        currentDiagram = diagram;
    }

    public static Diagram getCurrentDiagram(){
        return currentDiagram;
    }
}

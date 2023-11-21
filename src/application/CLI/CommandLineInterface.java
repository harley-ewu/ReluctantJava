package application.CLI;

import Class.Class;
import Controller.MenuController;
import Diagram.Diagram;
import application.Application;
import application.GUI.GraphicalUserInterface;
import SaveLoadSystem.SaveLoadSystem;
import application.UserInterface;
import application.CLI.AutoComplete;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/*
 * Description: This class will contain code related to the CLI
 * Use case: Call the start method from main to start the CLI
 * */
public class CommandLineInterface implements UserInterface {

    private static final int MAX_CHOICES = 8;
    private static AutoComplete ac = new AutoComplete();
    public CommandLineInterface(){}

    /*
     * Description: This method will start the CLI
     * Use Case: Call this from main
     * */


    public void launchUmlEditor(){

        boolean shouldTerminate = false;
        while (!shouldTerminate) {
            String userChoice = getUserChoice();
            if(userChoice.isEmpty()){
                continue;
            }
            if (!Character.isDigit(userChoice.charAt(0))){
                //autocomplete methods
                shouldTerminate = typingMainMenuControl(userChoice.trim());
                continue;
            }
            int choice = Integer.parseInt(userChoice);

            switch (choice) {
                case 1 -> Application.setCurrentDiagram(createNewDiagram(Application.getCurrentDiagram()));
                case 2 -> viewDiagram(Application.getCurrentDiagram());
                case 3 -> editDiagram(Application.getCurrentDiagram());
                case 4 -> saveDiagram(Application.getCurrentDiagram());
                case 5 -> Application.setCurrentDiagram(loadDiagram());
                case 6 -> help();
                case 7 -> new Thread(() -> new GraphicalUserInterface().launchUmlEditor()).start();
                case 8 -> shouldTerminate = exit(Application.getCurrentDiagram());
                default -> System.out.println("There is a bug in getUserChoice");
            }
        }
    }

    /*
     * Description: This method will handle collecting and validating CLI input
     * Use Case: Call this to get a clean user input
     * @return: Int representing the users choice
     * */

    private static String getUserChoice() {
        Scanner scan = new Scanner(System.in);
        int numberInput = -1;

        System.out.print("""
                \n--------------------------           
                    UML Diagram Menu
                --------------------------              
                 1 - New Diagram
                 2 - View Existing Diagram
                 3 - Edit Existing Diagram
                 4 - Save Current Diagram
                 5 - Load Diagram
                 6 - Help
                 7 - Open GUI
                 8 - Exit
                --------------------------
                """);
        if(Application.getCurrentDiagram() == null){
            System.out.println(" No diagrams are currently \nloaded or created");
        }
        else {
            System.out.println(" The diagram '"+ Application.getCurrentDiagram().getTitle() + "' \nis your current diagram");
        }
        System.out.println("--------------------------\n");
        System.out.println("Enter a number from menu above \n\tOR \nType a command (use tab to autocomplete):");
        System.out.println("--> ");
        ac.mainLineReader();
        String userInput = ac.getCommands();
        if(!ac.isNumber(userInput)){
            return userInput;
        }
        numberInput = Integer.parseInt(userInput);
        boolean first = true;
        while (true) {
            try {
                if(!first)
                    numberInput = Integer.parseInt(scan.nextLine());
                if (numberInput >= 1 && numberInput <= 8) {
                    userInput = numberInput + "";
                    break;
                } else {
                    first = false;
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
        String userChoice;
        Scanner scan = new Scanner(System.in);

        System.out.println("""
            \n--------------------------
            1 - Save to Default Path
            2 - Save to Custom Path
            --------------------------

            Enter a number from menu above:
            (Any other key - Do Not Save)
            """);
        System.out.println("--> ");

        userChoice = scan.nextLine();
        //When enter key is pressed
        if(userChoice == ""){
            System.out.println("\nContinuing without saving.");
            return;
        }
        switch (userChoice.charAt(0)) {
            case '1' -> saveToDefaultPath(diagram);
            case '2' -> saveToCustomPath(diagram);
            default -> System.out.println("\nContinuing without saving.");
        }
    }

    private static void saveToDefaultPath(Diagram diagram) {
        SaveLoadSystem.saveDefaultCLI(diagram.getTitle(), diagram);
        System.out.println("\nSaved to the default path.");
    }

    private static void saveToCustomPath(Diagram diagram) {
        Scanner scan = new Scanner(System.in);
        boolean saveSuccessful = false;

        while (!saveSuccessful) {
            System.out.println("\nEnter the custom file path:");
            String customPath = scan.nextLine();

            try {
                Path filePath = Paths.get(customPath);
                SaveLoadSystem.saveCustomCLI(filePath.toString(), diagram.getTitle(), diagram);
                System.out.println("\nSaved to custom path: " + filePath);
                saveSuccessful = true;
            } catch (InvalidPathException e) {
                System.out.println("Invalid file path. Please enter a valid path.");
                char userChoice;

                System.out.println("""
                    \n--------------------------
                    1 - Retry with a new path
                    2 - Save to Default Path
                    --------------------------
    
                    Enter a number from menu above:
                    (Any other key - Do Not Save)
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
                        System.out.println("\nExiting without saving.");
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
    public static void saveDiagram(Diagram currentDiagram){
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

                 Tab Btn  - Hit tab to see all available commands to type

                 Option 1 - New Diagram: Create a new UML Diagram
                
                 Option 2 - View Existing Diagram: View the currently loaded diagram
                
                 Option 3 - Edit Diagram: Edit the currently loaded diagram
                
                 Option 4 - Save Diagram: Saves the currently loaded diagram
                
                 Option 5 - Load Diagram: Loads an existing diagram from a file
                
                 Option 6 - Help: Lists a description of all available commands
                
                 Option 7 - Open GUI: Opens the graphical user interface associated with this program
                
                 Option 8 - Exit: Exit the program
                """);
    }

    /*
     * Description: This method will prompt the user to save, and then close the program
     * Use Case: Call this under 'exit' switch block
     * @param currentDiagram: The active diagram
     * @returns: true (Assign value to menu flag)
     * */

    public static boolean exit(Diagram currentDiagram) {
        savePrompt(currentDiagram);
        return true;
    }

    public static String diagramMenuChoice() {
        int numberInput = -99;
        Scanner scan = new Scanner(System.in);
        System.out.println("\n--------------------------");
        System.out.println("UML Diagram Editor Menu  \n\t'" + Application.getCurrentDiagram().getTitle() + "'");
        System.out.println("""
                             --------------------------
                             1 - Add Class
                             2 - Delete Class
                             3 - Rename Class
                             4 - Edit Class
                             5 - View Class
                             6 - View Diagram
                             7 - Save Changes
                             8 - Help
                             9 - Exit
                            --------------------------
                            """);
        System.out.println("Enter a number from menu above \n\tOR \nType a command (use tab to autocomplete):");
        System.out.println("--> ");
        ac.classLineReader();
        String userInput = ac.getCommands();
        if(!ac.isNumber(userInput)){
            return userInput;
        }
        numberInput = Integer.parseInt(userInput);
        boolean first = true;
        while (true) {
            try {
                if(!first)
                    numberInput = Integer.parseInt(scan.nextLine());
                if (numberInput >= 1 && numberInput <= 9) {
                    userInput = numberInput + "";
                    break;
                } else {
                    first = false;
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

            Tab Btn  - Hit tab to see all available commands to type.

            Option 1 - Add Class: Create and add a class to the diagram
           
            Option 2 - Delete Class: Delete existing class from the diagram
           
            Option 3 - Rename Class: Rename existing class in the diagram
           
            Option 4 - Edit Class: Opens up sub-menu with class editing options
           
            Option 5 - View Class: Lists all attributes and relationships of existing class
           
            Option 6 - View Diagram: Lists all classes and their properties in current diagram
           
            Option 7 - Save Changes: Saves diagram to your location of your choice

            Option 8 - Help: See how each option functions

            Option 9 - Exit: Exit the program
                """);
    }

    public static String newClassMenuChoice(){
        int numberInput = -99;
        Scanner scan = new Scanner(System.in);
        System.out.println("\n--------------------------");
        System.out.println("    New Class Editor");
        System.out.println("""
                            -------------------------- 
                             1 - Add Attribute
                             2 - Add Relationship
                             3 - Save Changes
                             4 - Back to Diagram Menu
                             5 - Help
                            --------------------------
                                """);
        System.out.println("Enter a number from menu above \n\tOR \nType a command (use tab to autocomplete):");
        System.out.println("--> ");
        ac.newClassLineReader();
        String userInput = ac.getCommands();
        if(!ac.isNumber(userInput)){
            return userInput;
        }
        numberInput = Integer.parseInt(userInput);
        boolean first = true;
        while (true) {
            try {
                if(!first)
                    numberInput = Integer.parseInt(scan.nextLine());
                if (numberInput >= 1 && numberInput <= 9) {
                    userInput = numberInput + "";
                    break;
                } else {
                    first = false;
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

            Tab Btn  - Hit tab to see all available commands to type

            Option 1 - Add Attribute: Opens up sub-menu for creating an attribute for the current class
           
            Option 2 - Add Relationship: Opens up a sub-menu for adding a relationship between two classes
           
            Option 3 - Save Changes: Saves current diagram to location of your choice
            
            Option 4 - Back to Diagram Menu: Returns you to Edit Diagram Menu
           
            Option 5 - Help: See how each option functions
                """);
    }

    public static String editClassMenuChoice(final Class currentClass) {
        int numberInput = -99;
        System.out.println("\n" + currentClass);
        Scanner scan = new Scanner(System.in);
        //the sub menu will loop until the user is done making necessary changes, they can step back to the previous menu
        System.out.println("\n--------------------------");
        System.out.println("'" + currentClass.getClassName() + "''  - Class Editor");
        System.out.println("""
                            --------------------------
                             1 - Add Attribute
                             2 - Delete Field
                             3 - Delete Method
                             4 - Rename Attribute
                             5 - Edit Relationships
                             6 - Display All Contents
                             7 - Save Changes
                             8 - Return to Diagram Menu
                             9 - Help
                            --------------------------
                            """);
        System.out.println("Enter a number from menu above \n\tOR \nType a command (use tab to autocomplete):");
        System.out.println("--> ");
        ac.editClassLineReader();
        String userInput = ac.getCommands();
        if(!ac.isNumber(userInput)){
            return userInput;
        }
        numberInput = Integer.parseInt(userInput);
        boolean first = true;
        while (true) {

            try {
                if(!first)
                    numberInput = Integer.parseInt(scan.nextLine());
                if (numberInput >= 1 && numberInput <= 9) {
                    userInput = numberInput + "";
                    break;
                } else {
                    first = false;
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

            Tab Btn  - Hit tab to see all available commands to type

            Option 1 - Add attribute: Prompts user to enter a name for a new attribute

            Option 2 - Delete Field: Prompts user to enter a name of an (existing) field to delete

            Option 3 - Delete Method: Prompts user to enter a name of an (existing) method to delete

            Option 4 - Rename Attribute: Prompts the user to enter a name of an existing attribute, then prompts user to enter a new name for that attribute
            
            Option 5 - Edit Relationships: Opens up sub-menu with relationship add/delete options

            Option 6 - Display All Contents: displays the contents of the class including name, attributes, and relationships

            Option 7 - Save Changes: Saves current diagram to location of your choice
           
            Option 8 - Return to Diagram Menu: returns the user to the diagram menu holding the class

            Option 9 - Help: Lists descriptions of each command
                """);
    }

    public static String editRelationshipsMenuChoice() {
        int numberInput = -99;
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--------------------------");
        System.out.println("Relationship Editor");
        System.out.println("""
                            --------------------------
                             1 - Add Relationship
                             2 - Delete Relationship
                             3 - View Relationships
                             4 - Save Changes
                             5 - Back to Diagram Menu
                            --------------------------   
                            """);
        System.out.println("Enter a number from menu above \n\tOR \nType a command (use tab to autocomplete):");
        System.out.println("--> ");
        ac.relationshipEditorLineReader();
        String userInput = ac.getCommands();
        if(!ac.isNumber(userInput)){
            return userInput;
        }
        numberInput = Integer.parseInt(userInput);
        boolean first = true;

        while (true) {
            try {
                if(!first)
                    numberInput = Integer.parseInt(scanner.nextLine());
                if (numberInput >= 1 && numberInput <= 9) {
                    userInput = numberInput + "";
                    break;
                } else {
                    first = false;
                    System.out.println("Invalid input. Please enter a number between 1 and 4");
                    System.out.print("--> ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
                System.out.print("--> ");
            }
        }
        return userInput;
    }

    public static boolean typingMainMenuControl(final String command) {
        Diagram currentDiagram = Application.getCurrentDiagram();
        switch (command) {
            case "new-diagram":
                currentDiagram = createNewDiagram(currentDiagram);
                Application.setCurrentDiagram(currentDiagram);
                break;
            case "view-diagram":
                viewDiagram(currentDiagram);
                break;
            case "edit-current-diagram":
                editDiagram(currentDiagram);
                break;
            case "save":
                saveDiagram(currentDiagram);
                break;
            case "load-existing-diagram":
                loadDiagram();
                break;
            case "help":
                help();
                break;
            case "gui":
                System.out.println("Please enter number 7");
                break;
            case "exit":
                return exit(currentDiagram);
            default:
                System.out.println("Not a recognized command.");
        }
        return false;
    }
}

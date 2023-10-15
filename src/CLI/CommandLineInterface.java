package src.CLI;

import java.util.Scanner;

/*
* Description: This class will contain code related to the CLI
* Use case: Call the start method from main to start the CLI
* */
public class CommandLineInterface {

    private static final int MAX_CHOICES = 7;
    public static void main(String[] args){
        boolean shouldTerminate = false;
        startCLI(shouldTerminate);
    }

    /*
    * Description: This method will start the CLI
    * Use Case: Call this from main
    * */


    private static void startCLI(boolean shouldTerminate){

        while (!shouldTerminate) {
            int userChoice = getUserChoice();

            switch (userChoice) {
                case 1 -> createNewDiagram();
                case 2 -> viewDiagram(); // May call external menu method if it's implemented in a separate class
                case 3 -> editDiagram(); // May call external menu method if it's implemented in a separate class
                case 4 -> saveDiagram(); // May call external menu method if it's implemented in a separate class
                case 5 -> loadDiagram(); // May call external menu method if it's implemented in a separate class
                case 6 -> help();
                case 7 -> shouldTerminate = exit();
                default -> System.out.println("There is a bug in getUserChoice");
            }
        }
    }

    /*
    * Description: This method will handle collecting and validating CLI input
    * Use Case: Call this to get a clean user input
    * */

    private static int getUserChoice() {
        Scanner scan = new Scanner(System.in);
        int userInput = -1;

        System.out.println("""
                
                
                Enter a number:
                
                1 - New Diagram
                2 - View Existing Diagram
                3 - Edit Existing Diagram
                4 - Save Current Diagram
                5 - Load Diagram
                6 - Help
                7 - Exit
                """);

        while (true) {
            try {
                userInput = Integer.parseInt(scan.nextLine());
                if (isValidUserInput(userInput)) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and " + MAX_CHOICES);
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }

        return userInput;
    }

    /*
    * Description: Validates user input
    * Use Case: Validate user choice input
    * */
    private static boolean isValidUserInput(int userInput) {
        return userInput >= 1 && userInput <= MAX_CHOICES;
    }

    private static void createNewDiagram(){
        //TODO: Implement
    }
    private static void viewDiagram(){
        //TODO: Implement
    }
    private static void editDiagram(){
        //TODO: Implement
    }
    private static void saveDiagram(){
        //TODO: Implement
    }
    private static void loadDiagram(){
        //TODO: Implement
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
                
                Option 5 - Help: Lists a description of all available commands
                
                Option 6 - Exit: Exit the program
                """);
    }

    /*
    * Description: This method will prompt the user to save, and then close the program
    * Use Case: Call this under 'exit' switch block
    * */

    private static boolean exit() {
        char userChoice;
        Scanner scan = new Scanner(System.in);

        System.out.println("Would you like to save before exiting?\n\n1 - Yes\nAnything else - No");
        userChoice = scan.next().charAt(0); // Pull first entered character

        if (userChoice == '1') {
            //TODO: Call Save when implemented
        }

        return true;
    }
}

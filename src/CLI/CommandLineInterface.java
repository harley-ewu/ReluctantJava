package src.CLI;

import java.util.Scanner;

/*
* Description: This class will contain code related to the CLI
* Use case: Call the start method from main to start the CLI
* */
public class CommandLineInterface {

    public static void main(String[] args){
        boolean shouldTerminate = false;
        startCLI(shouldTerminate);
    }

    /*
    * Description: This method will start the CLI
    * Use Case: Call this from main
    * */

    //TODO: Implement option 1
    //TODO: Implement option 2
    //TODO: Implement option 3

    private static void startCLI(boolean shouldTerminate){

        while (!shouldTerminate) {
            int userChoice = getUserChoice();

            switch (userChoice) {
                case 1 -> System.out.println("Option 1");
                case 2 -> System.out.println("Option 2");
                case 3 -> listRelationships();
                case 4 -> help();
                case 5 -> shouldTerminate = exit();
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
                
                1 - List Classes
                2 - List Class
                3 - List Relationships
                4 - Help
                5 - Exit
                """);

        while (true) {
            try {
                userInput = Integer.parseInt(scan.nextLine());
                if (isValidUserInput(userInput)) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 5.");
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
        return userInput >= 1 && userInput <= 5;
    }

    private static void listRelationships(){
        //TODO: Implement when relationships are ready to be listed
    }

    /*
    * Description: Prints description of each command to the console
    * Use Case: Call under 'help' switch block
    * */
    private static void help(){
        System.out.println("""


                Option 1 - List Classes: Lists all of the classes in your project
                
                Option 2 - List Class: Lists the contents of a specified class
                
                Option 3 - List Relationships: Lists all class relationships in your project
                
                Option 4 - Help: Lists a description of all available commands
                
                Option 5 - Exit: Exit the program
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

package CLI;

import java.util.Scanner;

/*
* Description: This class will contain code related to the CLI
* Use case: Call the start method from main to start the CLI
* */
public class CommandLineInterface {

    /*
    * Description: This method will start the CLI
    * Use Case: Call this from main
    * */

    //TODO: Implement option 1
    //TODO: Implement option 2
    //TODO: Implement option 3
    //TODO: Implement option 4
    //TODO: Implement option 5

    public static void startCLI(){
        while (true) {
            int userChoice = getUserChoice();

            switch (userChoice) {
                case 1 -> System.out.println("Option 1");
                case 2 -> System.out.println("Option 2");
                case 3 -> System.out.println("Option 3");
                case 4 -> System.out.println("Option 4");
                case 5 -> exit();
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

        System.out.println("Enter a number:\n\n1 - Option 1\n2 - Option 2\n3 - Option 3\n4 - Option 4\n5 - Option 5");

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

    private static boolean isValidUserInput(int userInput) {
        return userInput >= 1 && userInput <= 5;
    }


    /*
    * Description: This method will prompt the user to save, and then close the program
    * Use Case: Call this under 'exit' switch block
    * */

    private static void exit(){
        char userChoice;
        Scanner scan = new Scanner(System.in);

        System.out.println("Would you like to save before exiting?\n\n1 - Yes\nAnything else - No");
        userChoice = scan.next().charAt(0); // Pull first entered character

        if(userChoice == '1'){
            //TODO: Call Save when implemented
        }

        System.exit(0);
    }
}
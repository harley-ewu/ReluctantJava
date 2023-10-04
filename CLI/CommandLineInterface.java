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
    public static void startCLI(){
        int userChoice = -1;

        switch(userChoice){
            case 1:
                System.out.println("Option 1");
                break;
            case 2:
                System.out.println("Option 2");
                break;
            case 3:
                System.out.println("Option 3");
            default:
                System.out.println("");
        }
    }

    /*
    * Description: This method will prompt the user to save, and then close the program
    * Use Case: Call this under 'exit' switch block
    * */
    private static void exit(){
        boolean hasAnswered = false;
        char userChoice;
        Scanner scan = new Scanner(System.in);

        System.out.println("Would you like to save before exiting?\n\n1 - Yes\nAnything else - No");
        userChoice = scan.next().charAt(0); // Pull first entered character

        if(userChoice == '1'){
            //TODO: Call save when implemented
        }

        System.exit(0);
    }
}

package application;

import Diagram.Diagram;
import application.CLI.CommandLineInterface;
import application.GUI.GraphicalUserInterface;
import application.startup.Startup;

public class Application {

    private static Diagram currentDiagram;

    public static void main(String[] args){
        Startup.init();

        UserInterface userInterface;

        if(!(args.length == 0) && args[0].equals("GUI")){
            userInterface = new GraphicalUserInterface();
        }
        else{
            userInterface = new CommandLineInterface();
        }

        userInterface.launchUmlEditor();
    }

    public static Diagram getCurrentDiagram(){
        return currentDiagram;
    }

    public static void setCurrentDiagram(Diagram diagram){
        currentDiagram = diagram;
    }
}

package GUI;

import javafx.scene.control.Button;

public class MainMenuController {

    public void createDiagramGUI() throws Exception{
        if(GraphicalUserInterface.getCurrentDiagram() != null) {
            GraphicalUserInterface.showSavePrompt();
        }
        else{
            GraphicalUserInterface.openPopup();
            System.out.println("Diagram created");
        }     
    }

    public void viewDiagramGUI() throws Exception{
        if(GraphicalUserInterface.getCurrentDiagram() != null) {
            GraphicalUserInterface.openDiagram(GraphicalUserInterface.getCurrentDiagram());
            System.out.println("Editing/Viewing Diagram");
        }else{
            GraphicalUserInterface.noDiagramLoadedAlert();
        }
        
    }

    public void helpMainGUI(){
        GraphicalUserInterface.displayHelpPopup();
        System.out.println("Help Menu");
    }

    public void exitUI(){
        System.exit(0);
    }
}

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

    public void viewDiagramGUI(){
        System.out.println("Viewing Diagram");
    }

    public void editDiagramGUI(){
        System.out.println("Editing Diagram");
    }

    public void exitUI(){
        System.exit(0);
    }
}

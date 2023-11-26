package application.mediator.controllers.menubarcontroller.guiSave;

import Diagram.Diagram;
import SaveLoadSystem.SaveLoadSystem;
import application.Application;
import application.mediator.common.IHandler;
import application.mediator.common.Request;
import javafx.scene.control.Alert;

import java.io.File;

public class GuiSaveRequestHandler implements IHandler {
    public Void handle(Request request){
        Diagram diagram = Application.getCurrentDiagram();

        if(diagram == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning: No Project");
            alert.setContentText("There is currently no project loaded or in progress.\n" +
                    "Please either start or load a project before saving.");
            alert.isResizable();
            alert.showAndWait();
            return null;
        }

        if(diagram.getSaveLocation() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning: Project not saved yet");
            alert.setContentText("The has not been saved to a location yet.\n" +
                    "Please use \"Save As...\" first before using \"Save\".");
            alert.isResizable();
            alert.showAndWait();
            return null;
        }

        try{
            File file = new File(diagram.getSaveLocation());
            SaveLoadSystem.saveProjectGUI(diagram, file);
        }catch(Exception e){
            System.err.println("There was an error saving the file. \n");
        }
        return null;
    }
}

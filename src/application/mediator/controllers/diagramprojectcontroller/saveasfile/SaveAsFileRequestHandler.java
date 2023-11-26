package application.mediator.controllers.diagramprojectcontroller.saveasfile;

import Diagram.Diagram;
import SaveLoadSystem.SaveLoadSystem;
import application.Application;
import application.mediator.common.IHandler;
import application.mediator.common.Request;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class SaveAsFileRequestHandler implements IHandler {
    public Void handle(Request request){
        SaveAsFileRequest newRequest = (SaveAsFileRequest) request;
        Window stage = newRequest.getStage();

        System.out.println("Saving file as!");
        String homeFolder = System.getProperty("user.home");
        FileChooser fileChooser = new FileChooser();
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

        fileChooser.setInitialDirectory(new File(homeFolder));
        fileChooser.setTitle("Save project as...");
        fileChooser.setInitialFileName(diagram.getTitle());
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON file", "*.json")
        );

        try{
            File file = fileChooser.showSaveDialog(stage);
            diagram.setSaveLocation(file.toString());
            SaveLoadSystem.saveProjectGUI(diagram, file);
        }catch(NullPointerException nullPointerException){
            System.out.println("Cancelled Save. \n");
        }catch(Exception e){
            System.err.println("There was an error trying to save the project. \n");
        }
        return null;
    }
}

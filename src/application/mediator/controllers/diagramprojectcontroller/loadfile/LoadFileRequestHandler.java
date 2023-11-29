package application.mediator.controllers.diagramprojectcontroller.loadfile;

import Diagram.Diagram;
import SaveLoadSystem.SaveLoadSystem;
import application.Application;
import application.GUI.GUIDiagramProject;
import application.GUI.GraphicalUserInterface;
import application.mediator.common.IHandler;
import application.mediator.common.Request;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class LoadFileRequestHandler implements IHandler {
    public Void handle(Request request){
        if(Application.getCurrentDiagram() != null){
            GraphicalUserInterface.showSavePrompt();
        }

        LoadFileRequest newRequest = (LoadFileRequest) request;
        Window stage = newRequest.getStage();

        System.out.println("loading a file!");
        String homeFolder = System.getProperty("user.home");
        FileChooser fileChooser = new FileChooser();
        Diagram diagram;
        GUIDiagramProject view = GraphicalUserInterface.getDiagramView();

        fileChooser.setInitialDirectory(new File(homeFolder));
        fileChooser.setTitle("Load project...");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON file", "*.json")
        );

        try{
            File file = fileChooser.showOpenDialog(stage);
            diagram = SaveLoadSystem.loadProjectGUI(file);
            Application.setCurrentDiagram(diagram);
            if(view != null){
                GraphicalUserInterface.closeDiagram();
                GraphicalUserInterface.openDiagram(diagram);
            }
            System.out.println("Successfully loaded project. \n");
        }catch(NullPointerException nullPointerException){
            System.out.println("Cancelled Load. \n");
        }catch(Exception e){
            System.err.println("There was an error trying to load the project. \n");
        }
        return null;
    }
}

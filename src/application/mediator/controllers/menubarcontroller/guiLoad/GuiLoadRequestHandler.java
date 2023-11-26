package application.mediator.controllers.menubarcontroller.guiLoad;

import Diagram.Diagram;
import SaveLoadSystem.SaveLoadSystem;
import application.Application;
import application.mediator.common.IHandler;
import application.mediator.common.Request;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class GuiLoadRequestHandler implements IHandler {
    public Void handle(Request request) {
        GuiLoadRequest newRequest = (GuiLoadRequest) request;
        HBox menuBar = newRequest.getMenuBar();

        String homeFolder = System.getProperty("user.home");
        FileChooser fileChooser = new FileChooser();
        Diagram diagram;
        Window stage = menuBar.getScene().getWindow();

        fileChooser.setInitialDirectory(new File(homeFolder));
        fileChooser.setTitle("Load project...");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON file", "*.json")
        );

        try {
            File file = fileChooser.showOpenDialog(stage);
            diagram = SaveLoadSystem.loadProjectGUI(file);
            Application.setCurrentDiagram(diagram);
            if (diagram != null) {
                System.out.println("Successfully loaded project. \n");
            }
        } catch (NullPointerException nullPointerException) {
            nullPointerException.printStackTrace();
            System.out.println("Cancelled Load. \n");
        } catch (Exception e) {
            System.err.println("There was an error trying to load the project. \n");
        }
        return null;
    }
}

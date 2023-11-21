package application.mediator.controllers.menubarcontroller;

import application.Application;
import application.CLI.CommandLineInterface;
import Diagram.Diagram;
import SaveLoadSystem.SaveLoadSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;

public class MenuBarController {
    @FXML
    HBox hbMenuBar;

    @FXML
    public void handleSaveAs(ActionEvent event) {
        String homeFolder = System.getProperty("user.home");
        FileChooser fileChooser = new FileChooser();
        Diagram diagram = Application.getCurrentDiagram();
        Window stage = hbMenuBar.getScene().getWindow();

        if(diagram == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning: No Project");
            alert.setContentText("There is currently no project loaded or in progress.\n" +
                    "Please either start or load a project before saving.");
            alert.isResizable();
            alert.showAndWait();
            return;
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
        }catch(Exception e){
            System.err.println("There was an error trying to save the project. \n");
        }
    }

    @FXML
    public void handleSave(ActionEvent event) {
        Diagram diagram = Application.getCurrentDiagram();

        if(diagram == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning: No Project");
            alert.setContentText("There is currently no project loaded or in progress.\n" +
                    "Please either start or load a project before saving.");
            alert.isResizable();
            alert.showAndWait();
            return;
        }

        if(diagram.getSaveLocation() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning: Project not saved yet");
            alert.setContentText("The has not been saved to a location yet.\n" +
                    "Please use \"Save As...\" first before using \"Save\".");
            alert.isResizable();
            alert.showAndWait();
            return;
        }

        try{
            File file = new File(diagram.getSaveLocation());
            SaveLoadSystem.saveProjectGUI(diagram, file);
        }catch(Exception e){
            System.err.println("There was an error saving the file. \n");
        }

    }

    @FXML
    public void handleLoad(ActionEvent event) {
        String homeFolder = System.getProperty("user.home");
        FileChooser fileChooser = new FileChooser();
        Diagram diagram;
        Window stage = hbMenuBar.getScene().getWindow();

        fileChooser.setInitialDirectory(new File(homeFolder));
        fileChooser.setTitle("Load project...");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON file", "*.json")
        );

        try{
            File file = fileChooser.showOpenDialog(stage);
            diagram = SaveLoadSystem.loadProjectGUI(file);
            Application.setCurrentDiagram(diagram);
            System.out.println("Successfully loaded project. \n");
        }catch(NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
            System.out.println("Cancelled Load. \n");
        }catch(Exception e){
            System.err.println("There was an error trying to load the project. \n");
        }
    }

    @FXML
    public void handleMinimize(ActionEvent event) {
        Window window = ((Node) event.getSource()).getScene().getWindow();
        ((Stage) window).setIconified(true);
    }

    @FXML
    public void handleMaximize(ActionEvent event) {
        Window window = ((Node) event.getSource()).getScene().getWindow();
        Stage stage = (Stage) window;
        stage.setMaximized(!stage.isMaximized());
    }

    @FXML
    public void handleClose(ActionEvent event) {
        Window window = ((Node) event.getSource()).getScene().getWindow();
        ((Stage) window).close();
    }
}

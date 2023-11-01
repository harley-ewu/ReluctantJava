package GUI;

import CLI.CommandLineInterface;
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
        FileChooser fc = new FileChooser();
        Diagram diagram = CommandLineInterface.getCurrentDiagram();
        Window stage = hbMenuBar.getScene().getWindow();

        if(diagram == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning: No Project");
            alert.setContentText("There is currently no project loaded or in progress.\n" +
                    "Please either start or load a project.");
            alert.isResizable();
            alert.showAndWait();
            return;
        }

        fc.setInitialDirectory(new File(homeFolder));
        fc.setTitle("Save project as...");
        fc.setInitialFileName(diagram.getTitle());
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON file", "*.json")
        );

        try{
            File file = fc.showSaveDialog(stage);
            SaveLoadSystem.saveAsProjectGUI(diagram, file);
        }catch(Exception e){
            System.err.println("There was an error trying to save the project.");
        }
    }

    @FXML
    public void handleSave() {
        // Handle save action
    }

    @FXML
    public void handleLoad(ActionEvent event) {
        String homeFolder = System.getProperty("user.home");
        FileChooser fc = new FileChooser();
        Diagram diagram = null;
        Window stage = hbMenuBar.getScene().getWindow();

        fc.setInitialDirectory(new File(homeFolder));
        fc.setTitle("Load project...");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON file", "*.json")
        );

        try{
            File file = fc.showOpenDialog(stage);
            diagram = SaveLoadSystem.loadProjectGUI(file);
            CommandLineInterface.setCurrentDiagram(diagram);
        }catch(Exception e){
            System.err.println("There was an error trying to load the project.");
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

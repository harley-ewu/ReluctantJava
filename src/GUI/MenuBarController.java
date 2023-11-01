package GUI;

import CLI.CommandLineInterface;
import Diagram.Diagram;
import SaveLoadSystem.SaveLoadSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
        //Diagram diagram = CommandLineInterface.getCurrentDiagram();
        Window stage = hbMenuBar.getScene().getWindow();

        Diagram diagram = new Diagram("TestGUIDiagram");
        diagram.addClass("TestClassOne");
        diagram.addClass("TestClassTwo");

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
    public void handleLoad() {
        // Handle load action
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

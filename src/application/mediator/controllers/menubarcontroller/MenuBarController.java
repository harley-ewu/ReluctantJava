package application.mediator.controllers.menubarcontroller;

import application.Application;
import application.CLI.CommandLineInterface;
import Diagram.Diagram;
import SaveLoadSystem.SaveLoadSystem;
import application.mediator.common.Mediator;
import application.mediator.common.Request;
import application.mediator.controllers.menubarcontroller.close.CloseRequest;
import application.mediator.controllers.menubarcontroller.guiLoad.GuiLoadRequest;
import application.mediator.controllers.menubarcontroller.guiSave.GuiSaveRequest;
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

    }

    @FXML
    public void handleSave(ActionEvent event) {
        Request request = new GuiSaveRequest();
        Mediator.send(request);
    }

    @FXML
    public void handleLoad(ActionEvent event) {
        Request request = new GuiLoadRequest(event, hbMenuBar);
        Mediator.send(request);
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
        Request request = new CloseRequest(event);
        Mediator.send(request);
    }
}

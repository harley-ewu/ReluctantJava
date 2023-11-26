package application.mediator.controllers.menubarcontroller;

import application.mediator.common.Mediator;
import application.mediator.common.Request;
import application.mediator.controllers.menubarcontroller.close.CloseRequest;
import application.mediator.controllers.menubarcontroller.guiLoad.GuiLoadRequest;
import application.mediator.controllers.menubarcontroller.guiSave.GuiSaveRequest;
import application.mediator.controllers.menubarcontroller.guiSaveAs.GuiSaveAsRequest;
import application.mediator.controllers.menubarcontroller.maximize.MaximizeRequest;
import application.mediator.controllers.menubarcontroller.minimize.MinimizeRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;


public class MenuBarController {
    @FXML
    HBox hbMenuBar;

    @FXML
    public void handleSaveAs(ActionEvent event) {
        Request request = new GuiSaveAsRequest(event, hbMenuBar);
        Mediator.send(request);
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
        Request request = new MinimizeRequest(event);
        Mediator.send(request);
    }

    @FXML
    public void handleMaximize(ActionEvent event) {
        Request request = new MaximizeRequest(event);
        Mediator.send(request);
    }

    @FXML
    public void handleClose(ActionEvent event) {
        Request request = new CloseRequest(event);
        Mediator.send(request);
    }
}

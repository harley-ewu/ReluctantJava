package application.mediator.controllers.menubarcontroller.close;

import application.Application;
import application.GUI.GraphicalUserInterface;
import application.mediator.common.IHandler;
import application.mediator.common.Request;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.stage.Window;

import javafx.event.ActionEvent;

public class CloseRequestHandler implements IHandler {
    public Void handle(Request request){
        if(Application.getCurrentDiagram() != null){
            GraphicalUserInterface.showSavePrompt();
        }

        CloseRequest newRequest = (CloseRequest) request;
        ActionEvent event = newRequest.getEvent();

        Window window = ((Node) event.getSource()).getScene().getWindow();
        ((Stage) window).close();
        return null;
    }
}

package application.mediator.controllers.menubarcontroller.maximize;

import application.mediator.common.IHandler;
import application.mediator.common.Request;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MaximizeRequestHandler implements IHandler {
    public Void handle(Request request){
        MaximizeRequest newRequest = (MaximizeRequest) request;
        Event event = newRequest.getEvent();

        Window window = ((Node) event.getSource()).getScene().getWindow();
        Stage stage = (Stage) window;
        stage.setMaximized(!stage.isMaximized());

        return null;
    }
}

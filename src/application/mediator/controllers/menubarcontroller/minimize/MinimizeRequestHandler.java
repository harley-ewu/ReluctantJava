package application.mediator.controllers.menubarcontroller.minimize;

import application.mediator.common.IHandler;
import application.mediator.common.Request;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MinimizeRequestHandler implements IHandler {
    public Void handle(Request request){
        MinimizeRequest newRequest = (MinimizeRequest) request;
        Event event = newRequest.getEvent();

        Window window = ((Node) event.getSource()).getScene().getWindow();
        ((Stage) window).setIconified(true);

        return null;
    }
}

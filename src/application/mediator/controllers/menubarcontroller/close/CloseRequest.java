package application.mediator.controllers.menubarcontroller.close;

import application.mediator.common.Request;

import javafx.event.ActionEvent;

public class CloseRequest extends Request {

    private final ActionEvent event;
    public CloseRequest(ActionEvent event) {
        super("CloseRequest");
        this.event = event;
    }

    public ActionEvent getEvent(){
        return this.event;
    }
}

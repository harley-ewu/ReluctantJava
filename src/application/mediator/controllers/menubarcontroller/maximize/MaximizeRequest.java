package application.mediator.controllers.menubarcontroller.maximize;

import application.mediator.common.Request;
import javafx.event.Event;

public class MaximizeRequest extends Request {
    private Event event;
    public MaximizeRequest(Event event){
        super("MaximizeRequest");
        this.event = event;
    }

    public Event getEvent(){
        return this.event;
    }
}

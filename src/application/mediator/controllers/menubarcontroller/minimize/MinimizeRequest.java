package application.mediator.controllers.menubarcontroller.minimize;

import application.mediator.common.Request;
import javafx.event.Event;

public class MinimizeRequest extends Request {
    private Event event;

    public MinimizeRequest(Event event){
        super("MinimizeRequest");
        this.event = event;
    }

    public Event getEvent(){
        return this.event;
    }
}

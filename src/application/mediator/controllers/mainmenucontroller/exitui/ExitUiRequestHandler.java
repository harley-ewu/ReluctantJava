package application.mediator.controllers.mainmenucontroller.exitui;

import application.mediator.common.IHandler;
import application.mediator.common.Request;

public class ExitUiRequestHandler implements IHandler {
    public Void handle(Request request){
        System.exit(0);
        return null;
    }
}

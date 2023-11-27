package application.mediator.controllers.diagramprojectcontroller.exit;

import application.mediator.common.IHandler;
import application.mediator.common.Request;

public class ExitRequestHandler implements IHandler {
    public Void handle(Request request){
        System.out.println("Exiting...");
        return null;
    }
}

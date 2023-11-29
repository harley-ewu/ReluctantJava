package application.mediator.controllers.diagramprojectcontroller.exit;

import application.Application;
import application.GUI.GraphicalUserInterface;
import application.mediator.common.IHandler;
import application.mediator.common.Request;

public class ExitRequestHandler implements IHandler {
    public Void handle(Request request){
        if(Application.getCurrentDiagram() != null){
            GraphicalUserInterface.showSavePrompt();
        }

        System.out.println("Exiting...");
        return null;
    }
}

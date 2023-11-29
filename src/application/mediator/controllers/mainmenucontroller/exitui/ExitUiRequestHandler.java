package application.mediator.controllers.mainmenucontroller.exitui;

import application.Application;
import application.GUI.GraphicalUserInterface;
import application.mediator.common.IHandler;
import application.mediator.common.Request;

public class ExitUiRequestHandler implements IHandler {
    public Void handle(Request request){
        if(Application.getCurrentDiagram() != null && Application.getCurrentDiagram().getSaveLocation() == null){
            GraphicalUserInterface.showSavePrompt();
        }

        System.exit(0);
        return null;
    }
}

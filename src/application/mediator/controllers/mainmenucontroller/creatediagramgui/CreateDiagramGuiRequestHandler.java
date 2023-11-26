package application.mediator.controllers.mainmenucontroller.creatediagramgui;

import application.GUI.GraphicalUserInterface;
import application.mediator.common.IHandler;
import application.mediator.common.Request;

public class CreateDiagramGuiRequestHandler implements IHandler {
    public Void handle(Request request){
        if(GraphicalUserInterface.getCurrentDiagram() != null) {
            GraphicalUserInterface.showSavePrompt();
        }
        else{
            GraphicalUserInterface.openPopup();
            System.out.println("Diagram created");
        }
        return null;
    }
}

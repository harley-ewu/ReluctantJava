package application.mediator.controllers.mainmenucontroller.viewdiagramgui;

import application.Application;
import application.GUI.GraphicalUserInterface;
import application.mediator.common.IHandler;
import application.mediator.common.Request;

public class ViewDiagramGuiRequestHandler implements IHandler {
    public Void handle(Request request) {
        if(Application.getCurrentDiagram() != null) {
            try {
                GraphicalUserInterface.openDiagram(Application.getCurrentDiagram());
                System.out.println("Editing/Viewing Diagram");
            }
            catch (Exception e){
                System.out.println("Something broke in ViewDiagramGuiRequestHandler");
            }
        }else{
            GraphicalUserInterface.noDiagramLoadedAlert();
        }
        return null;
    }
}

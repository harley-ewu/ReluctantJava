package application.mediator.controllers.mainmenucontroller.helpmaingui;

import application.GUI.GraphicalUserInterface;
import application.mediator.common.IHandler;
import application.mediator.common.Request;

public class HelpMainGuiRequestHandler implements IHandler {
    public Void handle(Request request){
        GraphicalUserInterface.displayHelpPopup();
        System.out.println("Help Menu");

        return null;
    }
}

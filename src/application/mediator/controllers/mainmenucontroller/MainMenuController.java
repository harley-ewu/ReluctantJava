package application.mediator.controllers.mainmenucontroller;

import application.Application;
import application.CLI.CommandLineInterface;
import application.GUI.GraphicalUserInterface;
import application.mediator.common.Mediator;
import application.mediator.common.Request;
import application.mediator.controllers.mainmenucontroller.creatediagramgui.CreateDiagramGuiRequest;
import application.mediator.controllers.mainmenucontroller.exitui.ExitUiRequest;
import application.mediator.controllers.mainmenucontroller.helpmaingui.HelpMainGuiRequest;
import application.mediator.controllers.mainmenucontroller.viewdiagramgui.ViewDiagramGuiRequest;

public class MainMenuController {

    public void createDiagramGUI() throws Exception{
        Request request = new CreateDiagramGuiRequest();
        Mediator.send(request);
    }

    public void viewDiagramGUI() throws Exception{
        Request request = new ViewDiagramGuiRequest();
        Mediator.send(request);
    }

    public void helpMainGUI(){
        Request request = new HelpMainGuiRequest();
        Mediator.send(request);
    }

    public void exitUI(){
        Request request = new ExitUiRequest();
        Mediator.send(request);
    }
}

package application.startup;

import application.mediator.common.Mediator;
import application.mediator.controllers.diagramprojectcontroller.addclass.*;
import application.mediator.controllers.diagramprojectcontroller.addrelationship.*;
import application.mediator.controllers.diagramprojectcontroller.exit.*;
import application.mediator.controllers.diagramprojectcontroller.loadfile.LoadFileRequest;
import application.mediator.controllers.diagramprojectcontroller.loadfile.LoadFileRequestHandler;
import application.mediator.controllers.diagramprojectcontroller.loadfile.LoadFileRequestValidator;
import application.mediator.controllers.diagramprojectcontroller.saveasfile.SaveAsFileRequest;
import application.mediator.controllers.diagramprojectcontroller.saveasfile.SaveAsFileRequestHandler;
import application.mediator.controllers.diagramprojectcontroller.saveasfile.SaveAsFileRequestValidator;
import application.mediator.controllers.diagramprojectcontroller.savefile.SaveFileRequest;
import application.mediator.controllers.diagramprojectcontroller.savefile.SaveFileRequestHandler;
import application.mediator.controllers.diagramprojectcontroller.savefile.SaveFileRequestValidator;
import application.mediator.controllers.mainmenucontroller.creatediagramgui.*;
import application.mediator.controllers.mainmenucontroller.exitui.*;
import application.mediator.controllers.mainmenucontroller.helpmaingui.*;
import application.mediator.controllers.mainmenucontroller.viewdiagramgui.*;
import application.mediator.controllers.menubarcontroller.close.*;
import application.mediator.controllers.menubarcontroller.guiLoad.*;
import application.mediator.controllers.menubarcontroller.guiSave.*;
import application.mediator.controllers.menubarcontroller.guiSaveAs.*;
import application.mediator.controllers.menubarcontroller.maximize.*;
import application.mediator.controllers.menubarcontroller.minimize.*;
import application.mediator.controllers.updateviewcontroller.initguidiagramview.*;
import application.mediator.controllers.updateviewcontroller.updateaddclass.*;
import application.mediator.controllers.updateviewcontroller.updateaddrelationship.*;


public class Startup {
    public static void init(){
        registerServices(); //Only call in this method for now, but init may grow later if there are other init actions we may need
    }

    private static void registerServices(){
        Mediator.registerService(new InitGuiDiagramViewRequestValidator(), new InitGuiDiagramViewRequestHandler(), new InitGuiDiagramViewRequest(null));
        Mediator.registerService(new UpdateAddClassRequestValidator(), new UpdateAddClassRequestHandler(), new UpdateAddClassRequest(null, null));
        Mediator.registerService(new UpdateAddRelationshipRequestValidator(), new UpdateAddRelationshipRequestHandler(), new UpdateAddRelationshipRequest(null, null));

        Mediator.registerService(new CloseRequestValidator(), new CloseRequestHandler(), new CloseRequest(null));
        Mediator.registerService(new GuiLoadRequestValidator(), new GuiLoadRequestHandler(), new GuiLoadRequest(null, null));
        Mediator.registerService(new GuiSaveRequestValidator(), new GuiSaveRequestHandler(), new GuiSaveRequest());
        Mediator.registerService(new GuiSaveAsRequestValidator(), new GuiSaveAsRequestHandler(), new GuiSaveAsRequest(null, null));
        Mediator.registerService(new MaximizeRequestValidator(), new MaximizeRequestHandler(), new MaximizeRequest(null));
        Mediator.registerService(new MinimizeRequestValidator(), new MinimizeRequestHandler(), new MinimizeRequest(null));

        Mediator.registerService(new CreateDiagramGuiRequestValidator(), new CreateDiagramGuiRequestHandler(), new CreateDiagramGuiRequest());
        Mediator.registerService(new ExitUiRequestValidator(), new ExitUiRequestHandler(), new ExitUiRequest());
        Mediator.registerService(new HelpMainGuiRequestValidator(), new HelpMainGuiRequestHandler(), new HelpMainGuiRequest());
        Mediator.registerService(new ViewDiagramGuiRequestValidator(), new ViewDiagramGuiRequestHandler(), new ViewDiagramGuiRequest());

        Mediator.registerService(new AddClassRequestValidator(), new AddClassRequestHandler(), new AddClassRequest());
        Mediator.registerService(new AddRelationshipRequestValidator(), new AddRelationshipRequestHandler(), new AddRelationshipRequest());
        Mediator.registerService(new ExitRequestValidator(), new ExitRequestHandler(), new ExitRequest());
        Mediator.registerService(new LoadFileRequestValidator(), new LoadFileRequestHandler(), new LoadFileRequest(null));
        Mediator.registerService(new SaveAsFileRequestValidator(), new SaveAsFileRequestHandler(), new SaveAsFileRequest(null));
        Mediator.registerService(new SaveFileRequestValidator(), new SaveFileRequestHandler(), new SaveFileRequest());
    }
}

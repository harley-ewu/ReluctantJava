package application.startup;

import application.mediator.common.Mediator;
import application.mediator.common.MediatorSingletonHandler;
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
        initMediator();
        registerServices();
    }

    private static void registerServices(){
        final Mediator mediator = new MediatorSingletonHandler().getInstance();

        mediator.registerService(new InitGuiDiagramViewRequestValidator(), new InitGuiDiagramViewRequestHandler(), new InitGuiDiagramViewRequest(null));
        mediator.registerService(new UpdateAddClassRequestValidator(), new UpdateAddClassRequestHandler(), new UpdateAddClassRequest(null, null));
        mediator.registerService(new UpdateAddRelationshipRequestValidator(), new UpdateAddRelationshipRequestHandler(), new UpdateAddRelationshipRequest(null, null));

        mediator.registerService(new CloseRequestValidator(), new CloseRequestHandler(), new CloseRequest(null));
        mediator.registerService(new GuiLoadRequestValidator(), new GuiLoadRequestHandler(), new GuiLoadRequest(null, null));
        mediator.registerService(new GuiSaveRequestValidator(), new GuiSaveRequestHandler(), new GuiSaveRequest());
        mediator.registerService(new GuiSaveAsRequestValidator(), new GuiSaveAsRequestHandler(), new GuiSaveAsRequest(null, null));
        mediator.registerService(new MaximizeRequestValidator(), new MaximizeRequestHandler(), new MaximizeRequest(null));
        mediator.registerService(new MinimizeRequestValidator(), new MinimizeRequestHandler(), new MinimizeRequest(null));

        mediator.registerService(new CreateDiagramGuiRequestValidator(), new CreateDiagramGuiRequestHandler(), new CreateDiagramGuiRequest());
        mediator.registerService(new ExitUiRequestValidator(), new ExitUiRequestHandler(), new ExitUiRequest());
        mediator.registerService(new HelpMainGuiRequestValidator(), new HelpMainGuiRequestHandler(), new HelpMainGuiRequest());
        mediator.registerService(new ViewDiagramGuiRequestValidator(), new ViewDiagramGuiRequestHandler(), new ViewDiagramGuiRequest());

        mediator.registerService(new AddClassRequestValidator(), new AddClassRequestHandler(), new AddClassRequest());
        mediator.registerService(new AddRelationshipRequestValidator(), new AddRelationshipRequestHandler(), new AddRelationshipRequest());
        mediator.registerService(new ExitRequestValidator(), new ExitRequestHandler(), new ExitRequest());
        mediator.registerService(new LoadFileRequestValidator(), new LoadFileRequestHandler(), new LoadFileRequest(null));
        mediator.registerService(new SaveAsFileRequestValidator(), new SaveAsFileRequestHandler(), new SaveAsFileRequest(null));
        mediator.registerService(new SaveFileRequestValidator(), new SaveFileRequestHandler(), new SaveFileRequest());
    }

    private static void initMediator(){
        MediatorSingletonHandler handler = new MediatorSingletonHandler();
        handler.initMediator();
    }
}

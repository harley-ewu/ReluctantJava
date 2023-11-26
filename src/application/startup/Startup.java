package application.startup;

import application.mediator.common.Mediator;
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
    }
}

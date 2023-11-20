package application.startup;

import application.mediator.common.Mediator;
import application.mediator.controllers.updateviewcontroller.initguidiagramview.InitGuiDiagramViewRequest;
import application.mediator.controllers.updateviewcontroller.initguidiagramview.InitGuiDiagramViewRequestHandler;
import application.mediator.controllers.updateviewcontroller.initguidiagramview.InitGuiDiagramViewRequestValidator;
import application.mediator.controllers.updateviewcontroller.updateaddclass.UpdateAddClassRequest;
import application.mediator.controllers.updateviewcontroller.updateaddclass.UpdateAddClassRequestHandler;
import application.mediator.controllers.updateviewcontroller.updateaddclass.UpdateAddClassRequestValidator;
import application.mediator.controllers.updateviewcontroller.updateaddrelationship.UpdateAddRelationshipRequest;
import application.mediator.controllers.updateviewcontroller.updateaddrelationship.UpdateAddRelationshipRequestHandler;
import application.mediator.controllers.updateviewcontroller.updateaddrelationship.UpdateAddRelationshipRequestValidator;


public class Startup {
    public static void init(){
        registerServices(); //Only call in this method for now, but init may grow later if there are other init actions we may need
    }

    private static void registerServices(){
        Mediator.registerService(new InitGuiDiagramViewRequestValidator(), new InitGuiDiagramViewRequestHandler(), new InitGuiDiagramViewRequest(null));
        Mediator.registerService(new UpdateAddClassRequestValidator(), new UpdateAddClassRequestHandler(), new UpdateAddClassRequest(null, null));
        Mediator.registerService(new UpdateAddRelationshipRequestValidator(), new UpdateAddRelationshipRequestHandler(), new UpdateAddRelationshipRequest(null, null));
    }
}

package application.mediator.controllers.diagramprojectcontroller.addrelationship;

import application.mediator.common.IValidator;
import application.mediator.common.Request;

public class AddRelationshipRequestValidator implements IValidator {
    public void validate(Request request){
        if(!(request instanceof AddRelationshipRequest)){
            throw new IllegalArgumentException("Request passed into AddRelationshipRequestValidator is not of type AddRelationshipRequest");
        }
    }
}

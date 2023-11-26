package application.mediator.controllers.diagramprojectcontroller.addclass;

import application.mediator.common.IValidator;
import application.mediator.common.Request;

public class AddClassRequestValidator implements IValidator {
    public void validate(Request request){
        if(!(request instanceof AddClassRequest)){
            throw new IllegalArgumentException("Request passed into AddClassValidator is not of type AddClassRequest");
        }
    }
}

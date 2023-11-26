package application.mediator.controllers.diagramprojectcontroller.exit;

import application.mediator.common.IValidator;
import application.mediator.common.Request;

public class ExitRequestValidator implements IValidator {
    public void validate(Request request){
        if(!(request instanceof ExitRequest)){
            throw new IllegalArgumentException("Request passed into ExitRequestValidator is not of type ExitRequest");
        }
    }
}

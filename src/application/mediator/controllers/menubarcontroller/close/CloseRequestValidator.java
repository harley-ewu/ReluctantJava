package application.mediator.controllers.menubarcontroller.close;

import application.mediator.common.IValidator;
import application.mediator.common.Request;

public class CloseRequestValidator implements IValidator {
    public void validate(Request request){
        if(!(request instanceof CloseRequest)){
            throw new IllegalArgumentException("Request passed into CloseRequestValidator is not CloseRequest");
        }
    }
}

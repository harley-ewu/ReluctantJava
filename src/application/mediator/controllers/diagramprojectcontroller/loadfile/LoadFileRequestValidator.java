package application.mediator.controllers.diagramprojectcontroller.loadfile;

import application.mediator.common.IValidator;
import application.mediator.common.Request;

public class LoadFileRequestValidator implements IValidator {
    public void validate(Request request){
        if(!(request instanceof LoadFileRequest)){
            throw new IllegalArgumentException("Request passed into LoadFileRequestValidator is not of type LoadValidatorRequest");
        }
    }
}

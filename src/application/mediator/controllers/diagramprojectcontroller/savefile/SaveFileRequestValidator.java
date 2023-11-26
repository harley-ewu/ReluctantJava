package application.mediator.controllers.diagramprojectcontroller.savefile;

import application.mediator.common.IValidator;
import application.mediator.common.Request;

public class SaveFileRequestValidator implements IValidator {
    public void validate(Request request){
        if(!(request instanceof SaveFileRequest)){
            throw new IllegalArgumentException("Request passed into SaveFileRequestValidator is not of type SaveFileRequest");
        }
    }
}

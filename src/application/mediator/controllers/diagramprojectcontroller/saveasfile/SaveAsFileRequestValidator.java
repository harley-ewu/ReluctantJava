package application.mediator.controllers.diagramprojectcontroller.saveasfile;

import application.mediator.common.IValidator;
import application.mediator.common.Request;

import java.util.Objects;

public class SaveAsFileRequestValidator implements IValidator {
    public void validate(Request request){
        if(!(request instanceof SaveAsFileRequest newRequest)){
            throw new IllegalArgumentException("Request passed into SaveAsFileRequestValidator is not of type SaveAsFileRequest");
        }

        Objects.requireNonNull(newRequest.getStage(), "Null window passed into SaveAsFileRequestValidator");
    }
}

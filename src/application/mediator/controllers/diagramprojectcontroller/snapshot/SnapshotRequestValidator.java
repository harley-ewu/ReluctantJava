package application.mediator.controllers.diagramprojectcontroller.snapshot;

import application.mediator.common.IValidator;
import application.mediator.common.Request;

public class SnapshotRequestValidator implements IValidator {
    public void validate(Request request){
        if(!(request instanceof SnapshotRequest)){
            throw new IllegalArgumentException("Request passed into SnapshotRequestValidator is not of type SnapshotRequest");
        }
    }
}

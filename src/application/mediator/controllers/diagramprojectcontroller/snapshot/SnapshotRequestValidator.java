package application.mediator.controllers.diagramprojectcontroller.snapshot;

import application.mediator.common.IValidator;
import application.mediator.common.Request;

public class SnapshotRequestValidator implements IValidator {
    public void validate(Request request){
        if(!(request instanceof SnapshotRequest newRequest)){
            throw new IllegalArgumentException("Request passed into SnapshotRequestValidator is not of type SnapshotRequest");
        }

        if(newRequest.getContentRoot() == null){
            throw new IllegalArgumentException("Null contentRoot passed into SnapshotRequestValidator");
        }
    }
}

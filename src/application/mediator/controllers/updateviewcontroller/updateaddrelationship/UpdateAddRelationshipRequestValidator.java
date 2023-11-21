package application.mediator.controllers.updateviewcontroller.updateaddrelationship;

import application.mediator.common.IValidator;
import application.mediator.common.Request;

public class UpdateAddRelationshipRequestValidator implements IValidator {

    @Override
    public void validate(Request request) {
        if (!(request instanceof UpdateAddRelationshipRequest updateRequest)) {
            throw new IllegalArgumentException("Request must be an UpdateAddRelationshipRequest");
        }

        if (updateRequest.getView() == null) {
            throw new IllegalArgumentException("Null GUIDiagramProject passed into UpdateAddRelationshipRequestValidator");
        }

        if (updateRequest.getRelationship() == null) {
            throw new IllegalArgumentException("Null Relationship passed into UpdateAddRelationshipRequestValidator");
        }
    }
}

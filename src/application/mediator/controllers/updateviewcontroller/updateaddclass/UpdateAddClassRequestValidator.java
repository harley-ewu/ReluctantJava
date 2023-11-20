package application.mediator.controllers.updateviewcontroller.updateaddclass;

import application.mediator.common.IValidator;
import application.mediator.common.Request;

public class UpdateAddClassRequestValidator implements IValidator {

    @Override
    public void validate(Request request) {
        if (!(request instanceof UpdateAddClassRequest updateRequest)) {
            throw new IllegalArgumentException("Request must be an UpdateAddClassRequest");
        }

        if (updateRequest.getView() == null) {
            throw new IllegalArgumentException("Null GuiDiagramProject passed into UpdateAddClassRequestValidator");
        }

        if (updateRequest.getUmlClass() == null) {
            throw new IllegalArgumentException("Null UML Class passed into UpdateAddClassRequestValidator");
        }
    }
}

package application.mediator.controllers.menubarcontroller.maximize;

import application.mediator.common.IValidator;
import application.mediator.common.Request;
import application.mediator.controllers.menubarcontroller.minimize.MinimizeRequest;

public class MaximizeRequestValidator implements IValidator {
    public void validate(Request request){
        if(!(request instanceof MaximizeRequest)){
            throw new IllegalArgumentException("Request passed into MaximizeRequestValidator is not of type MaximizeRequest");
        }
    }
}

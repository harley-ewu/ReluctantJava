package application.mediator.controllers.menubarcontroller.minimize;

import application.mediator.common.IValidator;
import application.mediator.common.Request;
import application.mediator.controllers.menubarcontroller.maximize.MaximizeRequest;

public class MinimizeRequestValidator implements IValidator {
    public void validate(Request request){
        if(!(request instanceof MinimizeRequest)){
            throw new IllegalArgumentException("Request passed into MinimizeRequestValidator is not of type MinimizeRequest");
        }
    }
}

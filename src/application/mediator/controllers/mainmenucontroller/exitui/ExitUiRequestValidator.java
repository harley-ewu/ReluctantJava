package application.mediator.controllers.mainmenucontroller.exitui;

import application.mediator.common.IValidator;
import application.mediator.common.Request;

public class ExitUiRequestValidator implements IValidator {
    public void validate(Request request){
        if(!(request instanceof ExitUiRequest)){
            throw new IllegalArgumentException("Request passed into ExitUiRequestValidator is not of type ExitUiRequest");
        }
    }
}

package application.mediator.controllers.mainmenucontroller.helpmaingui;

import application.mediator.common.IValidator;
import application.mediator.common.Request;

public class HelpMainGuiRequestValidator implements IValidator {
    public void validate(Request request){
        if(!(request instanceof HelpMainGuiRequest)){
            throw new IllegalArgumentException("Request passed into HelpMainGuiRequestValidator is not of type HelpMainGuiRequest");
        }
    }
}

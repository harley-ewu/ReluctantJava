package application.mediator.controllers.menubarcontroller.guiLoad;

import application.mediator.common.IValidator;
import application.mediator.common.Request;

public class GuiLoadRequestValidator implements IValidator {
    public void validate(Request request){
        if(!(request instanceof GuiLoadRequest)){
            throw new IllegalArgumentException("Request passed into GuiLoadRequestValidator is not a GuiLoadRequest");
        }
    }
}

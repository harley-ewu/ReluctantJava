package application.mediator.controllers.menubarcontroller.guiSaveAs;

import application.mediator.common.IValidator;
import application.mediator.common.Request;

public class GuiSaveAsRequestValidator implements IValidator {
    public void validate(Request request){
        if(!(request instanceof GuiSaveAsRequest)){
            throw new IllegalArgumentException("Request passed into GuiSaveAsRequestValidator is not a GuiSaveAsRequest");
        }
    }
}

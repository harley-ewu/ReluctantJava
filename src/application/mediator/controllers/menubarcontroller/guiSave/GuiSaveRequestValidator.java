package application.mediator.controllers.menubarcontroller.guiSave;

import application.mediator.common.IValidator;
import application.mediator.common.Request;

public class GuiSaveRequestValidator implements IValidator {
    public void validate(Request request){
        if(!(request instanceof GuiSaveRequest)){
            throw new IllegalArgumentException("Request passed into GuiLoadRequestValidator is not a GuiSaveRequest");
        }
    }
}

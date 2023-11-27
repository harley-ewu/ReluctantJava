package application.mediator.controllers.mainmenucontroller.creatediagramgui;

import application.mediator.common.IValidator;
import application.mediator.common.Request;

public class CreateDiagramGuiRequestValidator implements IValidator {
    public void validate(Request request){
        if(!(request instanceof CreateDiagramGuiRequest)){
            throw new IllegalArgumentException("Request passed into CreateDiagramGuiRequestValidator is not of type CreateDiagramGuiRequest");
        }
    }
}

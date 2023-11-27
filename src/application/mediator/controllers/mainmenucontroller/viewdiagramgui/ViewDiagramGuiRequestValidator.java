package application.mediator.controllers.mainmenucontroller.viewdiagramgui;

import application.mediator.common.IValidator;
import application.mediator.common.Request;

public class ViewDiagramGuiRequestValidator implements IValidator {
    public void validate(Request request){
        if(!(request instanceof ViewDiagramGuiRequest)){
            throw new IllegalArgumentException("Request passed into ViewDiagramGuiRequestValidator is not of type ViewDiagramGuiRequest");
        }
    }
}

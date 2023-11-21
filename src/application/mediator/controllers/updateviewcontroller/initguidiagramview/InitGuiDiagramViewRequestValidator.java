package application.mediator.controllers.updateviewcontroller.initguidiagramview;

import application.mediator.common.IValidator;
import application.mediator.common.Request;

public class InitGuiDiagramViewRequestValidator implements IValidator {

    @Override
    public void validate(Request request){
        if(!(request instanceof InitGuiDiagramViewRequest newRequest)){
            throw new IllegalArgumentException("Invalid request type passed into InitGuiDiagramViewValidator");
        }

        if(newRequest.getView() == null){
            throw new IllegalArgumentException("GuiDiagramProject provided in InitGuiDiagramViewRequest is null");
        }
    }
}

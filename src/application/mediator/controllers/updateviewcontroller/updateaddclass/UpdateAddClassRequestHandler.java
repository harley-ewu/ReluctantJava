package application.mediator.controllers.updateviewcontroller.updateaddclass;

import application.mediator.common.IHandler;
import application.mediator.common.Request;
import application.GUI.GUIDiagramProject;
import Class.Class;

public class UpdateAddClassRequestHandler implements IHandler<Void> {

    @Override
    public Void handle(Request request) {
        if (request instanceof UpdateAddClassRequest) {
            updateAddClass(
                    ((UpdateAddClassRequest) request).getView(),
                    ((UpdateAddClassRequest) request).getUmlClass()
            );
        }
        return null;
    }

    private void updateAddClass(GUIDiagramProject view, Class umlClass) {
        view.setWasAddedToTrue(); //sets the boolean to true in addSingleClassAsset
        view.addSingleClassAsset(umlClass);

    }
}

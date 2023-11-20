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
        view.getContentPane().getChildren().removeAll(view.getClassPanes());
        view.getClassPanes().clear();
        view.getClassList().add(umlClass);
        view.getClassAssets().clear();
        view.addClassAssets(view.getClassList());
        view.addClassPanes();
        view.addClassPanesToPaneWindow();
    }
}

package application.mediator.controllers.updateviewcontroller.initguidiagramview;

import application.mediator.common.Request;
import application.GUI.GUIDiagramProject;

public class InitGuiDiagramViewRequest extends Request {
    private final GUIDiagramProject view;

    public InitGuiDiagramViewRequest(GUIDiagramProject view) {
        super("InitGuiDiagramViewRequest");
        this.view = view;
    }

    public GUIDiagramProject getView() {
        return this.view;
    }
}

package application.mediator.controllers.updateviewcontroller.updateaddclass;

import application.mediator.common.Request;
import application.GUI.GUIDiagramProject;
import Class.Class;

public class UpdateAddClassRequest extends Request {
    private final GUIDiagramProject view;
    private final Class umlClass;

    public UpdateAddClassRequest(GUIDiagramProject view, Class umlClass) {
        super("UpdateAddClassRequest");
        this.view = view;
        this.umlClass = umlClass;
    }

    public GUIDiagramProject getView() {
        return this.view;
    }

    public Class getUmlClass() {
        return this.umlClass;
    }
}

package application.mediator.controllers.updateviewcontroller.updateaddrelationship;

import Relationships.Relationship;
import application.mediator.common.Request;
import application.GUI.GUIDiagramProject;

public class UpdateAddRelationshipRequest extends Request {
    private final GUIDiagramProject view;
    private final Relationship relationship;

    public UpdateAddRelationshipRequest(GUIDiagramProject view, Relationship relationship) {
        super("UpdateAddRelationshipRequest");
        this.view = view;
        this.relationship = relationship;
    }

    public GUIDiagramProject getView() {
        return this.view;
    }

    public Relationship getRelationship() {
        return this.relationship;
    }
}

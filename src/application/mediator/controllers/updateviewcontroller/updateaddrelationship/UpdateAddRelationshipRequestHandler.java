package application.mediator.controllers.updateviewcontroller.updateaddrelationship;

import Relationships.Relationship;
import application.mediator.common.IHandler;
import application.mediator.common.Request;
import application.GUI.GUIDiagramProject;

public class UpdateAddRelationshipRequestHandler implements IHandler<Void> {

    @Override
    public Void handle(Request request) {
        if (request instanceof UpdateAddRelationshipRequest) {
            updateAddRelationship(
                    ((UpdateAddRelationshipRequest) request).getView(),
                    ((UpdateAddRelationshipRequest) request).getRelationship()
            );
        }
        return null;
    }

    private void updateAddRelationship(GUIDiagramProject view, Relationship relationship) {
        view.getContentPane().getChildren().removeAll(view.getRelationshipLines());
        view.getRelationshipLines().clear();
        view.getRelationshipList().add(relationship);
        view.getRelationshipAssets().clear();
        view.addRelationshipAsset(view.getRelationshipList());
        view.addRelationshipPanes();
        view.addRelationshipLinesToPaneWindow();
    }
}

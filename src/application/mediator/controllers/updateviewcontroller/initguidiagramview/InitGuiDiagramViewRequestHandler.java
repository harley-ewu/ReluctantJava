package application.mediator.controllers.updateviewcontroller.initguidiagramview;

import Relationships.Relationship;
import Class.Class;
import application.Application;
import application.GUI.GUIDiagramProject;
import application.mediator.common.IHandler;
import application.mediator.common.Request;

import java.util.HashMap;

public class InitGuiDiagramViewRequestHandler implements IHandler {
    public Void handle(Request request) {
        InitGuiDiagramViewRequest newRequest = (InitGuiDiagramViewRequest) request;
        initView(newRequest.getView());

        return null;
    }

    private static void initView(GUIDiagramProject view) {
        HashMap<String, Class> diagramClasses = Application.getCurrentDiagram().getClassList();
        HashMap<String, Relationship> relationshipClasses = Application.getCurrentDiagram().getRelationshipList();

        view.getContentPane().getChildren().removeAll(view.getClassPanes());
        view.getContentPane().getChildren().removeAll(view.getRelationshipLines());
        view.getClassPanes().clear();
        view.getRelationshipLines().clear();

        view.getClassList().clear();
        view.getClassList().addAll(diagramClasses.values());

        view.addClassAssets();

        if (!view.getHasMoved()) {
            view.onInitClassPaneCoordinates(); //if hasMoved has been set to true within the diagram, do not execute -- need to save this boolean in json file
        }
        view.addClassPanes();
        view.addClassPanesToPaneWindow();

        view.getRelationshipList().clear();
        view.getRelationshipList().addAll(relationshipClasses.values());

        view.addRelationshipAsset(view.getRelationshipList());
        view.addRelationshipPanes();
        view.addRelationshipLinesToPaneWindow();
    }
}

package GUI;

import CLI.CommandLineInterface;
import Relationships.Relationship;
import Class.Class;

import java.util.HashMap;

public class UpdateViewController {

    private UpdateViewController(){}

    public static void initView(GUIDiagramProject view){
        view.getContentPane().getChildren().clear();
        view.getClassPanes().clear();
        view.getRelationshipPanes().clear();

        HashMap<String, Class> diagramClasses = CommandLineInterface.getCurrentDiagram().getClassList();
        view.getClassList().clear();
        view.getClassList().addAll(diagramClasses.values());

        view.addClassAssets(view.getClassList());
        view.addClassPanes();
        view.addClassPanesToPaneWindow();

        HashMap<String, Relationship> relationshipClasses = CommandLineInterface.getCurrentDiagram().getRelationshipList();
        view.getRelationshipList().clear();
        view.getRelationshipList().addAll(relationshipClasses.values());

        view.addRelationshipAsset(view.getRelationshipList());
        view.addRelationshipPanes();
        view.addRelationshipPanesToPaneWindow();
    }

    public static void updateAddClass(GUIDiagramProject view, Class umlClass){
        view.getContentPane().getChildren().removeAll(view.getClassPanes());
        view.getClassPanes().clear();
        view.getClassList().add(umlClass);
        view.getClassAssets().clear();
        view.addClassAssets(view.getClassList());
        view.addClassPanes();
        view.addClassPanesToPaneWindow();
    }

    public static void updateRemoveClass(){

    }

    public static void updateAddRelationship(GUIDiagramProject view, Relationship relationship){
        view.getContentPane().getChildren().removeAll(view.getRelationshipPanes());
        view.getRelationshipPanes().clear();
        view.getRelationshipList().add(relationship);
        view.getRelationshipAssets().clear();
        view.addRelationshipAsset(view.getRelationshipList());
        view.addRelationshipPanes();
        view.addRelationshipPanesToPaneWindow();
    }

    public static void updateRemoveRelationship(){

    }
}

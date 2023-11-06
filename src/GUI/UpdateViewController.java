package GUI;

import CLI.CommandLineInterface;
import Relationships.Relationship;
import Class.Class;

import java.util.HashMap;

public class UpdateViewController {

    private UpdateViewController(){}

    public static void initView(GUIDiagramProject view){
        HashMap<String, Class> diagramClasses = CommandLineInterface.getCurrentDiagram().getClassList();
        view.getClassList().clear();
        view.getClassList().addAll(diagramClasses.values());

        view.addClassAssets(view.getClassList());
        view.addClassPanes();
        view.addClassPanesToPaneWindow();

        HashMap<String, Relationship> relationshipClasses = CommandLineInterface.getCurrentDiagram().getRelationshipList();
        view.getRelationshipList().clear();
        view.getRelationshipList().addAll(relationshipClasses.values());
    }

    public static void updateAddClass(GUIDiagramProject view, Class umlClass){
        view.getClassList().add(umlClass);
        view.getClassAssets().clear();
        view.addClassAssets(view.getClassList());
        view.addClassPanes();
        view.addClassPanesToPaneWindow();
    }

    public static void updateRemoveClass(){

    }

    public static void updateAddRelationship(){

    }

    public static void updateRemoveRelationship(){

    }
}

package application.mediator.controllers.updateviewcontroller;

import Relationships.Relationship;
import Class.Class;
import application.GUI.GUIDiagramProject;
import application.mediator.common.Mediator;
import application.mediator.common.Request;
import application.mediator.controllers.updateviewcontroller.initguidiagramview.InitGuiDiagramViewRequest;
import application.mediator.controllers.updateviewcontroller.updateaddclass.UpdateAddClassRequest;
import application.mediator.controllers.updateviewcontroller.updateaddrelationship.UpdateAddRelationshipRequest;

public class UpdateViewController {

    private UpdateViewController(){}

    public static void initView(application.GUI.GUIDiagramProject view){
        Request request = new InitGuiDiagramViewRequest(view);
        Mediator.send(request);
    }

    public static void updateAddClass(GUIDiagramProject view, Class umlClass){
        Request request = new UpdateAddClassRequest(view, umlClass);
        Mediator.send(request);
    }

    public static void updateRemoveClass(){

    }

    public static void updateAddRelationship(GUIDiagramProject view, Relationship relationship){
        Request request = new UpdateAddRelationshipRequest(view, relationship);
        Mediator.send(request);
    }

    public static void updateRemoveRelationship(){

    }
}

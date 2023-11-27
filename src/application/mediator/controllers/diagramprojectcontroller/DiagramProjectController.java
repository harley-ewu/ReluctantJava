package application.mediator.controllers.diagramprojectcontroller;


import application.Application;
import Class.Class;
import Diagram.Diagram;
import Relationships.Relationship;
import SaveLoadSystem.SaveLoadSystem;
import application.GUI.GUIDiagramProject;
import application.GUI.GraphicalUserInterface;
import application.mediator.common.Mediator;
import application.mediator.common.Request;
import application.mediator.controllers.diagramprojectcontroller.addclass.AddClassRequest;
import application.mediator.controllers.diagramprojectcontroller.addrelationship.AddRelationshipRequest;
import application.mediator.controllers.diagramprojectcontroller.exit.ExitRequest;
import application.mediator.controllers.diagramprojectcontroller.loadfile.LoadFileRequest;
import application.mediator.controllers.diagramprojectcontroller.saveasfile.SaveAsFileRequest;
import application.mediator.controllers.diagramprojectcontroller.savefile.SaveFileRequest;
import application.mediator.controllers.updateviewcontroller.UpdateViewController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;


public class DiagramProjectController {

    /**
     * Description: Opens the "Save As" from the project view so that the user
     * can save their work to a specified folder.
     * @param stage - Takes the Stage object of the project view.
     */
    public static void saveAsFile(Stage stage) {
        Request request = new SaveAsFileRequest(stage);
        Mediator.send(request);
    }

    /**
     * Description: Allows the user to save new work to an existing project
     * from the project view.
     */
    public static void saveFile() {
        Request request = new SaveFileRequest();
        Mediator.send(request);
    }

    /**
     * Description: Allows the user to load an existing project from their computer
     * from the project view.
     * @param stage - Takes the Stage object from the project view.
     */
    public static void loadFile(Stage stage) {
        Request request = new LoadFileRequest(stage);
        Mediator.send(request);
    }

    public static void exit() {
        Request request = new ExitRequest();
        Mediator.send(request);
    }

    /**
     * Description: Allows the user to add a class to their project by selecting
     * "Add Class" from the menu "Add" on the menu bar.
     */
    public static void addClass() {
        Request request = new AddClassRequest();
        Mediator.send(request);
    }

    /**
     * Description: Allows a user to add a relationship to the project by selecting
     * "Add Relationship" from the menu "Add" on the menu bar.
     */
    public static void addRelationship() {
        Request request = new AddRelationshipRequest();
        Mediator.send(request);
    }

    public static void editClass() {
        System.out.println("editing class...");
    }

}

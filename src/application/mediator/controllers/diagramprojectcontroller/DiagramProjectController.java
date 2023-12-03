package application.mediator.controllers.diagramprojectcontroller;

import application.mediator.common.Mediator;
import application.mediator.common.MediatorSingletonHandler;
import application.mediator.common.Request;
import application.mediator.controllers.diagramprojectcontroller.addclass.AddClassRequest;
import application.mediator.controllers.diagramprojectcontroller.addrelationship.AddRelationshipRequest;
import application.mediator.controllers.diagramprojectcontroller.exit.ExitRequest;
import application.mediator.controllers.diagramprojectcontroller.loadfile.LoadFileRequest;
import application.mediator.controllers.diagramprojectcontroller.saveasfile.SaveAsFileRequest;
import application.mediator.controllers.diagramprojectcontroller.savefile.SaveFileRequest;
import application.mediator.controllers.diagramprojectcontroller.snapshot.SnapshotRequest;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.stage.Window;


public class DiagramProjectController {

    private static final Mediator mediator = new MediatorSingletonHandler().getInstance();
    /**
     * Description: Opens the "Save As" from the project view so that the user
     * can save their work to a specified folder.
     * @param stage - Takes the Stage object of the project view.
     */
    public static void saveAsFile(Stage stage) {
        Request request = new SaveAsFileRequest(stage);
        mediator.send(request);
    }

    /**
     * Description: Allows the user to save new work to an existing project
     * from the project view.
     */
    public static void saveFile() {
        Request request = new SaveFileRequest();
        mediator.send(request);
    }

    /**
     * Description: Allows the user to load an existing project from their computer
     * from the project view.
     * @param stage - Takes the Stage object from the project view.
     */
    public static void loadFile(Stage stage) {
        Request request = new LoadFileRequest(stage);
        mediator.send(request);
    }

    public static void exit() {
        Request request = new ExitRequest();
        mediator.send(request);
    }

    /**
     * Description: Allows the user to add a class to their project by selecting
     * "Add Class" from the menu "Add" on the menu bar.
     */
    public static void addClass() {
        Request request = new AddClassRequest();
        mediator.send(request);
    }

    /**
     * Description: Allows a user to add a relationship to the project by selecting
     * "Add Relationship" from the menu "Add" on the menu bar.
     */
    public static void addRelationship() {
        Request request = new AddRelationshipRequest();
        mediator.send(request);
    }

    /**
     * Creates a snapshot of the users current diagram
     * */
    public static void snapshot(Node contentPane){
        Request request = new SnapshotRequest(contentPane);
        mediator.send(request);
    }
    public static void editClass() {
        System.out.println("editing class...");
    }

}

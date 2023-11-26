package application.mediator.controllers.diagramprojectcontroller.addclass;

import Diagram.Diagram;
import Class.Class;
import application.Application;
import application.GUI.GUIDiagramProject;
import application.GUI.GraphicalUserInterface;
import application.mediator.common.IHandler;
import application.mediator.common.Request;
import application.mediator.controllers.updateviewcontroller.UpdateViewController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddClassRequestHandler implements IHandler {
    public Void handle(Request request){
        AddClassRequest newRequest = (AddClassRequest) request;

        System.out.println("adding a new class!");
        Stage popupStage = new Stage();
        TextField tf = new TextField();
        Label errorLabel = new Label();
        Label label = new Label("Enter a name for the Class");
        Button submitBtn = new Button("Submit");
        Diagram diagram = Application.getCurrentDiagram();
        Class newClass = new Class("");
        GUIDiagramProject view = GraphicalUserInterface.getDiagramView();

        submitBtn.setOnAction(e -> {
            String inputText = tf.getText();
            if(inputText.isEmpty() || inputText.length() > 50) {
                errorLabel.setText("Please enter a non-empty name less than 50 characters.");
            } else {
                boolean notDuplicate = false;
                String name = tf.getText();
                for (Class curClass : view.getClassList()) {
                    if (curClass.getClassName().equals(name)) {
                        notDuplicate = true;
                        break;
                    }
                }
                if (notDuplicate) {
                    Alert duplicateNameAlert = new Alert(Alert.AlertType.ERROR);
                    duplicateNameAlert.setContentText("Class cannot contain duplicate name!");
                    duplicateNameAlert.showAndWait();
                } else {
                    newClass.setClassName(inputText);
                    System.out.println("Submitted Class name: " + inputText);
                    diagram.addClass(newClass.getClassName());
                    UpdateViewController.updateAddClass(view, newClass);
                    popupStage.close();
                }
            }
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(label, tf, errorLabel, submitBtn);

        Scene scene = new Scene(layout, 400, 200);

        popupStage.setScene(scene);
        popupStage.setTitle("Add Class");
        popupStage.show();
        return null;
    }
}

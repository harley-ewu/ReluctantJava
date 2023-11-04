package GUIAssets;
import Attributes.Attribute;
import Attributes.Field;
import Class.Class;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;


public class ClassAsset {

    private double xOffset = 0;
    private double yOffset = 0;

    public Pane createClassAsset(Class currentClass) {
        Pane classContainer = new Pane();
        classContainer.setStyle("-fx-background-color: lightblue;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: black;" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 10");

        Insets margins = new Insets(5, 2,2, 5);
        VBox textContainer = new VBox();
        Text className = new Text();
        Text fieldsNames = new Text();
        Text methodsNames = new Text();

        ArrayList<String> fields = this.returnFieldNames(currentClass);
        ArrayList<String> methods = this.returnMethodNames(currentClass);

        //setting text for each category
        className.setText("Class: " + currentClass.getClassName());
        VBox.setMargin(className, margins);
        fieldsNames.setText("Fields:\n" +
                this.displayContents(fields));
        VBox.setMargin(fieldsNames, margins);
        methodsNames.setText("Methods:\n" +
                this.displayContents(methods));
        VBox.setMargin(methodsNames, margins);

        Button editButton = new Button("edit");
        VBox.setMargin(editButton, margins);

        textContainer.getChildren().add(className);
        textContainer.getChildren().add(fieldsNames);
        textContainer.getChildren().add(methodsNames);
        textContainer.getChildren().add(editButton);
        classContainer.getChildren().add(textContainer);

        classContainer.setOnMousePressed(this::onMousePressed);
        classContainer.setOnMouseDragged(this::onMouseDragged);

        return classContainer;
    }

    private void onMousePressed(MouseEvent event) {
        this.xOffset = event.getSceneX();
        this.yOffset = event.getSceneY();
    }

    private void onMouseDragged(MouseEvent event) {
        double deltaX = event.getSceneX() - this.xOffset;
        double deltaY = event.getSceneY() - this.yOffset;

        Pane pane = new Pane();

        if (event.getSource() instanceof Pane) {
            pane = (Pane) event.getSource();
        }

        pane.setTranslateX(pane.getTranslateX() + deltaX);
        pane.setTranslateY(pane.getTranslateY() + deltaY);

        this.xOffset = event.getSceneX();
        this.yOffset = event.getSceneY();
    }

    private StringBuilder displayContents(ArrayList<String> contents) {
        if (contents == null) {
            return null;
        }
        StringBuilder contentList = new StringBuilder();


        for (String content : contents) {
            contentList.append(content + "\n");
        }

        return contentList;
    }

    private ArrayList<String> returnFieldNames(Class currentClass) {
        if (currentClass == null) {
            return null;
        }
        ArrayList<String> fieldNameList = new ArrayList<>();

        for(Attribute field : currentClass.getAttributes()) {
            if (field.getType() == Attribute.Type.FIELD) {
                fieldNameList.add(field.toString());
            }

        }

        return fieldNameList;
    }

    private ArrayList<String> returnMethodNames(Class currentClass) {
        if (currentClass == null) {
            return null;
        }

        ArrayList<String> methodNamesList = new ArrayList<>();

        for (Attribute method: currentClass.getAttributes()) {
            if(method.getType() == Attribute.Type.METHOD) {
                methodNamesList.add(method.toString());
            }
        }

        return methodNamesList;
    }
}

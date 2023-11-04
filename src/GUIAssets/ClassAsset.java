package GUIAssets;
import Attributes.Attribute;
import Class.Class;
import GUI.DiagramProjectController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;


public class ClassAsset {

    private double xOffset = 0;
    private double yOffset = 0;

    private double xCoordinate = 0;
    private double yCoordinate = 0;

    private ArrayList<String> fields;
    private ArrayList<String> methods;
    private Pane classContainer;


    public Pane createClassAsset(Class currentClass) {
        int textSize = 12;
        String fontType = "Verdana";

        this.classContainer = new Pane();
        this.classContainer.setStyle("-fx-background-color: lightblue;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: black;" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 10");

        Insets margins = new Insets(5, 5,5, 5);
        VBox textContainer = this.setupTextContainer(fontType, textSize, currentClass, margins);

        this.classContainer.getChildren().add(textContainer);
        this.classContainer.setOnMousePressed(this::onMousePressed);
        this.classContainer.setOnMouseDragged(this::onMouseDragged);

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

    /**
     * searches an existing array list and puts contents in a stringbuilder
     * @param contents
     * @return
     */
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

    /**
     * description: searches an attribute arraylist for fields and stores them in an arraylist of type string
     * @param currentClass
     * @return
     */

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

    /**
     * description: searches an arraylist of type attribute for methods and puts them in a String arraylist
     * @param currentClass
     * @return
     */
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

    /**
     * descriptions: setup method for the edit and delete buttons
     * @param fontType
     * @param textSize
     * @param margins
     * @return
     */

    public HBox setUpButtons(String fontType, int textSize, Insets margins ) {
        HBox buttonContainer = new HBox();
        buttonContainer.setSpacing(120.0);

        Button editButton = new Button("Edit");
        editButton.setFont(Font.font(fontType, textSize));
        editButton.setOnAction(e -> DiagramProjectController.editClass());

        Button deleteButton = new Button("Delete");
        deleteButton.setFont(Font.font(fontType, textSize));
        deleteButton.setOnAction(e -> DiagramProjectController.deleteClass());

        buttonContainer.getChildren().add(editButton);
        buttonContainer.getChildren().add(deleteButton);

        VBox.setMargin(buttonContainer, margins);
        return buttonContainer;

    }

    /**
     * description: setup for the contents of the class
     * @param fontType
     * @param textSize
     * @param currentClass
     * @param margins
     * @return
     */

    public VBox setupTextContainer(String fontType, int textSize, Class currentClass, Insets margins) {
        VBox textContainer = new VBox();
        Text className = new Text();

        className.setFont(Font.font(fontType, textSize));
        Text fieldsNames = new Text();

        fieldsNames.setFont(Font.font(fontType, textSize));

        Text methodsNames = new Text();
        methodsNames.setFont(Font.font(fontType, textSize));

        ArrayList<String> fields = this.returnFieldNames(currentClass);
        ArrayList<String> methods = this.returnMethodNames(currentClass);

        //setting text for each category
        className.setText("Class: " + currentClass.getClassName());
        VBox.setMargin(className, margins);

        fieldsNames.setText("Fields:\n" + this.displayContents(fields));
        VBox.setMargin(fieldsNames, margins);

        methodsNames.setText("Methods:\n" + this.displayContents(methods));
        VBox.setMargin(methodsNames, margins);

        HBox buttonContainer = this.setUpButtons(fontType, textSize, margins);
        textContainer.getChildren().addAll(className, fieldsNames, methodsNames, buttonContainer);

        return textContainer;
    }

    public double getCurrentX() {
        return this.classContainer.localToScene(this.classContainer.getBoundsInLocal()).getMinX();
    }

    public double getCurrentY() {
        return this.yCoordinate = this.classContainer.localToScene(this.classContainer.getBoundsInLocal()).getMinY();
    }



}

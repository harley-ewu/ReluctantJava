package GUIAssets;
import Attributes.Attribute;
import Class.Class;
import GUI.DiagramProjectController;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;


public class ClassAsset {
    private Class currentClass;
    private Pane classContainer;
    private ArrayList<String> fields;
    private ArrayList<String> methods;

    private double xOffset = 0;
    private double yOffset = 0;

    private double xCoordinate = 0;
    private double yCoordinate = 0;


    private int pos;

    public ClassAsset(Class currentClass, int pos) {
        this.currentClass = currentClass;
        this.pos = pos;
    }

    public Pane createClassAsset(ArrayList<Pane> paneArrayList) {
        int textSize = 12;
        String fontType = "Verdana";

        this.classContainer = new Pane();
        classContainer.setStyle("-fx-background-color: lightblue;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: black;" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 10");

        Insets margins = new Insets(5, 5,5, 5);
        VBox textContainer = this.setupTextContainer(fontType, textSize, margins, paneArrayList);

        this.classContainer.getChildren().add(textContainer);
        this.classContainer.setOnMousePressed(this::onMousePressed);
        this.classContainer.setOnMouseDragged(this::onMouseDragged);

        return this.classContainer;
    }

    public int getPos() {
        return this.pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
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

    public HBox setUpButtons(String fontType, int textSize, Insets margins, ArrayList<Pane> paneArrayList ) {
        HBox buttonContainer = new HBox();
        buttonContainer.setSpacing(120.0);

        Button editButton = new Button("Edit");
        editButton.setFont(Font.font(fontType, textSize));
        editButton.setOnAction(e -> DiagramProjectController.editClass());

        Button deleteButton = new Button("Delete");
        deleteButton.setFont(Font.font(fontType, textSize));
        deleteButton.setOnAction(e -> this.deleteClass(paneArrayList));

        buttonContainer.getChildren().add(editButton);
        buttonContainer.getChildren().add(deleteButton);

        VBox.setMargin(buttonContainer, margins);
        return buttonContainer;

    }

    /**
     * description: setup for the contents of the class
     * @param fontType
     * @param textSize
     * @param margins
     * @return
     */

    public VBox setupTextContainer(String fontType, int textSize, Insets margins, ArrayList<Pane> paneArrayList) {
        VBox textContainer = new VBox();
        Text className = new Text();

        className.setFont(Font.font(fontType, textSize));
        Text fieldsNames = new Text();

        fieldsNames.setFont(Font.font(fontType, textSize));

        Text methodsNames = new Text();
        methodsNames.setFont(Font.font(fontType, textSize));

        this.fields = this.returnFieldNames(this.currentClass);
        this.methods = this.returnMethodNames(this.currentClass);

        //setting text for each category
        className.setText("Class: " + currentClass.getClassName());
        VBox.setMargin(className, margins);

        fieldsNames.setText("Fields:\n" + this.displayContents(this.fields));
        VBox.setMargin(fieldsNames, margins);

        methodsNames.setText("Methods:\n" + this.displayContents(this.methods));
        VBox.setMargin(methodsNames, margins);

        HBox buttonContainer = this.setUpButtons(fontType, textSize, margins, paneArrayList);
        textContainer.getChildren().addAll(className, fieldsNames, methodsNames, buttonContainer);

        return textContainer;
    }

    public double getCurrentX() {
        return this.classContainer.localToScene(this.classContainer.getBoundsInLocal()).getMinX();
    }

    public double getCurrentY() {
        return this.yCoordinate = this.classContainer.localToScene(this.classContainer.getBoundsInLocal()).getMinY();
    }

    public void deleteClass(ArrayList<Pane> classAssetList) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setContentText("Are you sure you want to delete this class?");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        if (result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
            System.out.println("class deleted");
            classAssetList.remove(this.pos);
        }

    }

}

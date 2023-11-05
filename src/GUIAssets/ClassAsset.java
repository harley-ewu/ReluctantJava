package GUIAssets;
import Attributes.Attribute;
import Class.Class;
import GUI.DiagramProjectController;

import GUI.GUIDiagramProject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

    public Pane createClassAsset(ArrayList<Pane> paneArrayList, ArrayList<ClassAsset> classAssets, ArrayList<Point2D> coordinates, GUIDiagramProject guiDiagramProject) {
        int textSize = 12;
        String fontType = "Verdana";

        this.classContainer = new Pane();
        classContainer.setStyle("-fx-background-color: lightblue;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: black;" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 10");

        Insets margins = new Insets(5, 5,5, 5);
        VBox textContainer = this.setupTextContainer(fontType, textSize, margins, paneArrayList, classAssets, coordinates, guiDiagramProject);

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

    public HBox setUpButtons(String fontType, int textSize, Insets margins, ArrayList<Pane> paneArrayList, ArrayList<ClassAsset> classAssets,
                             ArrayList<Point2D> coordinates,GUIDiagramProject guiDiagramProject) {
        HBox buttonContainer = new HBox();
        buttonContainer.setSpacing(120.0);

        Button editButton = new Button("Edit");
        editButton.setFont(Font.font(fontType, textSize));
        editButton.setOnAction(e -> this.editClass());

        Button deleteButton = new Button("Delete");
        deleteButton.setFont(Font.font(fontType, textSize));
        deleteButton.setOnAction(e -> this.deleteClass(paneArrayList, classAssets, coordinates, guiDiagramProject));

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

    public VBox setupTextContainer(String fontType, int textSize, Insets margins, ArrayList<Pane> paneArrayList,
                                   ArrayList<ClassAsset> classAssets, ArrayList<Point2D> coordinates, GUIDiagramProject guiDiagramProject) {
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

        HBox buttonContainer = this.setUpButtons(fontType, textSize, margins, paneArrayList,
                classAssets, coordinates, guiDiagramProject);
        textContainer.getChildren().addAll(className, fieldsNames, methodsNames, buttonContainer);

        return textContainer;
    }

    public double getCurrentX() {
        return this.classContainer.localToScene(this.classContainer.getBoundsInLocal()).getMinX();
    }

    public double getCurrentY() {
        return this.yCoordinate = this.classContainer.localToScene(this.classContainer.getBoundsInLocal()).getMinY();
    }

    public void deleteClass(ArrayList<Pane> classAssetPaneList, ArrayList<ClassAsset> classAssets,
                            ArrayList<Point2D> coordinates, GUIDiagramProject guiDiagramProject) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setContentText("Are you sure you want to delete this class?");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
            System.out.println("class deleted");
            classAssetPaneList.remove(this.pos);

            for (int i = 0; i < classAssetPaneList.size(); i++) {
                Point2D coords = new Point2D(classAssetPaneList.get(i).localToScene(classAssetPaneList.get(i).getBoundsInLocal()).getMinX(),
                        classAssetPaneList.get(i).localToScene(classAssetPaneList.get(i).getBoundsInLocal()).getMinY());
                coordinates.add(coords);
            }

            classAssets.remove(this.pos);
            this.updateClassAssetListPos(classAssets);
            guiDiagramProject.refreshClassPanes();
            guiDiagramProject.refreshClassPanesToPaneWindow();

            System.out.println("class asset list size now: " + classAssetPaneList.size());
        }

    }

    public void updateClassAssetListPos(ArrayList<ClassAsset> classAssets) {
        for (int i = this.pos; i < classAssets.size(); i++) {
            if (i != 0) {
                classAssets.get(i).setPos(i - 1);
            }
        }
    }

    public void editClass() {

        Stage popUpStage = new Stage();
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.setWidth(640);
        popUpStage.setHeight(480);
        popUpStage.setResizable(false);
        Pane root = new Pane();
        Scene scene = new Scene(root, 640, 480);

        //setup drop down menu for fields
        ComboBox<String> comboBoxFields = new ComboBox();
        comboBoxFields.setLayoutX(scene.getWidth()/8);
        comboBoxFields.setLayoutY(scene.getHeight()-400);
        comboBoxFields.setValue("Fields");

        ObservableList<String> observableFieldsList = FXCollections.observableArrayList();
        for (String field : this.fields) {
            observableFieldsList.add(field);
        }

        comboBoxFields.setItems(observableFieldsList);

        //setup drop down menu for methods
        ComboBox<String> comboBoxMethods = new ComboBox();
        comboBoxMethods.setLayoutX(scene.getWidth()/8);
        comboBoxMethods.setLayoutY(scene.getHeight()-250);
        comboBoxMethods.setValue("Methods");

        ObservableList<String> observableMethodsList = FXCollections.observableArrayList();
        for (String method : this.methods) {
            observableMethodsList.add(method);
        }

        comboBoxMethods.setItems(observableMethodsList);

        root.getChildren().addAll(comboBoxFields, comboBoxMethods);

        popUpStage.setTitle("Class Editor");
        popUpStage.setScene(scene);
        popUpStage.show();

    }

}

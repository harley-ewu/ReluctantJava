package GUIAssets;
import Attributes.Attribute;
import Class.Class;

import GUI.GUIDiagramProject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;


public class ClassAsset {

    private Class currentClass;
    private Pane classContainer;
    private ArrayList<String> fields;
    private ArrayList<String> methods;

    private double xOffset = 0;
    private double yOffset = 0;

    private int pos;

    public ClassAsset(final Class currentClass, final int pos) {
        this.currentClass = currentClass;
        this.pos = pos;
    }

    /**
     * Creates and returns a graphical representation of a class asset within a GUI diagram project.
     *
     * @param classList          The list of classes in the project.
     * @param paneArrayList      The list of panes associated with class assets.
     * @param classAssets        The list of class assets.
     * @param coordinates        A list of coordinates representing the positions of class assets.
     * @param guiDiagramProject  The GUI diagram project that manages the graphical elements.
     * @return                  A JavaFX Pane representing the class asset.
     */


    public Pane createClassAsset(final ArrayList<Class> classList, final ArrayList<Pane> paneArrayList, final ArrayList<ClassAsset> classAssets,
                                 final ArrayList<Point2D> coordinates, final GUIDiagramProject guiDiagramProject) {
        Objects.requireNonNull(classList, "Class list can't be null.");
        Objects.requireNonNull(paneArrayList, "Pane ArrayList list can't be null.");
        Objects.requireNonNull(classAssets, "Class assets can't be null.");
        Objects.requireNonNull(coordinates, "Coordinates can't be null.");
        Objects.requireNonNull(guiDiagramProject, "Class list can't be null.");

        int textSize = 12;
        String fontType = "Verdana";

        this.classContainer = new Pane();
        classContainer.setStyle("-fx-background-color: lightblue;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: black;" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 10");

        Insets margins = new Insets(5, 5,5, 5);
        VBox textContainer = this.setupTextContainer(fontType, textSize, margins, classList, paneArrayList,
                classAssets, coordinates, guiDiagramProject);

        this.classContainer.getChildren().add(textContainer);
        this.classContainer.setOnMousePressed(this::onMousePressed);
        this.classContainer.setOnMouseDragged(this::onMouseDragged);

        return this.classContainer;
    }


    private void onMousePressed(final MouseEvent event) {
        Objects.requireNonNull(event, "Event can't be null.");
        this.xOffset = event.getSceneX();
        this.yOffset = event.getSceneY();
    }

    private void onMouseDragged(final MouseEvent event) {
        Objects.requireNonNull(event, "Class list can't be null.");
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
    private StringBuilder displayContents(final ArrayList<String> contents) {
        Objects.requireNonNull(contents, "Class list can't be null.");
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

    private ArrayList<String> returnFieldNames(final Class currentClass) {
        Objects.requireNonNull(currentClass, "Class can't be null.");
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
    private ArrayList<String> returnMethodNames(final Class currentClass) {
        Objects.requireNonNull(currentClass, "Current class can't be null.");

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

    private HBox setUpButtons(final String fontType, final int textSize, final Insets margins, final ArrayList<Class> classList,
                             final ArrayList<Pane> paneArrayList, final ArrayList<ClassAsset> classAssets,
                             final ArrayList<Point2D> coordinates, final GUIDiagramProject guiDiagramProject){
        Objects.requireNonNull(classList, "Class list can't be null.");
        Objects.requireNonNull(paneArrayList, "Pane array can't be null.");
        Objects.requireNonNull(classAssets, "Class assets can't be null.");
        Objects.requireNonNull(coordinates, "Coordinates list can't be null.");
        Objects.requireNonNull(guiDiagramProject, "Project can't be null.");

        HBox buttonContainer = new HBox();
        buttonContainer.setSpacing(120.0);

        Button editButton = new Button("Edit");
        editButton.setFont(Font.font(fontType, textSize));
        editButton.setOnAction(e -> this.editClass());

        Button deleteButton = new Button("Delete");
        deleteButton.setFont(Font.font(fontType, textSize));
        deleteButton.setOnAction(e -> this.deleteClass(classList, paneArrayList, classAssets, coordinates, guiDiagramProject));

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

    private VBox setupTextContainer(final String fontType, final int textSize, final Insets margins, final ArrayList<Class> classList,
                                   final ArrayList<Pane> paneArrayList, final ArrayList<ClassAsset> classAssets,
                                   final ArrayList<Point2D> coordinates, final GUIDiagramProject guiDiagramProject){
        Objects.requireNonNull(classList, "Class list can't be null.");
        Objects.requireNonNull(paneArrayList, "Pane list can't be null.");
        Objects.requireNonNull(classAssets, "Class assets can't be null.");
        Objects.requireNonNull(coordinates, "Coordinates can't be null.");
        Objects.requireNonNull(guiDiagramProject, "Diagram can't be null.");

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

        HBox buttonContainer = this.setUpButtons(fontType, textSize, margins,classList, paneArrayList,
                classAssets, coordinates, guiDiagramProject);
        textContainer.getChildren().addAll(className, fieldsNames, methodsNames, buttonContainer);

        return textContainer;
    }

    public double getCurrentX() {
        return this.classContainer.localToScene(this.classContainer.getBoundsInLocal()).getMinX();
    }

    public double getCurrentY() {
        return this.classContainer.localToScene(this.classContainer.getBoundsInLocal()).getMinX();
    }

    /**
     * This method is used to delete a class and its associated assets from a GUI diagram project.
     *
     * @param classList            The list of classes in the project.
     * @param classAssetPaneList   The list of panes associated with the class assets.
     * @param classAssets          The list of class assets.
     * @param coordinates          A list of coordinates representing the positions of class assets.
     * @param guiDiagramProject    The GUI diagram project that manages the graphical elements.
     */

    public void deleteClass(final ArrayList<Class> classList, final ArrayList<Pane> classAssetPaneList, final ArrayList<ClassAsset> classAssets,
                            final ArrayList<Point2D> coordinates, final GUIDiagramProject guiDiagramProject) {
        Objects.requireNonNull(classList, "Class list can't be null.");
        Objects.requireNonNull(classAssetPaneList, "Pane list can't be null.");
        Objects.requireNonNull(classAssets, "Class assets can't be null.");
        Objects.requireNonNull(coordinates, "Coordinates can't be null.");
        Objects.requireNonNull(guiDiagramProject, "Diagram can't be null.");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setContentText("Are you sure you want to delete this class?");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
            System.out.println("class deleted");
            //remove the class from the class list first
            classList.remove(this.pos);
            //remove the class pane from the pane list next
            classAssetPaneList.remove(this.pos);
            //get the x/y positions from the remaining class asset panes
            for (int i = 0; i < classAssetPaneList.size(); i++) {
                double currentXCoordinate = classAssetPaneList.get(i).localToScene(classAssetPaneList.get(i).getBoundsInLocal()).getMinX();
                double currentYCoordinate = classAssetPaneList.get(i).localToScene(classAssetPaneList.get(i).getBoundsInLocal()).getMinY();
                Point2D coords = new Point2D(currentXCoordinate, currentYCoordinate);
                coordinates.add(coords);
            }

            //update the class asset list by taking the new class list and creating new class assets from them
            this.updateClassAssetListPos(classList, classAssets); //to be removed
            //refresh the class asset panes and the window
            guiDiagramProject.refreshClassPanes();
            guiDiagramProject.refreshPanesToPaneWindow();

        }

    }

    /**
     * description: this will take the classList field and classAssets field from the
     * GUIDiagramProject class, it will then clear the classAssets arrayList and repopulate it with
     * new ClassAssets created from the classList
     * @param classList
     * @param classAssets
     */

    public void updateClassAssetListPos(final ArrayList<Class> classList, final ArrayList<ClassAsset> classAssets) {
        Objects.requireNonNull(classList, "Class list can't be null.");
        Objects.requireNonNull(classAssets, "Class assets can't be null.");
        classAssets.clear();
        for (int i = 0; i < classList.size(); i++) {
            ClassAsset newClassAsset = new ClassAsset(classList.get(i), i);
            classAssets.add(newClassAsset);
        }
    }

    /**
     * description: this is the action event for the edit button
     */
    private void editClass() {

        Stage popUpStage = new Stage();
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.setWidth(640);
        popUpStage.setHeight(480);
        popUpStage.setResizable(false);
        Pane root = new Pane();
        Scene scene = new Scene(root, 640, 480);

        //setup drop down menu for fields
        HBox fieldsHBox = new HBox();
        fieldsHBox.setSpacing(150);

        ComboBox<String> comboBoxFields = new ComboBox();

        fieldsHBox.getChildren().add(comboBoxFields);
        fieldsHBox.setLayoutX(scene.getWidth()/8);
        fieldsHBox.setLayoutY(scene.getHeight()-400);
        comboBoxFields.setValue("Fields");
        comboBoxFields.setPrefWidth(120);

        VBox fieldButtonContainer = new VBox();
        fieldButtonContainer.setSpacing(5);

        //edit field button
        Button editFieldButton = new Button();
        editFieldButton.setText("Edit Field");
        editFieldButton.setOnAction(e -> editField());


        //add field button
        Button addFieldButton = new Button();
        addFieldButton.setText("Add Field");

        //delete field button
        Button deleteFieldButton = new Button();
        deleteFieldButton.setText("Delete Field");

        fieldButtonContainer.getChildren().addAll(editFieldButton, addFieldButton, deleteFieldButton);

        fieldsHBox.getChildren().addAll(fieldButtonContainer);

        ObservableList<String> observableFieldsList = FXCollections.observableArrayList();
        for (String field : this.fields) {
            observableFieldsList.add(field);
        }

        comboBoxFields.setItems(observableFieldsList);

        //setup drop down menu for methods
        HBox methodsHBox = new HBox();
        methodsHBox.setSpacing(150);
        ComboBox<String> comboBoxMethods = new ComboBox();

        methodsHBox.getChildren().add(comboBoxMethods);
        methodsHBox.setLayoutX(scene.getWidth()/8);
        methodsHBox.setLayoutY(scene.getHeight()-270);
        comboBoxMethods.setValue("Methods");
        comboBoxMethods.setPrefWidth(120);

        VBox methodsButtonContainer = new VBox();
        methodsButtonContainer.setSpacing(5);
        Text fields = new Text();
        fields.setText("Fields");
        fields.setFont(Font.font("Verdana", 24));
        fields.setLayoutX(scene.getWidth()/6 - 28);
        fields.setLayoutY(scene.getHeight()- 410);

        Text methods = new Text();
        methods.setText("Methods");
        methods.setLayoutX(scene.getWidth()/6 - 28);
        methods.setLayoutY(scene.getHeight()-280);
        methods.setFont(Font.font("Verdana", 24));

        //edit method button
        Button editMethodButton = new Button();
        editMethodButton.setText("Edit Method");

        //add method button
        Button addMethodButton = new Button();
        addMethodButton.setText("Add Method");

        //delete method button
        Button deleteMethodButton = new Button();
        deleteMethodButton.setText("Delete Method");

        methodsButtonContainer.getChildren().addAll(editMethodButton, addMethodButton, deleteMethodButton);
        methodsHBox.getChildren().addAll(methodsButtonContainer);

        //Submit button
        HBox submitButtonContainer = new HBox();
        submitButtonContainer.setSpacing(50);
        Button submitButton = new Button();
        submitButton.setText("Submit");

        //Cancel button
        Button cancelButton = new Button();
        cancelButton.setText("Cancel");

        submitButtonContainer.getChildren().addAll(submitButton, cancelButton);
        submitButtonContainer.setLayoutX(root.getWidth()/2-80);
        submitButtonContainer.setLayoutY(root.getHeight()-100);
        ObservableList<String> observableMethodsList = FXCollections.observableArrayList();

        for (String method : this.methods) {
            observableMethodsList.add(method);
        }

        comboBoxMethods.setItems(observableMethodsList);

        Rectangle background = new Rectangle();
        background.setStyle("-fx-fill: lightblue; -fx-stroke: black; -fx-stroke-width: 1;");
        background.setWidth(scene.getWidth()-18);
        background.setHeight(scene.getHeight()-110);

        root.getChildren().addAll(background, fieldsHBox, methodsHBox, submitButtonContainer, fields, methods);

        popUpStage.setTitle("Class Editor");
        popUpStage.setScene(scene);
        popUpStage.show();

    }

    private void editField() {
        Stage popUpStage = new Stage();
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.setWidth(426);
        popUpStage.setHeight(240);
        popUpStage.setResizable(false);
        Pane root = new Pane();
        root.setStyle("-fx-background-color: lightblue");
        Scene scene = new Scene(root, 426, 240);

        //edit name text field
        HBox editNameContainer = new HBox();
        editNameContainer.setSpacing(20);
        Text editFieldLabel = new Text();
        TextField editNameField = new TextField();
        editFieldLabel.setText("Enter New Name:");
        editNameContainer.setLayoutX(50);
        editNameContainer.setLayoutY(scene.getHeight()/2-70);

        editNameContainer.getChildren().addAll(editFieldLabel, editNameField);

        //edit primitive type text field
        HBox editPrimitiveContainer = new HBox();
        editPrimitiveContainer.setSpacing(17);
        Text editPrimitiveLabel = new Text();
        TextField editPrimitiveField = new TextField();
        editPrimitiveLabel.setText("Enter New Type:");
        editPrimitiveContainer.setLayoutX(60);
        editPrimitiveContainer.setLayoutY(scene.getHeight()/2-20);

        editPrimitiveContainer.getChildren().addAll(editPrimitiveLabel, editPrimitiveField);

        root.getChildren().addAll(editNameContainer, editPrimitiveContainer);
        popUpStage.setTitle("Edit Field");
        popUpStage.setScene(scene);
        popUpStage.show();

    }


}

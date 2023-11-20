package GUIAssets;

import Attributes.Attribute;
import CLI.CommandLineInterface;
import Class.Class;
import GUI.GUIDiagramProject;
import Relationships.Relationship;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;


public class ClassAsset {

    private Class currentClass;
    private Pane classContainer;
    private ArrayList<Attribute> attributeList = new ArrayList<>();

    private ArrayList<String> fields;
    private ArrayList<String> methods;

    private double xOffset = 0;
    private double yOffset = 0;

    private int pos;

    public ClassAsset(final Class currentClass, final int pos) {
        this.currentClass = currentClass;
        this.pos = pos;
        this.initAttributeList(currentClass);
    }

    public Class getCurrentClass() {
        return this.currentClass;
    }

    /**
     * Creates and returns a graphical representation of a class asset within a GUI diagram project.
     *
     * @param classList          The list of classes in the project.
     * @param classPaneArrayList      The list of panes associated with class assets.
     * @param classAssets        The list of class assets.
     * @param classCoordinates        A list of coordinates representing the positions of class assets.
     * @param relationshipListArrayList     The list of panes associates with relationship assets
     * @param relationshipCoordinates      A list of coordinates representing the positions of relationship assets
     * @param relationshipAssets
     * @param guiDiagramProject  The GUI diagram project that manages the graphical elements
     * @return                  A JavaFX Pane representing the class asset.
     */


    public <relationshipAssets> Pane createClassAsset(final ArrayList<Class> classList, final ArrayList<Pane> classPaneArrayList, final ArrayList<ClassAsset> classAssets,
                                                      final ArrayList<Point2D> classCoordinates, final ArrayList<Line> relationshipListArrayList,
                                                      final ArrayList<Point2D> relationshipCoordinates, final ArrayList<RelationshipAsset> relationshipAssets, final GUIDiagramProject guiDiagramProject) {
        int textSize = 12;
        String fontType = "Verdana";

        this.classContainer = new Pane();
        classContainer.setStyle("-fx-background-color: lightblue;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: black;" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 10");

        Insets margins = new Insets(5, 5,5, 5);

        VBox textContainer = this.setupTextContainer(fontType, textSize, margins, classList, classPaneArrayList,
                classAssets, classCoordinates, relationshipListArrayList, relationshipCoordinates, guiDiagramProject);

        this.classContainer.getChildren().add(textContainer);
        this.classContainer.setOnMousePressed(this::onMousePressed);
        this.classContainer.setOnMouseDragged(event -> onMouseDragged(event, classPaneArrayList, classAssets, classCoordinates, relationshipAssets));

        return this.classContainer;
    }


    private void onMousePressed(final MouseEvent event) {
        this.xOffset = event.getSceneX();
        this.yOffset = event.getSceneY();
    }

    private void onMouseDragged(final MouseEvent event, final ArrayList<Pane> classPaneArrayList, final ArrayList<ClassAsset> classAssets,
                                final ArrayList<Point2D> classCoordinates, final ArrayList<RelationshipAsset> relationshipAssets) {
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

        for (RelationshipAsset relationshipAsset :relationshipAssets) {
            RelationshipAsset.updateRelationshipLines(relationshipAsset, classPaneArrayList, classCoordinates, classAssets);
        }
    }

    /**
     * searches an existing array list and puts contents in a stringbuilder
     * @param contents
     * @return
     */
    private StringBuilder displayContents(final ArrayList<String> contents) {
        if (contents == null) {
            return null;
        }
        StringBuilder contentList = new StringBuilder();

        for (String content : contents) {
            contentList.append(content + "\n");
        }

        return contentList;
    }

    private void initAttributeList(final Class currentClass) {
        this.attributeList.addAll(currentClass.getAttributes());
    }

    /**
     * description: searches an attribute arraylist for fields and stores them in an arraylist of type string
     * @param currentClass
     * @return
     */

    private ArrayList<String> returnFieldNames(final Class currentClass) {
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
    private ArrayList<String> returnMethodNames(final Class currentClass) {
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
     * @param classList
     * @param classPaneArrayList
     * @param classAssets
     * @param classCoordinates
     * @param relationshipLineArrayList
     * @param relationshipCoordinates
     * @param guiDiagramProject
     * @return
     */


    public HBox setUpButtons(final String fontType, final int textSize, final Insets margins, final ArrayList<Class> classList,
                             final ArrayList<Pane> classPaneArrayList, final ArrayList<ClassAsset> classAssets,
                             final ArrayList<Point2D> classCoordinates, final ArrayList<Line> relationshipLineArrayList,
                             final ArrayList<Point2D> relationshipCoordinates, final GUIDiagramProject guiDiagramProject){

        HBox buttonContainer = new HBox();
        buttonContainer.setSpacing(120.0);

        Button editButton = new Button("Edit");
        editButton.setFont(Font.font(fontType, textSize));
        editButton.setOnAction(e -> this.editClass(classList, classPaneArrayList, classAssets, classCoordinates, guiDiagramProject));

        Button deleteButton = new Button("Delete");
        deleteButton.setFont(Font.font(fontType, textSize));

        deleteButton.setOnAction(e -> this.deleteClass(classList, classPaneArrayList, classAssets, classCoordinates,
                relationshipLineArrayList, relationshipCoordinates, guiDiagramProject));

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
     * @param classList
     * @param classPaneArrayList
     * @param classAssets
     * @param classCoordinates
     * @param relationshipLineArrayList
     * @param relationshipCoordinates
     * @param guiDiagramProject
     * @return
     */

    public VBox setupTextContainer(final String fontType, final int textSize, final Insets margins, final ArrayList<Class> classList,
                                   final ArrayList<Pane> classPaneArrayList, final ArrayList<ClassAsset> classAssets,
                                   final ArrayList<Point2D> classCoordinates, final ArrayList<Line> relationshipLineArrayList,
                                   final ArrayList<Point2D> relationshipCoordinates, final GUIDiagramProject guiDiagramProject){

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


        HBox buttonContainer = this.setUpButtons(fontType, textSize, margins,classList, classPaneArrayList,
                classAssets, classCoordinates, relationshipLineArrayList, relationshipCoordinates, guiDiagramProject);

        textContainer.getChildren().addAll(className, fieldsNames, methodsNames, buttonContainer);

        return textContainer;
    }


    /**
     * This method is used to delete a class and its associated assets from a GUI diagram project.
     *
     * @param classList            The list of classes in the project.
     * @param classAssetPaneList   The list of panes associated with the class assets.
     * @param classAssets          The list of class assets.
     * @param classCoordinates          A list of coordinates representing the positions of class assets.
     * @param relationshipAssetLineList     the list of panes associated with the relationship assets.
     * @param relationshipCoordinates   A list of coordinates representing the positions of relationship assets
     * @param guiDiagramProject    The GUI diagram project that manages the graphical elements.
     */

    public void deleteClass(final ArrayList<Class> classList, final ArrayList<Pane> classAssetPaneList, final ArrayList<ClassAsset> classAssets,
                            final ArrayList<Point2D> classCoordinates, final ArrayList<Line> relationshipAssetLineList,
                            final ArrayList<Point2D> relationshipCoordinates, final GUIDiagramProject guiDiagramProject) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setContentText("Are you sure you want to delete this class and it's relationships?");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
            System.out.println("class and it's relationships deleted");
            //remove the class from the class list first
            classList.remove(this.pos);

            ArrayList<Relationship> classRelationships = CommandLineInterface.getCurrentDiagram().getSingleClassRelationships(currentClass);
            for(Relationship relationship : classRelationships) {
                if(guiDiagramProject.getRelationshipAssetFromList(relationship) != null) {
                    guiDiagramProject.getRelationshipAssetFromList(relationship).deleteRelationship(guiDiagramProject.getRelationshipList(),
                            relationshipAssetLineList, guiDiagramProject.getRelationshipAssets(), relationshipCoordinates,
                            classAssetPaneList, classCoordinates, guiDiagramProject);
                }
            }

            CommandLineInterface.getCurrentDiagram().deleteClass(currentClass);

            //remove the class pane from the pane list next
            classAssetPaneList.remove(this.pos);

            relationshipCoordinates.clear();
            classCoordinates.clear();

            //get the x/y positions from the remaining class asset panes

            this.updateCoordinates(classAssetPaneList, classCoordinates);

            for (int i = 0; i < relationshipAssetLineList.size(); i++) {
                double currentXCoordinate = relationshipAssetLineList.get(i).localToScene(relationshipAssetLineList.get(i).getBoundsInLocal()).getMinX();
                double currentYCoordinate = relationshipAssetLineList.get(i).localToScene(relationshipAssetLineList.get(i).getBoundsInLocal()).getMinY();
                Point2D coords = new Point2D(currentXCoordinate, currentYCoordinate);
                relationshipCoordinates.add(coords);
            }

            //update the class asset list by taking the new class list and creating new class assets from them
            this.updateClassAssetListPos(classList, classAssets);
            //refresh the class asset panes and the window
            guiDiagramProject.refreshClassPanes();
            guiDiagramProject.refreshClassPanesToPaneWindow();

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
        classAssets.clear();
        for (int i = 0; i < classList.size(); i++) {
            ClassAsset newClassAsset = new ClassAsset(classList.get(i), i);
            classAssets.add(newClassAsset);
        }
    }

    /**
     * description: this is the action event for the edit button
     */
    private void editClass(ArrayList<Class> classList, ArrayList<Pane> classAssetPaneList, ArrayList<ClassAsset> classAssets,ArrayList<Point2D> classCoordinates, GUIDiagramProject guiDiagramProject ) {

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
        editFieldButton.setOnAction(e -> {
            for (Attribute attribute : this.attributeList) {
                String comboBoxName = comboBoxFields.getValue();
                if (attribute.toString().equals(comboBoxName)) {
                    this.editField(attribute);
                    return;
                }
            }
        });

        //add field button
        Button addFieldButton = new Button();
        addFieldButton.setText("Add Field");
        addFieldButton.setOnAction(e -> this.addField());

        //delete field button
        Button deleteFieldButton = new Button();
        deleteFieldButton.setText("Delete Field");
        deleteFieldButton.setOnAction(e -> {
            for (Attribute attribute : this.attributeList) {
                String comboBoxName = comboBoxFields.getValue();
                if (attribute.toString().equals(comboBoxName)) {
                    this.deleteField(attribute);
                    return;
                }
            }
        });

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

        //Update button
        HBox submitButtonContainer = new HBox();
        submitButtonContainer.setSpacing(50);
        Button submitButton = new Button();
        submitButton.setText("Update Pane");
        submitButton.setOnAction(e -> {

            this.updateCoordinates(classAssetPaneList, classCoordinates);
            //update the class asset list by taking the new class list and creating new class assets from them
            //this.updateClassAssetListPos(classList, classAssets);
            //refresh the class asset panes and the window
            guiDiagramProject.refreshClassPanes();
            guiDiagramProject.refreshClassPanesToPaneWindow();
            popUpStage.close();
        });

        //Cancel button
        Button cancelButton = new Button();
        cancelButton.setText("Cancel");
        cancelButton.setOnAction(e -> popUpStage.close());

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

    private void editField(Attribute attribute) {
        if (attribute == null) {
            System.out.println("attribute is null");
        }

        Stage popUpStage = new Stage();
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.setWidth(426);
        popUpStage.setHeight(240);
        popUpStage.setResizable(false);
        Pane root = new Pane();
        root.setStyle("-fx-background-color: lightblue");
        Scene scene = new Scene(root, 426, 240);

        //current name and type labels
        Text currentNames = new Text();
        currentNames.setLayoutX(20);
        currentNames.setLayoutY(20);
        currentNames.setText("current name: " + attribute.getName() + "\t\t\t\t" + "current type: " + attribute.getPrimitive());


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

        //container for submit and cancel button
        HBox submitContainer = new HBox();
        submitContainer.setLayoutX(scene.getWidth()/2-100);
        submitContainer.setLayoutY(150);
        submitContainer.setSpacing(20);
        //submit button
        Button submitButton = new Button();
        submitButton.setText("Submit");

        submitButton.setOnAction(e -> {
            if (!editNameField.getText().isEmpty() || !editPrimitiveField.getText().isEmpty()) {
                boolean isUnique = true;
                for (Attribute currentAttribute : this.attributeList) {
                    if (attribute.getName().equals(editNameField.getText())) {
                        isUnique = false;
                    }
                }
                //handle unique name
                if (isUnique) {
                    attribute.setName(editNameField.getText());
                    attribute.setPrimitive(editPrimitiveField.getText());
                    popUpStage.close();
                } else {
                    Alert notUnique = new Alert(Alert.AlertType.WARNING);
                    notUnique.setContentText("Please enter a unique name!");
                    notUnique.showAndWait();
                }

            } else {
                Alert notUnique = new Alert(Alert.AlertType.WARNING);
                notUnique.setContentText("No empty fields! (or hit cancel to exit)");
                notUnique.showAndWait();
            }

            this.printAttributeList();

        });
        //cancel button
        Button cancelButton = new Button();
        cancelButton.setText("Cancel");
        cancelButton.setOnAction(e -> popUpStage.close());

        submitContainer.getChildren().addAll(submitButton, cancelButton);

        root.getChildren().addAll(currentNames,editNameContainer, editPrimitiveContainer, submitContainer);
        popUpStage.setTitle("Edit Field");
        popUpStage.setScene(scene);
        popUpStage.show();

    }

    public void addField() {

        Stage popUpStage = new Stage();
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.setWidth(426);
        popUpStage.setHeight(240);
        popUpStage.setResizable(false);
        Pane root = new Pane();
        root.setStyle("-fx-background-color: lightblue");
        Scene scene = new Scene(root, 426, 240);

        //current name and type labels
        Text currentNames = new Text();
        currentNames.setLayoutX(20);
        currentNames.setLayoutY(20);
        currentNames.setText("Create a new Attribute");


        HBox addNameContainer = new HBox();
        addNameContainer.setSpacing(20);
        Text addFieldLabel = new Text();
        TextField addNameField = new TextField();
        addFieldLabel.setText("Enter New Name:");
        addNameContainer.setLayoutX(50);
        addNameContainer.setLayoutY(scene.getHeight()/2-70);

        addNameContainer.getChildren().addAll(addFieldLabel, addNameField);

        //edit primitive type text field
        HBox addPrimitiveContainer = new HBox();
        addPrimitiveContainer.setSpacing(17);
        Text addPrimitiveLabel = new Text();
        TextField addPrimitiveField = new TextField();
        addPrimitiveLabel.setText("Add A Type:");
        addPrimitiveContainer.setLayoutX(60);
        addPrimitiveContainer.setLayoutY(scene.getHeight()/2-20);

        addPrimitiveContainer.getChildren().addAll(addPrimitiveLabel, addPrimitiveField);

        //container for submit and cancel button
        HBox submitContainer = new HBox();
        submitContainer.setLayoutX(scene.getWidth()/2-100);
        submitContainer.setLayoutY(150);
        submitContainer.setSpacing(20);
        //submit button
        Button submitButton = new Button();
        submitButton.setText("Submit");

        Attribute newAttribute = new Attribute();

        submitButton.setOnAction(e -> {
            boolean isUnique = true;
            for (Attribute currentAttribute : this.attributeList) {
                if (currentAttribute.getName().equals(addNameField.getText())) {
                    isUnique = false;
                }
            }
            //handle unique name
            if (isUnique) {
                newAttribute.setName(addNameField.getText());
                newAttribute.setPrimitive(addPrimitiveField.getText());
                this.attributeList.add(newAttribute);
                popUpStage.close();
            } else {
                Alert notUnique = new Alert(Alert.AlertType.WARNING);
                notUnique.setContentText("Please enter a unique name!");
                notUnique.showAndWait();
            }

            this.printAttributeList();
        });
        //cancel button
        Button cancelButton = new Button();
        cancelButton.setText("Cancel");
        cancelButton.setOnAction(e -> popUpStage.close());

        submitContainer.getChildren().addAll(submitButton, cancelButton);

        root.getChildren().addAll(currentNames,addNameContainer, addPrimitiveContainer, submitContainer);
        popUpStage.setTitle("Add Field");
        popUpStage.setScene(scene);
        popUpStage.show();
    }

    public void deleteField(Attribute attribute) {
        if (attribute == null) {
            System.out.println("attribute or combo box is null");
        }

        this.attributeList.remove(attribute);

        this.printAttributeList();
    }

    public void printAttributeList() {
        for (Attribute attribute2 : this.attributeList) {
            System.out.println(attribute2.toString());
        }
    }

    public void updateCoordinates(ArrayList<Pane> classAssetPaneList, ArrayList<Point2D> classCoordinates) {
        for (int i = 0; i < classAssetPaneList.size(); i++) {
            double currentXCoordinate = classAssetPaneList.get(i).localToScene(classAssetPaneList.get(i).getBoundsInLocal()).getMinX();
            double currentYCoordinate = classAssetPaneList.get(i).localToScene(classAssetPaneList.get(i).getBoundsInLocal()).getMinY();
            Point2D coords = new Point2D(currentXCoordinate, currentYCoordinate);
            classCoordinates.add(coords);
        }
    }
}

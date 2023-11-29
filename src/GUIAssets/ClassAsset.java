
package GUIAssets;

import Attributes.Field;
import Attributes.Method;
import Class.Class;
import Relationships.Relationship;
import application.Application;
import application.GUI.GUIDiagramProject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private ArrayList<Field> fieldList = new ArrayList<>();
    public ArrayList<Method> methodList = new ArrayList<>();

    private ArrayList<String> fields;
    private ArrayList<String> methods;

    private double xOffset = 0;
    private double yOffset = 0;

    private int pos;

    public ClassAsset(final Class currentClass) {
        this.currentClass = currentClass;
    }

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
     * TODO: discard changes for editClass when user hits cancel (as of now, the local arraylists
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
        //this.classContainer.setOnMousePressed(this::onMousePressed);
        //this.classContainer.setOnMouseDragged(event -> onMouseDragged(event, classPaneArrayList, classAssets, classCoordinates, relationshipAssets));

        return this.classContainer;
    }



/*    private void onMousePressed(final MouseEvent event) {
        this.xOffset = event.getSceneX();
        this.yOffset = event.getSceneY();
    }*/
    /*
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
    */

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
        this.fieldList.addAll(currentClass.getFields());
        this.methodList.addAll(currentClass.getMethods());
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

        for(Field field : currentClass.getFields()) {
            fieldNameList.add(field.toString());
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

        for (Method method: currentClass.getMethods()) {
            methodNamesList.add(method.toString());
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
        editButton.setOnAction(e -> this.editClass(classPaneArrayList, classCoordinates, guiDiagramProject));

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

            ArrayList<Relationship> classRelationships = Application.getCurrentDiagram().getSingleClassRelationships(currentClass);
            for(Relationship relationship : classRelationships) {
                if(guiDiagramProject.getRelationshipAssetFromList(relationship) != null) {
                    guiDiagramProject.getRelationshipAssetFromList(relationship).deleteRelationship(guiDiagramProject.getRelationshipList(),
                            relationshipAssetLineList, guiDiagramProject.getRelationshipAssets(), relationshipCoordinates,
                            classAssetPaneList, classCoordinates, guiDiagramProject);
                }
            }

            Application.getCurrentDiagram().deleteClass(currentClass);

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
            guiDiagramProject.addClassPanes();
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
     * generic pop up box alert message for null values
     * @param methodName
     */

    public void nullAlertMessage(String methodName) {
        System.out.println("A parameter is null in " + methodName + "!");
        Alert nullAlert = new Alert(Alert.AlertType.ERROR);
        nullAlert.setContentText("A parameter is null in " + methodName + "!");
        nullAlert.showAndWait();
    }

    /**
     * delete attribute functionality for the editClass method
     * @param newFields
     * @param deletedFields
     * @param comboBox
     */
    public void deleteField(ArrayList<Field> newFields, ArrayList<Field> deletedFields, ComboBox<String> comboBox) {
        for (Field field : newFields) {
            String comboBoxName = comboBox.getValue();
            if (field.toString().equals(comboBoxName)) {
                deletedFields.add(field);
                newFields.remove(field); //add this to the "deleted array list"
                return;
            }
        }
    }

    public void deleteMethod(ArrayList<Method> newMethods, ArrayList<Method> deletedMethods, ComboBox<String> comboBox) {
        for (Method method : newMethods) {
            String comboBoxName = comboBox.getValue();
            if (method.toString().equals(comboBoxName)) {
                deletedMethods.add(method);
                newMethods.remove(method); //add this to the "deleted array list"
                return;
            }
        }
    }

    /**
     * allows the user to edit an existing class in a diagram
     * @param classAssetPaneList
     * @param classCoordinates
     * @param guiDiagramProject
     */
    private void editClass(final ArrayList<Pane> classAssetPaneList, final ArrayList<Point2D> classCoordinates, final GUIDiagramProject guiDiagramProject) {
        //local fields and methods list so changes won't be immediately applied when doing anything within menu
        if (classAssetPaneList == null || classCoordinates == null || guiDiagramProject == null) {
            nullAlertMessage("editClass");
        }

        ArrayList<Field> newFields = new ArrayList<>(currentClass.getFields());
        ArrayList<Field> deletedFields = new ArrayList<>();

        ArrayList<Method> newMethods = new ArrayList<>(currentClass.getMethods());
        ArrayList<Method> deletedMethods = new ArrayList<>();

        ArrayList<Relationship> currentRelationships = new ArrayList<>(Application.getCurrentDiagram().getSingleClassRelationships(this.currentClass));
        ArrayList<Relationship> deletedRelationships = new ArrayList<>();

        ObservableList<String> observableMethodsList = FXCollections.observableArrayList();
        ObservableList<String> observableFieldsList = FXCollections.observableArrayList();
        ObservableList<String> observableRelatinoshipsList = FXCollections.observableArrayList();

        Stage popUpStage = new Stage();
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.setWidth(640);
        popUpStage.setHeight(640);
        popUpStage.setResizable(false);
        Pane root = new Pane();
        Scene scene = new Scene(root, 640, 540);

        VBox miscText = new VBox();

        //notify user
        Text onSubmit = new Text();
        onSubmit.setText("*To delete, select from drop down menu and click appropriate 'Delete' button\n*'Submit' button to apply all changes" +
                "\t\t*'Cancel' button to discard or exit\n");

        //display deleted attributes
        Text deletedList = new Text();
        deletedList.setText("*To be deleted: " + deletedFields + " " + deletedMethods);

        miscText.getChildren().addAll(onSubmit, deletedList);
        miscText.setLayoutX(20);
        miscText.setLayoutY(scene.getHeight()-100);

        //edit class name
        Text currentName = new Text("Current class name: " + this.currentClass.getClassName());
        currentName.setLayoutX(80);
        currentName.setLayoutY(20);

        //text field
        HBox newNameAddContainer = new HBox();
        Text enterNewName = new Text("Enter new class name here");
        TextField newNameField = new TextField();
        newNameAddContainer.getChildren().addAll(newNameField, enterNewName);
        newNameAddContainer.setSpacing(20);
        newNameAddContainer.setLayoutX(80);
        newNameAddContainer.setLayoutY(50);


        //setup drop down menu for fields
        HBox fieldsHBox = new HBox();
        fieldsHBox.setSpacing(150);

        ComboBox<String> comboBoxFields = new ComboBox();

        fieldsHBox.getChildren().add(comboBoxFields);
        fieldsHBox.setLayoutX(scene.getWidth()/8);
        fieldsHBox.setLayoutY(scene.getHeight()-450);
        comboBoxFields.setValue("Fields");
        comboBoxFields.setPrefWidth(120);

        VBox fieldButtonContainer = new VBox();
        fieldButtonContainer.setSpacing(5);

        //edit field button
        Button editFieldButton = new Button();
        editFieldButton.setText("Edit Field");
        editFieldButton.setOnAction(e -> {
            for (Field field : newFields) {
                String comboBoxName = comboBoxFields.getValue();
                if (field.toString().equals(comboBoxName)) {
                    this.editField(newFields, field, comboBoxFields, observableFieldsList);
                    return;
                }
            }
        });

        //add field button
        Button addFieldButton = new Button();
        addFieldButton.setText("Add Field");
        addFieldButton.setOnAction(e -> this.addField(newFields, comboBoxFields, observableFieldsList));

        //delete field button
        Button deleteFieldButton = new Button();
        deleteFieldButton.setText("Delete Field");
        deleteFieldButton.setOnAction(e -> {
            this.deleteField(newFields, deletedFields, comboBoxFields);
            observableFieldsList.clear();
            for (Field field : newFields) {
                observableFieldsList.add(field.toString());
            }

            comboBoxFields.setItems(observableFieldsList);
            comboBoxFields.setValue("Fields");
            deletedList.setText("*To be deleted: " + deletedFields);
        });

        fieldButtonContainer.getChildren().addAll(editFieldButton, addFieldButton, deleteFieldButton);
        fieldsHBox.getChildren().addAll(fieldButtonContainer);

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
        methodsHBox.setLayoutY(scene.getHeight()-320);
        comboBoxMethods.setValue("Methods");
        comboBoxMethods.setPrefWidth(120);

        VBox methodsButtonContainer = new VBox();
        methodsButtonContainer.setSpacing(5);

        //edit method button
        Button editMethodButton = new Button();
        editMethodButton.setText("Edit Method");
        editMethodButton.setOnAction(e -> {
            for (Method method : newMethods) {
                String comboBoxName = comboBoxMethods.getValue();
                if (method.toString().equals(comboBoxName)) {
                    this.editMethod(newMethods, method, comboBoxMethods, observableMethodsList);
                    return;
                }
            }
        });

        //add method button
        Button addMethodButton = new Button();
        addMethodButton.setText("Add Method");
        addMethodButton.setOnAction(e -> this.addMethod(newMethods, comboBoxMethods, observableMethodsList));

        //delete method button
        Button deleteMethodButton = new Button();
        deleteMethodButton.setText("Delete Method");
        deleteMethodButton.setOnAction(e -> {
                    this.deleteMethod(newMethods, deletedMethods, comboBoxMethods);
                    observableMethodsList.clear();
                    for (Method method : newMethods) {
                        observableMethodsList.add(method.toString());
                    }
                    comboBoxMethods.setValue("Methods");
                    comboBoxMethods.setItems(observableMethodsList);
                    deletedList.setText("*To be deleted: " + deletedMethods);
                }
        );

        methodsButtonContainer.getChildren().addAll(editMethodButton, addMethodButton, deleteMethodButton);
        methodsHBox.getChildren().addAll(methodsButtonContainer);

        //setup drop down menu for Relationships
        HBox relationshipsHBox = new HBox();
        relationshipsHBox.setSpacing(150);
        ComboBox<String> comboBoxRelationships = new ComboBox();


        relationshipsHBox.getChildren().add(comboBoxRelationships);
        relationshipsHBox.setLayoutX(scene.getWidth()/8);
        relationshipsHBox.setLayoutY(scene.getHeight()-180);
        comboBoxRelationships.setValue("Relationships");
        comboBoxRelationships.setPrefWidth(140);

        updateRelationshipComboBox(currentRelationships, comboBoxRelationships, observableRelatinoshipsList);

        //delete relationship button
        Button deleteRelationshipButton = new Button();
        deleteRelationshipButton.setText("Delete Relationship");
        deleteRelationshipButton.setOnAction(e -> {
            for(Relationship relationship : currentRelationships) {
                String comboBoxName = comboBoxRelationships.getValue();
                if(relationship.getClass1().getClassName() == comboBoxName || relationship.getClass2().getClassName() ==comboBoxName){
                    currentRelationships.remove(relationship);
                    deletedRelationships.add(relationship);
                }
            }
        });

        relationshipsHBox.getChildren().add(deleteRelationshipButton);
        //Submit button
        HBox submitButtonContainer = new HBox();
        submitButtonContainer.setSpacing(50);
        Button submitButton = new Button();
        submitButton.setText("Submit");
        submitButton.setOnAction(e -> {

            boolean isUnique = true;

            for (Class currentClass : guiDiagramProject.getClassList()) {
                if (newNameField.getText().equals(currentClass.getClassName())) {
                    isUnique = false;
                }

            }

            if (isUnique) {
                if (!newNameField.getText().isEmpty()) {
                    this.currentClass.setClassName(newNameField.getText());
                }
            }
            //for fields and methods, we will clear the attributes list once and update with the local lists
            this.currentClass.getFields().clear();
            this.currentClass.getMethods().clear();
            this.currentClass.getFields().addAll(newFields);
            this.currentClass.getMethods().addAll(newMethods);
            //for relationships, we will clear the relationship list once and update with the local lists
            Application.getCurrentDiagram().getRelationshipList().clear();
            for(Relationship relationship : currentRelationships) {
                Application.getCurrentDiagram().addRelationship(relationship);
            }

            //apply deleted attributes
            for (Field deletedField : deletedFields) {
                this.currentClass.getFields().remove(deletedField);
            }

            for (Method deletedMethod : deletedMethods) {
                this.currentClass.getFields().remove(deletedMethod);
            }
            //apply deleted relationships
            for(Relationship deletedRelationship : deletedRelationships) {
                Application.getCurrentDiagram().deleteRelationship(deletedRelationship.getClass1(), deletedRelationship.getClass2());
                for(RelationshipAsset relationshipAsset : GUIDiagramProject.getRelationshipAssets()) {
                    if(relationshipAsset.getRelationship() == deletedRelationship) {
                        relationshipAsset.deleteRelationship( guiDiagramProject.getRelationshipList(),
                                GUIDiagramProject.getRelationshipLines(), GUIDiagramProject.getRelationshipAssets(),
                                guiDiagramProject.getRelationshipPanesCoordinates(), guiDiagramProject.getClassPanes(),
                                guiDiagramProject.getClassPanesCoordinates(), guiDiagramProject);
                    }
                }
            }

            //refresh the class asset panes and the window
            guiDiagramProject.addClassPanes();
            guiDiagramProject.addClassPanesToPaneWindow();
            popUpStage.close();
        });

        //Cancel button
        Button cancelButton = new Button();
        cancelButton.setText("Cancel");
        cancelButton.setOnAction(e ->
                {
                    newFields.clear();
                    newMethods.clear();
                    deletedFields.clear();
                    deletedMethods.clear();
                    deletedRelationships.clear();
                    popUpStage.close();
                }
        );

        submitButtonContainer.getChildren().addAll(submitButton, cancelButton);
        submitButtonContainer.setLayoutX(root.getWidth()/2-80);
        submitButtonContainer.setLayoutY(root.getHeight()-20);


        for (String method : this.methods) {
            observableMethodsList.add(method);
        }

        comboBoxMethods.setItems(observableMethodsList);

        Rectangle background = new Rectangle();
        background.setStyle("-fx-fill: lightblue; -fx-stroke: black; -fx-stroke-width: 1;");
        background.setWidth(scene.getWidth()-18);
        background.setHeight(scene.getHeight()-40);

        root.getChildren().addAll(background, currentName,newNameAddContainer,fieldsHBox, methodsHBox, relationshipsHBox,submitButtonContainer, miscText);

        popUpStage.setTitle("Class Editor");
        popUpStage.setScene(scene);
        popUpStage.show();

    }

    private void editField(final ArrayList<Field> newFieldsList, final Field field, final ComboBox<String> comboBoxFields, final ObservableList<String> observableListFields) {
        if (field == null) {
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
        currentNames.setText("current name: " + field.getName() + "\t\t\t\t" + "current type: " + field.getPrimitive());


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
                if (newFieldsList.size() > 1) {
                    for (int i = 1; i < newFieldsList.size(); i++) {
                        if (editNameField.getText().equals(newFieldsList.get(i).toString())) {
                            isUnique = false;
                        }
                    }
                }
                //handle unique name
                if (isUnique) {
                    field.setName(editNameField.getText());
                    field.setPrimitive(editPrimitiveField.getText());

                    this.updateFieldComboBox(newFieldsList, comboBoxFields, observableListFields);
                    popUpStage.close();
                } else {
                    Alert notUnique = new Alert(Alert.AlertType.WARNING);
                    notUnique.setContentText("A method with the same name and parameters already exists.");
                    notUnique.showAndWait();
                }

            } else {
                Alert notUnique = new Alert(Alert.AlertType.WARNING);
                notUnique.setContentText("No empty fields! (or hit cancel to exit)");
                notUnique.showAndWait();
            }


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

    /**
     * allows the user to edit an existing method in a class
     * @param newMethodsList
     * @param method
     * @param comboBoxMethods
     * @param observableMethodsList
     */
    private void editMethod(final ArrayList<Method> newMethodsList, final Method method,
                            final ComboBox<String> comboBoxMethods, final ObservableList<String> observableMethodsList) {

        ArrayList<String> newParameters = new ArrayList<>();

        if (method != null && !method.getParameters().isEmpty()) {
            newParameters.addAll(method.getParameters());
        }

        ArrayList<String> deletedParameters = new ArrayList<>();

        Stage popUpStage = new Stage();
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.setWidth(640);
        popUpStage.setHeight(480);
        popUpStage.setResizable(false);
        Pane root = new Pane();
        root.setStyle("-fx-background-color: lightblue");
        Scene scene = new Scene(root, 640, 480);

        //edit class name
        Text currentName = new Text("Current method name: \t\t\t\t\t" + method.getName());
        currentName.setLayoutX(80);
        currentName.setLayoutY(20);

        //display deleted attributes
        Text deletedList = new Text();
        deletedList.setText("To be deleted: " + deletedParameters);
        deletedList.setLayoutX(20);
        deletedList.setLayoutY(scene.getWidth()-260);

        //text field
        HBox newNameAddContainer = new HBox();
        Text enterNewName = new Text("Enter new method name here:");
        TextField newNameField = new TextField();
        newNameAddContainer.getChildren().addAll(enterNewName, newNameField);
        newNameAddContainer.setSpacing(80);
        newNameAddContainer.setLayoutX(80);
        newNameAddContainer.setLayoutY(50);

        //setup drop down menu for fields
        HBox parametersHBox = new HBox();
        parametersHBox.setSpacing(150);

        ComboBox<String> comboBoxParameters = new ComboBox();

        parametersHBox.getChildren().add(comboBoxParameters);
        parametersHBox.setLayoutX(scene.getWidth()/8);
        parametersHBox.setLayoutY(scene.getHeight()-350);
        comboBoxParameters.setValue("Parameters");
        comboBoxParameters.setPrefWidth(120);

        VBox parameterButtonContainer = new VBox();
        parameterButtonContainer.setSpacing(5);

        ObservableList<String> observableParameterList = FXCollections.observableArrayList();

        //store the parameters in the combobox
        observableParameterList.addAll(method.getParameters());

        //edit parameter button
        Button editParametersButton = new Button();
        editParametersButton.setText("Edit Parameter");
        editParametersButton.setOnAction(e -> {

            String comboBoxParamValue = comboBoxParameters.getValue();
            for (int i = 0; i < newParameters.size(); i++) {
                if (newParameters.get(i).equals(comboBoxParameters.getValue())) {
                    this.editParameter(method, newMethodsList, newParameters, newParameters.get(i), i, comboBoxParameters, observableParameterList);
                    return;
                }
            }
        });

        //add parameter button
        Button addParameterButton = new Button();
        addParameterButton.setText("Add Parameter");
        addParameterButton.setOnAction(e ->  {

                    this.addParameter(method, newMethodsList,newParameters, observableParameterList, comboBoxParameters);
                    //update combo box
                    //System.out.println("new parameters:" + newParameters);
                }
        );

        //delete parameter button
        Button deleteParameterButton = new Button();
        deleteParameterButton.setText("Delete Parameter");
        deleteParameterButton.setOnAction(e -> {
            for (String parameter : newParameters) {
                String comboBoxName = comboBoxParameters.getValue();
                if (parameter.equals(comboBoxName)) {
                    deletedParameters.add(parameter);
                    newParameters.remove(parameter);
                    this.updateParameterComboBox(newParameters, comboBoxParameters, observableParameterList);
                    deletedList.setText("To be deleted: " + deletedParameters);

                    return;
                }
            }
        });

        parameterButtonContainer.getChildren().addAll(editParametersButton, addParameterButton, deleteParameterButton);
        parametersHBox.getChildren().addAll(parameterButtonContainer);
        ObservableList<String> observableParametersList = FXCollections.observableArrayList();
        observableParametersList.addAll(method.getParameters());

        comboBoxParameters.setItems(observableParametersList);

        //Submit button
        HBox submitButtonContainer = new HBox();
        submitButtonContainer.setSpacing(50);
        Button submitButton = new Button();
        submitButton.setText("Submit");
        submitButton.setOnAction(e -> {

            //clear the attribute parameters list and add the new list (which will include previous parameters or if delete, not the previous params)
            Method tempMethod = new Method(method.getName());
            tempMethod.getParameters().addAll(newParameters);

            boolean isUnique = true;
            for (Method currentMethod : newMethodsList) {
                if (tempMethod.toString().equals(currentMethod.toString())) {
                    isUnique = false;
                    break;
                }
            }

            if (isUnique) {
                method.getParameters().clear();
                method.getParameters().addAll(newParameters);
                this.updateMethodComboBox(newMethodsList, comboBoxMethods, observableMethodsList);
                popUpStage.close();
            } else {
                if (!deletedParameters.isEmpty()) {
                    newParameters.addAll(deletedParameters);
                    deletedParameters.clear();
                    deletedList.setText("To be deleted: " + deletedParameters);
                    this.updateParameterComboBox(newParameters, comboBoxParameters, observableParameterList);


                }
                Alert notUnique = new Alert(Alert.AlertType.INFORMATION);
                notUnique.setContentText("Duplicate method or no changes to submit! (hit cancel)\n");
                notUnique.showAndWait();
            }
        });

        //Cancel button
        Button cancelButton = new Button();
        cancelButton.setText("Cancel");
        cancelButton.setOnAction(e -> popUpStage.close());

        submitButtonContainer.getChildren().addAll(submitButton, cancelButton);
        submitButtonContainer.setLayoutX(root.getWidth()/2-80);
        submitButtonContainer.setLayoutY(root.getHeight()-100);

        Text instructions = new Text("*Select a parameter from the drop down menu to edit\n" +
                "*All modifications to the method will update when clicking 'Submit\n" +
                "*To add a parameter click the 'add parameter' button\n" +
                "*To delete a parameter, select parameter in drop down menu, \n" +
                "and click 'delete parameter'");
        instructions.setLayoutX(80);
        instructions.setLayoutY(scene.getWidth()-390);

        comboBoxParameters.setItems(observableParameterList);
        root.getChildren().addAll(currentName,newNameAddContainer,parametersHBox, instructions, submitButtonContainer, deletedList);

        popUpStage.setTitle("Method Editor");
        popUpStage.setScene(scene);
        popUpStage.show();

    }

    public boolean checkForDuplicateMethod(final Method method, final ArrayList<Method> newMethodsList) {
        boolean isUnique = true;
        for (Method curMethod : newMethodsList) {
            if (method.toString().equals(curMethod.toString())) {
                isUnique = false;
                break;
            }
        }

        return isUnique;
    }


    /**
     * allows user to edit an existing parameter in a method
     * @param newParameters
     * @param paramName
     * @param index
     * @param menu
     * @param observableParametersList
     */

    private void editParameter(final Method method, final ArrayList<Method> newMethodsList, final ArrayList<String> newParameters,
                               final String paramName, final int index, ComboBox menu, final ObservableList<String> observableParametersList) {

        if (newParameters == null || paramName == null || menu == null || observableParametersList == null) {
            nullAlertMessage("editParameter");
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
        currentNames.setText("current name: " + paramName);

        HBox editParamContainer = new HBox();
        editParamContainer.setSpacing(20);
        Text editParamLabel = new Text();
        TextField editNameField = new TextField();
        editParamLabel.setText("Enter New Name:");
        editParamContainer.setLayoutX(50);
        editParamContainer.setLayoutY(scene.getHeight()/2-70);
        editParamContainer.getChildren().addAll(editParamLabel, editNameField);

        //container for submit and cancel button
        HBox submitContainer = new HBox();
        submitContainer.setLayoutX(scene.getWidth()/2-100);
        submitContainer.setLayoutY(150);
        submitContainer.setSpacing(20);
        //submit button
        Button submitButton = new Button();
        submitButton.setText("Submit");

        submitButton.setOnAction(e -> {
            if (!editNameField.getText().isEmpty()) {
                boolean isUnique = true;
                for (String param : newParameters) {
                    if (param.equals(editNameField.getText())) {
                        isUnique = false;
                    }
                }

                //check method
                Method tempMethod = new Method(method.getName());
                tempMethod.getParameters().addAll(newParameters);
                tempMethod.getParameters().set(index, editNameField.getText());

                for (int i = 0; i < newMethodsList.size(); i ++) {
                    if (tempMethod.toString().equals(newMethodsList.get(i).toString())) {
                        isUnique = false;
                    }
                }

                //handle unique name
                if (isUnique) {
                    newParameters.set(index, editNameField.getText()); //needs to discard if user hits cancel
                    this.updateParameterComboBox(newParameters, menu, observableParametersList);
                    popUpStage.close();
                } else {
                    Alert notUnique = new Alert(Alert.AlertType.WARNING);
                    notUnique.setContentText("A method with the same name and parameters already exists\nor a parameter of the same name already in method");
                    notUnique.showAndWait();
                }

            } else {
                Alert notUnique = new Alert(Alert.AlertType.WARNING);
                notUnique.setContentText("No empty fields! (or hit cancel to exit)");
                notUnique.showAndWait();
            }

        });
        //cancel button
        Button cancelButton = new Button();
        cancelButton.setText("Cancel");
        cancelButton.setOnAction(e -> popUpStage.close());

        submitContainer.getChildren().addAll(submitButton, cancelButton);

        root.getChildren().addAll(currentNames,editParamContainer, submitContainer);
        popUpStage.setTitle("Edit Parameter");
        popUpStage.setScene(scene);
        popUpStage.show();

    }

    /**
     * allows the user to add a parameter to an existing method
     * @param parametersList
     * @param observableParametersList
     * @param comboBoxParameters
     */
    private void addParameter(final Method method, final ArrayList<Method> newMethodsList, final ArrayList<String> parametersList,
                              final ObservableList<String> observableParametersList, final ComboBox comboBoxParameters) {
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
        currentNames.setText("*Enter a parameter name\n" +
                "*Parameter name needs to be unique");

        HBox addNameContainer = new HBox();
        addNameContainer.setSpacing(20);
        Text addParameterLabel = new Text();
        TextField addParameterField = new TextField();
        addParameterLabel.setText("Enter new name:");
        addNameContainer.setLayoutX(50);
        addNameContainer.setLayoutY(scene.getHeight()/2-70);

        addNameContainer.getChildren().addAll(addParameterLabel, addParameterField);

        //container for submit and cancel button
        HBox submitContainer = new HBox();
        submitContainer.setLayoutX(scene.getWidth()/2-100);
        submitContainer.setLayoutY(150);
        submitContainer.setSpacing(20);
        //submit button
        Button submitButton = new Button();
        submitButton.setText("Submit");

        submitButton.setOnAction(e -> {
            boolean isUnique = true;

            if (!addParameterLabel.getText().isEmpty()) {
                for (String parameter: parametersList) {
                    String param = addParameterField.getText();
                    if (parameter.equals(param)) {
                        isUnique = false;
                        break;
                    }
                }


                //check the methods list for duplicate method
                Method tempMethod = new Method(method.getName());
                tempMethod.getParameters().addAll(parametersList);
                tempMethod.getParameters().add(addParameterField.getText());

                for(Method currentMethod : newMethodsList) {
                    if (tempMethod.toString().equals(currentMethod.toString())) {
                        isUnique = false;
                        break;
                    }
                }

                //handle unique name
                if (isUnique) {
                    parametersList.add(addParameterField.getText());
                    //to test delete later
                    observableParametersList.clear();
                    observableParametersList.addAll(parametersList);
                    comboBoxParameters.setItems(observableParametersList);
                    popUpStage.close();
                } else {
                    parametersList.clear();
                    Alert notUnique = new Alert(Alert.AlertType.WARNING);
                    notUnique.setContentText("A method with the same name and parameters already exists\nPlease Enter a Unique Parameters!");
                    notUnique.showAndWait();
                }
            } else {
                Alert notUnique = new Alert(Alert.AlertType.WARNING);
                notUnique.setContentText("Please fill out the name field!");
                notUnique.showAndWait();
            }


            //this.printAttributeList();
        });
        //cancel button
        Button cancelButton = new Button();
        cancelButton.setText("Cancel");
        cancelButton.setOnAction(e -> popUpStage.close());

        submitContainer.getChildren().addAll(submitButton, cancelButton);

        root.getChildren().addAll(currentNames,addNameContainer, submitContainer);
        popUpStage.setTitle("Add Parameter");
        popUpStage.setScene(scene);
        popUpStage.show();
    }
    /**
     * description: method for add field button in edit class
     * @param newFieldList
     */

    public void addField(final ArrayList<Field> newFieldList, final ComboBox<String> comboBoxFields, final ObservableList<String> observableFieldsList) {

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
        currentNames.setText("Create a new Field");

        HBox addNameContainer = new HBox();
        addNameContainer.setSpacing(20);
        Text addFieldLabel = new Text();
        TextField addNameField = new TextField();
        addFieldLabel.setText("Enter new name:");
        addNameContainer.setLayoutX(50);
        addNameContainer.setLayoutY(scene.getHeight()/2-70);

        addNameContainer.getChildren().addAll(addFieldLabel, addNameField);

        //edit primitive type text field
        HBox addPrimitiveContainer = new HBox();
        addPrimitiveContainer.setSpacing(42);
        Text addPrimitiveLabel = new Text();
        TextField addPrimitiveField = new TextField();
        addPrimitiveLabel.setText("Add a type:");
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

        submitButton.setOnAction(e -> {
            boolean isUnique = true;
            for (Field currentField : this.fieldList) {
                if (currentField.getName().equals(addNameField.getText())) {
                    isUnique = false;
                }
            }

            for (Field currentField: newFieldList) {
                if (currentField.getName().equals(addNameField.getText())) {
                    isUnique = false;
                }
            }

            //handle unique name
            if (isUnique && !addNameField.getText().isEmpty() && !addPrimitiveField.getText().isEmpty()) {
                ArrayList<String> primitiveType = new ArrayList<>();
                primitiveType.add(addPrimitiveField.getText());
                newFieldList.add(new Field(addNameField.getText(), primitiveType.get(0)));

                //update combobox (turn into method later?)
                this.updateFieldComboBox(newFieldList, comboBoxFields, observableFieldsList);
                popUpStage.close();
            } else {
                Alert notUnique = new Alert(Alert.AlertType.WARNING);
                notUnique.setContentText("Please enter a unique field name and type!");
                notUnique.showAndWait();
            }

            this.printAttributeList();
        });
        //cancel button
        Button cancelButton = new Button();
        cancelButton.setText("Cancel");
        cancelButton.setOnAction(e ->
                popUpStage.close());

        submitContainer.getChildren().addAll(submitButton, cancelButton);

        root.getChildren().addAll(currentNames,addNameContainer, addPrimitiveContainer, submitContainer);
        popUpStage.setTitle("Add Field");
        popUpStage.setScene(scene);
        popUpStage.show();
    }

    /**
     * add method functionality for editClass method
     * @param newMethodsList
     * @param comboBoxMethod
     * @param observableMethodsList
     */
    public void addMethod(final ArrayList<Method> newMethodsList, final ComboBox<String> comboBoxMethod, final ObservableList<String> observableMethodsList) {
        Stage popUpStage = new Stage();
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.setWidth(426);
        popUpStage.setHeight(360);
        popUpStage.setResizable(false);
        Pane root = new Pane();
        root.setStyle("-fx-background-color: lightblue");
        Scene scene = new Scene(root, 426, 360);

        //new Name
        Text createAMethod = new Text();
        createAMethod.setLayoutX(20);
        createAMethod.setLayoutY(20);
        createAMethod.setText("*All changes will be applied when hitting the 'Submit' button\n" +
                "*Hit add to add a parameter to the method\n" +
                "*Duplicates of parameters aren't allowed\n" +
                "*Method can share a name with another method as long \n as the parameters are different");

        HBox addNameContainer = new HBox();
        addNameContainer.setSpacing(20);
        Text addNameLabel = new Text();
        TextField addNameField = new TextField();
        addNameLabel.setText("Enter a name:");
        addNameContainer.setLayoutX(50);
        addNameContainer.setLayoutY(scene.getHeight()-220);

        addNameContainer.getChildren().addAll(addNameLabel, addNameField);

        //add parameter
        Text displayAddedParameters = new Text();
        displayAddedParameters.setLayoutX(scene.getWidth()/2-200);
        displayAddedParameters.setLayoutY(scene.getHeight()-120);
        displayAddedParameters.setText("parameters: ");

        ArrayList<String> addedParameters = new ArrayList<>();

        HBox addParameterContainer = new HBox();
        addParameterContainer .setSpacing(8);
        Text addParameterLabel = new Text();
        TextField addParameterField = new TextField();
        addParameterLabel.setText("Add a parameter:");
        Button addParameterButton = new Button();
        addParameterButton.setText("add");
        addParameterContainer.setLayoutX(40);
        addParameterContainer.setLayoutY(scene.getHeight()-180);

        addParameterButton.setOnAction(e -> {
            boolean isUnique = true;
            if (!addParameterField.getText().isEmpty()) {
                for (String parameter : addedParameters) {
                    String currentParameter = addParameterField.getText();
                    if (parameter.equals(currentParameter)) {
                        isUnique = false;
                    }
                }

/*                Method tempMethod = new Method(method.getName());
                method.getParameters().addAll(addedParameters);
                for (Method currentMethod : newMethodsList) {
                    if (tempMethod.toString().equals(currentMethod)) {
                        isUnique = false;
                    }
                }*/

                if (isUnique) {
                    addedParameters.add(addParameterField.getText());
                    displayAddedParameters.setText("parameters: " + addedParameters);
                } else {
                    Alert notUnique = new Alert(Alert.AlertType.WARNING);
                    notUnique.setContentText("please enter a unique parameter name");
                    notUnique.showAndWait();
                }
            }
        });

        addParameterContainer.getChildren().addAll(addParameterLabel, addParameterField, addParameterButton);

        //container for submit and cancel button
        HBox submitContainer = new HBox();
        submitContainer.setLayoutX(scene.getWidth()/2-100);
        submitContainer.setLayoutY(scene.getHeight()-80);
        submitContainer.setSpacing(20);
        //submit button
        Button submitButton = new Button();
        submitButton.setText("Submit");

        submitButton.setOnAction(e -> {

            Method newMethod = new Method(addNameField.getText(), addedParameters);
            //Attribute container = attributeFactory.addAttribute(addNameField.getText(), addedParameters, Attribute.Type.METHOD);

            boolean isUnique = true;
            if (!addNameField.getText().isEmpty()) {
                //check the local methods list for duplicate methods
                for (Method currentMethod : newMethodsList) {
                    //take the to string from current index in attribute list and compare it to generate attribute
                    if (currentMethod.toString().equals(newMethod.toString())) {
                        isUnique = false;
                    }
                }
                //check the class attributes list for duplicate methods
                for (Method currentAttribute : this.methodList) {
                    //take the to string from current index in attribute list and compare it to generate attribute
                    if (currentAttribute.toString().equals(newMethod.toString())) {
                        isUnique = false;
                    }
                }
                //handle unique name
                if (isUnique) {
                    newMethodsList.add(newMethod);
                    this.updateMethodComboBox(newMethodsList, comboBoxMethod, observableMethodsList);
                    System.out.println("temp method list elements: " + newMethodsList); //to be removed
                    popUpStage.close();
                } else {
                    addedParameters.clear();
                    displayAddedParameters.setText("parameters: " + addedParameters);
                    Alert notUnique = new Alert(Alert.AlertType.WARNING);
                    notUnique.setContentText("Please enter a unique field name and type!");
                    notUnique.showAndWait();
                }
            } else{
                Alert isEmpty = new Alert(Alert.AlertType.WARNING);
                isEmpty.setContentText("Please enter a (unique) name.");
                isEmpty.showAndWait();
            }

            this.printAttributeList();
        });
        //cancel button
        Button cancelButton = new Button();
        cancelButton.setText("Cancel");
        cancelButton.setOnAction(e -> popUpStage.close());

        submitContainer.getChildren().addAll(submitButton, cancelButton);

        root.getChildren().addAll(createAMethod,addNameContainer, addParameterContainer, submitContainer, displayAddedParameters);
        popUpStage.setTitle("Add Method");
        popUpStage.setScene(scene);
        popUpStage.show();
    }

    /**
     * generic functionality for updating an attribute list for editClass method
     * @param newFieldList
     * @param AttributeComboBox
     * @param observableAttributeList
     */

    public void updateFieldComboBox(ArrayList<Field> newFieldList, ComboBox<String> AttributeComboBox, ObservableList<String> observableAttributeList) {
        observableAttributeList.clear();
        for (Field attribute : newFieldList) {
            observableAttributeList.add(attribute.toString());
        }

        AttributeComboBox.setItems(observableAttributeList);
    }

    public void updateMethodComboBox(ArrayList<Method> newMethodList, ComboBox<String> AttributeComboBox, ObservableList<String> observableAttributeList) {
        observableAttributeList.clear();
        for (Method method : newMethodList) {
            observableAttributeList.add(method.toString());
        }

        AttributeComboBox.setItems(observableAttributeList);
    }

    public void updateRelationshipComboBox(ArrayList<Relationship> currentRelationships, ComboBox<String> RelationshipComboBox,
                                           ObservableList<String> observableRelationshipList) {
        observableRelationshipList.clear();
        for(Relationship relationship : currentRelationships) {
            if(relationship.getClass1() == this.currentClass) {
                observableRelationshipList.add(relationship.getClass2().getClassName());
            }
            else {
                observableRelationshipList.add(relationship.getClass1().getClassName());
            }
        }

        RelationshipComboBox.setItems(observableRelationshipList);
    }

    /**
     * update parameter combo box functionality for editMethod
     * @param newParametersList
     * @param parameterComboBox
     * @param observableParametersList
     */
    public void updateParameterComboBox(final ArrayList<String> newParametersList, final ComboBox<String> parameterComboBox, final ObservableList<String> observableParametersList) {
        observableParametersList.clear();
        for (String parameter : newParametersList) {
            observableParametersList.add(parameter);
        }

        parameterComboBox.setItems(observableParametersList);
    }

    /**
     * testing method -- to be deleted
     */
    public void printAttributeList() {
        for (Field field : this.fieldList) {
            System.out.println(field.toString());
        }
        for (Method method : this.methodList) {
            System.out.println(method.toString());
        }
    }

    /**
     * functionality for saving coordinates for assets on diagram for modifying the diagram
     * @param classAssetPaneList
     * @param classCoordinates
     */
    public void updateCoordinates(final ArrayList<Pane> classAssetPaneList, final ArrayList<Point2D> classCoordinates) {
        classCoordinates.clear();

        for (int i = 0; i < classAssetPaneList.size(); i++) {
            double currentXCoordinate = classAssetPaneList.get(i).localToScene(classAssetPaneList.get(i).getBoundsInLocal()).getMinX();
            double currentYCoordinate = classAssetPaneList.get(i).localToScene(classAssetPaneList.get(i).getBoundsInLocal()).getMinY();
            Point2D coords = new Point2D(currentXCoordinate, currentYCoordinate);
            classCoordinates.add(coords);
        }
    }
}
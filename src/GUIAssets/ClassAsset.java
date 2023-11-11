package GUIAssets;
import Attributes.Attribute;
import Attributes.Field;
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
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.Attr;

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

    /**
     * Creates and returns a graphical representation of a class asset within a GUI diagram project.
     *
     * @param classList          The list of classes in the project.
     * @param classPaneArrayList      The list of panes associated with class assets.
     * @param classAssets        The list of class assets.
     * @param classCoordinates        A list of coordinates representing the positions of class assets.
     * @param relationshipPaneArrayList     The list of panes associates with relationship assets
     * @param relationshipCoordinates      A list of coordinates representing the positions of relationship assets
     * @param guiDiagramProject  The GUI diagram project that manages the graphical elements
     * @return                  A JavaFX Pane representing the class asset.
     */


    public Pane createClassAsset(final ArrayList<Class> classList, final ArrayList<Pane> classPaneArrayList, final ArrayList<ClassAsset> classAssets,
                                 final ArrayList<Point2D> classCoordinates, final ArrayList<Pane> relationshipPaneArrayList,
                                 final ArrayList<Point2D> relationshipCoordinates, final GUIDiagramProject guiDiagramProject) {
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
                classAssets, classCoordinates, relationshipPaneArrayList, relationshipCoordinates, guiDiagramProject);

        this.classContainer.getChildren().add(textContainer);
        this.classContainer.setOnMousePressed(this::onMousePressed);
        this.classContainer.setOnMouseDragged(this::onMouseDragged);

        return this.classContainer;
    }


    private void onMousePressed(final MouseEvent event) {
        this.xOffset = event.getSceneX();
        this.yOffset = event.getSceneY();
    }

    private void onMouseDragged(final MouseEvent event) {
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
     * @param relationshipPaneArrayList
     * @param relationshipCoordinates
     * @param guiDiagramProject
     * @return
     */


    public HBox setUpButtons(final String fontType, final int textSize, final Insets margins, final ArrayList<Class> classList,
                             final ArrayList<Pane> classPaneArrayList, final ArrayList<ClassAsset> classAssets,
                             final ArrayList<Point2D> classCoordinates, final ArrayList<Pane> relationshipPaneArrayList,
                             final ArrayList<Point2D> relationshipCoordinates, final GUIDiagramProject guiDiagramProject){

        HBox buttonContainer = new HBox();
        buttonContainer.setSpacing(120.0);

        Button editButton = new Button("Edit");
        editButton.setFont(Font.font(fontType, textSize));
        editButton.setOnAction(e -> this.editClass(classList, classPaneArrayList, classAssets, classCoordinates, guiDiagramProject));

        Button deleteButton = new Button("Delete");
        deleteButton.setFont(Font.font(fontType, textSize));

        deleteButton.setOnAction(e -> this.deleteClass(classList, classPaneArrayList, classAssets, classCoordinates,
                relationshipPaneArrayList, relationshipCoordinates, guiDiagramProject));

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
     * @param relationshipPaneArrayList
     * @param relationshipCoordinates
     * @param guiDiagramProject
     * @return
     */

    public VBox setupTextContainer(final String fontType, final int textSize, final Insets margins, final ArrayList<Class> classList,
                                   final ArrayList<Pane> classPaneArrayList, final ArrayList<ClassAsset> classAssets,
                                   final ArrayList<Point2D> classCoordinates, final ArrayList<Pane> relationshipPaneArrayList,
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
                classAssets, classCoordinates, relationshipPaneArrayList, relationshipCoordinates, guiDiagramProject);

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
     * @param relationshipAssetPaneList     the list of panes associated with the relationship assets.
     * @param relationshipCoordinates   A list of coordinates representing the positions of relationship assets
     * @param guiDiagramProject    The GUI diagram project that manages the graphical elements.
     */

    public void deleteClass(final ArrayList<Class> classList, final ArrayList<Pane> classAssetPaneList, final ArrayList<ClassAsset> classAssets,
                            final ArrayList<Point2D> classCoordinates, final ArrayList<Pane> relationshipAssetPaneList,
                            final ArrayList<Point2D> relationshipCoordinates, final GUIDiagramProject guiDiagramProject) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setContentText("Are you sure you want to delete this class and it's relationships?");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
            System.out.println("class and it's relationships deleted");
            //remove the class from the class list first
            classList.remove(this.pos);

            ArrayList<Relationship> classRelationships = guiDiagramProject.getDiagram().getSingleClassRelationships(currentClass);
            for(Relationship relationship : classRelationships) {
                if(guiDiagramProject.getRelationshipAssetFromList(relationship) != null) {
                    guiDiagramProject.getRelationshipAssetFromList(relationship).deleteRelationship(guiDiagramProject.getRelationshipList(),
                            relationshipAssetPaneList, guiDiagramProject.getRelationshipAssets(), relationshipCoordinates,
                            classAssetPaneList, classCoordinates, guiDiagramProject);
                }
            }

            guiDiagramProject.getDiagram().deleteClass(currentClass);

            //remove the class pane from the pane list next
            classAssetPaneList.remove(this.pos);

            relationshipCoordinates.clear();
            classCoordinates.clear();

            //get the x/y positions from the remaining class asset panes

            this.updateCoordinates(classAssetPaneList, classCoordinates);

            for (int i = 0; i < relationshipAssetPaneList.size(); i++) {
                double currentXCoordinate = relationshipAssetPaneList.get(i).localToScene(relationshipAssetPaneList.get(i).getBoundsInLocal()).getMinX();
                double currentYCoordinate = relationshipAssetPaneList.get(i).localToScene(relationshipAssetPaneList.get(i).getBoundsInLocal()).getMinY();
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
    private void editClass(ArrayList<Class> classList, ArrayList<Pane> classAssetPaneList, ArrayList<ClassAsset> classAssets,
                           ArrayList<Point2D> classCoordinates, GUIDiagramProject guiDiagramProject ) {

        ArrayList<Attribute> newFields = new ArrayList<>();
        ArrayList<Attribute> newMethods = new ArrayList<>();


        Stage popUpStage = new Stage();
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.setWidth(640);
        popUpStage.setHeight(480);
        popUpStage.setResizable(false);
        Pane root = new Pane();
        Scene scene = new Scene(root, 640, 480);

        //edit class name
        Text currentName = new Text("Current class name: " + this.currentClass.getClassName());
        currentName.setLayoutX(80);
        currentName.setLayoutY(20);

        // -- text field
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
        fieldsHBox.setLayoutY(scene.getHeight()-350);
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
        addFieldButton.setOnAction(e -> this.addField(newFields));

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
        methodsHBox.setLayoutY(scene.getHeight()-220);
        comboBoxMethods.setValue("Methods");
        comboBoxMethods.setPrefWidth(120);

        VBox methodsButtonContainer = new VBox();
        methodsButtonContainer.setSpacing(5);
        Text fields = new Text();
        fields.setText("Fields");
        fields.setFont(Font.font("Verdana", 24));
        fields.setLayoutX(scene.getWidth()/6-28);
        fields.setLayoutY(scene.getHeight()-360);

        Text methods = new Text();
        methods.setText("Methods");
        methods.setLayoutX(scene.getWidth()/6-28);
        methods.setLayoutY(scene.getHeight()-240);
        methods.setFont(Font.font("Verdana", 24));

        //edit method button
        Button editMethodButton = new Button();
        editMethodButton.setText("Edit Method");
        editMethodButton.setOnAction(e -> {
            for (Attribute attribute : this.attributeList) {
                String comboBoxName = comboBoxMethods.getValue();
                if (attribute.toString().equals(comboBoxName)) {
                    this.editMethod(attribute);
                    return;
                }
            }
        });

        //add method button
        Button addMethodButton = new Button();
        addMethodButton.setText("Add Method");
        addMethodButton.setOnAction(e -> this.addMethod(newMethods));

        //delete method button
        Button deleteMethodButton = new Button();
        deleteMethodButton.setText("Delete Method");

        methodsButtonContainer.getChildren().addAll(editMethodButton, addMethodButton, deleteMethodButton);
        methodsHBox.getChildren().addAll(methodsButtonContainer);

        //Update button
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

            if (!newFields.isEmpty()) {
                this.currentClass.getAttributes().addAll(newFields);
            }

            if (!newMethods.isEmpty()) {
                this.currentClass.getAttributes().addAll(newMethods);
            }

            this.updateCoordinates(classAssetPaneList, classCoordinates);
            //update the class asset list by taking the new class list and creating new class assets from them
            //this.updateClassAssetListPos(classList, classAssets);
            //refresh the class asset panes and the window
            //guiDiagramProject.refreshClassPanes();
            //guiDiagramProject.refreshClassPanesToPaneWindow();
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

        root.getChildren().addAll(background, currentName,newNameAddContainer,fieldsHBox, methodsHBox, submitButtonContainer, fields, methods);

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

    private void editMethod(Attribute attribute) {

        ArrayList<String> newParameters = new ArrayList<>();

        if (attribute != null && !attribute.getParameters().isEmpty()) {
            newParameters.addAll(attribute.getParameters());
        }

        Stage popUpStage = new Stage();
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.setWidth(640);
        popUpStage.setHeight(480);
        popUpStage.setResizable(false);
        Pane root = new Pane();
        root.setStyle("-fx-background-color: lightblue");
        Scene scene = new Scene(root, 640, 480);


        //edit class name
        Text currentName = new Text("Current method name: \t\t\t\t" + this.currentClass.getClassName());
        currentName.setLayoutX(80);
        currentName.setLayoutY(20);

        // -- text field
        HBox newNameAddContainer = new HBox();
        Text enterNewName = new Text("Enter new method name here");
        TextField newNameField = new TextField();
        newNameAddContainer.getChildren().addAll(newNameField, enterNewName);
        newNameAddContainer.setSpacing(20);
        newNameAddContainer.setLayoutX(80);
        newNameAddContainer.setLayoutY(50);


        //setup drop down menu for fields
        HBox parametersHBox = new HBox();
        parametersHBox.setSpacing(150);

        ComboBox<String> comboBoxParameters = new ComboBox();

        parametersHBox.getChildren().add(comboBoxParameters );
        parametersHBox.setLayoutX(scene.getWidth()/8);
        parametersHBox.setLayoutY(scene.getHeight()-350);
        comboBoxParameters.setValue("Parameters");
        comboBoxParameters.setPrefWidth(120);

        VBox parameterButtonContainer = new VBox();
        parameterButtonContainer.setSpacing(5);

        ObservableList<String> observableParameterList = FXCollections.observableArrayList();

        //store the parameters in the combobox
        for (String method : attribute.getParameters()) {
            observableParameterList.add(method);
        }


        //edit parameter button
        Button editParametersButton = new Button();
        editParametersButton.setText("Edit Parameter");
        editParametersButton.setOnAction(e -> {

            for (int i = 0; i < attribute.getParameters().size(); i++) {
                String comboBoxName = comboBoxParameters.getValue();
                if (attribute.getParameters().get(i).equals(comboBoxName)) {
                    this.editParameter(attribute, attribute.getParameters().get(i).toString(), i, comboBoxParameters, observableParameterList);
                    return;
                }
            }
        });

        //add parameter button
        Button addParameterButton = new Button();
        addParameterButton.setText("Add Parameter");
        //addParameterButton.setOnAction(e -> this.addField(newParameters));

        //delete parameter button
        Button deleteParameterButton = new Button();
        deleteParameterButton.setText("Delete Parameter");
        deleteParameterButton.setOnAction(e -> {
            for (String parameter : attribute.getParameters()) {
                String comboBoxName = comboBoxParameters.getValue();
                if (attribute.toString().equals(comboBoxName)) {
                    //this.deleteParameter(attribute);
                    return;
                }
            }
        });

        parameterButtonContainer.getChildren().addAll(editParametersButton, addParameterButton, deleteParameterButton);
        parametersHBox.getChildren().addAll(parameterButtonContainer);
        ObservableList<String> observableParametersList = FXCollections.observableArrayList();
        for (String parameter: attribute.getParameters()) {
            observableParametersList.add(parameter);
        }

        comboBoxParameters.setItems(observableParametersList);

        //Submit button
        HBox submitButtonContainer = new HBox();
        submitButtonContainer.setSpacing(50);
        Button submitButton = new Button();
        submitButton.setText("Submit");
        submitButton.setOnAction(e -> {
            //clear the attribute parameters list and add the new list (which will include previous parameters or if delete, not the previous params)
            attribute.getParameters().clear();
            attribute.getParameters().addAll(newParameters);

            boolean isUnique = true;

            popUpStage.close();
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
        instructions.setLayoutY(260);

        comboBoxParameters.setItems(observableParameterList);
        root.getChildren().addAll(currentName,newNameAddContainer,parametersHBox, instructions, submitButtonContainer);



        popUpStage.setTitle("Method Editor");
        popUpStage.setScene(scene);
        popUpStage.show();

    }

    private void editParameter(final Attribute attribute , final String paramName, final int index, ComboBox menu, ObservableList<String> menuItems) {
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
                for (String param : attribute.getParameters()) {
                    if (param.equals(editNameField.getText())) {
                        isUnique = false;
                    }
                }
                //handle unique name
                if (isUnique) {
                    attribute.getParameters().set(index, editNameField.getText()); //needs to discard if user hits cancel
                    menuItems.clear();
                    for (String method : attribute.getParameters()) {
                        menuItems.add(method);
                    }

                    menu.setItems(menuItems);
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

        root.getChildren().addAll(currentNames,editParamContainer, submitContainer);
        popUpStage.setTitle("Edit Parameter");
        popUpStage.setScene(scene);
        popUpStage.show();

    }


    /**
     * description: method for add field button in edit class
     * @param newFieldList
     */

    public void addField(ArrayList<Attribute> newFieldList) {

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

        Attribute newAttribute = new Attribute();

        submitButton.setOnAction(e -> {
            boolean isUnique = true;
            for (Attribute currentAttribute : this.attributeList) {
                if (currentAttribute.getName().equals(addNameField.getText())) {
                    isUnique = false;
                }
            }

            for (Attribute currentAttribute: newFieldList) {
                if (currentAttribute.getName().equals(addNameField.getText())) {
                    isUnique = false;
                }
            }

            //handle unique name
            if (isUnique && !addNameField.getText().isEmpty() && !addPrimitiveField.getText().isEmpty()) {
                ArrayList<String> primitiveType = new ArrayList<>();
                primitiveType.add(addPrimitiveField.getText());
                newFieldList.add(newAttribute.addAttribute(addNameField.getText(), primitiveType, Attribute.Type.FIELD));
                //to test delete later
                System.out.println(newFieldList);
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



    public void addMethod(ArrayList<Attribute> newMethodsList) {
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
        createAMethod.setText("Create a new method");

        HBox addNameContainer = new HBox();
        addNameContainer.setSpacing(20);
        Text addNameLabel = new Text();
        TextField addNameField = new TextField();
        addNameLabel.setText("Enter a name:");
        addNameContainer.setLayoutX(50);
        addNameContainer.setLayoutY(scene.getHeight()/2-70);

        addNameContainer.getChildren().addAll(addNameLabel, addNameField);

        //edit primitive type text field
        HBox addParameterContainer = new HBox();
        addParameterContainer .setSpacing(42);
        Text addPrimitiveLabel = new Text();
        TextField addPrimitiveField = new TextField();
        addPrimitiveLabel.setText("Add a parameter:");
        addParameterContainer .setLayoutX(60);
        addParameterContainer .setLayoutY(scene.getHeight()/2-20);

        addParameterContainer .getChildren().addAll(addPrimitiveLabel, addPrimitiveField);

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
            if (isUnique && !addNameField.getText().isEmpty() && !addPrimitiveField.getText().isEmpty()) {
                ArrayList<String> primitiveType = new ArrayList<>();
                primitiveType.add(addPrimitiveField.getText());
                //this.attributeList.add(newAttribute.addAttribute(addNameField.getText(), primitiveType, Attribute.Type.METHOD));
                //to test delete later
                System.out.println(newMethodsList);
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

        root.getChildren().addAll(createAMethod,addNameContainer, addParameterContainer, submitContainer);
        popUpStage.setTitle("Add Method");
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

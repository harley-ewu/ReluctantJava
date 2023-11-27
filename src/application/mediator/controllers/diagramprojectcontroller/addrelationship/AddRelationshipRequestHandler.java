package application.mediator.controllers.diagramprojectcontroller.addrelationship;

import Diagram.Diagram;
import Class.Class;
import Relationships.Relationship;
import application.Application;
import application.GUI.GUIDiagramProject;
import application.GUI.GraphicalUserInterface;
import application.mediator.common.IHandler;
import application.mediator.common.Request;
import application.mediator.controllers.updateviewcontroller.UpdateViewController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddRelationshipRequestHandler implements IHandler {
    public Void handle(Request request){
        System.out.println("adding a new relationship!");
        Stage popupStage = new Stage();
        Diagram diagram = Application.getCurrentDiagram();
        GUIDiagramProject view = GraphicalUserInterface.getDiagramView();

        HBox setRelationshipType = new HBox();
        HBox classOneBox = new HBox();
        HBox classTwoBox = new HBox();
        HBox cardinalitiesBox = new HBox();
        HBox ownerBox = new HBox();
        HBox buttonsBox = new HBox();

        TextField cardinalityOfClassOne = new TextField();
        TextField cardinalityOfClassTwo = new TextField();

        ChoiceBox<String> firstClass = new ChoiceBox<>();
        ChoiceBox<String> secondClass = new ChoiceBox<>();
        //ChoiceBox<String> owner = new ChoiceBox<>();

        Label firstClassLabel = new Label("Pick Class 1:");
        Label secondClassLabel = new Label("Pick Class 2:");
        Label relationshipLabel = new Label("Owner of Relationship:");
        Label classOneCardLabel = new Label("Class 1's cardinality:");
        Label classTwoCardLabel = new Label("Class 2's cardinality:");
        Label explainationOfCard = new Label("(Use -1 to represent a relationship of many)");

        Button submitBtn = new Button("Submit");
        Button cancelBtn = new Button("Cancel");

        ToggleGroup radioBtns = new ToggleGroup();
        RadioButton realization = new RadioButton("Realization");
        RadioButton aggregation = new RadioButton("Aggregation");
        RadioButton composition = new RadioButton("Composition");
        RadioButton inheritance = new RadioButton("Inheritance");

        realization.setToggleGroup(radioBtns);
        realization.setUserData("REALIZATION");
        aggregation.setToggleGroup(radioBtns);
        aggregation.setUserData("AGGREGATION");
        composition.setToggleGroup(radioBtns);
        composition.setUserData("COMPOSITION");
        inheritance.setToggleGroup(radioBtns);
        inheritance.setUserData("INHERITANCE");

        ToggleGroup ownerBtns = new ToggleGroup();
        RadioButton classOneOwner = new RadioButton("Class 1:");
        RadioButton classTwoOwner = new RadioButton("Class 2:");

        classOneOwner.setToggleGroup(ownerBtns);
        classOneOwner.setUserData("ClassOne");
        classTwoOwner.setToggleGroup(ownerBtns);
        classTwoOwner.setUserData("ClassTwo");

        setRelationshipType.setSpacing(10);
        setRelationshipType.getChildren().addAll(
                realization,
                aggregation,
                composition,
                inheritance
        );

        classOneBox.setSpacing(10);
        classOneBox.getChildren().addAll(firstClassLabel, firstClass);

        classTwoBox.setSpacing(10);
        classTwoBox.getChildren().addAll(secondClassLabel, secondClass);

        ownerBox.setSpacing(10);
        ownerBox.getChildren().addAll(relationshipLabel, classOneOwner, classTwoOwner);

        buttonsBox.setSpacing(10);
        buttonsBox.getChildren().addAll(submitBtn, cancelBtn);

        cardinalitiesBox.setSpacing(10);
        cardinalitiesBox.getChildren().addAll(
                classOneCardLabel,
                cardinalityOfClassOne,
                classTwoCardLabel,
                cardinalityOfClassTwo,
                explainationOfCard
        );

        for(Class umlClass : diagram.getClassList().values()) {
            firstClass.getItems().add(umlClass.getClassName());
            secondClass.getItems().add(umlClass.getClassName());
        }

        //Save for when the owner of a class is Class and not Boolean
        /*
        firstClass.setOnAction(e -> {
            owner.getItems().add(firstClass.getValue());
        });

        secondClass.setOnAction(e -> {
            owner.getItems().add(secondClass.getValue());
        });*/

        submitBtn.setOnAction(e -> {
            //System.out.println(radioBtns.getSelectedToggle().getUserData());
            Relationship.RelationshipType relationshipType = null;
            Class classOne = null;
            Class classTwo = null;
            boolean ownerClass = false;
            int classOneCard = 0;
            int classTwoCard = 0;
            Relationship relationship;

            if(firstClass.getValue() == null || secondClass.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Must Select Classes Warning");
                alert.setContentText("You must select two classes for the relationship.");
                alert.setWidth(300);
                alert.showAndWait();
            }else if (firstClass.getValue().equals(secondClass.getValue())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cannot Select Same Class Warning");
                alert.setContentText("You cannot add a relationship to the same class");
                alert.setWidth(300);
                alert.showAndWait();
            }else if(radioBtns.getSelectedToggle() == null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Relationship Type Warning");
                alert.setContentText("You must select a relationship type.");
                alert.setWidth(300);
                alert.showAndWait();
            }else if(ownerBtns.getSelectedToggle() == null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Owning Class Warning");
                alert.setContentText("You must choose an owner for the relationship.");
                alert.setWidth(300);
                alert.showAndWait();
            }else{
                String type = (String) radioBtns.getSelectedToggle().getUserData();
                switch (type) {
                    case "REALIZATION" -> relationshipType = Relationship.RelationshipType.Realization;
                    case "INHERITANCE" -> relationshipType = Relationship.RelationshipType.Inheritance;
                    case "AGGREGATION" -> relationshipType = Relationship.RelationshipType.Aggregation;
                    case "COMPOSITION" -> relationshipType = Relationship.RelationshipType.Composition;
                    default -> relationshipType = null;
                }

                String owner = (String) ownerBtns.getSelectedToggle().getUserData();
                switch (owner) {
                    case "ClassOne" -> ownerClass = true;
                    case "ClassTwo" -> ownerClass = false;
                }

                try{
                    classOneCard = Integer.parseInt(cardinalityOfClassOne.getText());
                    classTwoCard = Integer.parseInt(cardinalityOfClassTwo.getText());
                }catch(NumberFormatException numberFormatException){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Cardinality Warning");
                    alert.setContentText("You can only enter a numbers for the cardinality and they cannot be empty.");
                    alert.setWidth(300);
                    alert.showAndWait();
                    return;
                }

                try{
                    classOne = diagram.getClassList().get(firstClass.getValue());
                    classTwo = diagram.getClassList().get(secondClass.getValue());
                }catch(Exception didNotSelectClasses){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setContentText("The one or both of the classes selected do not exist in the project.");
                    alert.setWidth(300);
                    alert.showAndWait();
                    return;
                }

                //ownerClass = diagram.getClassList().get(owner.getValue());
                try{
                    relationship = new Relationship(relationshipType,classOne, classTwo, classOneCard, classTwoCard, ownerClass);
                    diagram.addRelationship(relationship);
                    UpdateViewController.updateAddRelationship(view, relationship);
                    popupStage.close();
                }catch(Exception errorMakingRelationship){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setContentText("There was an error creating the relationship.");
                    alert.setWidth(300);
                    alert.showAndWait();
                }

            }
        });

        cancelBtn.setOnAction(e -> {
            popupStage.close();
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(
                classOneBox,
                classTwoBox,
                ownerBox,
                setRelationshipType,
                cardinalitiesBox,
                buttonsBox
        );

        Scene scene = new Scene(layout, 800, 250);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(scene);
        popupStage.setTitle("Add Relationship");
        popupStage.show();

        return null;
    }
}

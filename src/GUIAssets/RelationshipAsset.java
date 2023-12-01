package GUIAssets;

import Class.Class;
import Relationships.Relationship;
import application.Application;
import application.GUI.GUIDiagramProject;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class RelationshipAsset {

    private Relationship currentRelationship;
    private Line relationshipContainer;
    private double xOffset = 0;
    private double yOffset = 0;

    private double xCoordinate = 0;
    private double yCoordinate = 0;

    private int index;

    public RelationshipAsset(Relationship currentRelationship, int index) {
        this.currentRelationship = currentRelationship;
        this.index = index;
    }

    public Relationship getRelationship() {
        return this.currentRelationship;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    public Line createRelationshipAsset(final ArrayList<Relationship> relationshipList, final ArrayList<Line> relationshipAssetLineList,
                                        final ArrayList<RelationshipAsset> relationshipAssets, final ArrayList<Point2D> relationshipCoordinates,
                                        final ArrayList<Pane> classAssetPaneList, final ArrayList<Point2D> classCoordinates, final ArrayList<ClassAsset> classAssets) {
        int textSize = 12;
        String fontType = "Verdana";

        this.relationshipContainer = new Line();
        this.relationshipContainer.setStroke(Color.BLACK);
        this.relationshipContainer.setStrokeWidth(5);

        Class ownerClass;
        Class otherClass;
        if(this.currentRelationship.getIsOwner()) {
            ownerClass = this.currentRelationship.getClass1();
            otherClass = this.currentRelationship.getClass2();
        }
        else {
            ownerClass = this.currentRelationship.getClass2();
            otherClass = this.currentRelationship.getClass1();
        }

        int ownerIndex = -1;
        int otherIndex = -1;

        //issue with for loop
        for(ClassAsset classAsset : classAssets) {
            if(classAsset.getCurrentClass().getClassName().equals(ownerClass.getClassName())) {
                ownerIndex = classAssets.indexOf(classAsset);
            }
            if(classAsset.getCurrentClass().getClassName().equals(otherClass.getClassName())) {
                otherIndex = classAssets.indexOf(classAsset);
            }
        }

        classCoordinates.clear();

        for (int i = 0; i < classAssetPaneList.size(); i++) {
            double currentXCoordinate = classAssetPaneList.get(i).localToParent(classAssetPaneList.get(i).getBoundsInLocal()).getCenterX();
            double currentYCoordinate = classAssetPaneList.get(i).localToParent(classAssetPaneList.get(i).getBoundsInLocal()).getCenterY();
            Point2D coords = new Point2D(currentXCoordinate, currentYCoordinate);
            classCoordinates.add(coords);
        }

        relationshipContainer.toBack();

        this.relationshipContainer.setStartX(classCoordinates.get(ownerIndex).getX());
        this.relationshipContainer.setStartY(classCoordinates.get(ownerIndex).getY());

        this.relationshipContainer.setEndX(classCoordinates.get(otherIndex).getX());
        this.relationshipContainer.setEndY(classCoordinates.get(otherIndex).getY());

        relationshipContainer.toBack();

        return this.relationshipContainer;
    }

    /**
     * description: setup for the contents of the relationship
     * @param fontType
     * @param textSize
     * @param margins
     * @param relationshipList
     * @param relationshipAssetLineList
     * @param relationshipAssets
     * @param relationshipCoordinates
     * @param classAssetPaneList
     * @param classCoordinates
     * @param guiDiagramProject
     * @return
     */
    public VBox setupTextContainer(final String fontType, final int textSize, final Insets margins, final ArrayList<Relationship> relationshipList,
                                   final ArrayList<Line> relationshipAssetLineList, final ArrayList<RelationshipAsset> relationshipAssets,
                                   final ArrayList<Point2D> relationshipCoordinates, final ArrayList<Pane> classAssetPaneList,
                                   final ArrayList<Point2D> classCoordinates, final GUIDiagramProject guiDiagramProject) {
        VBox textContainer = new VBox();
        Text relationshipInfo = new Text();
        relationshipInfo.setFont(Font.font(fontType, textSize));

        relationshipInfo.setText(this.currentRelationship.toString());
        VBox.setMargin(relationshipInfo, margins);

        return textContainer;
    }

    public double getCurrentX() {
        return this.relationshipContainer.localToScene(this.relationshipContainer.getBoundsInLocal()).getMinX();
    }

    public double getCurrentY() {
        return this.yCoordinate = this.relationshipContainer.localToScene(this.relationshipContainer.getBoundsInLocal()).getMinY();
    }

    /**
     * This method is used to delete a class and its associated assets from a GUI diagram project.
     *
     * @param relationshipList            The list of relationships in the project.
     * @param relationshipAssetLineList     the list of panes associated with the relationship assets.
     * @param relationshipAssets          The list of class assets.
     * @param relationshipCoordinates   A list of coordinates representing the positions of relationship assets
     * @param classAssetPaneList   The list of panes associated with the class assets.
     * @param classCoordinates          A list of coordinates representing the positions of class assets.
     * @param guiDiagramProject    The GUI diagram project that manages the graphical elements.
     */
    //TODO: fix deleteRelationship when a Class Pane is deleted or when a Single Relationship is deleted in Class Pane edit button
    public void deleteRelationship(final ArrayList<Relationship> relationshipList, final ArrayList<Line> relationshipAssetLineList,
                                   final ArrayList<RelationshipAsset> relationshipAssets, final ArrayList<Point2D> relationshipCoordinates,
                                   final ArrayList<Pane> classAssetPaneList, final ArrayList<Point2D> classCoordinates,
                                   final GUIDiagramProject guiDiagramProject) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setContentText("Are you sure you want to delete this relationship?");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
            System.out.println("relationship deleted");
            //remove the relationship from the relationship list first
            relationshipList.remove(this.currentRelationship);

            Application.getCurrentDiagram().deleteRelationship(currentRelationship.getClass1(), currentRelationship.getClass2());

            //remove the relationship line from the line list next
            relationshipAssetLineList.remove(this.relationshipContainer);
            relationshipAssets.remove(this);

            //update the class asset list by taking the new class list and creating new class assets from them
            //this.updateRelationshipAssetListIndex(relationshipList, relationshipAssets); //to be removed

            for(RelationshipAsset relationshipAsset : relationshipAssets) {
                RelationshipAsset.updateRelationshipLines(relationshipAsset, classAssetPaneList, classCoordinates, guiDiagramProject.getClassAssets());
            }

            //refresh the class asset panes and the window
            guiDiagramProject.refreshRelationshipLines();
            guiDiagramProject.refreshRelationshipLinesToPaneWindow();

        }

    }

    /**
     * description: this will take the relationshipList field and relationshipAssets field from the
     * GUIDiagramProject class, it will then clear the relationshipAssets arrayList and repopulate it with
     * new RelationshipAssets created from the relationshipList
     * @param relationshipList
     * @param relationshipAssets
     */
    public void updateRelationshipAssetListIndex(final ArrayList<Relationship> relationshipList, final ArrayList<RelationshipAsset> relationshipAssets) {
        relationshipAssets.clear();
        for (int i = 0; i < relationshipList.size(); i++) {
            RelationshipAsset newRelationshipAsset = new RelationshipAsset(relationshipList.get(i), i);
            relationshipAssets.add(newRelationshipAsset);
        }
    }

    public static void updateRelationshipLines(final RelationshipAsset relationshipAsset, final ArrayList<Pane> classAssetPaneList,
                                               final ArrayList<Point2D> classCoordinates, final ArrayList<ClassAsset> classAssets) {
        Line relationshipLine = relationshipAsset.relationshipContainer;

        Class ownerClass;
        Class otherClass;
        if(relationshipAsset.currentRelationship.getIsOwner()) {
            ownerClass = relationshipAsset.currentRelationship.getClass1();
            otherClass = relationshipAsset.currentRelationship.getClass2();
        }
        else {
            ownerClass = relationshipAsset.currentRelationship.getClass2();
            otherClass = relationshipAsset.currentRelationship.getClass1();
        }

        int ownerIndex = -1;
        int otherIndex = -1;

        for(ClassAsset classAsset : classAssets) {
            if(classAsset.getCurrentClass().getClassName().equals(ownerClass.getClassName())) {
                ownerIndex = classAssets.indexOf(classAsset);
            }
            if(classAsset.getCurrentClass().getClassName().equals(otherClass.getClassName())) {
                otherIndex = classAssets.indexOf(classAsset);
            }
        }

        classCoordinates.clear();

        for (int i = 0; i < classAssetPaneList.size(); i++) {
            double currentXCoordinate = classAssetPaneList.get(i).localToParent(classAssetPaneList.get(i).getBoundsInLocal()).getCenterX();
            double currentYCoordinate = classAssetPaneList.get(i).localToParent(classAssetPaneList.get(i).getBoundsInLocal()).getCenterY();
            Point2D coords = new Point2D(currentXCoordinate, currentYCoordinate);
            classCoordinates.add(coords);
        }

        relationshipLine.toBack();

        relationshipLine.setStartX(classCoordinates.get(ownerIndex).getX());
        relationshipLine.setStartY(classCoordinates.get(ownerIndex).getY());

        relationshipLine.setEndX(classCoordinates.get(otherIndex).getX());
        relationshipLine.setEndY(classCoordinates.get(otherIndex).getY());

        relationshipLine.toBack();
    }
}

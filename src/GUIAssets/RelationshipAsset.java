package GUIAssets;

import CLI.CommandLineInterface;
import GUI.GUIDiagramProject;
import Relationships.Relationship;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class RelationshipAsset {

    private Relationship currentRelationship;
    private Pane relationshipContainer;
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

    /**
     * Creates and returns a graphical representation of a relationship asset within a GUI diagram project.
     *
     * @param relationshipList
     * @param relationshipAssetPaneList
     * @param relationshipAssets
     * @param relationshipCoordinates
     * @param classAssetPaneList
     * @param classCoordinates
     * @param guiDiagramProject  The GUI diagram project that manages the graphical elements
     * @return                  A JavaFX Pane representing the class asset.
     */
    public Pane createRelationshipAsset(final ArrayList<Relationship> relationshipList, final ArrayList<Pane> relationshipAssetPaneList,
                                        final ArrayList<RelationshipAsset> relationshipAssets, final ArrayList<Point2D> relationshipCoordinates,
                                        final ArrayList<Pane> classAssetPaneList, final ArrayList<Point2D> classCoordinates,
                                        final GUIDiagramProject guiDiagramProject) {
        int textSize = 12;
        String fontType = "Verdana";

        this.relationshipContainer = new Pane();
        this.relationshipContainer.setStyle("-fx-background-color: lightblue;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: black;" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 10");
        Insets margins = new Insets(5, 5,5, 5);
        VBox textContainer = this.setupTextContainer(fontType, textSize, margins, relationshipList, relationshipAssetPaneList,
                relationshipAssets, relationshipCoordinates, classAssetPaneList, classCoordinates, guiDiagramProject);

        this.relationshipContainer.getChildren().add(textContainer);
        this.relationshipContainer.setOnMousePressed(this::onMousePressed);
        this.relationshipContainer.setOnMouseDragged(this::onMouseDragged);

        return this.relationshipContainer;
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
     * description: setup for the contents of the relationship
     * @param fontType
     * @param textSize
     * @param margins
     * @param relationshipList
     * @param relationshipAssetPaneList
     * @param relationshipAssets
     * @param relationshipCoordinates
     * @param classAssetPaneList
     * @param classCoordinates
     * @param guiDiagramProject
     * @return
     */
    public VBox setupTextContainer(final String fontType, final int textSize, final Insets margins, final ArrayList<Relationship> relationshipList,
                                   final ArrayList<Pane> relationshipAssetPaneList, final ArrayList<RelationshipAsset> relationshipAssets,
                                   final ArrayList<Point2D> relationshipCoordinates, final ArrayList<Pane> classAssetPaneList,
                                   final ArrayList<Point2D> classCoordinates, final GUIDiagramProject guiDiagramProject) {
        VBox textContainer = new VBox();
        Text relationshipInfo = new Text();
        relationshipInfo.setFont(Font.font(fontType, textSize));

        relationshipInfo.setText(this.currentRelationship.toString());
        VBox.setMargin(relationshipInfo, margins);

        HBox buttonContainer = this.setUpButton(fontType, textSize, margins, relationshipList, relationshipAssetPaneList,
                relationshipAssets, relationshipCoordinates, classAssetPaneList, classCoordinates, guiDiagramProject);
        textContainer.getChildren().addAll(relationshipInfo, buttonContainer);

        return textContainer;
    }

    /**
     * descriptions: setup method for the delete button
     * @param fontType
     * @param textSize
     * @param margins
     * @param relationshipList
     * @param relationshipAssetPaneList
     * @param relationshipAssets
     * @param relationshipCoordinates
     * @param classAssetPaneList
     * @param classCoordinates
     * @param guiDiagramProject
     * @return
     */
    public HBox setUpButton(final String fontType, final int textSize, final Insets margins, final ArrayList<Relationship> relationshipList,
                            final ArrayList<Pane> relationshipAssetPaneList, final ArrayList<RelationshipAsset> relationshipAssets,
                            final ArrayList<Point2D> relationshipCoordinates, final ArrayList<Pane> classAssetPaneList,
                            final ArrayList<Point2D> classCoordinates, final GUIDiagramProject guiDiagramProject) {
        HBox buttonContainer = new HBox();
        buttonContainer.setSpacing(120.0);

        Button deleteButton = new Button("Delete");
        deleteButton.setFont(Font.font(fontType, textSize));
        deleteButton.setOnAction(e -> this.deleteRelationship(relationshipList, relationshipAssetPaneList, relationshipAssets,
                relationshipCoordinates, classAssetPaneList, classCoordinates, guiDiagramProject));

        buttonContainer.getChildren().add(deleteButton);

        VBox.setMargin(buttonContainer, margins);
        return buttonContainer;
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
     * @param relationshipAssetPaneList     the list of panes associated with the relationship assets.
     * @param relationshipAssets          The list of class assets.
     * @param relationshipCoordinates   A list of coordinates representing the positions of relationship assets
     * @param classAssetPaneList   The list of panes associated with the class assets.
     * @param classCoordinates          A list of coordinates representing the positions of class assets.
     * @param guiDiagramProject    The GUI diagram project that manages the graphical elements.
     */
    public void deleteRelationship(final ArrayList<Relationship> relationshipList, final ArrayList<Pane> relationshipAssetPaneList,
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
            relationshipList.remove(this.index);

            CommandLineInterface.getCurrentDiagram().deleteRelationship(currentRelationship.getClass1(), currentRelationship.getClass2());

            //remove the relationship pane from the pane list next
            relationshipAssetPaneList.remove(this.index);

            relationshipCoordinates.clear();
            classCoordinates.clear();

            //get the x/y positions from the remaining relationship asset panes
            for (int i = 0; i < relationshipAssetPaneList.size(); i++) {
                double currentXCoordinate = relationshipAssetPaneList.get(i).localToScene(relationshipAssetPaneList.get(i).getBoundsInLocal()).getMinX();
                double currentYCoordinate = relationshipAssetPaneList.get(i).localToScene(relationshipAssetPaneList.get(i).getBoundsInLocal()).getMinY();
                Point2D coords = new Point2D(currentXCoordinate, currentYCoordinate);
                relationshipCoordinates.add(coords);
            }

            for (int i = 0; i < classAssetPaneList.size(); i++) {
                double currentXCoordinate = classAssetPaneList.get(i).localToScene(classAssetPaneList.get(i).getBoundsInLocal()).getMinX();
                double currentYCoordinate = classAssetPaneList.get(i).localToScene(classAssetPaneList.get(i).getBoundsInLocal()).getMinY();
                Point2D coords = new Point2D(currentXCoordinate, currentYCoordinate);
                classCoordinates.add(coords);
            }

            //update the class asset list by taking the new class list and creating new class assets from them
            this.updateRelationshipAssetListIndex(relationshipList, relationshipAssets); //to be removed

            //refresh the class asset panes and the window
            guiDiagramProject.refreshRelationshipPanes();
            guiDiagramProject.refreshRelationshipPanesToPaneWindow();

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
}

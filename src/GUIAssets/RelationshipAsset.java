package GUIAssets;

import Relationships.Relationship;
import javafx.geometry.Insets;
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

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Pane createRelationshipAsset(ArrayList<Pane> paneArrayList) {
        int textSize = 12;
        String fontType = "Verdana";

        this.relationshipContainer = new Pane();
        this.relationshipContainer.setStyle("-fx-background-color: lightblue;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: black;" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 10");
        Insets margins = new Insets(5, 5,5, 5);
        VBox textContainer = this.setupTextContainer(fontType, textSize, margins, paneArrayList);

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

    public VBox setupTextContainer(String fontType, int textSize, Insets margins, ArrayList<Pane> paneArrayList) {
        VBox textContainer = new VBox();
        Text relationshipInfo = new Text();
        relationshipInfo.setFont(Font.font(fontType, textSize));

        relationshipInfo.setText(this.currentRelationship.toString());
        VBox.setMargin(relationshipInfo, margins);

        HBox buttonContainer = this.setUpButton(fontType, textSize, margins, paneArrayList);
        textContainer.getChildren().addAll(relationshipInfo, buttonContainer);

        return textContainer;
    }

    public HBox setUpButton(String fontType, int textSize, Insets margins, ArrayList<Pane> paneArrayList ) {
        HBox buttonContainer = new HBox();
        buttonContainer.setSpacing(120.0);

        Button deleteButton = new Button("Delete");
        deleteButton.setFont(Font.font(fontType, textSize));
        deleteButton.setOnAction(e -> this.deleteRelationship(paneArrayList));

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

    public void deleteRelationship(ArrayList<Pane> relationshipAssetPaneList) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setContentText("Are you sure you want to delete this relationship?");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        if (result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
            System.out.println("relationship deleted");
            relationshipAssetPaneList.remove(this.index);
            System.out.println("relationship asset list size now: " + relationshipAssetPaneList.size());
        }

    }

    /*
    private Line relationshipLine;

    public Line createLine(Pane c1, Pane c2) {
        this.createListenersForPanes(c1, c2);

        this.relationshipLine = new Line();
        this.relationshipLine.setStroke(Color.BLACK);
        this.relationshipLine.setStrokeWidth(2);

        this.updateLinePosition(c1.getLayoutX(), c1.getLayoutY(), c2.getLayoutX(), c2.getLayoutY());
        return this.relationshipLine;
    }

    public void createListenersForPanes(Pane c1, Pane c2) {

        c1.layoutXProperty().addListener((obs, oldX, newX) -> updateLinePosition(c1.getLayoutX(), c1.getLayoutY(), c2.getLayoutX(), c2.getLayoutY()));
        c1.layoutYProperty().addListener((obs, oldY, newY) -> updateLinePosition(c1.getLayoutX(), c1.getLayoutY(), c2.getLayoutX(), c2.getLayoutY()));
        c2.layoutXProperty().addListener((obs, oldX, newX) -> updateLinePosition(c1.getLayoutX(), c1.getLayoutY(), c2.getLayoutX(), c2.getLayoutY()));
        c2.layoutYProperty().addListener((obs, oldY, newY) -> updateLinePosition(c1.getLayoutX(), c1.getLayoutY(), c2.getLayoutX(), c2.getLayoutY()));

    }

    public void updateLinePosition(double startX, double startY, double endX, double endY) {
        this.relationshipLine.setStartX(startX);
        this.relationshipLine.setStartY(startY);
        this.relationshipLine.setEndX(endX);
        this.relationshipLine.setEndY(endY);
    }
    */
}

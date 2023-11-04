package GUIAssets;

import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class RelationshipAsset {

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

}

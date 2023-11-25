package GUIAssets;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public class GUIDiagramProjectDto {
    private boolean hasMoved;
    private ArrayList<Point2D> classPanesCoordinates;

    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public ArrayList<Point2D> getClassPanesCoordinates() {
        return classPanesCoordinates;
    }

    public void setClassPanesCoordinates(ArrayList<Point2D> classPanesCoordinates) {
        this.classPanesCoordinates = classPanesCoordinates;
    }
}

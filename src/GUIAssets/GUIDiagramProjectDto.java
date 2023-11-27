package GUIAssets;

import com.google.gson.annotations.Expose;
import javafx.geometry.Point2D;

import java.util.ArrayList;

public class GUIDiagramProjectDto {
    @Expose
    private boolean hasMoved;
    @Expose
    private ArrayList<Point2D> classPanesCoordinates;

    public GUIDiagramProjectDto(boolean hasMoved, ArrayList<Point2D> classPanesCoordinates){
        this.hasMoved = hasMoved;
        this.classPanesCoordinates = classPanesCoordinates;
    }

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

package application.mediator.controllers.diagramprojectcontroller.snapshot;

import application.mediator.common.IHandler;
import application.mediator.common.Request;
import javafx.scene.image.WritableImage;
import javafx.stage.Window;

public class SnapshotRequestHandler implements IHandler {
    public Void handle(Request request){
        SnapshotRequest newRequest = (SnapshotRequest) request;
        Window stage = newRequest.getStage();

        WritableImage snapshot = stage.getScene().snapshot(null);
        return null;
    }
}

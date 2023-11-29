package application.mediator.controllers.diagramprojectcontroller.snapshot;

import application.mediator.common.Request;
import javafx.stage.Window;

public class SnapshotRequest extends Request {

    private final Window stage;
    public SnapshotRequest(Window stage){
        super("SnapshotRequest");
        this.stage = stage;
    }

    public Window getStage(){
        return this.stage;
    }
}

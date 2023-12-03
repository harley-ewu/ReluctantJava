package application.mediator.controllers.diagramprojectcontroller.snapshot;

import application.mediator.common.Request;
import javafx.scene.Node;
import javafx.stage.Window;

public class SnapshotRequest extends Request {

    private final Node contentRoot;
    public SnapshotRequest(Node contentRoot){
        super("SnapshotRequest");
        this.contentRoot = contentRoot;
    }

    public Node getContentRoot() {
        return this.contentRoot;
    }
}

package application.mediator.controllers.diagramprojectcontroller.loadfile;

import application.mediator.common.Request;
import javafx.stage.Window;

public class LoadFileRequest extends Request {
    private final Window stage;

    public LoadFileRequest(Window stage){
        super("LoadFileRequest");
        this.stage = stage;
    }

    public Window getStage(){
        return this.stage;
    }
}

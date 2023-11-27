package application.mediator.controllers.diagramprojectcontroller.saveasfile;

import application.mediator.common.Request;
import javafx.stage.Window;

public class SaveAsFileRequest extends Request {
    private final Window stage;

    public SaveAsFileRequest(Window stage){
        super("SaveAsFileRequest");
        this.stage = stage;
    }

    public Window getStage(){
        return this.stage;
    }
}

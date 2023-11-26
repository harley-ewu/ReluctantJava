package application.mediator.controllers.menubarcontroller.guiSaveAs;

import application.mediator.common.Request;
import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;

public class GuiSaveAsRequest extends Request {
    private final ActionEvent event;
    private final HBox menuBar;

    public GuiSaveAsRequest(ActionEvent event, HBox menuBar){
        super("GuiSaveAsRequest");
        this.event = event;
        this.menuBar = menuBar;
    }

    public ActionEvent getEvent(){
        return this.event;
    }

    public HBox getMenuBar(){
        return this.menuBar;
    }
}

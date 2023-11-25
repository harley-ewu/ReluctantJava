package application.mediator.controllers.menubarcontroller.guiLoad;

import application.mediator.common.Request;
import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;

public class GuiLoadRequest extends Request {
    private final ActionEvent event;
    private final HBox menuBar;

    public GuiLoadRequest(ActionEvent event, HBox menuBar){
        super("GuiLoadRequest");
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

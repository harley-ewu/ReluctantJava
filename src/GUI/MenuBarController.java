package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MenuBarController {

    @FXML
    public void handleSave() {
        // Handle save action
    }

    @FXML
    public void handleLoad() {
        // Handle load action
    }

    @FXML
    public void handleMinimize(ActionEvent event) {
        Window window = ((Node) event.getSource()).getScene().getWindow();
        ((Stage) window).setIconified(true);
    }

    @FXML
    public void handleMaximize(ActionEvent event) {
        Window window = ((Node) event.getSource()).getScene().getWindow();
        Stage stage = (Stage) window;
        stage.setMaximized(!stage.isMaximized());
    }

    @FXML
    public void handleClose(ActionEvent event) {
        Window window = ((Node) event.getSource()).getScene().getWindow();
        ((Stage) window).close();
    }
}

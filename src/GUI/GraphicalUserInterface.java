package GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.net.URL;

public class GraphicalUserInterface extends javafx.application.Application{

    /**
     * Description: Launches the graphical user interface display for the UML editor
     * Use Case: Call this under the 'Start GUI' Switch block
     */
    public static void startGUI(String[] args){
        try{
            launch();
        }
        catch(IllegalStateException e){
            System.out.println("GUI is already running");
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL mainView = getClass().getResource("MainMenu.fxml");
        URL cssPath = getClass().getResource("MenuStyles.css");

        if(mainView == null){
            throw new FileNotFoundException("Could not find MainMenu.fxml");
        }

        if(cssPath == null){
            throw new FileNotFoundException("Could not find MenuStyles.css");
        }

        String cssStyles = cssPath.toString();
        Parent root = FXMLLoader.load(mainView);

        Scene scene = new Scene(root, 300, 250);
        scene.getStylesheets().add(cssStyles);

        primaryStage.setTitle("UML Editor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

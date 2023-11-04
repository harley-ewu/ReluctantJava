package GUI;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Optional;

import Diagram.Diagram;

public class GraphicalUserInterface extends javafx.application.Application{

    private static Stage mainMenuStage;
    private static Stage diagramStage;
    private static Diagram diagram;
    private static TextField filePathField;

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
        mainMenuStage = primaryStage;
        URL mainView = getClass().getResource("MainMenu.fxml");
        URL cssPath = getClass().getResource("MenuStyles.css");
        

        if(mainView == null){
            throw new FileNotFoundException("Could not find MainMenu.fxml");
        }

        if(cssPath == null){
            throw new FileNotFoundException("Could not find MenuStyles.css");
        }
        FXMLLoader loader = new FXMLLoader(mainView);
        String cssStyles = cssPath.toString();
        Parent root = loader.load();

        Scene scene = new Scene(root, 300, 250);
        scene.getStylesheets().add(cssStyles);

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("UML Editor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Diagram getCurrentDiagram(){
        return diagram;
    }

    public static void openPopup() {
        Stage popupStage = new Stage();
        TextField tf = new TextField();
        Label errorLabel = new Label();
        Label label = new Label("Enter a name for the Diagram");
        Button submitBtn = new Button("Submit");
        diagram = new Diagram("");
        submitBtn.setOnAction(e -> {
            String inputText = tf.getText();
            if(inputText.isEmpty() || inputText.length() > 50) {
                errorLabel.setText("Please enter a non-empty name less than 50 characters.");
            } else {
                diagram.setTitle(inputText);
                System.out.println("Submitted text: " + inputText);
                popupStage.close();
            }
            try {
                
                openDiagram(diagram);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(label, tf, errorLabel, submitBtn);

        Scene scene = new Scene(layout, 300, 200);

        popupStage.setScene(scene);
        popupStage.setTitle("Popup Window");
        popupStage.show();
    }

    public static void openDiagram(final Diagram currentDiagram) throws Exception {
        if(diagramStage == null) {
            diagramStage = new Stage();
            GUIDiagramProject diagramGui = new GUIDiagramProject();
            diagramGui.setCurrentDiagram(currentDiagram);
            diagramGui.start(diagramStage);
            mainMenuStage.setResizable(false);
        }
        
    }
    public static void closeDiagram() {
        if(diagramStage != null) {
            diagramStage.close();
            diagramStage = null;
            mainMenuStage.setResizable(true);
        }
    }

    public static void showSavePrompt() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save Work");
        alert.setHeaderText("Do you want to save your work?");
        alert.setContentText("Choose an option:");

        ButtonType saveDefaultButtonType = new ButtonType("Save to Default Path");
        ButtonType saveCustomButtonType = new ButtonType("Save to Custom Path");
        ButtonType cancelButtonType = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(saveDefaultButtonType, saveCustomButtonType, cancelButtonType);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == saveDefaultButtonType) {
                // Save to the default path
                saveToDefaultPath();
            } else if (result.get() == saveCustomButtonType) {
                // Save to a custom path
                saveToCustomPath();
            } else {
                diagram = null;
            }
        }
    }

    private static void saveToDefaultPath() {
        System.out.println("Saving to the default path...");
        // Add your code to save to the default path
    }

    private static void saveToCustomPath() {
        Alert customPathAlert = new Alert(Alert.AlertType.CONFIRMATION);
        customPathAlert.setTitle("Save Work");
        customPathAlert.setHeaderText("Please enter the file path:");
        customPathAlert.setContentText("File Path:");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        Label filePathLabel = new Label("File Path:");
        filePathField = new TextField();

        gridPane.add(filePathLabel, 0, 0);
        gridPane.add(filePathField, 1, 0);

        customPathAlert.getDialogPane().setContent(gridPane);

        ButtonType saveButtonType = new ButtonType("Save");
        ButtonType cancelButtonType = new ButtonType("Cancel");
        customPathAlert.getButtonTypes().setAll(saveButtonType, cancelButtonType);

        Optional<ButtonType> result = customPathAlert.showAndWait();
        if (result.isPresent() && result.get() == saveButtonType) {
            String filePath = filePathField.getText();
            System.out.println("File path to save: " + filePath);
            // Add your code to save to the custom path
        }
    }
}

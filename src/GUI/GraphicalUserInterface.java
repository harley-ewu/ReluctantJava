package GUI;

import CLI.CommandLineInterface;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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
    private static GUIDiagramProject diagramGui;

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
                CommandLineInterface.setCurrentDiagram(diagram);
                try {

                    openDiagram(diagram);
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(label, tf, errorLabel, submitBtn);

        Scene scene = new Scene(layout, 400, 200);

        popupStage.setScene(scene);
        popupStage.setTitle("Popup Window");
        popupStage.show();
    }

    public static void openDiagram(final Diagram currentDiagram) throws Exception {
        diagramStage = new Stage();
        diagramGui = new GUIDiagramProject();
        //diagramGui.setCurrentDiagram(currentDiagram);
        diagramGui.start(diagramStage);
        mainMenuStage.setResizable(false);
        
    }
    public static void closeDiagram() {
        if(diagramStage != null) {
            diagramStage.close();
            diagramStage = null;
            mainMenuStage.setResizable(true);
        }
    }

    public static void noDiagramLoadedAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("View/Edit Diagram");
        alert.setHeaderText(null);
        alert.setContentText("No diagram is loaded.");
        alert.showAndWait();
    }

    public static void showSavePrompt() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save Work");
        alert.setHeaderText("Do you want to save your work?");
        alert.setContentText("Choose an option:");

        ButtonType saveButton = new ButtonType("Save");
        ButtonType cancelButtonType = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(saveButton, cancelButtonType);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == saveButton) {
                save();
                closeDiagram();
            } else {
                diagram = null;
                closeDiagram();
            }
        }
    }

    private static void save() {
        if(CommandLineInterface.getCurrentDiagram().getSaveLocation() == null){
            DiagramProjectController.saveAsFile(mainMenuStage);
        }else{
            DiagramProjectController.saveFile();
        }
    }

    public static void displayHelpPopup() {
        Stage helpPopupStage = new Stage();
        helpPopupStage.initModality(Modality.APPLICATION_MODAL);
        helpPopupStage.setTitle("Help - Main Menu");
        helpPopupStage.setMinWidth(250);

        Label helpLabel = new Label("""
                OPTIONS:
                - Create New Diagram: If a diagram is not currently loaded, you will be prompted to name the new diagram and will be brought to your blank diagram
                - View/Edit Current Diagram: If a diagram is currently loaded, you will be brought to the diagram view with edit options
                - Help: Shows you how to use each command
                - Exit: Prompts you to save your diagram if one is loaded, then exits the program

                MENU BAR: 
                - File: 
                    - Save As: Using currently loaded diagram, saves the diagram as json file with name and location of your choice
                    - Save: Saves the currently loaded diagram given you have already saved it to your computer
                    - Load: Option to load previously saved json diagram file
                - Minimize: Shrinks the window
                - Maximize: Fits the window to the full screen
                - 'X': Prompts you to save your diagram if one is loaded, then exits the program. 
                """);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> helpPopupStage.close());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(helpLabel, closeButton);

        Scene helpScene = new Scene(layout);
        helpPopupStage.setScene(helpScene);
        helpPopupStage.showAndWait();
    }

    public static GUIDiagramProject getDiagramView(){
        return diagramGui;
    }
}

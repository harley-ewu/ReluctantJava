package GUI;
import Attributes.Attribute;
import Diagram.Diagram;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import GUIAssets.*;
import Class.Class;
import java.util.ArrayList;
public class GUIDiagramProject extends javafx.application.Application {

    private final double scaleFactor = 1.1;
    private final Pane contentPane = new Pane();
    private final Scale scaleTransform = new Scale(1, 1);
    private Diagram diagram = new Diagram("Test Project");

    public static void startGUI(String args) {
        try{
            launch();
        }
        catch(IllegalStateException e){
            System.out.println("GUI is already running");
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.contentPane.setPrefSize(3840,2160);
        //hbox for zoom in and zoom out buttons
        HBox zoomButtons = this.setUpZoomButtons();
        //menu bar creation
        MenuBar menuBar = this.setUpMenuBar();
        //setting up scene for stage
        ScrollPane scrollPane = new ScrollPane(this.contentPane);
        scrollPane.setPrefSize(1280,678);
        Pane root = new Pane(scrollPane, menuBar, zoomButtons);
        Scene scene = new Scene(root,1280,720);
        stage.setResizable(false);
        stage.setTitle(this.diagram.getTitle()); //place holder for where a diagram name should be
        //test classes
        this.testAssets();
        //set stage
        stage.setScene(scene);
        stage.show();
    }

    /**
     * descrption: allows zoom in functionality
     */
    private void zoomIn() {
        this.scaleTransform.setX(this.scaleTransform.getX() * this.scaleFactor);
        this.scaleTransform.setY(this.scaleTransform.getY() * this.scaleFactor);
    }
    /**
     * descrption: allows zoom out functionality
     */
    private void zoomOut() {
        this.scaleTransform.setX(this.scaleTransform.getX() / this.scaleFactor);
        this.scaleTransform.setY(this.scaleTransform.getY() / this.scaleFactor);
    }

    /**
     * descrption: setup for the zoom buttons
     */
    private HBox setUpZoomButtons() {
        HBox zoomButtons = new HBox();
        zoomButtons.setSpacing(10);
        Button zoomInButton = new Button("Zoom In");
        Button zoomOutButton = new Button("Zoom Out");
        zoomInButton.setOnAction(event -> this.zoomIn());
        zoomOutButton.setOnAction(event -> this.zoomOut());
        this.contentPane.getTransforms().add(this.scaleTransform);
        //adding buttons to hbox
        zoomButtons.getChildren().add(zoomInButton);
        zoomButtons.getChildren().add(zoomOutButton);
        //setting orientation for buttons
        zoomButtons.setLayoutX(1110);
        zoomButtons.setLayoutY(680);
        return zoomButtons;
    }

    /**
     * setup for the menu bar
     * @return
     */
    private MenuBar setUpMenuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.setPrefWidth(1280);
        //file menu creation
        Menu fileMenu = setUpFileMenu();
        //class menu creation
        Menu classMenu = this.setUpClassMenu();
        //adding menus to menu bar
        menuBar.getMenus().addAll(fileMenu,classMenu);
        return menuBar;
    }

    /**
     * setup for the file menu and its items
     * @return
     */
    private Menu setUpFileMenu() {
        Menu fileMenu = new Menu("File");

        MenuItem openItem = new MenuItem("Open");
        openItem.setOnAction(e -> DiagramProjectController.openFile());

        MenuItem saveItem = new MenuItem("Save");
        saveItem.setOnAction(e -> DiagramProjectController.saveFile());

        MenuItem loadItem = new MenuItem("Load");
        loadItem.setOnAction(e -> DiagramProjectController.loadFile());

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> DiagramProjectController.exit());

        fileMenu.getItems().addAll(openItem, saveItem, loadItem, exitItem);
        return fileMenu;
    }

    /**
     * setup for the class menu and its items
     * @return
     */
    private Menu setUpClassMenu() {
        Menu classMenu = new Menu("Class");
        MenuItem addClassItem = new MenuItem("Add Class");
        addClassItem.setOnAction(e -> DiagramProjectController.addClass());

        MenuItem addRelationshipItem = new MenuItem("Add Relationship");
        addRelationshipItem.setOnAction(e -> DiagramProjectController.addRelationship());

        classMenu.getItems().addAll(addClassItem, addRelationshipItem);
        return classMenu;
    }

    /**
     * description: temp method to test out functionality of assets ; to be removed
     */
    private void testAssets() {
        ClassAsset classAsset = new ClassAsset();
        Class testClass = new Class("test class");

        ArrayList<String> field1List = new ArrayList<>();
        ArrayList<String> field2List = new ArrayList<>();

        field1List.add("integer");
        field2List.add("character");

        ArrayList<String> params = new ArrayList<>();
        ArrayList<String> params1 = new ArrayList<>();
        params1.add("param1");
        params1.add("param2");

        ArrayList<String> params2 = new ArrayList<>();
        params2.add("param1");
        params2.add("param2");
        params2.add("param3");

        Attribute attribute = new Attribute();

        testClass.getAttributes().add(attribute.addAttribute("field1",field1List,Attribute.Type.FIELD));
        testClass.getAttributes().add(attribute.addAttribute("field2",field2List,Attribute.Type.FIELD));
        testClass.getAttributes().add(attribute.addAttribute("method1",params,Attribute.Type.METHOD));
        testClass.getAttributes().add(attribute.addAttribute("method2",params1,Attribute.Type.METHOD));
        testClass.getAttributes().add(attribute.addAttribute("method3",params2,Attribute.Type.METHOD));

        Pane newClass = classAsset.createClassAsset(testClass);
        newClass.setLayoutX(100);
        newClass.setLayoutY(100);
        Pane newClass2 = classAsset.createClassAsset(testClass);
        newClass2.setLayoutX(400);
        newClass2.setLayoutY(300);
        RelationshipAsset newRelationship = new RelationshipAsset();
        //test relationship
        Line newLine = newRelationship.createLine(newClass, newClass2);
        newLine.setDisable(true);
        this.contentPane.getChildren().addAll(newClass,newClass2,newLine);
    }



}

package GUI;
import Attributes.Attribute;
import Diagram.Diagram;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import GUIAssets.*;
import Class.Class;
import java.util.ArrayList;
public class GUIDiagramProject extends javafx.application.Application {

    private final double scaleFactor = 1.1;
    private final Pane contentPane = new Pane();
    private final Scale scaleTransform = new Scale(1, 1);
    private Diagram diagram = new Diagram("Test Project"); // this should be set in the create diagram menu option
    private ArrayList<Pane> panes = new ArrayList<>();
    private ArrayList<ClassAsset> classAssets = new ArrayList<>();

    public static void startGUI(String[] args){
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
        //this.testAssets();
        //set stage
        stage.setScene(scene);
        stage.show();
    }

    public void setCurrentDiagram(final Diagram diagram) {
        this.diagram = diagram;
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

        Class testClass = new Class("test class");
        Class testClass2 = new Class("test class 2");

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

        testClass2.getAttributes().add(attribute.addAttribute("field1",field1List,Attribute.Type.FIELD));
        testClass2.getAttributes().add(attribute.addAttribute("field2",field2List,Attribute.Type.FIELD));
        testClass2.getAttributes().add(attribute.addAttribute("method1",params,Attribute.Type.METHOD));
        testClass2.getAttributes().add(attribute.addAttribute("method2",params1,Attribute.Type.METHOD));
        testClass2.getAttributes().add(attribute.addAttribute("method3",params2,Attribute.Type.METHOD));

        ArrayList<Class> testClassArrayList = new ArrayList<>();

        testClassArrayList.add(testClass);
        testClassArrayList.add(testClass2);

        this.addClassAssets(testClassArrayList);
        this.addClassPanes();




    }

    public void addClassAssets(final ArrayList<Class> classList) {
        int i = 0;
        for (Class currentClass : classList) {
            ClassAsset temp = new ClassAsset(currentClass, i);
            this.classAssets.add(temp);
            i++;
        }
    }

    public void addClassPanes() {
        int i = 0;
        for (ClassAsset classAsset : this.classAssets) {
            Pane temp = classAsset.createClassAsset(this.panes);
            this.panes.add(temp);
        }
    }



}

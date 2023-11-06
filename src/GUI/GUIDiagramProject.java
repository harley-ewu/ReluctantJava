package GUI;
import Attributes.Attribute;
import Diagram.Diagram;
import Relationships.Relationship;
import javafx.geometry.Point2D;
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
    private ArrayList<Pane> classPanes = new ArrayList<>();
    private ArrayList<Pane> relationshipPanes = new ArrayList<>();
    private ArrayList<ClassAsset> classAssets = new ArrayList<>();
    private ArrayList<RelationshipAsset> relationshipAssets = new ArrayList<>();
    private ArrayList<Point2D> classPanesCoordinates = new ArrayList<>();
    private ArrayList<Point2D> relationshipPanesCoordinates = new ArrayList<>();
    private ArrayList<Class> classList = new ArrayList<>();
    private ArrayList<Relationship> relationshipList = new ArrayList<>();

    public static void startGUI(String[] args){
        try{
            launch();
        }
        catch(IllegalStateException e){
            System.out.println("GUI is already running");
        }
    }

    @Override
    public void start(final Stage stage) throws Exception {
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
        Class testClass3 = new Class("test class 3");
        Class testClass4 = new Class("test class 4");

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
        testClass.getAttributes().add(attribute.addAttribute("fieldf",field2List,Attribute.Type.FIELD));
        testClass.getAttributes().add(attribute.addAttribute("method1",params,Attribute.Type.METHOD));
        testClass.getAttributes().add(attribute.addAttribute("meterd2",params1,Attribute.Type.METHOD));
        testClass.getAttributes().add(attribute.addAttribute("method3",params2,Attribute.Type.METHOD));

        testClass2.getAttributes().add(attribute.addAttribute("freld1",field1List,Attribute.Type.FIELD));
        testClass2.getAttributes().add(attribute.addAttribute("field2",field2List,Attribute.Type.FIELD));
        testClass2.getAttributes().add(attribute.addAttribute("mwhod1",params,Attribute.Type.METHOD));
        testClass2.getAttributes().add(attribute.addAttribute("method2",params1,Attribute.Type.METHOD));
        testClass2.getAttributes().add(attribute.addAttribute("method3",params2,Attribute.Type.METHOD));

        testClass3.getAttributes().add(attribute.addAttribute("field1",field1List,Attribute.Type.FIELD));
        testClass3.getAttributes().add(attribute.addAttribute("field2",field2List,Attribute.Type.FIELD));
        testClass3.getAttributes().add(attribute.addAttribute("method1",params,Attribute.Type.METHOD));
        testClass3.getAttributes().add(attribute.addAttribute("meted2",params1,Attribute.Type.METHOD));
        testClass3.getAttributes().add(attribute.addAttribute("method3",params2,Attribute.Type.METHOD));

        testClass4.getAttributes().add(attribute.addAttribute("field1",field1List,Attribute.Type.FIELD));
        testClass4.getAttributes().add(attribute.addAttribute("field2",field2List,Attribute.Type.FIELD));
        testClass4.getAttributes().add(attribute.addAttribute("method4",params,Attribute.Type.METHOD));
        testClass4.getAttributes().add(attribute.addAttribute("me2",params1,Attribute.Type.METHOD));
        testClass4.getAttributes().add(attribute.addAttribute("metwod3",params2,Attribute.Type.METHOD));

        //ArrayList<Class> testClassArrayList = new ArrayList<>();

        this.classList.add(testClass);
        this.classList.add(testClass2);
        this.classList.add(testClass3);
        this.classList.add(testClass4);

        this.addClassAssets(this.classList);
        this.addClassPanes();
        this.addClassPanesToPaneWindow();

        System.out.println(this.classAssets.toString());

        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, testClass2, 1, 1, false);
        Relationship testRelationship2 = new Relationship(Relationship.RelationshipType.Aggregation, testClass2, testClass3, 1, 1, false);
        Relationship testRelationship3 = new Relationship(Relationship.RelationshipType.Aggregation, testClass, testClass3, 1, 1, false);

        this.relationshipList.add(testRelationship);
        this.relationshipList.add(testRelationship2);
        this.relationshipList.add(testRelationship3);

        this.addRelationshipAsset(this.relationshipList);
        this.addRelationshipPanes();
        this.addRelationshipPanesToPaneWindow();

        System.out.println(this.relationshipAssets.toString());

    }

    /**
     * 1. description: use this method to take contents from the diagram
     * and store in the appropriate array lists (the classes should go to the classList,
     * the relationships should go to the relationshipList)
     *
     * 2. then we will take the contents from the class and relationships list
     * convert them to class and relationship assets
     *
     * 3. once converted, we will create panes from each asset list and store them in the panes arraylist
     * (we need to separate the panes list so that classes and diagram have their own respective panes and they don't
     * interfere with the methods that use them)
     *
     * 4. we will then take the newly created panes arraylist and populate the contentPane
     *
     */

    public void initializeDiagramContents() {
        //write your code here for populating class array list

        this.addClassAssets(this.classList);
        this.addClassPanes();
        this.addClassPanesToPaneWindow();

        //relationship  stuff should be handled here

    }

    /**
     * description: takes in a list of classes and converts them to 'ClassAsset' objects
     * the 'ClassAsset' objects are then stored into the classAssets arraylist field
     * @param classList
     */

    public void addClassAssets(final ArrayList<Class> classList) {
        int i = 0;
        for (Class currentClass : classList) {
            ClassAsset temp = new ClassAsset(currentClass, i);
            this.classAssets.add(temp);
            i++;
        }
    }

    /**
     * description: takes the classAssets list, converts them to pane modules using the built in
     * createClassAsset method and then stores them into the classPanes arraylist
     */

    public void addClassPanes() {
        for (ClassAsset classAsset : this.classAssets) {
            Pane temp = classAsset.createClassAsset(this.classList,this.classPanes, this.classAssets,
                    this.classPanesCoordinates, this.relationshipPanes, this.relationshipPanesCoordinates, this);
            this.classPanes.add(temp);
        }
    }

    /**
     * description: populates the contentPane with the classAssets list
     */

    public void addClassPanesToPaneWindow() {
        for (Pane classAsset : this.classPanes) {
            this.contentPane.getChildren().add(classAsset);
        }
    }

    /**
     * description: takes in a list of relationships and converts them to 'RelationshipAsset' objects
     * the 'RelationshipAsset' objects are then stored into the relationshipAssets arraylist field
     * @param relationshipList
     */
    public void addRelationshipAsset(final ArrayList<Relationship> relationshipList) {
        int i = 0;
        for(Relationship currentRelationship: relationshipList) {
            RelationshipAsset temp = new RelationshipAsset(currentRelationship, i);
            this.relationshipAssets.add(temp);
            i++;
        }
    }

    /**
     * description: takes the relationshipAssets list, converts them to pane modules using the built-in
     * createRelationshipAsset method and then stores them into the relationshipPanes arraylist
     */
    public void addRelationshipPanes() {
        for(RelationshipAsset relationshipAsset : this.relationshipAssets) {
            Pane temp = relationshipAsset.createRelationshipAsset(this.relationshipList, this.relationshipPanes, this.relationshipAssets,
                    this.relationshipPanesCoordinates, this.classPanes, this.classPanesCoordinates, this);
            this.relationshipPanes.add(temp);
        }
    }

    /**
     * description: populates the contentPane with the relationshipAssets list
     */
    public void addRelationshipPanesToPaneWindow() {
        for (Pane relationshipAsset : this.relationshipPanes) {
            this.contentPane.getChildren().add(relationshipAsset);
        }
    }

    /**
     * description: method used to refresh the relationshipPanes list
     * 1. clears the current elements in the relationshipPanes list
     * 2. takes the elements from the updated relationshipAssets list and stores them in the newly cleared relationshipPanes list
     */

    public void refreshRelationshipPanes() {
        this.relationshipPanes.clear();
        for (RelationshipAsset relationshipAsset : this.relationshipAssets) {
            Pane temp = relationshipAsset.createRelationshipAsset(this.relationshipList, this.relationshipPanes, this.relationshipAssets,
                    this.relationshipPanesCoordinates,this.classPanes, this.classPanesCoordinates, this);
            this.relationshipPanes.add(temp);
        }
    }

    /**
     * description: method used to refresh the classPanes list
     * 1. clears the current elements in the classPanes list
     * 2. takes the elements from the updated classAssets list and stores them in the newly cleared classPanes list
     */

    public void refreshClassPanes() {
        this.classPanes.clear();
        for (ClassAsset classAsset : this.classAssets) {
            Pane temp = classAsset.createClassAsset(this.classList, this.classPanes,
                    this.classAssets, this.classPanesCoordinates, this.relationshipPanes, this.relationshipPanesCoordinates, this);
            this.classPanes.add(temp);
        }
    }

    /**
     * currently being used in ClassAsset's deleteClass method for the delete button
     * and in RelationshipAsset's deleteRelationship method for the delete button
     *
     * description: clears the contents of the contentPane and repopulates
     * the contentPane (the project window) with the classPanes and their respective coordinates
     * and does the same for relationshipPanes and their respective coordinates
     *
     * HOW THIS CURRENTLY WORKS:
     * - when deleting a class or a relationship, the method goes through the remaining classes and stores their x and y position
     * as Point2D objects and then stores them into an array list of Point2D objects
     *
     * - this method then takes the update classPanes list and the coordinates arraylist and assigns
     * the coordinates to the panes, so that when you delete a class asset, the remaining class assets
     * hold their position (since we need to clear the contentPane during the removal process)
     *
     * - in execution, the index position of the coordinates should share the same index position as its respective
     * classPane
     *
     * - it then does the same for all relationship
     *
     * */
    /*
    public void refreshPanesToPaneWindow() {

        this.contentPane.getChildren().clear();

        for (int i = 0; i < this.classPanes.size(); i++) {
            this.classPanes.get(i).setLayoutX(this.classPanesCoordinates.get(i).getX());
            this.classPanes.get(i).setLayoutY(this.classPanesCoordinates.get(i).getY());
            this.contentPane.getChildren().add(this.classPanes.get(i));
        }

        this.classPanesCoordinates.clear();

        for (int i = 0; i < this.relationshipPanes.size(); i++) {
            this.relationshipPanes.get(i).setLayoutX(this.relationshipPanesCoordinates.get(i).getX());
            this.relationshipPanes.get(i).setLayoutY(this.relationshipPanesCoordinates.get(i).getY());
            this.contentPane.getChildren().add(this.relationshipPanes.get(i));
        }

        this.relationshipPanesCoordinates.clear();

    }
    */
    public void refreshClassPanesToPaneWindow() {
        this.contentPane.getChildren().clear();

        for (int i = 0; i < this.classPanes.size(); i++) {
            this.classPanes.get(i).setLayoutX(this.classPanesCoordinates.get(i).getX());
            this.classPanes.get(i).setLayoutY(this.classPanesCoordinates.get(i).getY());
            this.contentPane.getChildren().add(this.classPanes.get(i));
        }

        this.classPanesCoordinates.clear();

        for (int i = 0; i < this.relationshipPanes.size(); i++) {
            this.relationshipPanes.get(i).setLayoutX(this.relationshipPanesCoordinates.get(i).getX());
            this.relationshipPanes.get(i).setLayoutY(this.relationshipPanesCoordinates.get(i).getY());
            this.contentPane.getChildren().add(this.relationshipPanes.get(i));
        }
    }

    public void refreshRelationshipPanesToPaneWindow() {
        this.contentPane.getChildren().clear();

        for (int i = 0; i < this.classPanes.size(); i++) {
            this.classPanes.get(i).setLayoutX(this.classPanesCoordinates.get(i).getX());
            this.classPanes.get(i).setLayoutY(this.classPanesCoordinates.get(i).getY());
            this.contentPane.getChildren().add(this.classPanes.get(i));
        }

        for (int i = 0; i < this.relationshipPanes.size(); i++) {
            this.relationshipPanes.get(i).setLayoutX(this.relationshipPanesCoordinates.get(i).getX());
            this.relationshipPanes.get(i).setLayoutY(this.relationshipPanesCoordinates.get(i).getY());
            this.contentPane.getChildren().add(this.relationshipPanes.get(i));
        }

        this.relationshipPanesCoordinates.clear();
    }

    public void refreshCoordinates() {

    }
}

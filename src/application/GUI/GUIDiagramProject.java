package application.GUI;

import Class.Class;
import Diagram.Diagram;
import GUIAssets.ClassAsset;
import GUIAssets.GUIDiagramProjectDto;
import GUIAssets.RelationshipAsset;
import Relationships.Relationship;
import application.Application;
import application.mediator.controllers.diagramprojectcontroller.DiagramProjectController;
import application.mediator.controllers.menubarcontroller.MenuBarController;
import application.mediator.controllers.updateviewcontroller.UpdateViewController;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;

public class GUIDiagramProject extends javafx.application.Application {
    Pane root;
    Scene scene;
    ScrollPane scrollPane = new ScrollPane(this.contentPane);
    private  double scaleFactor = 1.1;
    private final Pane contentPane = new Pane();
    private final Scale scaleTransform = new Scale(1, 1);

    private boolean hasMoved = false;
    private Boolean wasAdded = false;
    private Diagram diagram = Application.getCurrentDiagram(); // this should be set in the create diagram menu option
    //Diagram diagram = new Diagram("test diagram");
    private ArrayList<Pane> classPanes = new ArrayList<>();
    private static ArrayList<Line> relationshipLines = new ArrayList<>();
    private ArrayList<ClassAsset> classAssets = new ArrayList<>();
    private static ArrayList<RelationshipAsset> relationshipAssets = new ArrayList<>();
    private ArrayList<Point2D> classPanesCoordinates = new ArrayList<>();
    private ArrayList<Point2D> relationshipPanesCoordinates = new ArrayList<>();
    private ArrayList<Class> classList = new ArrayList<>();
    private ArrayList<Relationship> relationshipList = new ArrayList<>();
    public static ArrayList<Line> getRelationshipLines() {
        return relationshipLines;

    }

    public static ArrayList<RelationshipAsset> getRelationshipAssets() {
        return relationshipAssets;
    }


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
        UpdateViewController.initView(this);
        this.contentPane.setPrefSize(3840,2160);
        //hbox for zoom in and zoom out buttons
        HBox zoomButtons = this.setUpZoomButtons();
        //menu bar creation
        MenuBar menuBar = this.setUpMenuBar(stage);
        //setting up scene for stage
        this.scrollPane = new ScrollPane(this.contentPane);
        scrollPane.setPrefSize(1280,678);
        this.root = new Pane(scrollPane, menuBar, zoomButtons);
        this.scene = new Scene(root,1280,720);
        stage.setResizable(false);
        stage.setTitle(this.diagram.getTitle()); //place holder for where a diagram name should be
        //set stage
        stage.setScene(this.scene);
        stage.show();
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public ArrayList<Point2D> getClassPanesCoordinates() {
        return classPanesCoordinates;
    }

    public void setClassPanesCoordinates(ArrayList<Point2D> classPanesCoordinates) {
        this.classPanesCoordinates.clear();
        this.classPanesCoordinates.addAll(classPanesCoordinates);
    }

    public ArrayList<Point2D> getRelationshipPanesCoordinates() { return relationshipPanesCoordinates; }

    public void setWasAddedToTrue() {
        this.wasAdded = true;
    }

    public Diagram getDiagram() {
        return this.diagram;
    }

    public RelationshipAsset getRelationshipAssetFromList(Relationship relationship) {
        for(RelationshipAsset relationshipAsset : relationshipAssets) {
            if(relationshipAsset.getRelationship() == relationship) {
                return relationshipAsset;
            }
        }

        return null;
    }


    /**
     * descrption: allows zoom in functionality
     */

    private void zoom(final double factor) {
        this.scaleFactor *= factor;

        System.out.println("scale factor: " + this.scaleFactor);

        for (Pane classPane : this.classPanes) {
            classPane.setScaleX(this.scaleFactor);
            classPane.setScaleY(this.scaleFactor);
        }
    }

    /**
     * descrption: setup for the zoom buttons
     */
    private HBox setUpZoomButtons() {
        HBox zoomButtons = new HBox();
        zoomButtons.setSpacing(10);
        Button zoomInButton = new Button("Zoom In");
        Button zoomOutButton = new Button("Zoom Out");
        zoomInButton.setOnAction(event -> this.zoom(1.1));
        zoomOutButton.setOnAction(event -> this.zoom(0.9));
        //this.contentPane.getTransforms().add(this.scaleTransform);
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
    private MenuBar setUpMenuBar(Stage stage) {
        MenuBar menuBar = new MenuBar();
        menuBar.setPrefWidth(1280);
        //file menu creation
        Menu fileMenu = setUpFileMenu(stage);
        //class menu creation
        Menu classMenu = this.setUpClassMenu();
        Menu editMenu = this.setUpEditMenu();
        //adding menus to menu bar
        menuBar.getMenus().addAll(fileMenu,classMenu, editMenu);
        return menuBar;
    }

    /**
     * setup for the file menu and its items
     * @return
     */
    private Menu setUpFileMenu(Stage stage) {
        Menu fileMenu = new Menu("File");

        MenuItem openItem = new MenuItem("Save as...");
        openItem.setOnAction(e -> DiagramProjectController.saveAsFile(stage));

        MenuItem saveItem = new MenuItem("Save");
        saveItem.setOnAction(e -> DiagramProjectController.saveFile());

        MenuItem loadItem = new MenuItem("Load...");
        loadItem.setOnAction(e -> DiagramProjectController.loadFile(stage));

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> DiagramProjectController.exit());

        fileMenu.getItems().addAll(openItem, saveItem, loadItem, exitItem);
        return fileMenu;
    }

    private Menu setUpEditMenu() {
        Menu editMenu = new Menu("Edit");

        MenuItem undoItem = new MenuItem("Undo");
        undoItem.setOnAction(e -> this.undo());

        MenuItem redoItem = new MenuItem("Redo");
        redoItem.setOnAction(e -> this.redo());

        editMenu.getItems().addAll(undoItem, redoItem);

        return editMenu;
    }


    /**
     * setup for the class menu and its items
     * @return
     */
    private Menu setUpClassMenu() {
        Menu classMenu = new Menu("Add");
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
    public ArrayList<Class> getClassList() {
        return classList;
    }

    public ArrayList<Relationship> getRelationshipList() {
        return relationshipList;
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
        HashMap<String, Class> diagramClasses = Application.getCurrentDiagram().getClassList();
        this.classList.clear();
        this.classList.addAll(diagramClasses.values());
    }

    public boolean getHasMoved() {
        return this.hasMoved;
    }

    /**
     * description: takes in a list of classes and converts them to 'ClassAsset' objects
     * the 'ClassAsset' objects are then stored into the classAssets arraylist field
     *
     */

    public void addClassAssets() {
        this.classAssets.clear();
        int i = 0;
        for (Class currentClass : this.classList) {
            ClassAsset temp = new ClassAsset(currentClass, i);
            this.classAssets.add(temp);
            i++;
        }

    }

    /**
     * description: create a
     */

    boolean follow;
    public void addSingleClassAsset(Class umlClass) {
        ClassAsset classAsset = new ClassAsset(umlClass);
        Pane classPane = classAsset.createClassAsset(this.classList, this.classPanes, this.classAssets,this.classPanesCoordinates,
                this.relationshipLines, this.relationshipPanesCoordinates, this.relationshipAssets, this);

        classPane.setOpacity(0.6);

        this.follow = true;
        this.contentPane.setOnMouseMoved(event -> {
            // Update the moving pane position to follow the mouse
            if (this.follow) {
                classPane.setLayoutX(event.getX() - classPane.getWidth() / 2);
                classPane.setLayoutY(event.getY() - classPane.getHeight() / 2);
            }
        });

        this.contentPane.getChildren().add(classPane);

        this.contentPane.setOnMouseClicked(e -> {
            //to be used
            double currentX = e.getX() - classPane.getWidth()/2;
            double currentY = e.getY() - classPane.getHeight()/2;
            if (this.wasAdded) {
                this.executeSingleClassAdd(umlClass, classPane);
            }
//keep for debugging purposes
/*           System.out.println("class panes: " + this.classPanes);
            System.out.println("class coords: " + this.classPanesCoordinates);*/

            });


    }

    /**
     * Description: method for creating the new class in addSingleClassAsset()
     * @param umlClass
     * @param classPane
     */
    public void executeSingleClassAdd(Class umlClass, Pane classPane) {

        if (this.wasAdded) {
            this.follow = false;
            this.contentPane.getChildren().remove(this.contentPane.getChildren().size() - 1);

            diagram.getClassList().put(umlClass.getClassName(), new Class(umlClass.getClassName()));

            Point2D coordinates = new Point2D(classPane.getLayoutX(), classPane.getLayoutY());
            this.classPanesCoordinates.add(coordinates);
            this.classList.add(umlClass);
            //add new values in classList to the diagram class hashmap
            this.refreshClassHashMap();
            this.getContentPane().getChildren().removeAll(this.getClassPanes());
            this.getClassPanes().clear();
            this.getClassAssets().clear();
            this.addClassAssets();
            this.addClassPanes();
            this.addClassPanesToPaneWindow();
            //keeping for debugging purposes
            /*System.out.println("I'm inside");
            System.out.println("class panes: " + this.classPanes);
            System.out.println("class coords: " + this.classPanesCoordinates);*/
            this.wasAdded = false;
        }
    }

    public void refreshClassHashMap() {
        this.diagram.getClassList().clear();
        IntStream.range(0, this.classList.size()).forEach(i -> this.diagram.getClassList().put(this.classList.get(i).getClassName(), this.classList.get(i)));
    }

    /**
     * description: converts class assets to draggable
     */

    public void addClassPanes() {
        classPanes.clear();
        for (int i = 0; i < this.classAssets.size(); i++) {

            double x = this.classPanesCoordinates.get(i).getX();
            double y = this.classPanesCoordinates.get(i).getY();
            ClassAsset classAsset = this.classAssets.get(i);
            Pane temp = this.createDraggablePane(x,y,classAsset);
            this.classPanes.add(temp);
        }
    }

    /**
     * description: turns a class box into a draggable box
     * @param x
     * @param y
     * @param classAsset
     * @return
     */

    private Pane createDraggablePane(double x, double y, ClassAsset classAsset) {
        Pane temp = classAsset.createClassAsset(this.classList, this.classPanes, this.classAssets, this.classPanesCoordinates,
                this.relationshipLines, this.relationshipPanesCoordinates, this.relationshipAssets, this);

        temp.relocate(x,y);

        temp.setOnMousePressed(e -> {
            temp.getProperties().put("startX", e.getSceneX());
            temp.getProperties().put("startY", e.getSceneY());
            //e.consume();
        });

        temp.setOnMouseDragged(e -> {

            double deltaX = e.getSceneX() - (double) temp.getProperties().get("startX");
            double deltaY = e.getSceneY() - (double) temp.getProperties().get("startY");

            // Update the start position for the next drag event
            temp.getProperties().put("startX", e.getSceneX());
            temp.getProperties().put("startY", e.getSceneY());

            // Calculate the new position
            double newX = temp.getLayoutX() + deltaX;
            double newY = temp.getLayoutY() + deltaY;

            boolean collisionDetected = false;
            for (Pane otherClassPane : this.classPanes) {
                if (otherClassPane != temp && isColliding(newX, newY, temp.getWidth(), temp.getHeight(), otherClassPane)) {
                    collisionDetected = true;
                }
            }

            if (!collisionDetected) {
               temp.setLayoutX(newX);
               temp.setLayoutY(newY);
            }
            e.consume();

            for (RelationshipAsset relationshipAsset : this.relationshipAssets) {
                RelationshipAsset.updateRelationshipLines(relationshipAsset, this.classPanes, this.classPanesCoordinates, this.classAssets);
            }
        });

        temp.setOnMouseReleased(e -> {
            this.updateClassPaneCoordinates();
            if (!this.hasMoved) {
                this.hasMoved = true;
            }
            Application.getCurrentDiagram().setCoordinates(new GUIDiagramProjectDto(true, this.classPanesCoordinates));

            for (RelationshipAsset relationshipAsset : this.relationshipAssets) {
                RelationshipAsset.updateRelationshipLines(relationshipAsset, this.classPanes, this.classPanesCoordinates, this.classAssets);
            }
        });

        this.scrollPane.setOnScroll(e -> {
            // Adjust the node's position by the scroll delta
            temp.setLayoutX(temp.getLayoutX() + e.getDeltaX());
            temp.setLayoutY(temp.getLayoutY() + e.getDeltaY());

            // Consume the event to prevent scrolling of the ScrollPane

            e.consume();

            for (RelationshipAsset relationshipAsset : this.relationshipAssets) {
                RelationshipAsset.updateRelationshipLines(relationshipAsset, this.classPanes, this.classPanesCoordinates, this.classAssets);
            }
        });

        return temp;
    }

    /**
     * description: checks if two class assets are colliding
     * @param x
     * @param y
     * @param width
     * @param height
     * @param otherClassPane
     * @return
     */

    private boolean isColliding(double x, double y, double width, double height, Pane otherClassPane) {

        double x2 = otherClassPane.getLayoutX();
        double y2 = otherClassPane.getLayoutY();
        double width2 = otherClassPane.getWidth();
        double height2 = otherClassPane.getHeight();

        return x < x2 + width2 && x + width > x2 &&
                y < y2 + height2 && y + height > y2;

    }

    public Pane getContentPane() {
        return this.contentPane;
    }

    public ArrayList<ClassAsset> getClassAssets() {
        return this.classAssets;
    }

    /**
     * description: populates the contentPane with the classAssets list
     */

    public void addClassPanesToPaneWindow() {
        this.contentPane.getChildren().clear();
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
            Line temp = relationshipAsset.createRelationshipAsset(this.relationshipList, this.relationshipLines, this.relationshipAssets,
                    this.relationshipPanesCoordinates, this.classPanes, this.classPanesCoordinates, this.classAssets);
            this.relationshipLines.add(temp);
        }
    }

    /**
     * description: populates the contentPane with the relationshipAssets list
     */
    public void addRelationshipLinesToPaneWindow() {
        for (Line relationshipAsset : this.relationshipLines) {
            this.contentPane.getChildren().add(relationshipAsset);
        }
    }

    public ArrayList<Pane> getClassPanes() {
        return this.classPanes;
    }

    /**
     * description: method used to refresh the relationshipPanes list
     * 1. clears the current elements in the relationshipPanes list
     * 2. takes the elements from the updated relationshipAssets list and stores them in the newly cleared relationshipPanes list
     */

    public void refreshRelationshipLines() {
        this.relationshipLines.clear();
        for (RelationshipAsset relationshipAsset : this.relationshipAssets) {
            Line temp = relationshipAsset.createRelationshipAsset(this.relationshipList, this.relationshipLines, this.relationshipAssets,
                    this.relationshipPanesCoordinates,this.classPanes, this.classPanesCoordinates, this.classAssets);
            this.relationshipLines.add(temp);
        }
    }


    /**
     * description: clears the contents of the contentPane and repopulates
     * the contentPane (the project window) with the classPanes and their respective coordinates
     * and does the same for relationshipPanes and their respective coordinates
     * */
    public void refreshClassPanesToPaneWindow() {
        this.contentPane.getChildren().clear();

        for (int i = 0; i < this.classPanes.size(); i++) {
            this.classPanes.get(i).setLayoutX(this.classPanesCoordinates.get(i).getX());
            this.classPanes.get(i).setLayoutY(this.classPanesCoordinates.get(i).getY());
            this.contentPane.getChildren().add(this.classPanes.get(i));
        }

        this.classPanesCoordinates.clear();

    }

    /**
     * description: clears the contents of the contentPane and repopulates
     * the contentPane (the project window) with the relationshipPanes and their respective coordinates
     * and does the same for classPanes and their respective coordinates
     * */
    public void refreshRelationshipPanesToPaneWindow() {
        this.contentPane.getChildren().clear();

        for (int i = 0; i < this.classPanes.size(); i++) {
            this.classPanes.get(i).setLayoutX(this.classPanesCoordinates.get(i).getX());
            this.classPanes.get(i).setLayoutY(this.classPanesCoordinates.get(i).getY());
            this.contentPane.getChildren().add(this.classPanes.get(i));
        }

        this.classPanesCoordinates.clear();

        for (int i = 0; i < this.relationshipLines.size(); i++) {
            this.relationshipLines.get(i).setLayoutX(this.relationshipPanesCoordinates.get(i).getX());
            this.relationshipLines.get(i).setLayoutY(this.relationshipPanesCoordinates.get(i).getY());
            this.contentPane.getChildren().add(this.relationshipLines.get(i));
        }

        this.relationshipPanesCoordinates.clear();
    }


    /**
     * description: this method is used in the first initialization of a diagram -- the default init positions of class panes
     * it will be used during the first initialization of a diagram, and once the user begins to move panes around,
     * the coords should be replaced by the users new pane layout
     */
    public void onInitClassPaneCoordinates() {
        this.classPanesCoordinates.clear();
        double x = 50;
        double y = 50;
        for (int i = 0; i < this.classAssets.size(); i++) {
            Point2D initCoords = new Point2D(x,y);
            this.classPanesCoordinates.add(initCoords);
            x += 400;
        }
    }

    public void updateClassPaneCoordinates() {
        this.classPanesCoordinates.clear();
        for (Pane classPane : this.classPanes) {
            Point2D coordinates = new Point2D(classPane.getLayoutX(), classPane.getLayoutY());
            this.classPanesCoordinates.add(coordinates);
        }
        System.out.println("I was updated!");
    }

    public void undo() {
        this.diagram.undo();
        System.out.println("Undoing..");
        this.getContentPane().getChildren().removeAll(this.getClassPanes());
        this.getClassAssets().clear();
        this.getClassPanes().clear();
        this.initializeDiagramContents();
    }

    public void redo() {
        this.diagram.redo();
        this.getContentPane().getChildren().removeAll(this.getClassPanes());
        this.getClassAssets().clear();
        this.getClassPanes().clear();
        this.initializeDiagramContents();
    }
}
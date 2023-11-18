package Diagram;

import Relationships.Relationship;
import Class.Class;

import java.util.HashMap;

public class DiagramMemento {

    private Diagram diagram;
    private String saveLocation = null;
    private String title;
    private HashMap<String, Class> classList;
    private HashMap<String, Relationship> relationshipList;

    public DiagramMemento(Diagram diagram) {
        this.diagram = diagram;
        this.title = diagram.getTitle();
        this.classList = diagram.getClassList();
        this.relationshipList = diagram.getRelationshipList();
    }

    public Diagram getDiagram() {return this.diagram;}

    public String getSaveLocation() {return this.saveLocation;}

    public String getTitle() {return this.title;}

    public HashMap<String, Class> getClassList() {return this.classList;}

    public HashMap<String, Relationship> getRelationshipList() {return this.relationshipList;}

    /*public void restore() {
        this.diagram.setTitle(this.title);
        this.diagram.setSaveLocation(saveLocation);
        this.diagram.setClassList(this.classList);
        this.diagram.setRelationshipList(relationshipList);
    }*/
}

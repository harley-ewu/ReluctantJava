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

    public DiagramMemento(Diagram diagram, final String title) {

        if (title == null) {
            throw new NullPointerException("invalid param in Diagram constructor");
        }
        this.diagram = diagram;
        this.title = title;
        this.classList = diagram.getClassList();
        this.relationshipList = diagram.getRelationshipList();
    }

    public void restore() {
        this.diagram.setTitle(this.title);
        this.diagram.setSaveLocation(saveLocation);
        this.diagram.setClassList(this.classList);
        this.diagram.setRelationshipList(relationshipList);
    }
}

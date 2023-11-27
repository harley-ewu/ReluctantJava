package Diagram;

import Attributes.Method;
import Relationships.Relationship;
import Class.Class;
import com.google.gson.annotations.Expose;

import java.util.HashMap;
import java.util.Map;

public class DiagramMemento {

    @Expose
    private Diagram diagram;
    @Expose
    private String saveLocation = null;
    @Expose
    private String title;
    @Expose
    private HashMap<String, Class> classList;
    @Expose
    private HashMap<String, Relationship> relationshipList;

    public DiagramMemento(Diagram diagram) {
        this.diagram = diagram;
        this.title = diagram.getTitle();
        this.classList = deepCopy(diagram.getClassList());
        this.relationshipList = new HashMap<>(diagram.getRelationshipList());
    }

    public Diagram getDiagram() {return this.diagram;}

    public String getSaveLocation() {return this.saveLocation;}

    public String getTitle() {return this.title;}

    public HashMap<String, Class> getClassList() {return this.classList;}

    public HashMap<String, Relationship> getRelationshipList() {return this.relationshipList;}

    private HashMap<String, Class> deepCopy(HashMap<String, Class> original) {
        HashMap<String, Class> copy = new HashMap<>();
        for (Map.Entry<String, Class> entry : original.entrySet()) {
            copy.put(entry.getKey(), entry.getValue().clone());
        }
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiagramMemento memento = (DiagramMemento) o;
        return this.diagram.equals(memento.getDiagram()) &&
                this.title.equals(memento.getTitle()) &&
                this.classList.equals(memento.getClassList()) &&
                this.relationshipList.equals(memento.getRelationshipList());
    }

}

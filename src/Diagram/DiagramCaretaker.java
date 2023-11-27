package Diagram;

import application.Application;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class DiagramCaretaker {
    @Expose
    private ArrayList<DiagramMemento> diagramMementoList = new ArrayList<>();
    @Expose
    private int currentIndex = -1;

    public void undo(Diagram diagram) {
        DiagramMemento duplicateCheck = new DiagramMemento(diagram);
        if(this.currentIndex == this.diagramMementoList.size() -1 && !this.diagramMementoList.contains(duplicateCheck)){
            //diagram.createSnapshot();
        }
        if (this.getCurrentIndex() != 0) {
            DiagramMemento memento = this.getDiagram(this.getCurrentIndex() - 1);
            diagram.applyMemento(memento);
            this.decrementIndex();
        }
    }

    public void redo(Diagram diagram) {
        if (this.getCurrentIndex() < this.getDiagramMementoList().size() - 1) {
            this.incrementIndex();
            DiagramMemento memento = this.getDiagram(this.getCurrentIndex());
            diagram.applyMemento(memento);
        }
    }

    public void makeBackupUp(DiagramMemento memento) {
        this.diagramMementoList.add(memento);
        this.currentIndex++;
    }

    public DiagramMemento getDiagram(int index) {
        return this.diagramMementoList.get(index);
    }

    public int getCurrentIndex() {
        return this.currentIndex;
    }
    public void incrementIndex() {
        if (currentIndex != diagramMementoList.size()) {
            this.currentIndex++;
        }
    }
    public void decrementIndex() {
        if (currentIndex != 0) {
            this.currentIndex--;
        }
    }
    public ArrayList<DiagramMemento> getDiagramMementoList() {return this.diagramMementoList;}
}

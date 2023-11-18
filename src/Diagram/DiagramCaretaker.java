package Diagram;

import java.util.ArrayList;

public class DiagramCaretaker {
    private ArrayList<DiagramMemento> diagramMementoList = new ArrayList<>();
    private int currentIndex = -1;

    public void makeBackupUp(DiagramMemento memento) {
        this.diagramMementoList.add(memento);
        this.currentIndex++;
    }

    public DiagramMemento getDiagram(int index) {
        return diagramMementoList.get(index);
    }

    public int getCurrentIndex() {
        return this.currentIndex;
    }
}

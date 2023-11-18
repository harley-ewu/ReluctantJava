package Diagram;

import java.util.ArrayList;

public class DiagramCaretaker {
    private ArrayList<DiagramMemento> diagramMementoList = new ArrayList<>();
    private int currentIndex = -1;

    public void makeBackupUp(DiagramMemento memento) {
        this.diagramMementoList.add(memento);
        this.currentIndex++;
    }

    public DiagramMemento restore(int index) {
        return diagramMementoList.get(index);
    }

    public int getIndex() {
        return this.currentIndex;
    }
}

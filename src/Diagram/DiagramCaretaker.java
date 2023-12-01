package Diagram;

import com.google.gson.annotations.Expose;

public class DiagramCaretaker {

    @Expose
    private UndoRedoStack<DiagramMemento> mementoStack = new UndoRedoStack<DiagramMemento>();

    public void undo(Diagram diagram) {
        if(this.mementoStack.canUndo()){
            diagram.applyMemento(this.mementoStack.undo());
        }
    }

    public void redo(Diagram diagram) {
        if (this.mementoStack.canRedo()) {
            diagram.applyMemento(this.mementoStack.redo());
        }
    }

    public void makeBackupUp(DiagramMemento memento) {
        this.mementoStack.push(memento);
    }

    public boolean stacksNotEmpty(){
        return this.mementoStack.stacksNotEmpty();
    }
}

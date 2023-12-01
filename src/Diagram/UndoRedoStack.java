package Diagram;


import com.google.gson.annotations.Expose;

import java.util.Stack;

public class UndoRedoStack<E> {
    @Expose
    private Stack<E> undoStack;
    @Expose
    private Stack<E> redoStack;

    public UndoRedoStack(){
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    public void push(E value){
        this.undoStack.push(value);
        this.redoStack.clear();
    }

    public boolean canUndo(){
        return !undoStack.isEmpty() && undoStack.size() != 1;
    }

    public E undo(){
        this.redoStack.push(this.undoStack.pop());
        return this.undoStack.get(this.undoStack.size() - 1);
    }

    public boolean canRedo(){
        return !redoStack.isEmpty();
    }

    public E redo(){
        return this.undoStack.push(this.redoStack.pop());
    }

    public boolean stacksNotEmpty(){
        return !this.undoStack.isEmpty() && !this.redoStack.isEmpty() && this.undoStack.size() > 1;
    }

}

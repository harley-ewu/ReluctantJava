import Diagram.UndoRedoStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UndoRedoStackTests {
    private UndoRedoStack<String> stack;

    @BeforeEach
    public void init(){
        stack = new UndoRedoStack<String>();
        stack.push("first");
        stack.push("second");
        stack.push("third");
    }

    @Test
    public void testPushNewAction(){
        stack.push("fourth");
        assertEquals(4, stack.sizeOfUndoStack());
        assertEquals(0, stack.sizeOfRedoStack());
    }

    @Test
    public void testCanUndoWhileCanUndoTrue(){
        assertTrue(stack.canUndo());
    }

    @Test
    public void testCanUndoWhileCanUndoFalse(){
        UndoRedoStack<String> newStack = new UndoRedoStack<String>();
        assertFalse(newStack.canUndo());
    }

    @Test
    public void testUndoWhileCanUndoReturnsNewCurrentState(){
        assertEquals("second", stack.undo());
    }

    @Test
    public void testUndoWhileCannotUndoThrowsException(){
        UndoRedoStack<String> newStack = new UndoRedoStack<String>();
        assertThrows(IllegalStateException.class, newStack::undo);
    }

    @Test
    public void testCanRedoWhileCanRedoTrue(){
        stack.undo();
        assertTrue(stack.canRedo());
    }

    @Test
    public void testCanRedoWhileCanRedoFalse(){
        UndoRedoStack<String> newStack = new UndoRedoStack<String>();
        assertFalse(newStack.canRedo());
    }

    @Test
    public void testRedoWhileCanRedoReturnsNewCurrentState(){
        stack.undo();
        assertEquals("third", stack.redo());
    }

    @Test
    public void testRedoWhileCannotRedoThrowsException(){
        UndoRedoStack<String> newStack = new UndoRedoStack<String>();
        assertThrows(IllegalStateException.class, newStack::redo);
    }

    @Test
    public void testStacksNotEmptyReturnsTrue(){
        assertTrue(stack.stacksNotEmpty());
    }

    @Test
    public void testStacksNotEmptyReturnsFalse(){
        UndoRedoStack<String> newStack = new UndoRedoStack<String>();
        assertFalse(newStack.stacksNotEmpty());
    }
}

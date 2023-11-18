import Diagram.Diagram;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiagramMementoTests {
    Diagram diagram = new Diagram("OriginalDiagram");

    @Test
    public void nothingToUndo() {
        diagram.undo();
        assertEquals("OriginalDiagram", diagram.getTitle());
    }

    @Test
    public void undoDiagramNameTest() {
        diagram.setTitle("NewDiagram");
        assertEquals("NewDiagram", diagram.getTitle());

        diagram.undo();
        assertEquals("OriginalDiagram", diagram.getTitle());
    }

    @Test
    public void redoDiagramNameTest() {
        diagram.setTitle("NewDiagram");
        assertEquals("NewDiagram", diagram.getTitle());

        diagram.undo();
        assertEquals("OriginalDiagram", diagram.getTitle());

        diagram.redo();
        assertEquals("NewDiagram", diagram.getTitle());
    }

    @Test
    public void undoRedoUndoTest() {
        diagram.setTitle("NewDiagram");
        assertEquals("NewDiagram", diagram.getTitle());

        diagram.undo();
        assertEquals("OriginalDiagram", diagram.getTitle());

        diagram.redo();
        assertEquals("NewDiagram", diagram.getTitle());

        diagram.undo();
        assertEquals("OriginalDiagram", diagram.getTitle());
    }

    @Test
    public void undoClassTest() {
        diagram.addClass("TestClass");
        diagram.addClass("AnotherClass");
        String expected =
                "\nDiagram: OriginalDiagram\n\n" +
                "Class Name: AnotherClass\n" +
                "---------------------\n" +
                "Attributes: \n" +
                "Class Name: TestClass\n" +
                "---------------------\n" +
                "Attributes: \n" +
                "\n" +
                "Relationship List: \n";
        assertEquals(expected, diagram.toString());

        String newExpectation =
                "\nDiagram: OriginalDiagram\n\n" +
                "Class Name: TestClass\n" +
                "---------------------\n" +
                "Attributes: \n\n" +
                "Relationship List: \n";
        diagram.undo();
        assertEquals(newExpectation, diagram.toString());
    }
}

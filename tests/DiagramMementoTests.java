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
    public void nothingToRedo() {
        diagram.redo();
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
    public void consecutiveUndos() {
        diagram.setTitle("OneFish");
        assertEquals("OneFish", diagram.getTitle());
        diagram.setTitle("TwoFish");
        assertEquals("TwoFish", diagram.getTitle());
        diagram.setTitle("RedFish");
        assertEquals("RedFish", diagram.getTitle());
        diagram.setTitle("BlueFish");
        assertEquals("BlueFish", diagram.getTitle());

        diagram.undo();
        assertEquals("RedFish", diagram.getTitle());

        diagram.undo();
        assertEquals("TwoFish", diagram.getTitle());

        diagram.undo();
        assertEquals("OneFish", diagram.getTitle());

        diagram.undo();
        assertEquals("OriginalDiagram", diagram.getTitle());

        diagram.undo();
        assertEquals("OriginalDiagram", diagram.getTitle());
    }

    @Test
    public void consecutiveRedos() {
        diagram.setTitle("OneFish");
        assertEquals("OneFish", diagram.getTitle());
        diagram.setTitle("TwoFish");
        assertEquals("TwoFish", diagram.getTitle());
        diagram.setTitle("RedFish");
        assertEquals("RedFish", diagram.getTitle());
        diagram.setTitle("BlueFish");
        assertEquals("BlueFish", diagram.getTitle());
        diagram.setTitle("Finished");
        assertEquals("Finished", diagram.getTitle());

        diagram.undo();
        assertEquals("BlueFish", diagram.getTitle());

        diagram.undo();
        assertEquals("RedFish", diagram.getTitle());

        diagram.undo();
        assertEquals("TwoFish", diagram.getTitle());

        diagram.undo();
        assertEquals("OneFish", diagram.getTitle());

        diagram.undo();
        assertEquals("OriginalDiagram", diagram.getTitle());

        diagram.redo();
        assertEquals("OneFish", diagram.getTitle());

        diagram.redo();
        assertEquals("TwoFish", diagram.getTitle());

        diagram.redo();
        assertEquals("RedFish", diagram.getTitle());

        diagram.redo();
        assertEquals("BlueFish", diagram.getTitle());

        diagram.redo();
        assertEquals("Finished", diagram.getTitle());
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

    @Test
    public void redoClassTest() {
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

        diagram.redo();
        assertEquals(expected, diagram.toString());
    }
}

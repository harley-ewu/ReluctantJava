import Diagram.Diagram;
import org.junit.jupiter.api.Test;
import Diagram.DiagramMemento;

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
        String expected = "\n" +
                "--------------------------\n" +
                "Diagram: OriginalDiagram\n" +
                "\n" +
                "\n" +
                "--------------------------\n" +
                "Class Name: AnotherClass\n" +
                "--------------------------\n" +
                "Fields: \n" +
                "Methods: \n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "--------------------------\n" +
                "Class Name: TestClass\n" +
                "--------------------------\n" +
                "Fields: \n" +
                "Methods: \n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "\n" +
                "--------------------------\n" +
                "Relationship List: \n" +
                "\n" +
                "\n" +
                "--------------------------\n";
        assertEquals(expected, diagram.toString());

        String newExpectation =
                "\n" +
                        "--------------------------\n" +
                        "Diagram: OriginalDiagram\n" +
                        "\n" +
                        "\n" +
                        "--------------------------\n" +
                        "Class Name: TestClass\n" +
                        "--------------------------\n" +
                        "Fields: \n" +
                        "Methods: \n" +
                        "\n" +
                        "--------------------------\n" +
                        "\n" +
                        "\n" +
                        "--------------------------\n" +
                        "Relationship List: \n" +
                        "\n" +
                        "\n" +
                        "--------------------------\n";
        diagram.undo();
        assertEquals(newExpectation, diagram.toString());
    }

    @Test
    public void redoClassTest() {
        diagram.addClass("TestClass");
        diagram.addClass("AnotherClass");
        String expected =
                "\n" +
                        "--------------------------\n" +
                        "Diagram: OriginalDiagram\n" +
                        "\n" +
                        "\n" +
                        "--------------------------\n" +
                        "Class Name: AnotherClass\n" +
                        "--------------------------\n" +
                        "Fields: \n" +
                        "Methods: \n" +
                        "\n" +
                        "--------------------------\n" +
                        "\n" +
                        "--------------------------\n" +
                        "Class Name: TestClass\n" +
                        "--------------------------\n" +
                        "Fields: \n" +
                        "Methods: \n" +
                        "\n" +
                        "--------------------------\n" +
                        "\n" +
                        "\n" +
                        "--------------------------\n" +
                        "Relationship List: \n" +
                        "\n" +
                        "\n" +
                        "--------------------------\n";
        assertEquals(expected, diagram.toString());

        String newExpectation =
                "\n" +
                        "--------------------------\n" +
                        "Diagram: OriginalDiagram\n" +
                        "\n" +
                        "\n" +
                        "--------------------------\n" +
                        "Class Name: TestClass\n" +
                        "--------------------------\n" +
                        "Fields: \n" +
                        "Methods: \n" +
                        "\n" +
                        "--------------------------\n" +
                        "\n" +
                        "\n" +
                        "--------------------------\n" +
                        "Relationship List: \n" +
                        "\n" +
                        "\n" +
                        "--------------------------\n";
        diagram.undo();
        assertEquals(newExpectation, diagram.toString());

        diagram.redo();
        assertEquals(expected, diagram.toString());
    }

    @Test
    public void getDiagramMementoTest(){
        DiagramMemento memento = new DiagramMemento(diagram);

        assertEquals(diagram, memento.getDiagram());
    }
}

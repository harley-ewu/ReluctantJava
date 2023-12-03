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
    public void undoRedoUndoTest() {
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
        diagram.redo();
        assertEquals(expected, diagram.toString());
    }

    @Test
    public void consecutiveUndos() {
        diagram.addClass("TestClass");
        diagram.addClass("AnotherClass");
        diagram.addClass("a");
        diagram.addClass("b");
        diagram.addClass("c");
        diagram.undo();
        diagram.undo();
        diagram.undo();
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
    }

    @Test
    public void consecutiveRedos() {
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
        diagram.undo();
        diagram.undo();
        diagram.undo();
        diagram.redo();
        diagram.redo();
        diagram.redo();
        diagram.redo();
        assertEquals(expected, diagram.toString());
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

}

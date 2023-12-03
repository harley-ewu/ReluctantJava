import Diagram.Diagram;
import Class.Class;
import SaveLoadSystem.SaveLoadSystem;
import com.google.gson.JsonElement;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


public class SaveLoadSystemTest {

    @Test
    void saveDefaultCLIRunsSuccessfully() throws Exception {
        Method method = SaveLoadSystem.class.getDeclaredMethod("saveDefaultCLI", String.class, Diagram.class);
        Diagram testDiagram = new Diagram("TestDiagram");

        method.invoke(SaveLoadSystem.class, "TestSaveDefaultCLI", testDiagram);
    }

    @Test
    void saveCustomCLIRunsSuccessfully() throws Exception{
        Method method = SaveLoadSystem.class.getDeclaredMethod("saveCustomCLI", String.class, String.class, Diagram.class);
        Diagram testDiagram = new Diagram("TestDiagram");
        String home = System.getProperty("user.home");
        Path path = Paths.get(home);

        method.invoke(SaveLoadSystem.class, path.toString(), "TestSaveCustomCLI", testDiagram);
    }

    @Test
    void loadDiagramCLIRunsSuccessfully() throws Exception{
        Method method = SaveLoadSystem.class.getDeclaredMethod("loadDiagramCLI", String.class, String.class);
        Diagram testDiagram = new Diagram("TestDiagram");
        SaveLoadSystem.saveDefaultCLI("TestLoadDiagramCLI", testDiagram);
        String home = System.getProperty("user.home");
        Path path = Paths.get(home);

        method.invoke(SaveLoadSystem.class, path.toString(), "TestLoadDiagramCLI.json");
    }

    @Test
    void loadDiagramCLIAndSaveDefaultCLIIntegrationTest(){
        Diagram testSaveDiagram = new Diagram("TestDiagram");
        Class testClassOne = new Class("TestClassOne");
        Class testClassTwo = new Class("TestClassTwo");
        HashMap<String, Class> classList = new HashMap<>();

        classList.put(testClassOne.getClassName(), testClassOne);
        classList.put(testClassTwo.getClassName(), testClassTwo);
        testSaveDiagram.setClassList(classList);

        SaveLoadSystem.saveDefaultCLI("IntegrationTestOfSaveAndLoad", testSaveDiagram);

        Diagram testLoadDiagram = SaveLoadSystem.loadDiagramCLI(Paths.get(System.getProperty("user.home")).toString(), "IntegrationTestOfSaveAndLoad");

        assert testLoadDiagram != null;
        assertEquals(testLoadDiagram.getClassList().get(testClassOne.getClassName()).getClassName(), testSaveDiagram.getClassList().get(testClassOne.getClassName()).getClassName());
        assertEquals(testLoadDiagram.getClassList().get(testClassTwo.getClassName()).getClassName(), testSaveDiagram.getClassList().get(testClassTwo.getClassName()).getClassName());

    }

    @Test
    public void testSaveDefaultCLIBadInputThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> SaveLoadSystem.saveDefaultCLI(null, null));
        assertThrows(IllegalArgumentException.class, () -> SaveLoadSystem.saveDefaultCLI("", null));
    }

    @Test
    public void testNullCheckFilePathAndNameThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> SaveLoadSystem.loadDiagramCLI(null, "Not Empty"));
        assertThrows(IllegalArgumentException.class, () -> SaveLoadSystem.loadDiagramCLI("", "Not Empty"));
        assertThrows(IllegalArgumentException.class, () -> SaveLoadSystem.loadDiagramCLI("Not Empty", null));
        assertThrows(IllegalArgumentException.class, () -> SaveLoadSystem.loadDiagramCLI("Not Empty", ""));
    }

    @Test
    public void testLoadDiagramCLIBadInputCatchesExceptionReturnsNull(){
        assertNull(SaveLoadSystem.loadDiagramCLI("Bad?Path??", "?Bad() Filename>>>"));
    }

    @Test
    public void testLoadDiagramCLIBadInputReturnsNull(){
        assertNull(SaveLoadSystem.loadDiagramCLI("Bad Path", "Bad Filename"));
    }

    @Test
    public void testSaveProjectGUIBadInputCatchesException(){
        System.out.println("Should print out: \n\"There was an error writing to the file\"\n");
        SaveLoadSystem.saveProjectGUI(null, null);
        System.out.println();
    }

    @Test
    public void testLoadProjectGUIBadInputCatchesExceptionReturnsNull(){
        assertNull(SaveLoadSystem.loadProjectGUI(null));

        String homeFolder = System.getProperty("user.home");
        Path path = Paths.get(homeFolder).resolve("DoesNotExist.json");
        File fileToBeLoaded = new File(path.toString());
        File anotherFile = new File("NotReal");

        assertNull(SaveLoadSystem.loadProjectGUI(fileToBeLoaded));
        assertNull(SaveLoadSystem.loadProjectGUI(anotherFile));
    }

    @Test
    public void testPoint2DSerializerAndDeserializerClasses(){
        Point2D original = new Point2D(50, 50);
        SaveLoadSystem.Point2DSerializer serializer = new SaveLoadSystem.Point2DSerializer();
        SaveLoadSystem.Point2DDeserializer deserializer = new SaveLoadSystem.Point2DDeserializer();

        JsonElement element = serializer.serialize(original, null, null);

        Point2D newPoint2D = deserializer.deserialize(element, null, null);

        assertEquals(original, newPoint2D);
    }
}

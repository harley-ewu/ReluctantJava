import Diagram.Diagram;
import Class.Class;
import SaveLoadSystem.SaveLoadSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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

        Assertions.assertEquals(testLoadDiagram.getClassList().get(testClassOne.getClassName()).getClassName(), testSaveDiagram.getClassList().get(testClassOne.getClassName()).getClassName());
        Assertions.assertEquals(testLoadDiagram.getClassList().get(testClassTwo.getClassName()).getClassName(), testSaveDiagram.getClassList().get(testClassTwo.getClassName()).getClassName());

    }
}

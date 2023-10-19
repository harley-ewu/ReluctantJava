import Diagram.Diagram;
import SaveLoadSystem.SaveLoadSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;


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
    void loadDiagramCLIRunsSuccessfully(){

    }
}

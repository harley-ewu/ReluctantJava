import Diagram.Diagram;
import SaveLoadSystem.SaveLoadSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;


public class SaveLoadSystemTest {

    @Test
    void saveDefaultCLIRunsSuccessfully() throws Exception {
        Method method = SaveLoadSystem.class.getDeclaredMethod("saveDefaultCLI", String.class, Diagram.class);
        Diagram testDiagram = new Diagram("TestDiagram");
        method.setAccessible(true);

        method.invoke(SaveLoadSystem.class, "TestSaveDefaultCLI", testDiagram);
    }

    @Test
    void saveCustomCLIRunsSuccessfully(){

    }

    @Test
    void loadDiagramCLIRunsSuccessfully(){

    }
}

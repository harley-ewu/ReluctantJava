package SaveLoadSystem;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SaveLoadSystemTest {

    @Test
    protected void testSaveFileToDefaultPath() throws Exception {
        MockUmlClass testClass = new MockUmlClass("test", "This is a test");
        ArrayList<MockUmlClass> list = new ArrayList<>();
        SaveLoadSystem saveLoad = new SaveLoadSystem();
        String home = System.getProperty("user.home");

        list.add(testClass);
        saveLoad.defaultPathSave("SaveLoadTestFile", list);
        String path = String.valueOf(Paths.get(home).resolve("SaveLoadTestFile.json"));
        File file = new File(path);

        // I assert that the file created is not empty
        Assert.assertTrue(file.length() > 0);
    }

    protected void testLoadFromDefaultPath() {

    }
}

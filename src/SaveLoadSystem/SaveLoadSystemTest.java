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
        saveLoad.defaultPathSave("SaveTestFile", list);
        String path = String.valueOf(Paths.get(home).resolve("SaveTestFile.json"));
        File file = new File(path);

        // I assert that the file created is not empty
        Assert.assertTrue(file.length() > 0);
    }

    @Test
    protected void testLoadFromDefaultPath() {

    }

    @Test
    protected void testIntegrationOfSaveAndLoad() {

        /**
         * Initialize the test class, the ArrayList to store the test class,
         * the save/load class, and get the path to the default directory.
         */
        MockUmlClass testClass = new MockUmlClass("test", "This is a test");
        ArrayList<MockUmlClass> list = new ArrayList<>();
        SaveLoadSystem saveLoad = new SaveLoadSystem();
        String defaultPath = System.getProperty("user.home");

        /**
         * Add the test class to the ArrayList, Save the ArrayList to a JSON file named SaveLoadIntegrationTest.json
         * in the default directory, then get the file path to the created file.
         */
        list.add(testClass);
        saveLoad.defaultPathSave("SaveLoadIntegrationTest", list);
        String path = String.valueOf(Paths.get(defaultPath).resolve("SaveLoadIntegrationTest.json"));

        /**
         * Load the file into the ArrayList.
         */
        list = saveLoad.load(path);

        // I assert that the description of the saved class is the same as the loaded class.
        assertTrue(testClass.getDescription().equals(list.getFirst().getDescription()));

        // I assert that the name of the saved class is the same as the loaded class.
        assertTrue(testClass.getName().equals(list.getFirst().getName()));
    }
}

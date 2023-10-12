package SaveLoadSystem;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SaveLoadSystemTest {

    /**
     * Description: Tests if SaveDefault() method runs successfully given good input.
     * @throws NoSuchMethodException : required for getDeclaredMethod() method
     * @throws InvocationTargetException : required for invoke() method
     * @throws IllegalAccessException : required for invoke() method
     */
    @Test
    protected void testSaveDefaultRunsSuccessfully() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = SaveLoadSystem.class.getDeclaredMethod("saveDefault", String.class, ArrayList.class);
        SaveLoadSystem saveDefault = new SaveLoadSystem();
        ArrayList<MockUmlClass> testList = new ArrayList<>();

        method.invoke(saveDefault, "TestSaveDefault", testList);
    }

    /**
     * Description: Tests if saveCustom() method runs successfully given good input.
     * @throws NoSuchMethodException : required for getDeclaredMethod()
     * @throws InvocationTargetException : required for invoke() method
     * @throws IllegalAccessException : required for invoke() method
     */
    @Test
    protected void testSaveCustomRunsSuccessfully() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = SaveLoadSystem.class.getDeclaredMethod("saveCustom", String.class, String.class, ArrayList.class);
        SaveLoadSystem saveCustom = new SaveLoadSystem();
        String path = Paths.get(System.getProperty("user.home")).toString();
        ArrayList<MockUmlClass> testList = new ArrayList<>();

        method.invoke(saveCustom, path, "TestSaveCustom", testList);
    }

    /**
     * Description: Tests if load() method runs successfully given good input.
     * @throws NoSuchMethodException : required for getDeclaredMethod()
     * @throws InvocationTargetException : required for invoke() method
     * @throws IllegalAccessException : required for invoke() method
     */
    @Test
    protected void testLoadRunsSuccessfully() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = SaveLoadSystem.class.getDeclaredMethod("load", String.class);
        SaveLoadSystem load = new SaveLoadSystem();
        ArrayList<MockUmlClass> testList = new ArrayList<>();
        String path = Paths.get(System.getProperty("user.home")).resolve("TestLoad.json").toString();

        load.saveDefault("TestLoad", testList);

        method.invoke(load, path);
    }

    /**
     * Description: Tests the integration of the saveDefault() method and the load() method.
     */
    @Test
    protected void testIntegrationOfSaveAndLoad() {

        //Initialize the test class, the ArrayList to store the test class,
        //the save/load class, and get the path to the default directory.
        MockUmlClass testClass = new MockUmlClass("test", "This is a test");
        ArrayList<MockUmlClass> list = new ArrayList<>();
        SaveLoadSystem saveLoad = new SaveLoadSystem();
        String defaultPath = System.getProperty("user.home");

        //Add the test class to the ArrayList, Save the ArrayList to a JSON file named SaveLoadIntegrationTest.json
        //in the default directory, then get the file path to the created file.
        list.add(testClass);
        saveLoad.saveDefault("SaveLoadIntegrationTest", list);
        String path = String.valueOf(Paths.get(defaultPath).resolve("SaveLoadIntegrationTest.json"));

        // Load the file into the ArrayList.
        list = saveLoad.load(path);

        // I assert that the description of the saved class is the same as the loaded class.
        assertEquals(testClass.getDescription(), list.getFirst().getDescription());

        // I assert that the name of the saved class is the same as the loaded class.
        assertEquals(testClass.getName(), list.getFirst().getName());
    }
}

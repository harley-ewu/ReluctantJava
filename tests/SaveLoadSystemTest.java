package tests;

import Class.Class;
import Attributes.Attribute;
import SaveLoadSystem.SaveLoadSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.ArrayList;

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
        ArrayList<Class> testList = new ArrayList<>();

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
        ArrayList<Class> testList = new ArrayList<>();

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
        ArrayList<Class> testList = new ArrayList<>();
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
        //the test attribute, the save/load class, and get the path to the default directory.
        Class testClass = new Class("test");
        ArrayList<Class> list = new ArrayList<>();
        SaveLoadSystem saveLoad = new SaveLoadSystem();
        String defaultPath = System.getProperty("user.home");

        //Add the test class to the ArrayList, Save the ArrayList to a JSON file named SaveLoadIntegrationTest.json
        //in the default directory, then get the file path to the created file.
        list.add(testClass);
        saveLoad.saveDefault("SaveLoadIntegrationTest", list);
        String path = String.valueOf(Paths.get(defaultPath).resolve("SaveLoadIntegrationTest.json"));

        // Load the file into the ArrayList.
        list = saveLoad.load(path);

        // I assert that the name of the saved class is the same as the loaded class.
        Assertions.assertEquals(testClass.getClassName(), list.getFirst().getClassName());

    }
}

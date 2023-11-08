import Class.Class;
import Diagram.Diagram;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClassTests {

    private final PrintStream ORIGINALOUT = System.out;
    private final InputStream ORIGINALIN = System.in;
    private ByteArrayOutputStream testOut;
    private ByteArrayInputStream testIn;

    Diagram newDiagram;
    Class newClass;

    @BeforeEach
    public void init() {

        this.testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(this.testOut));
    }

    @AfterEach
    public void cleanUp() {

        System.setOut(this.ORIGINALOUT);
        System.setIn(this.ORIGINALIN);
    }

    private void injectInput(final String msg) throws IllegalAccessException, NoSuchFieldException {

        this.testIn = new ByteArrayInputStream((msg +System.lineSeparator()).getBytes());
        System.setIn(this.testIn);
        Field field = Keyboard.class.getDeclaredField("in");
        field.setAccessible(true);
        field.set(null, new BufferedReader(new InputStreamReader(System.in)));
    }

    @BeforeEach
    void setUp() {
        this.newDiagram = new Diagram("Test Diagram");
        this.newClass = new Class("Test Class");
        //newDiagram.addClass(this.newClass);
    }

    @Test
    public void testGetClassName() {
        assertEquals("Test Class", this.newClass.getClassName());
    }

    @Test
    public void testSetClassName() {
        this.newClass.setClassName("New Class Name");
        assertEquals("New Class Name", this.newClass.getClassName());
    }

    @Test
    void testDisplayAttributes2() {
        Class test = new Class("Test Class");
        ArrayList<String> testList = new ArrayList<>();
        ArrayList<String> testList2 = new ArrayList<>();
        ArrayList<String> testList3 = new ArrayList<>();

        testList2.add("Boolean");
        test.createAttribute("testMethod", testList, 2);
        test.createAttribute("testField1", testList2, 1);
        test.createAttribute("testMethod2", testList, 2);

        test.sortArrayList(test.getAttributes());

        String expected = "Available Fields and Methods: \n" +
                "1. testMethod2()\n" +
                "2. testMethod()\n"+
                "3. testField1: Boolean\n";
        assertEquals(expected, test.displayAttributes());
    }
    @Test
    public void  testDisplayAttributes() {

        String output = "Available Fields and Methods: \n";

        assertEquals(output, this.newClass.displayAttributes());

    }
}


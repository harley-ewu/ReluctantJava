
import Attributes.Field;
import Attributes.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MethodTests {

    private ArrayList<String> params = new ArrayList<>();
    private String methodName;
    private Method testMethod;

    @BeforeEach
    public void init() {
        params.add("param1");
        methodName = "TestMethod";
        testMethod = new Method(methodName, params);
    }

    @Test
    public void testMethodConstructorJustNameGoodInput(){
        Method method = new Method("NewMethod");
    }

    @Test
    public void testMethodConstructorJustNameBadInputThrowsException(){
        IllegalArgumentException badInput = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Method method = new Method("");
                },
                "The method name cannot be null or empty"
        );

        assertTrue(badInput.getMessage().contains("The method name cannot be null or empty"));

        badInput = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Method method = new Method(null);
                },
                "The method name cannot be null or empty"
        );

        assertTrue(badInput.getMessage().contains("The method name cannot be null or empty"));
    }

    @Test
    public void testMethodConstructorNameAndParamsGoodInput(){
        ArrayList<String> newParams = new ArrayList<>();
        newParams.add("param1");
        Method method = new Method("NewMethod", newParams);
    }

    @Test
    public void testMethodConstructorNameAndParamsBadInputThrowsException(){
        ArrayList<String> newParams = new ArrayList<>();
        newParams.add("param1");

        IllegalArgumentException badInput = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Method method = new Method("", newParams);
                },
                "The method name cannot be null or empty"
        );

        assertTrue(badInput.getMessage().contains("The method name cannot be null or empty"));

        badInput = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Method method = new Method(null, newParams);
                },
                "The method name cannot be null or empty"
        );

        assertTrue(badInput.getMessage().contains("The method name cannot be null or empty"));

        badInput = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Method method = new Method("NewMethod", null);
                },
                "The parameters cannot be null"
        );

        assertTrue(badInput.getMessage().contains("The parameters cannot be null"));
    }

    @Test
    public void testGetAndSetParametersGoodInput(){
        ArrayList<String> newParams = new ArrayList<>();
        newParams.add("NewParam");

        testMethod.setParameters(newParams);

        assertEquals(newParams, testMethod.getParameters());
    }

    @Test
    public void testSetParametersBadInputThrowsException(){
        IllegalArgumentException badInput = assertThrows(
                IllegalArgumentException.class,
                () -> testMethod.setParameters(null),
                "The parameters cannot be null"
        );

        assertTrue(badInput.getMessage().contains("The parameters cannot be null"));
    }

    @Test
    public void testGetAndSetNameGoodInput(){
        testMethod.setName("NewName");
        assertEquals("NewName", testMethod.getName());
    }

    @Test
    public void testSetNameBadInputThrowsException(){
        IllegalArgumentException badInput = assertThrows(
                IllegalArgumentException.class,
                () -> testMethod.setName(""),
                "The method name cannot be null or empty"
        );

        assertTrue(badInput.getMessage().contains("The method name cannot be null or empty"));

        badInput = assertThrows(
                IllegalArgumentException.class,
                () -> testMethod.setName(null),
                "The method name cannot be null or empty"
        );

        assertTrue(badInput.getMessage().contains("The method name cannot be null or empty"));
    }

    @Test
    public void testAddParameterGoodInput(){
        assertTrue(testMethod.addParameter("param2"));
    }

    @Test
    public void testAddParametersBadInputThrowsException(){
        IllegalArgumentException badInput = assertThrows(
                IllegalArgumentException.class,
                () -> testMethod.addParameter(null),
                "The new parameter cannot be null or empty"
        );

        assertTrue(badInput.getMessage().contains("The new parameter cannot be null or empty"));

        badInput = assertThrows(
                IllegalArgumentException.class,
                () -> testMethod.addParameter(""),
                "The new parameter cannot be null or empty"
        );

        assertTrue(badInput.getMessage().contains("The new parameter cannot be null or empty"));
    }

    @Test
    public void testRemoveParametersGoodInputReturnsTrue(){
        assertTrue(testMethod.removeParameter(1));
    }

    @Test
    public void testRemoveParametersBadInputReturnsFalse(){
        assertFalse(testMethod.removeParameter(0));
        assertFalse(testMethod.removeParameter(testMethod.getParameters().size() + 1));
    }

    @Test
    public void testEqualsGoodInputReturnsTrue(){
        Method newMethod = new Method(methodName, params);

        assertEquals(testMethod, testMethod);
        assertEquals(testMethod, newMethod);
    }

    @Test
    public void testEqualsBadInputReturnsFalse(){
        Field field = new Field("TestField", "String");

        assertNotEquals(testMethod, field);
        assertNotEquals(null, testMethod);
    }

    @Test
    public void testToStringOutput(){
        String output = "TestMethod(param1)";

        assertEquals(output, testMethod.toString());
    }

}

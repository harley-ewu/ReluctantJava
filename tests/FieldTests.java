import Attributes.Field;
import Attributes.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class FieldTests {

    private String testName;
    private String testPrimitive;
    Field testField;

    @BeforeEach
    public void init(){
        testName = "TestField";
        testPrimitive = "String";
        testField = new Field(testName, testPrimitive);

    }

    @Test
    public void testFieldConstructorGoodInput(){
        Field newField = new Field("TestName", "TestPrimitive");
    }

    @Test
    public void testFieldConstructorBadInputThrowsException(){
        IllegalArgumentException badInput = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Field newField = new Field(null, testPrimitive);
                },
                "The field name cannot be null or empty"
        );

        assertTrue(badInput.getMessage().contains("The field name cannot be null or empty"));

        badInput = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Field newField = new Field("", testPrimitive);
                },
                "The field name cannot be null or empty"
        );

        assertTrue(badInput.getMessage().contains("The field name cannot be null or empty"));

        badInput = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Field newField = new Field(testName, null);
                },
                "The field primitive cannot be null or empty"
        );

        assertTrue(badInput.getMessage().contains("The field primitive cannot be null or empty"));

        badInput = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Field newField = new Field(testName, "");
                },
                "The field primitive cannot be null or empty"
        );

        assertTrue(badInput.getMessage().contains("The field primitive cannot be null or empty"));
    }

    @Test
    public void testGetAndSetPrimitiveGoodInput(){
        testField.setPrimitive("NewPrimitive");
        assertEquals("NewPrimitive", testField.getPrimitive());
    }

    @Test
    public void testSetPrimitiveBadInputThrowsException(){
        IllegalArgumentException badInput = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    testField.setPrimitive(null);
                },
                "The field primitive cannot be null or empty"
        );

        assertTrue(badInput.getMessage().contains("The field primitive cannot be null or empty"));

        badInput = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    testField.setPrimitive("");
                },
                "The field primitive cannot be null or empty"
        );

        assertTrue(badInput.getMessage().contains("The field primitive cannot be null or empty"));
    }

    @Test
    public void testGetAndSetNameGoodInput(){
        testField.setName("NewName");
        assertEquals("NewName", testField.getName());
    }

    @Test
    public void testSetNameBadInputThrowsException(){
        IllegalArgumentException badInput = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    testField.setName(null);
                },
                "The field name cannot be null or empty"
        );

        assertTrue(badInput.getMessage().contains("The field name cannot be null or empty"));

        badInput = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    testField.setName("");
                },
                "The field name cannot be null or empty"
        );

        assertTrue(badInput.getMessage().contains("The field name cannot be null or empty"));
    }

    @Test
    public void testEqualsSameObjectReturnsTrue(){
        boolean equals = testField.equals(testField);

        assertTrue(equals);
    }

    @Test
    public void testEqualsDifferentClassReturnsFalse(){
        ArrayList<String> params = new ArrayList<>();
        params.add("param1");
        Method method = new Method("testMethod", params);

        boolean equals = testField.equals(method);

        assertFalse(equals);
    }

    @Test
    public void testEqualsDifferentInstanceSameInfoReturnsTrue(){
        Field newField = new Field("TestField", "String");

        boolean equals = testField.equals(newField);

        assertTrue(equals);
    }

    @Test
    public void testEqualsDifferentInstanceDifferentInfoReturnsFalse(){
        Field newField = new Field("NewField", "int");

        boolean equals = testField.equals(newField);

        assertFalse(equals);
    }

    @Test
    public void testEqualsNullObjectReturnsFalse(){
        boolean equals = testField.equals(null);

        assertFalse(equals);
    }

    @Test
    public void testToStringOutput(){
        assertEquals("TestField: String", testField.toString());
    }
}

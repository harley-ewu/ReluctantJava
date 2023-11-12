import Class.Class;
import Attributes.Field;
import Attributes.Method;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class ClassTests {

    private final PrintStream ORIGINALOUT = System.out;
    private final InputStream ORIGINALIN = System.in;
    private ByteArrayOutputStream testOut;
    private ByteArrayInputStream testIn;

    private Class testClass;
    private ArrayList<Field> testFields;
    private ArrayList<Method> testMethods;
    private ArrayList<String> testParams;
    private Field testField;
    private Method testMethod;

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

    @BeforeEach
    void setUp() {
        this.testClass = new Class("Test Class");
        this.testFields = new ArrayList<>();
        this.testMethods = new ArrayList<>();
        this.testParams = new ArrayList<>();
        this.testParams.add("param1");
        this.testParams.add("param2");
        this.testField = new Field("TestField", "String");
        this.testMethod = new Method("TestMethod", this.testParams);
    }

    @Test
    public void testClassConstructorGoodInput(){
        Class newClass = new Class("NewClass");

        assertEquals("NewClass", newClass.getClassName());
    }

    @Test
    public void testClassConstructorBadInputThrowsException(){
        IllegalArgumentException badInput = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Class newClass = new Class(null);
                },
                "Class name is null or empty."
        );

        assertTrue(badInput.getMessage().contains("Class name is null or empty."));

        badInput = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Class newClass = new Class("");
                },
                "Class name is null or empty."
        );

        assertTrue(badInput.getMessage().contains("Class name is null or empty."));
    }

    @Test
    public void testGetClassName() {
        assertEquals("Test Class", this.testClass.getClassName());
    }

    @Test
    public void testSetAndGetClassName() {
        this.testClass.setClassName("New Class Name");
        assertEquals("New Class Name", this.testClass.getClassName());
    }

    @Test
    public void testSetClassNameThrowException(){
        IllegalArgumentException badInput = assertThrows(
                IllegalArgumentException.class,
                () -> this.testClass.setClassName(null),
                "Class name parameter is null or empty."
        );

        assertTrue(badInput.getMessage().contains("Class name parameter is null or empty."));

       badInput = assertThrows(
                IllegalArgumentException.class,
                () -> this.testClass.setClassName(""),
                "Class name parameter is null or empty."
       );

        assertTrue(badInput.getMessage().contains("Class name parameter is null or empty."));
    }

    @Test
    public void testSetAndGetFields(){
        this.testClass.setFields(this.testFields);
        assertEquals(this.testFields, this.testClass.getFields());
    }

    @Test
    public void testGetAndSetMethods(){
        this.testClass.setMethods(this.testMethods);
        assertEquals(this.testMethods, this.testClass.getMethods());
    }

    @Test
    public void testCreateFieldGoodInputReturnsTrue(){
        ArrayList<String> primitive = new ArrayList<>();
        primitive.add("String");

        assertTrue(this.testClass.createField("TestField", primitive));
    }

    @Test
    public void testCreateFieldBadInputReturnsFalse(){
        ArrayList<String> primitiveOne = new ArrayList<>();
        ArrayList<String> emptyPrimitiveList = new ArrayList<>();
        primitiveOne.add("String");

        assertFalse(this.testClass.createField(null, primitiveOne));
        assertFalse(this.testClass.createField("", primitiveOne));
        assertFalse(this.testClass.createField("TestField", null));
        assertFalse(this.testClass.createField("TestField", emptyPrimitiveList));
    }

    @Test
    public void testCreateFieldAddExistingFieldReturnsFalse(){
        this.testClass.getFields().add(this.testField);
        ArrayList<String> primitive = new ArrayList<>();
        primitive.add("int");

        assertFalse(this.testClass.createField("TestField", primitive));
    }

    @Test
    public void testCreateMethodGoodInputReturnsTrue(){
        ArrayList<String> params = new ArrayList<>();
        ArrayList<String> emptyParams = new ArrayList<>();
        params.add("param1");
        params.add("param2");

        assertTrue(this.testClass.createMethod("TestMethodOne", params));
        assertTrue(this.testClass.createMethod("TestMethodTwo", emptyParams));
    }

    @Test
    public void testCreateMethodAddOverloadMethodReturnsTrue(){
        ArrayList<String> params = new ArrayList<>();
        params.add("param1");
        params.add("param2");
        params.add("param3");

        //this.testMethod has the name "TestMethod" and params "param1, param2"
        this.testClass.getMethods().add(this.testMethod);

        //Even though they have the same name, because they have
        //different parameters this should still be added to the list.
        assertTrue(this.testClass.createMethod("TestMethod", params));
    }

    @Test
    public void testCreateMethodBadInputReturnsFalse(){
        ArrayList<String> params = new ArrayList<>();
        params.add("param1");

        assertFalse(this.testClass.createMethod("TestMethod", null));
        assertFalse(this.testClass.createMethod("", params));
        assertFalse(this.testClass.createMethod(null, params));
    }

    @Test
    public void testCreateMethodAddSameMethodReturnsFalse(){
        ArrayList<String> params = new ArrayList<>();
        params.add("param1");
        params.add("param2");

        //this.testMethod has the name "TestMethod" and params "param1, param2"
        this.testClass.getMethods().add(this.testMethod);

        //Since this.testMethod and the method being added have the same name and params
        //it should return false and not add it to the list.
        assertFalse(this.testClass.createMethod("TestMethod", params));
    }

    @Test
    public void testDeleteFieldGoodInputReturnsTrue(){
        this.testClass.getFields().add(this.testField);
        assertTrue(this.testClass.deleteField(1));

        this.testClass.getFields().add(this.testField);
        assertTrue(this.testClass.deleteField(this.testClass.getFields().size()));
    }

    @Test
    public void testDeleteFieldBadInputReturnsFalse(){
        this.testClass.getFields().add(this.testField);

        assertFalse(this.testClass.deleteField(-1));
        assertFalse(this.testClass.deleteField(this.testClass.getFields().size() + 1));
    }

    @Test
    public void testDeleteMethodGoodInputReturnsTrue(){
        this.testClass.getMethods().add(this.testMethod);
        assertTrue(this.testClass.deleteMethod(1));

        this.testClass.getMethods().add(this.testMethod);
        assertTrue(this.testClass.deleteMethod(this.testClass.getMethods().size()));
    }

    @Test
    public void testDeleteMethodBadInputReturnsFalse(){
        this.testClass.getMethods().add(this.testMethod);

        assertFalse(this.testClass.deleteMethod(-1));
        assertFalse(this.testClass.deleteMethod(this.testClass.getMethods().size() + 1));
    }

    @Test
    public void testRenameFieldGoodInputReturnsTrue(){
        this.testClass.getFields().add(this.testField);

        assertTrue(this.testClass.renameField(1, "NewTestField"));
        assertEquals("NewTestField", this.testClass.getFields().get(0).getName());
    }

    @Test
    public void testRenameFieldBadInputReturnsFalse(){
        this.testClass.getFields().add(this.testField);

        assertFalse(this.testClass.renameField(0, "NewTestField"));
        assertFalse(this.testClass.renameField(this.testClass.getFields().size() + 1, "NewTestMethod"));
        assertFalse(this.testClass.renameField(1, ""));
        assertFalse(this.testClass.renameField(1, null));
    }

    @Test
    public void testRenameFieldToExistingFieldNameReturnsFalse(){
        Field field = new Field("TestFieldTwo", "int");

        //this.testField's name is "TestField".
        this.testClass.getFields().add(this.testField);
        this.testClass.getFields().add(field);

        //Since we are trying to rename "TestField" to "TestFieldTwo" it
        //Should return false since that name already exists.
        assertFalse(this.testClass.renameField(1, "TestFieldTwo"));

        //The name of this.testField (or the first field in the list) should
        //still be "TestField" since it failed to be changed.
        assertEquals("TestField", this.testClass.getFields().get(0).getName());
    }

    @Test
    public void testRenameMethodGoodInputReturnsTrue(){
        this.testClass.getMethods().add(this.testMethod);

        assertTrue(this.testClass.renameMethod(1, "NewTestMethod", this.testParams));
    }

    @Test
    public void testRenameMethodBadInputReturnsFalse(){
        this.testClass.getMethods().add(this.testMethod);

        assertFalse(this.testClass.renameMethod(0, "NewTestMethod", this.testParams));
        assertFalse(this.testClass.renameMethod(this.testClass.getMethods().size() + 1, "NewTestMethod", this.testParams));
        assertFalse(this.testClass.renameMethod(1, null, this.testParams));
        assertFalse(this.testClass.renameMethod(0, "", this.testParams));
        assertFalse(this.testClass.renameMethod(0, "NewTestMethod", null));
    }

    @Test
    public void testRenameMethodToExistingMethodWithSameParamsReturnsFalse(){
        this.testClass.getMethods().add(this.testMethod);
        Method method = new Method("TestMethodTwo", this.testParams);
        this.testClass.getMethods().add(method);

        assertFalse(this.testClass.renameMethod(1, "TestMethodTwo", this.testParams));
    }

    @Test
    public void testRenameFieldPrimitiveGoodInputReturnsTrue() {
        this.testClass.getFields().add(this.testField);
        ArrayList<String> primitive = new ArrayList<>();
        primitive.add("int");

        assertTrue(this.testClass.renameFieldPrimitive(primitive, 1));
    }

    @Test
    public void testRenameFieldPrimitiveBadInputReturnsFalse() {
        this.testClass.getFields().add(this.testField);
        ArrayList<String> primitive = new ArrayList<>();
        primitive.add("int");

        assertFalse(this.testClass.renameFieldPrimitive(primitive, 0));
        assertFalse(this.testClass.renameFieldPrimitive(primitive, this.testClass.getFields().size() + 1));
        assertFalse(this.testClass.renameFieldPrimitive(null, 1));
        primitive.clear();
        assertFalse(this.testClass.renameFieldPrimitive(primitive, 0));
    }

    @Test
    public void testRenameMethodParamsGoodInputReturnsTrue() {
        this.testClass.getMethods().add(this.testMethod);
        ArrayList<String> parameters = new ArrayList<>();
        parameters.add("param1");
        parameters.add("param2");
        parameters.add("param3");

        assertTrue(this.testClass.renameMethodParams(parameters, 1));
    }

    @Test
    public void testRenameMethodParamsBadInputReturnsFalse() {
        this.testClass.getMethods().add(this.testMethod);
        ArrayList<String> parameters = new ArrayList<>();
        parameters.add("param1");
        parameters.add("param2");
        parameters.add("param3");

        assertFalse(this.testClass.renameMethodParams(parameters, 0));
        assertFalse(this.testClass.renameMethodParams(parameters, this.testClass.getMethods().size() + 1));
        assertFalse(this.testClass.renameMethodParams(null, 1));
    }

    @Test
    public void testRenameMethodParamsToExistingOverloadReturnsFalse() {
        this.testClass.getMethods().add(this.testMethod);
        ArrayList<String> parameters = new ArrayList<>();
        parameters.add("param1");
        parameters.add("param2");
        parameters.add("param3");
        Method TestMethodTwo = new Method("TestMethod", parameters);
        this.testClass.getMethods().add(TestMethodTwo);

        assertFalse(this.testClass.renameMethodParams(parameters, 1));
    }

    @Test
    public void  testDisplayAttributes() {
        this.testClass.getFields().add(this.testField);
        this.testClass.getMethods().add(this.testMethod);

        String output = "Available Fields and Methods: \n"
                + "Fields: \n"
                + "1. TestField: String\n"
                + "Methods: \n"
                + "1. TestMethod(param1, param2)\n";

        assertEquals(output, this.testClass.displayAttributes());

    }

    @Test
    public void testToString() {
        this.testClass.getFields().add(this.testField);
        this.testClass.getMethods().add(this.testMethod);

        String output = "Class Name: " + this.testClass.getClassName() + "\n"
                +"---------------------\n"
                + "Fields: \n"
                + "TestField: String\n"
                + "Methods: \n"
                + "TestMethod(param1, param2)\n"
                + "\n";

        assertEquals(output, this.testClass.toString());
    }
}


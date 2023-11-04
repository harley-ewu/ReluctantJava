import Attributes.Attribute;
import Class.Class;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AttributeTests {
    Class test = new Class("Test Class");
    ArrayList<String> testList = new ArrayList<>();
    ArrayList<String> testList2 = new ArrayList<>();
    ArrayList<String> testList3 = new ArrayList<>();
    @Test
    void fieldTest() {
        testList.add("Test field");
        test.createAttribute("Test attribute", testList, 1);
        String expected = "Class Name: Test Class\n" +
                "---------------------\n" +
                "Attributes: \n" +
                "Test attribute: Test field\n" +
                "\n" +
                "\n" +
                "Relationships: \n" +
                "\n";
        assertEquals(expected, test.toString());
    }

    @Test
    void sortingArrayListTest() {
        testList.add("Test param1");
        testList.add("Test param2");
        testList.add("Test param3");
        testList2.add("Boolean");
        testList3.add("Test param1");
        testList3.add("Test param2");
        testList3.add("Test param3");
        test.createAttribute("testMethod", testList, 2);
        test.createAttribute("Testfield", testList2, 1);
        test.createAttribute("anotherMethod", testList3, 2);
        String expected= "Class Name: Test Class\n" +
                "---------------------\n" +
                "Attributes: \n" +
                "testMethod(Test param1, Test param2, Test param3)\n" +
                "anotherMethod(Test param1, Test param2, Test param3)\n" +
                "Testfield: Boolean\n" +
                "\n" +
                "\n" +
                "Relationships: \n\n";
        System.out.println(test);
        assertEquals(expected, test.toString());
    }

    @Test
    void displayAttributes() {
        String expected = "Available Fields and Methods: \n" +
                "1. testMethod()\n" +
                "2. field: Boolean\n";

        test.createAttribute("testMethod", testList, 2);
        testList2.add("Boolean");
        test.createAttribute("field", testList2, 1);
        assertEquals(expected, test.displayAttributes());
    }

    @Test
    void deleteAttribute() {
        String expected = "Available Fields and Methods: \n" +
                "1. testMethod()\n" +
                "2. field: Boolean\n";
        test.createAttribute("testMethod", testList, 2);
        testList2.add("Boolean");
        test.createAttribute("field", testList2, 1);
        testList.add("param1");
        test.createAttribute("anotherMethod", testList, 2);
        System.out.println(test.displayAttributes());

        test.deleteAttribute(2);
        System.out.println(test.displayAttributes());
        assertEquals(expected, test.displayAttributes());
    }

    @Test
    void renameAttribute() {
        String expected = "Available Fields and Methods: \n" +
                "1. testMethod()\n" +
                "2. field: Boolean\n" +
                "3. differentField: String\n";
        test.createAttribute("testMethod", testList, 2);

        testList2.add("Boolean");
        test.createAttribute("field", testList2, 1);

        testList.add("param1");
        test.createAttribute("anotherMethod", testList, 2);

        System.out.println(test.displayAttributes());
        testList3.add("String");

        test.renameAttribute(2, "differentField", testList3, Attribute.Type.FIELD);
        System.out.println(test.displayAttributes());
        assertEquals(expected, test.displayAttributes());
    }

    @Test
    void renameSameNameAttribute() {
        String expected = "Available Fields and Methods: \n" +
                "1. testMethod()\n" +
                "2. field: Boolean\n" +
                "3. differentField: String\n" ;
        test.createAttribute("testMethod", testList, 2);

        testList2.add("Boolean");
        test.createAttribute("field", testList2, 1);

        testList.add("String");
        test.createAttribute("differentField", testList, 1);

        System.out.println(test.displayAttributes());
        testList3.add("Boolean");

        // Testing to confirm they were not allowed to change.
        test.renameAttribute(2, "field", testList3, Attribute.Type.FIELD);
        assertEquals(expected, test.displayAttributes());

    }

    @Test
    void renameToEmptyName() {
        String expected = "Available Fields and Methods: \n" +
                "1. testMethod()\n" +
                "2. field: Boolean\n";

        test.createAttribute("testMethod", testList, 2);
        testList2.add("Boolean");
        test.createAttribute("field", testList2, 1);
        test.renameAttribute(2, "", testList, Attribute.Type.FIELD);

        assertEquals(expected, test.displayAttributes());
    }

    @Test
    void changeParameters() {
        String expected = "Available Fields and Methods: \n" +
                "1. testMethod(String)\n";
        test.createAttribute("testMethod", testList, 2);
        testList2.add("String");
        test.renameAttributeParameters(testList2, 1);
        assertEquals(expected, test.displayAttributes());
    }
}

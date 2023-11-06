import Attributes.Attribute;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AttributeTests {
    @Test
    void fieldTest() {
        testList.add("Test field");
        test.createAttribute("Test attribute", testList, 1);
        String expected = "Class Name: Test Class\n" +
                "---------------------\n" +
                "Attributes: \n" +
                "Test attribute: Test field\n";
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
                "Testfield: Boolean\n" +
                "testMethod(Test param1, Test param2, Test param3)\n" +
                "anotherMethod(Test param1, Test param2, Test param3)\n";
        System.out.println(test);
        assertEquals(expected, test.toString());
    }

    @Test
    void toStringErrorCheck() {
        Attribute attribute = new Attribute();
        assertEquals("", attribute.toString());
    }

    @Test
    void deleteAttribute() {
        String expected = "Available Fields and Methods: \n" +
                "1. testMethod()\n" +
                "2. anotherMethod(param1)\n";
        test.createAttribute("testMethod", testList, 2);
        testList2.add("Boolean");
        test.createAttribute("field", testList2, 1);
        testList.add("param1");
        test.createAttribute("anotherMethod", testList, 2);
        System.out.println(test.displayAttributes());

        List<Attribute> result = attribute.getAttributes();

        // Check if the returned list contains the expected attributes
        assertEquals(2, result.size());
    }

    @Test
    void renameAttribute() {
        String expected = "Available Fields and Methods: \n" +
                "1. testMethod()\n" +
                "2. differentField: String\n" +
                "3. anotherMethod(param1)\n";
        test.createAttribute("testMethod", testList, 2);

        String check = "Name";
        // Check if we allowed adding of two of the same attributes.
        assertEquals(check, attribute.toString());
    }

    @Test
    void deleteAttributeTest() {
        Attribute attribute = new Attribute();
        attribute.addAttribute("Name");
        attribute.addAttribute("Birthday");
        attribute.addAttribute("Age");

        //Removing the attribute
        attribute.deleteAttribute("Birthday");

        String check = "Name\nAge";
        assertEquals(check, attribute.toString());
    }

    @Test
    void renameAttributeTest() {
        Attribute attribute = new Attribute();
        attribute.addAttribute("Name");
        attribute.addAttribute("Birthday");
        attribute.addAttribute("Age");

        assertEquals("Name\nBirthday\nAge", attribute.toString());

        attribute.renameAttribute("Birthday", "Weight");
        System.out.println(attribute.toString());
        assertEquals("Name\nWeight\nAge", attribute.toString());
    }
}

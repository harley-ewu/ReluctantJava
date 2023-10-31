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
        test.addAttribute("Test attribute", testList, 1);
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
        test.addAttribute("testMethod", testList, 2);
        test.addAttribute("Testfield", testList2, 1);
        test.addAttribute("anotherMethod", testList3, 2);
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

        test.addAttribute("testMethod", testList, 2);
        testList2.add("Boolean");
        test.addAttribute("field", testList2, 1);
        assertEquals(expected, test.displayAttributes());
    }

    @Test
    void deleteAttribute() {
        String expected = "Available Fields and Methods: \n" +
                "1. testMethod()\n" +
                "2. anotherMethod(param1)\n";
        test.addAttribute("testMethod", testList, 2);
        testList2.add("Boolean");
        test.addAttribute("field", testList2, 2);
        testList.add("param1");
        test.addAttribute("anotherMethod", testList, 2);
        System.out.println(test.displayAttributes());

        test.deleteAttribute(2);
        System.out.println(test.displayAttributes());
        assertEquals(expected, test.displayAttributes());
    }
}

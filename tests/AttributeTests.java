import Attributes.Attribute;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AttributeTests {
    @Test
    void toStringTest() {
        Attribute attribute = new Attribute();
        attribute.addAttribute("Test1", "String");
        attribute.addAttribute("Test2", "Boolean");

        assertEquals("Test1: String\nTest2: Boolean", attribute.toString());
    }

    @Test
    void toStringErrorCheck() {
        Attribute attribute = new Attribute();
        assertEquals("", attribute.toString());
    }

    @Test
    void getAttributesTest(){
        Attribute attribute = new Attribute();
        attribute.addAttribute("age", "int");
        attribute.addAttribute("name", "String");

        List<Attribute> result = attribute.getAttributes();

        // Check if the returned list contains the expected attributes
        assertEquals(2, result.size());
    }
}

import Attributes.Attribute;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AttributeTests {
    @Test
    void toStringTest() {
        Attribute attribute = new Attribute();
        attribute.addAttribute("Test1");
        attribute.addAttribute("Test2");

        assertEquals("Test1\nTest2", attribute.toString());
    }

    @Test
    void toStringErrorCheck() {
        Attribute attribute = new Attribute();
        assertEquals("", attribute.toString());
    }

    @Test
    void getAttributesTest(){
        Attribute attribute = new Attribute();
        attribute.addAttribute("age");
        attribute.addAttribute("name");

        List<Attribute> result = attribute.getAttributes();

        // Check if the returned list contains the expected attributes
        assertEquals(2, result.size());
    }
}

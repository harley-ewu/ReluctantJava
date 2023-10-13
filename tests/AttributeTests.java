import Attributes.Attribute;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Attr;

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

    @Test
    void checkIfAttributeIsInListTest(){
        Attribute attribute = new Attribute();
        attribute.addAttribute("Name");
        attribute.addAttribute("Name");

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
}

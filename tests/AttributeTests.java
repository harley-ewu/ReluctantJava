import Attributes.Attribute;
import org.junit.jupiter.api.Test;
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
}

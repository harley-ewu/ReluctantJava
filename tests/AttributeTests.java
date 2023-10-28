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
    @Test
    void fieldTest() {

        testList.add("Test field");
        test.addAttribute("Test attribute", testList, 1);
        System.out.println(test.toString());
    }

    @Test
    void methodTest() {
        testList.add("Test param1");
        testList.add("Test param2");
        testList.add("Test param3");
        test.addAttribute("Test attribute", testList, 2);
        System.out.println(test.toString());
    }

}

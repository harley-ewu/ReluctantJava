import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class CommandLineInterfaceTest {

    @Test
    void testIsValidUserInputForValidInputs() throws Exception {
        Method method = CLI.CommandLineInterface.class.getDeclaredMethod("isValidUserInput", int.class);
        method.setAccessible(true);

        assertTrue((Boolean) method.invoke(null, 1));
        assertTrue((Boolean) method.invoke(null, 2));
        assertTrue((Boolean) method.invoke(null, 3));
        assertTrue((Boolean) method.invoke(null, 4));
        assertTrue((Boolean) method.invoke(null, 5));
    }

    @Test
    void testIsValidUserInputForInvalidInputs() throws Exception {
        Method method = CLI.CommandLineInterface.class.getDeclaredMethod("isValidUserInput", int.class);
        method.setAccessible(true);

        // Testing invalid inputs
        assertFalse((Boolean) method.invoke(null, 0));
        assertFalse((Boolean) method.invoke(null, 6));
        assertFalse((Boolean) method.invoke(null, -1));
        assertFalse((Boolean) method.invoke(null, 100));
    }
}

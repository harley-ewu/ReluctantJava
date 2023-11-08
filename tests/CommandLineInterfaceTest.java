import CLI.CommandLineInterface;
import org.junit.Test;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class CommandLineInterfaceTest {

    public CommandLineInterfaceTest() {}
    @Test
    public void testIsValidUserInputForValidInputs() throws Exception {
        Method method = CLI.CommandLineInterface.class.getDeclaredMethod("isValidUserInput", int.class);
        Field maxChoicesField = CommandLineInterface.class.getDeclaredField("MAX_CHOICES");

        maxChoicesField.setAccessible(true);
        method.setAccessible(true);

        int maxChoices = (int) maxChoicesField.get(CommandLineInterface.class);

        for(int i = 1; i < (int) maxChoicesField.get(CommandLineInterface.class); i++){
            assertTrue((Boolean) method.invoke(null, i));
        }
    }

    @Test
    public void testIsValidUserInputForInvalidInputs() throws Exception {
        Method method = CLI.CommandLineInterface.class.getDeclaredMethod("isValidUserInput", int.class);
        Field maxChoicesField = CommandLineInterface.class.getDeclaredField("MAX_CHOICES");

        maxChoicesField.setAccessible(true);
        method.setAccessible(true);

        int maxChoices = (int) maxChoicesField.get(CommandLineInterface.class);

        for(int i = maxChoices + 1; i < 100; i++){
            assertFalse((Boolean) method.invoke(null, (int) (Math.random() * 100) + maxChoices + 1));
        }

        assertFalse((Boolean) method.invoke(null, 0));
        assertFalse((Boolean) method.invoke(null, -1));
    }
}

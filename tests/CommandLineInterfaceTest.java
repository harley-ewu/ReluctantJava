package tests;

import org.junit.jupiter.api.Test;
import src.CLI.CommandLineInterface;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class CommandLineInterfaceTest {

    @Test
    void testIsValidUserInputForValidInputs() throws Exception {
        Method method = src.CLI.CommandLineInterface.class.getDeclaredMethod("isValidUserInput", int.class);
        Field maxChoicesField = CommandLineInterface.class.getDeclaredField("MAX_CHOICES");

        maxChoicesField.setAccessible(true);
        method.setAccessible(true);

        int maxChoices = (int) maxChoicesField.get(CommandLineInterface.class);

        for(int i = 1; i < (int) maxChoicesField.get(CommandLineInterface.class); i++){
            assertTrue((Boolean) method.invoke(null, i));
        }
    }

    @Test
    void testIsValidUserInputForInvalidInputs() throws Exception {
        Method method = src.CLI.CommandLineInterface.class.getDeclaredMethod("isValidUserInput", int.class);
        Field maxChoicesField = CommandLineInterface.class.getDeclaredField("MAX_CHOICES");

        maxChoicesField.setAccessible(true);
        method.setAccessible(true);

        int maxChoices = (int) maxChoicesField.get(CommandLineInterface.class);

        for(int i = maxChoices; i < 100; i++){
            assertFalse((Boolean) method.invoke(null, (int) (Math.random() * 100)));
        }

        assertFalse((Boolean) method.invoke(null, 0));
        assertFalse((Boolean) method.invoke(null, -1));
    }
}

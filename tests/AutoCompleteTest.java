import application.CLI.AutoComplete;
import mockit.*;
import org.jline.reader.*;
import org.jline.terminal.TerminalBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AutoCompleteTest {

    @Mocked
    private LineReader lineReader;

    @Mocked
    private TerminalBuilder terminalBuilder;

    @Mocked
    private LineReaderBuilder lineReaderBuilder;

    @Injectable
    private ParsedLine parsedLine;

    private AutoComplete autoComplete;

    @BeforeEach
    void setUp() {
        autoComplete = new AutoComplete();
    }

    @Test
    void testClassLineReader() {
        autoComplete.classLineReader();
        assertNotNull(autoComplete.getCommands());
    }

    @Test
    void testMainLineReader() {
        autoComplete.mainLineReader();
        assertNotNull(autoComplete.getCommands());
    }

    @Test
    void testNewClassLineReader() {
        autoComplete.newClassLineReader();
        assertNotNull(autoComplete.getCommands());
    }

    @Test
    void testEditClassLineReader() {
        autoComplete.editClassLineReader();
        assertNotNull(autoComplete.getCommands());
    }

    @Test
    void testRelationshipEditorLineReader() {
        autoComplete.relationshipEditorLineReader();
        assertNotNull(autoComplete.getCommands());
    }

    @Test
    void testIsNumber() {
        assertTrue(autoComplete.isNumber("123"));
        assertFalse(autoComplete.isNumber("abc"));
        assertFalse(autoComplete.isNumber(""));
    }

}

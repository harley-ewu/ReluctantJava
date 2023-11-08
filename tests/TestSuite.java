import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AttributeTests.class,
        ClassTests.class,
        CommandLineInterfaceTest.class,
        DiagramTests.class,
        RelationshipTests.class,
        SaveLoadSystemTest.class
})

public class TestSuite {
    // This class remains empty, it is used only as a holder for the above annotations
}

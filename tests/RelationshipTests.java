import Class.Class;
import Relationships.Relationship;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RelationshipTests {
    @Test
    void toStringTest() {
        Class testClass = new Class("testClass");
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Association, testClass, 1, 2, false);
        String testResult = """
                Class has a Association relationship with testClass
                Owner: false
                This Class Cardinality: 1
                testClass Class Cardinality: 2
                """;

        assertEquals(testResult, testRelationship.toString());
    }
}
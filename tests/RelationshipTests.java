import Class.Class;
import Diagram.Diagram;
import Relationships.Relationship;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class RelationshipTests {

    @Test
    public void toStringTest() throws IllegalArgumentException {
        Diagram testDiagram = new Diagram("testDiagram");
        Class testClass = new Class("testClass");
        Class testClass2 = new Class("testClass2");
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Realization, testClass, testClass2, 1, 2, false);
        String testResult = """
                testClass has a Realization relationship with testClass2
                Owner: false
                testClass Class Cardinality: 1
                testClass2 Class Cardinality: 2
                """;

        assertEquals(testResult, testRelationship.toString());

        try {
            Relationship testRelationship2 = new Relationship(null, testClass, testClass2, 1, 2, false);
            fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // Exception expected
        }

        try {
            Relationship testRelationship2 = new Relationship(Relationship.RelationshipType.Realization, null, null, 1, 2, false);
            fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // Exception expected
        }

        try {
            Relationship testRelationship2 = new Relationship(Relationship.RelationshipType.Realization, testClass, testClass2, -2, 2, false);
            fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // Exception expected
        }

        try {
            Relationship testRelationship2 = new Relationship(Relationship.RelationshipType.Realization, testClass, testClass2, 2, -2, false);
            fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // Exception expected
        }
    }

    @Test
    public void toStringSpecialCardinalityTest() {
        Diagram testDiagram = new Diagram("testDiagram");
        Class testClass = new Class("testClass");
        Class testClass2 = new Class("testClass2");
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Realization, testClass, testClass2, -1, -1, false);
        String testResult = """
                testClass has a Realization relationship with testClass2
                Owner: false
                testClass Class Cardinality: *
                testClass2 Class Cardinality: *
                """;

        assertEquals(testResult, testRelationship.toString());

        Relationship testRelationship2 = new Relationship(Relationship.RelationshipType.Realization, testClass, testClass2, 1, -1, false);
        String testResult2 = """
                testClass has a Realization relationship with testClass2
                Owner: false
                testClass Class Cardinality: 1
                testClass2 Class Cardinality: *
                """;

        assertEquals(testResult2, testRelationship2.toString());

        Relationship testRelationship3 = new Relationship(Relationship.RelationshipType.Realization, testClass, testClass2, -1, 1, false);
        String testResult3 = """
                testClass has a Realization relationship with testClass2
                Owner: false
                testClass Class Cardinality: *
                testClass2 Class Cardinality: 1
                """;

        assertEquals(testResult3, testRelationship3.toString());
    }

    @Test
    public void relationshipTypeGetterSetterTest() {
        Diagram testDiagram = new Diagram("testDiagram");
        Class testClass = new Class("testClass");
        Class testClass2 = new Class("testClass2");
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, testClass2, 1, 2, false);

        assertEquals(Relationship.RelationshipType.Aggregation, testRelationship.getRelationshipType());
        testRelationship.setRelationshipType(Relationship.RelationshipType.Composition);
        assertEquals(Relationship.RelationshipType.Composition, testRelationship.getRelationshipType());

        try {
            testRelationship.setRelationshipType(null);
            fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // Exception expected
        }
    }

    @Test
    public void classGetterTest() {
        Diagram testDiagram = new Diagram("testDiagram");
        Class testClass = new Class("testClass");
        Class testClass2 = new Class("testClass2");
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, testClass2, 1, 2, false);

        assertEquals(testClass, testRelationship.getClass1());
    }

    @Test
    public void class2GetterTest() {
        Diagram testDiagram = new Diagram("testDiagram");
        Class testClass = new Class("testClass");
        Class testClass2 = new Class("testClass2");
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, testClass2, 1, 2, false);

        assertEquals(testClass2, testRelationship.getClass2());
    }

    @Test
    public void class1CardinalityGetterSetterTest() {
        Diagram testDiagram = new Diagram("testDiagram");
        Class testClass = new Class("testClass");
        Class testClass2 = new Class("testClass2");
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, testClass2, 1, 2, false);

        assertEquals(1, testRelationship.getClass1Cardinality());
        testRelationship.setClass1Cardinality(2);
        assertEquals(2, testRelationship.getClass1Cardinality());

        try {
            testRelationship.setClass1Cardinality(-2);
            fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // Exception expected
        }
    }

    @Test
    public void class2CardinalityGetterSetterTest() {
        Diagram testDiagram = new Diagram("testDiagram");
        Class testClass = new Class("testClass");
        Class testClass2 = new Class("testClass2");
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, testClass2, 1, 2, false);

        assertEquals(2, testRelationship.getClass2Cardinality());
        testRelationship.setClass2Cardinality(3);
        assertEquals(3, testRelationship.getClass2Cardinality());
    }

    @Test
    public void ownerGetterSetterTest() {
        Diagram testDiagram = new Diagram("testDiagram");
        Class testClass = new Class("testClass");
        Class testClass2 = new Class("testClass2");
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, testClass2, 1, 2, false);

        assertEquals(false, testRelationship.getIsOwner());
        testRelationship.setIsOwner(true);
        assertEquals(true, testRelationship.getIsOwner());

        try {
            testRelationship.setClass2Cardinality(-2);
            fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // Exception expected
        }
    }
}

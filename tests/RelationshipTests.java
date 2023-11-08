//Nick Parkman 10/12/2023 @10:30am
import Class.Class;
import Diagram.Diagram;
import Relationships.Relationship;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RelationshipTests {
    @Test
    void toStringTest() throws IllegalArgumentException{
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

        assertThrows(IllegalArgumentException.class, () ->
        {Relationship testRelationship2 = new Relationship(null, testClass, testClass2, 1, 2, false);});

        assertThrows(IllegalArgumentException.class, () ->
        {Relationship testRelationship2 = new Relationship(Relationship.RelationshipType.Realization, null, null, 1, 2, false);});

        assertThrows(IllegalArgumentException.class, () ->
        {Relationship testRelationship2 = new Relationship(Relationship.RelationshipType.Realization, testClass, testClass2, -2, 2, false);});

        assertThrows(IllegalArgumentException.class, () ->
        {Relationship testRelationship2 = new Relationship(Relationship.RelationshipType.Realization, testClass, testClass2, 2, -2, false);});
    }

    @Test
    void toStringSpecialCardinalityTest() {
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
    void relationshipTypeGetterSetterTest() {
        Diagram testDiagram = new Diagram("testDiagram");
        Class testClass = new Class("testClass");
        Class testClass2 = new Class("testClass2");
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, testClass2, 1, 2, false);

        assertEquals(Relationship.RelationshipType.Aggregation, testRelationship.getRelationshipType());
        testRelationship.setRelationshipType(Relationship.RelationshipType.Composition);
        assertEquals(Relationship.RelationshipType.Composition, testRelationship.getRelationshipType());

        assertThrows(IllegalArgumentException.class, () -> {testRelationship.setRelationshipType(null);});
    }

    @Test
    void classGetterTest() {
        Diagram testDiagram = new Diagram("testDiagram");
        Class testClass = new Class("testClass");
        Class testClass2 = new Class("testClass2");
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, testClass2, 1, 2, false);

        assertEquals(testClass, testRelationship.getClass1());
    }

    @Test
    void class2GetterTest() {
        Diagram testDiagram = new Diagram("testDiagram");
        Class testClass = new Class("testClass");
        Class testClass2 = new Class("testClass2");
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, testClass2, 1, 2, false);

        assertEquals(testClass2, testRelationship.getClass2());
    }

    @Test
    void class1CardinalityGetterSetterTest() {
        Diagram testDiagram = new Diagram("testDiagram");
        Class testClass = new Class("testClass");
        Class testClass2 = new Class("testClass2");
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, testClass2, 1, 2, false);

        assertEquals(1, testRelationship.getClass1Cardinality());
        testRelationship.setClass1Cardinality(2);
        assertEquals(2, testRelationship.getClass1Cardinality());

        assertThrows(IllegalArgumentException.class, () -> {testRelationship.setClass1Cardinality(-2);});
    }

    @Test
    void class2CardinalityGetterSetterTest() {
        Diagram testDiagram = new Diagram("testDiagram");
        Class testClass = new Class("testClass");
        Class testClass2 = new Class("testClass2");
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, testClass2, 1, 2, false);

        assertEquals(2, testRelationship.getClass2Cardinality());
        testRelationship.setClass2Cardinality(3);
        assertEquals(3, testRelationship.getClass2Cardinality());
    }

    @Test
    void ownerGetterSetterTest() {
        Diagram testDiagram = new Diagram("testDiagram");
        Class testClass = new Class("testClass");
        Class testClass2 = new Class("testClass2");
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, testClass2, 1, 2, false);

        assertEquals(false, testRelationship.getIsOwner());
        testRelationship.setIsOwner(true);
        assertEquals(true, testRelationship.getIsOwner());

        assertThrows(IllegalArgumentException.class, () -> {testRelationship.setClass2Cardinality(-2);});
    }
}
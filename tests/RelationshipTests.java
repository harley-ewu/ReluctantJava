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
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Realization, testClass, 1, 2, false);
        String testResult = """
                Class has a Realization relationship with testClass
                Owner: false
                This Class Cardinality: 1
                testClass Class Cardinality: 2
                """;

        assertEquals(testResult, testRelationship.toString());

        assertThrows(IllegalArgumentException.class, () ->
        {Relationship testRelationship2 = new Relationship(null, testClass, 1, 2, false);});

        assertThrows(IllegalArgumentException.class, () ->
        {Relationship testRelationship2 = new Relationship(Relationship.RelationshipType.Realization, null, 1, 2, false);});

        assertThrows(IllegalArgumentException.class, () ->
        {Relationship testRelationship2 = new Relationship(Relationship.RelationshipType.Realization, testClass, -2, 2, false);});

        assertThrows(IllegalArgumentException.class, () ->
        {Relationship testRelationship2 = new Relationship(Relationship.RelationshipType.Realization, testClass, 2, -2, false);});
    }

    @Test
    void relationshipTypeGetterSetterTest() {
        Diagram testDiagram = new Diagram("testDiagram");
        Class testClass = new Class("testClass");
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, 1, 2, false);

        assertEquals(Relationship.RelationshipType.Aggregation, testRelationship.getRelationshipType());
        testRelationship.setRelationshipType(Relationship.RelationshipType.Composition);
        assertEquals(Relationship.RelationshipType.Composition, testRelationship.getRelationshipType());

        assertThrows(IllegalArgumentException.class, () -> {testRelationship.setRelationshipType(null);});
    }

    @Test
    void otherClassNameGetterTest() {
        Diagram testDiagram = new Diagram("testDiagram");
        Class testClass = new Class("testClass");
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, 1, 2, false);

        assertEquals(testClass, testRelationship.getClass2());
    }

    @Test
    void thisClassCardinalityGetterSetterTest() {
        Diagram testDiagram = new Diagram("testDiagram");
        Class testClass = new Class("testClass");
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, 1, 2, false);

        assertEquals(1, testRelationship.getClass1Cardinality());
        testRelationship.setClass1Cardinality(2);
        assertEquals(2, testRelationship.getClass1Cardinality());

        assertThrows(IllegalArgumentException.class, () -> {testRelationship.setClass1Cardinality(-2);});
    }

    @Test
    void otherClassCardinalityGetterSetterTest() {
        Diagram testDiagram = new Diagram("testDiagram");
        Class testClass = new Class("testClass");
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, 1, 2, false);

        assertEquals(2, testRelationship.getClass2Cardinality());
        testRelationship.setClass2Cardinality(3);
        assertEquals(3, testRelationship.getClass2Cardinality());
    }

    @Test
    void ownerGetterSetterTest() {
        Diagram testDiagram = new Diagram("testDiagram");
        Class testClass = new Class("testClass");
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, 1, 2, false);

        assertEquals(false, testRelationship.getIsOwner());
        testRelationship.setIsOwner(true);
        assertEquals(true, testRelationship.getIsOwner());

        assertThrows(IllegalArgumentException.class, () -> {testRelationship.setClass2Cardinality(-2);});
    }
}
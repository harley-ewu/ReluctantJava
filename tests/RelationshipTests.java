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
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Association, testClass, 1, 2, false);
        String testResult = """
                Class has a Association relationship with testClass
                Owner: false
                This Class Cardinality: 1
                testClass Class Cardinality: 2
                """;

        assertEquals(testResult, testRelationship.toString());

        assertThrows(IllegalArgumentException.class, () ->
        {Relationship testRelationship2 = new Relationship(null, testClass, 1, 2, false);});

        assertThrows(IllegalArgumentException.class, () ->
        {Relationship testRelationship2 = new Relationship(Relationship.RelationshipType.Association, null, 1, 2, false);});

        assertThrows(IllegalArgumentException.class, () ->
        {Relationship testRelationship2 = new Relationship(Relationship.RelationshipType.Association, testClass, -2, 2, false);});

        assertThrows(IllegalArgumentException.class, () ->
        {Relationship testRelationship2 = new Relationship(Relationship.RelationshipType.Association, testClass, 2, -2, false);});
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

        assertEquals(testClass, testRelationship.getOtherClassName());
    }

    @Test
    void thisClassCardinalityGetterSetterTest() {
        Diagram testDiagram = new Diagram("testDiagram");
        Class testClass = new Class("testClass");
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, 1, 2, false);

        assertEquals(1, testRelationship.getThisClassCardinality());
        testRelationship.setThisClassCardinality(2);
        assertEquals(2, testRelationship.getThisClassCardinality());

        assertThrows(IllegalArgumentException.class, () -> {testRelationship.setThisClassCardinality(-2);});
    }

    @Test
    void otherClassCardinalityGetterSetterTest() {
        Diagram testDiagram = new Diagram("testDiagram");
        Class testClass = new Class("testClass");
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, 1, 2, false);

        assertEquals(2, testRelationship.getOtherClassCardinality());
        testRelationship.setOtherClassCardinality(3);
        assertEquals(3, testRelationship.getOtherClassCardinality());
    }

    @Test
    void ownerGetterSetterTest() {
        Diagram testDiagram = new Diagram("testDiagram");
        Class testClass = new Class("testClass");
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, 1, 2, false);

        assertEquals(false, testRelationship.getIsOwner());
        testRelationship.setIsOwner(true);
        assertEquals(true, testRelationship.getIsOwner());

        assertThrows(IllegalArgumentException.class, () -> {testRelationship.setOtherClassCardinality(-2);});
    }
}
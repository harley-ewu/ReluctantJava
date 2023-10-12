//Nick Parkman 10/12/2023 @10:30am
import Class.Class;
import Relationships.Relationship;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RelationshipTests {
    @Test
    void toStringTest() {
        Class testClass = new Class("testClass");
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Association, testClass, 1, 2, false);
        String testResult = "Class has a Association relationship with testClass\n" +
                "Owner: false\n" +
                "This Class Cardinality: 1\n" +
                "testClass Class Cardinality: 2\n" ;

        assertEquals(testResult, testRelationship.toString());
    }

    @Test
    void relationshipTypeGetterSetterTest() {
        Class testClass = new Class("testClass");
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, 1, 2, false);

        assertEquals(Relationship.RelationshipType.Aggregation, testRelationship.getRelationshipType());
        testRelationship.setRelationshipType(Relationship.RelationshipType.Composition);
        assertEquals(Relationship.RelationshipType.Composition, testRelationship.getRelationshipType());
    }

    @Test
    void otherClassNameGetterSetterTest() {
        Class testClass = new Class("testClass");
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, 1, 2, false);

        assertEquals(testClass, testRelationship.getOtherClassName());
    }

    @Test
    void thisClassCardinalityGetterSetterTest() {
        Class testClass = new Class("testClass");
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, 1, 2, false);

        assertEquals(1, testRelationship.getThisClassCardinality());
        testRelationship.setThisClassCardinality(2);
        assertEquals(2, testRelationship.getThisClassCardinality());
    }

    @Test
    void otherClassCardinalityGetterSetterTest() {
        Class testClass = new Class("testClass");
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, 1, 2, false);

        assertEquals(2, testRelationship.getOtherClassCardinality());
        testRelationship.setOtherClassCardinality(3);
        assertEquals(3, testRelationship.getOtherClassCardinality());
    }

    @Test
    void ownerGetterSetterTest() {
        Class testClass = new Class("testClass");
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, 1, 2, false);

        assertEquals(false, testRelationship.getIsOwner());
        testRelationship.setIsOwner(true);
        assertEquals(true, testRelationship.getIsOwner());
    }
}
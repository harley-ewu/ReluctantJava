import Class.Class;
import Relationships.Relationship;

public class main {


    public static void main(String[] args) {
        Class newClass = new Class("test class");
        newClass.addRelationship(Relationship.RelationshipType.Association, "other class", 1, 0, true );
        newClass.addRelationship(Relationship.RelationshipType.Aggregation, "other class2", 0, 1, false );

        System.out.println(newClass.toString());
    }

}

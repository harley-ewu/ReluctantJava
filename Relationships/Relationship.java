//Nick Parkman 10/4/2023 @3:10pm
package Relationships;
import java.util.*;

public class Relationship
{
   public static enum RelationshipType {
      Association,
      Aggregation,
      Composition,
      Generalization,
   }
   //list of relationships, to be moved to the Class that manages classes later
   private List<Relationship> relationships;
   
   //variables of a given relationship
   private RelationshipType relationshipType;//The type of relationship this given relationship is between the given classes
   private String className; //to be removed when List is moved above, this will be the class that is currently holding the relationship
   private String otherClassName; //the other class in the given relaitonship
   private int thisClassCardinality; //thinking -1 in case of * amount of cardinality
   private int otherClassCardinality; //thinking -1 in case of * amount of cardinality
   private boolean owner; //meaning the "contains" side in Aggregation or Composition, or the class that others are Inheirting from in Generalization.
   
   
   public Relationship(final RelationshipType relationshipType, final String className, final String otherClassName, final int thisClassCardinality, final int otherClassCardinality, final boolean owner)
   {
      this.relationshipType = relationshipType;
      this.className = className;
      this.otherClassName = otherClassName;
      this.thisClassCardinality = thisClassCardinality;
      this.otherClassCardinality = otherClassCardinality;
      this.owner = owner;
   }
   
   //to be moved to the Class that manages classes later with the list of relationships
   public void AddRelationship(final RelationshipType relationshipType, final String className, final String otherClassName, final int thisClassCardinality, final int otherClassCardinality, final boolean owner)
   {
      if(ContainsRelationship(className, otherClassName))
      {
         System.out.println("There is already a relationship between these two classes");
         return;
      }
   
      Relationship newRelationship = new Relationship(relationshipType, className, otherClassName, thisClassCardinality, otherClassCardinality, owner);
      relationships.add(newRelationship);
   }
   
   //to be moved to the Class that manages classes later with the list of relationships, will have to add way to delete the relationship from both classes relationship lists
   public void DeleteRelationship(final String className, final String otherClassName) //will be able to change this to just search by otherClassName when moved
   {
      //loop through relationship list looking for the given relationship
      for(Relationship relationship : relationships)
      {
         if(relationship.className == className && relationship.otherClassName == otherClassName)
         {
            //Deleteing the relationship from the list and returning to end the method call
            relationships.remove(relationship);
            return;
         }
      }
   }
   
   public boolean ContainsRelationship(final String className, final String otherClassName)
   {
      for(Relationship relationship : relationships)
      {
         if(relationship.className == className && relationship.otherClassName == otherClassName)
         {
            return true;
         }
      }
      
      return false;
   }
   
}
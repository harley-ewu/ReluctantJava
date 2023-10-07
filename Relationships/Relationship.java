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
   private List<Relationship> relationships = new ArrayList<Relationship>();
   
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
      this.className = className; //To be removed when relationship list is contained in the Class that manages classes
      this.otherClassName = otherClassName;
      this.thisClassCardinality = thisClassCardinality;
      this.otherClassCardinality = otherClassCardinality;
      this.owner = owner;
   }
   
   //to be moved to the Class that manages classes later with the list of relationships
   public void addRelationship(final RelationshipType relationshipType, final String className, final String otherClassName, final int thisClassCardinality, final int otherClassCardinality, final boolean owner)
   {
      Relationship newRelationship = new Relationship(relationshipType, className, otherClassName, thisClassCardinality, otherClassCardinality, owner);
   
      if(relationships.contains(newRelationship))
      {
         System.out.println("There is already a relationship between these two classes");
         return;
      }
      
      relationships.add(newRelationship);
   }
   
   //to be moved to the Class that manages classes later with the list of relationships, will have to add way to delete the relationship from both classes relationship lists
   public void deleteRelationship(Relationship relationship)
   {
      //checks if given relationship is null and if it is contained withing the relationsihp list
      if(relationship != null && relationships.contains(relationship))
      {
         relationships.remove(relationship);
      }
   }
   
   /*some get and set functions for the values that make up a Relationship below, 
   className is to be removed so didn't inlcude, 
   and the otherClassName should never change otherwise it would be a new relationship, so only included get for that variable*/
    
   public boolean getIsOwner(final Relationship relationship)
   {
      return relationship.owner;
   }
   
   public void setIsOwner(final Relationship relationship, final boolean isOwner)
   {
      relationship.owner = isOwner;
   }
   
   public int getThisClassCardinality(final Relationship relationship)
   {
      return relationship.thisClassCardinality;
   }
   
   public void setThisClassCardinality(final Relationship relationship, final int cardinality)
   {
      relationship.thisClassCardinality = cardinality;
   }
   
   public int getOtherClassCardinality(final Relationship relationship)
   {
      return relationship.otherClassCardinality;
   }
   
   public void setOtherClassCardinality(final Relationship relationship, final int cardinality)
   {
      relationship.otherClassCardinality = cardinality;
   }
   
   public RelationshipType getRelationshipType(final Relationship relationship)
   {
      return relationship.relationshipType;
   }
   
   public void setRelationshipType(final Relationship relationship, final RelationshipType relationshipType)
   {
      relationship.relationshipType = relationshipType;
   }
   
   public String getOtherClassName(final Relationship relationship)
   {
      return relationship.otherClassName;
   }
}
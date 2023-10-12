//Nick Parkman 10/9/2023 @10:30am
package Relationships;
import Class.Class;
import java.util.*;

public class Relationship {
   public static enum RelationshipType {
      Association,
      Aggregation,
      Composition,
      Generalization,
   }
   
   //variables of a given relationship
   private RelationshipType relationshipType;//The type of relationship this given relationship is between the given classes
   private Class otherClassName; //the other class in the given relaitonship
   private int thisClassCardinality; //thinking -1 in case of * amount of cardinality
   private int otherClassCardinality; //thinking -1 in case of * amount of cardinality
   private boolean owner; //meaning the "contains" side in Aggregation or Composition, or the class that others are Inheirting from in Generalization.
   
   
   public Relationship(final RelationshipType relationshipType, final Class otherClassName, final int thisClassCardinality, final int otherClassCardinality, final boolean owner) {
      this.relationshipType = relationshipType;
      this.otherClassName = otherClassName;
      this.thisClassCardinality = thisClassCardinality;
      this.otherClassCardinality = otherClassCardinality;
      this.owner = owner;
   }
   
   /*some get and set functions for the values that make up a Relationship below, 
   className is to be removed so didn't inlcude, 
   and the otherClassName should never change otherwise it would be a new relationship, so only included get for that variable*/
    
   public boolean getIsOwner() {
      return this.owner;
   }
   
   public void setIsOwner(final boolean isOwner) {
      this.owner = isOwner;
   }
   
   public int getThisClassCardinality() {
      return this.thisClassCardinality;
   }
   
   public void setThisClassCardinality(final int cardinality) {
      this.thisClassCardinality = cardinality;
   }
   
   public int getOtherClassCardinality() {
      return this.otherClassCardinality;
   }
   
   public void setOtherClassCardinality(final int cardinality) {
      this.otherClassCardinality = cardinality;
   }
   
   public RelationshipType getRelationshipType() {
      return this.relationshipType;
   }
   
   public void setRelationshipType(final RelationshipType relationshipType) {
      this.relationshipType = relationshipType;
   }
   
   public Class getOtherClassName() {
      return this.otherClassName;
   }
   
   //toString method
   @Override
   public String toString()
   {
      return "Class has a "+this.relationshipType+" relationship with "+this.otherClassName+"\n"
            +"Owner: "+this.owner+"\n"
            +"This Class Cardinality: "+this.thisClassCardinality+"\n"
            +this.otherClassName+" Class Cardinality: "+this.otherClassCardinality+"\n";
   }
}
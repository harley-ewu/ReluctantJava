package Relationships;

import Class.Class;
import com.google.gson.annotations.Expose;


public class Relationship {
   public enum RelationshipType {
      Realization,
      Aggregation,
      Composition,
      Inheritance,
   }
   
   //variables of a given relationship
   @Expose
   private RelationshipType relationshipType;//The type of relationship this given relationship is between the given classes
   @Expose
   private final Class class1; //the 1st class in the given relationship
   @Expose
   private final Class class2; //the 2nd class in the given relationship
   @Expose
   private int class1Cardinality; //thinking -1 in case of * amount of cardinality
   @Expose
   private int class2Cardinality; //thinking -1 in case of * amount of cardinality
   @Expose
   private boolean owner; //meaning the "contains" side in Aggregation or Composition, or the class that others are Inheriting from in Generalization.
   
   
   public Relationship(final RelationshipType relationshipType, final Class class1, final Class class2, final int class1Cardinality,
                       final int class2Cardinality, final boolean owner) throws IllegalArgumentException {
      if(relationshipType == null || class1 == null || class2 == null || class1Cardinality < -1 || class2Cardinality < -1) {
         throw new IllegalArgumentException("Invalid Arguments");
      }

      this.relationshipType = relationshipType;
      this.class1 = class1;
      this.class2 = class2;
      this.class1Cardinality = class1Cardinality;
      this.class2Cardinality = class2Cardinality;
      this.owner = owner;
   }
   
   /*some get and set functions for the values that make up a Relationship below, 
   className is to be removed so didn't include,
   and the otherClassName should never change otherwise it would be a new relationship, so only included get for that variable*/
    
   public boolean getIsOwner() {
      return this.owner;
   }
   
   public void setIsOwner(final boolean isOwner)  {
      this.owner = isOwner;
   }
   
   public int getClass1Cardinality() {
      return this.class1Cardinality;
   }
   
   public void setClass1Cardinality(final int cardinality) throws IllegalArgumentException {
      if(cardinality < -1) {
         throw new IllegalArgumentException("* cardinality == -1, cardinality cannot be negative");
      }

      this.class1Cardinality = cardinality;
   }
   
   public int getClass2Cardinality() {
      return this.class2Cardinality;
   }
   
   public void setClass2Cardinality(final int cardinality) throws IllegalArgumentException {
      if(cardinality < -1) {
         throw new IllegalArgumentException("* cardinality == -1, cardinality cannot be negative");
      }

      this.class2Cardinality = cardinality;
   }
   
   public RelationshipType getRelationshipType() {
      return this.relationshipType;
   }
   
   public void setRelationshipType(final RelationshipType relationshipType) throws IllegalArgumentException {
      if(relationshipType == null) {
         throw new IllegalArgumentException("relationship type cannot be null!");
      }

      this.relationshipType = relationshipType;
   }

   public Class getClass1() { return this.class1; }

   public Class getClass2() { return this.class2; }
   
   //toString method
   @Override
   public String toString() {
      String str;
      if(this.class1Cardinality == -1 && this.class2Cardinality == -1) {
         str = this.class1.getClassName() + " has a " + this.relationshipType + " relationship with " + this.class2.getClassName() + "\n"
                 + "Owner: " + this.owner + "\n"
                 + this.class1.getClassName() +" Class Cardinality: *\n"
                 + this.class2.getClassName() + " Class Cardinality: *\n";
      }
      else if(this.class1Cardinality == -1){
         str = this.class1.getClassName() + " has a " + this.relationshipType + " relationship with " + this.class2.getClassName() + "\n"
                 + "Owner: " + this.owner + "\n"
                 + this.class1.getClassName() +" Class Cardinality: *\n"
                 + this.class2.getClassName() + " Class Cardinality: " + this.class2Cardinality + "\n";
      }
      else if(this.class2Cardinality == -1){
         str = this.class1.getClassName() + " has a " + this.relationshipType + " relationship with " + this.class2.getClassName() + "\n"
                 + "Owner: " + this.owner + "\n"
                 + this.class1.getClassName() +" Class Cardinality: " + this.class1Cardinality + "\n"
                 + this.class2.getClassName() + " Class Cardinality: *\n";
      }
      else {
         str = this.class1.getClassName() + " has a " + this.relationshipType + " relationship with " + this.class2.getClassName() + "\n"
                 + "Owner: " + this.owner + "\n"
                 + this.class1.getClassName() +" Class Cardinality: " + this.class1Cardinality + "\n"
                 + this.class2.getClassName() + " Class Cardinality: " + this.class2Cardinality + "\n";
      }

      return str;
   }

}
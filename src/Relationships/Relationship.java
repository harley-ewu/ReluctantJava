//Nick Parkman 10/12/2023 @10:30am
package Relationships;

import Attributes.Attribute;
import Class.Class;
//import com.github.cliftonlabs.json_simple.JsonObject;

import java.util.ArrayList;

public class Relationship {
   public enum RelationshipType {
      Association,
      Aggregation,
      Composition,
      Generalization,
   }
   
   //variables of a given relationship
   private RelationshipType relationshipType;//The type of relationship this given relationship is between the given classes
   private final Class otherClassName; //the other class in the given relationship
   private int thisClassCardinality; //thinking -1 in case of * amount of cardinality
   private int otherClassCardinality; //thinking -1 in case of * amount of cardinality
   private boolean owner; //meaning the "contains" side in Aggregation or Composition, or the class that others are Inheirting from in Generalization.
   
   
   public Relationship(final RelationshipType relationshipType, final Class otherClassName, final int thisClassCardinality,
                       final int otherClassCardinality, final boolean owner) throws IllegalArgumentException {
      if(relationshipType == null || otherClassName == null || thisClassCardinality < -1 || otherClassCardinality < -1) {
         throw new IllegalArgumentException("Invalid Arguments");
      }

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
   
   public void setIsOwner(final boolean isOwner)  {
      this.owner = isOwner;
   }
   
   public int getThisClassCardinality() {
      return this.thisClassCardinality;
   }
   
   public void setThisClassCardinality(final int cardinality) throws IllegalArgumentException {
      if(cardinality < -1) {
         throw new IllegalArgumentException("* cardinality == -1, cardinality cannot be negative");
      }

      this.thisClassCardinality = cardinality;
   }
   
   public int getOtherClassCardinality() {
      return this.otherClassCardinality;
   }
   
   public void setOtherClassCardinality(final int cardinality) throws IllegalArgumentException {
      if(cardinality < -1) {
         throw new IllegalArgumentException("* cardinality == -1, cardinality cannot be negative");
      }

      this.otherClassCardinality = cardinality;
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
   
   public Class getOtherClassName() {
      return this.otherClassName;
   }
   
   //toString method
   @Override
   public String toString() {
      String str;
      if(this.thisClassCardinality == -1 && this.otherClassCardinality == -1) {
         str = "Class has a " + this.relationshipType + " relationship with " + this.otherClassName.getClassName() + "\n"
                 + "Owner: " + this.owner + "\n"
                 + "This Class Cardinality: *\n"
                 + this.otherClassName.getClassName() + " Class Cardinality: *\n";
      }
      else if(this.thisClassCardinality == -1){
         str = "Class has a " + this.relationshipType + " relationship with " + this.otherClassName.getClassName() + "\n"
                 + "Owner: " + this.owner + "\n"
                 + "This Class Cardinality: *\n"
                 + this.otherClassName.getClassName() + " Class Cardinality: " + this.otherClassCardinality + "\n";
      }
      else if(this.otherClassCardinality == -1){
         str = "Class has a " + this.relationshipType + " relationship with " + this.otherClassName.getClassName() + "\n"
                 + "Owner: " + this.owner + "\n"
                 + "This Class Cardinality: " + this.thisClassCardinality + "\n"
                 + this.otherClassName.getClassName() + " Class Cardinality: *\n";
      }
      else {
         str = "Class has a " + this.relationshipType + " relationship with " + this.otherClassName.getClassName() + "\n"
                 + "Owner: " + this.owner + "\n"
                 + "This Class Cardinality: " + this.thisClassCardinality + "\n"
                 + this.otherClassName.getClassName() + " Class Cardinality: " + this.otherClassCardinality + "\n";
      }

      return str;
   }

   /**
    * Description: Converts a Relationship object into a JsonObject for saving.
    * @return : returns a JsonObject of the Relationship object.
    */
   /*public JsonObject toJsonObject(){
      JsonObject jsonObject = new JsonObject();
      jsonObject.put("relationshipType", relationshipType);
      jsonObject.put("otherClassName", otherClassName.toJsonObject());
      jsonObject.put("thisClassCardinality", thisClassCardinality);
      jsonObject.put("otherClassCardinality", otherClassCardinality);
      jsonObject.put("owner", owner);
      return jsonObject;
   }*/

   /**
    * Description: Converts a JsonObject from a loaded file back into an Relationship object.
    * @param jsonObject: the JsonObject read from the file.
    * @return : The Relationship object that was saved to the file.
    */
   /*public static Relationship fromJsonObject(JsonObject jsonObject){
      RelationshipType relationshipType = (RelationshipType) jsonObject.get("relationshipType");
      Class otherClassName = (Class) jsonObject.get("otherClassName");
      int thisClassCardinality = (int) jsonObject.get("thisClassCardinality");
      int otherClassCardinality = (int) jsonObject.get("otherClassCardinality");
      boolean owner = (boolean) jsonObject.get("owner");
      return new Relationship(relationshipType, otherClassName, thisClassCardinality, otherClassCardinality, owner);
   }*/
}
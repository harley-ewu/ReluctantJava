package Diagram;

import Class.Class;
import Relationships.Relationship;
import MenuPrompts.MenuPrompts;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//Class name subject to change for what we name the project
public class Diagram {

   @Expose
   private String saveLocation = null;
   @Expose
   private String title;
   //private List<Class> classList = new ArrayList<Class>();
   @Expose
   private HashMap<String, Class> classList;
   @Expose
   private HashMap<String, Relationship> relationshipList;
   private Scanner scanner = new Scanner(System.in);

   public Diagram(final String title) {

      if (title == null) {
         throw new NullPointerException("invalid param in Diagram constructor");
      }
      //if diagram comes in as null/empty, should we initialize an empty arraylist?
      this.title = title;
      this.classList = new HashMap<>();
      this.relationshipList = new HashMap<>();
   }
   
   /*
   getter for diagram title
   */
   public String getTitle(){
      return this.title;
   }
   
   /*Setter for diagram title*/
   public void setTitle(final String title){
      this.title = title;
   }
   
   /*
    * Getter for classList
    * */
   public HashMap<String, Class> getClassList(){
      return this.classList;
   }

   /*
   * Setter for classList
   * */

   public void setClassList(HashMap<String, Class> classList){
      this.classList = classList;
   }

   //Getter for RelationshipList
   public HashMap<String, Relationship> getRelationshipList() { return this.relationshipList; }
   //Setter for RelationshipList
   public void setRelationshipList(HashMap<String, Relationship> relationshipList) { this.relationshipList = relationshipList; }

   /*
   Adds a class to the classList
   */
   public void addClass(final String className){
      
      Class c = this.classList.get(className);
      if (c == null) {
         this.classList.put(className, new Class(className));
      }
      else {
         System.out.println("Class already exists.");
      }
   }
   
   /*
   Deletes a class from the classList and also severs existing relationships
   - Iterate through the classList
   - For each class, iterate through relationship list
   - If relationship list contains relationship.otherClassName == deletedNameName
   - call current class that the loop is on, .deleterelationship(deletedClass)
   */
   public void deleteClass(final Class deletedClass){
      if (deletedClass.getClassName().isEmpty()) {
         return;
      }
      classList.remove(deletedClass.getClassName());
      
      for(Class item : classList.values()){
         this.deleteRelationship(deletedClass, item);
      }
   }

   /*
   Renames a class in the classList
   */
   public void renameClass(final Class old, final String newName) {
      if(old != null && findSingleClass(old.getClassName()) == null){
         System.out.println("Class does not exist to rename.");
         return;
      }
      else if(old != null && !(newName.isEmpty())){
         Class temp = this.classList.get(old.getClassName());
         this.classList.remove(temp.getClassName());
         temp.setClassName(newName);
         this.classList.put(newName, temp);
      }
      else{
         System.out.println("Bad Parameters");
      }
   }

   /*
   Lists out all of the classes present in the classList
   */
   public String listClasses() {
      if(this.classList.size() == 0){
         return "Diagram is empty.";
      }
      else {
         return "Classes: " +
         this.classList.values();
         }
      
   } 
   
   /*
   Finds out if class exists
   */
   public Class findSingleClass(final String className) {
      if(className == null || className.isEmpty()) {
         System.out.println("Invalid class name.");
         return null;
      }
      Class c = this.classList.get(className);
      return c;
   }
   
   /*
   Prints out all information about a given class
   */
   public String printSingleClass(final Class c) {
      if (c == null){
         return "Class does not exist.";
      }
      else {
         return c.toString() + "\n"
         +"---------------------\n"
         + this.listOneClassRelationships(c);
      }
   }

   public Class getSingleClass(String className) {
      return classList.get(className);
   }

   /*
    * Prompts user for both class names, then prompts for all relevant relationship information 
    * and builds relationships between the classes, then adds it to either of their relationship lists
    */
   /*public void addRelationship(Class c1, Class c2) {
      if (c1 == c2) return;

      Relationship.RelationshipType relationshipType = null;
      int c1Cardinality = -2;
      int c2Cardinality = -2;
      Boolean owner = false;

      relationshipType = MenuPrompts.relationshipTypePrompt();
      if(relationshipType == null){
         return;
      }
      c1Cardinality = MenuPrompts.class1CardinalityPrompt(c1);
      if(c1Cardinality < -1) {
         return;
      }
      c2Cardinality = MenuPrompts.class2CardinalityPrompt(c2);
      owner = MenuPrompts.whichClassIsOwnerPrompt(c1, c2);

      Relationship relationship = new Relationship(relationshipType, c1, c2, c1Cardinality, c2Cardinality, owner);
      addRelationship(relationship);
   }*/

   public void addRelationship(final Relationship relationship) {
      String relationshipName = relationship.getClass1().getClassName() + relationship.getClass2().getClassName();
      this.relationshipList.put(relationshipName, relationship);
   }
   

      /*
    * Finds out both classes belonging to the relationship and deletes the relationship from both of the classes corresponding lists
    */
   public void deleteRelationship(final Class c1, final Class c2){

      String relationshipName = c1.getClassName()+c2.getClassName();
      String relationshipName2 = c2.getClassName()+c1.getClassName();

      this.relationshipList.remove(relationshipName);
      this.relationshipList.remove(relationshipName2);
      
   }

   public Relationship findSingleRelationship(final Class c1, final Class c2) {
      String relationshipName = c1.getClassName()+c2.getClassName();
      Relationship relationship = this.relationshipList.get(relationshipName);
      if(relationship == null)
      {
         relationshipName = c2.getClassName()+c1.getClassName();
         relationship = this.relationshipList.get(relationshipName);
      }

      if (relationship == null)
      {
         System.out.println("No relationship exists between these two classes");
      }

      return relationship;
   }

   public ArrayList<Relationship> getSingleClassRelationships(final Class c1) {
      ArrayList<Relationship> classRelationships = new ArrayList<Relationship>();

      System.out.println(this.getRelationshipList());

      for(Class item : this.classList.values()){
         if(item.equals(c1)) continue;

         if(this.relationshipList.get(c1.getClassName() + item.getClassName()) != null){
            classRelationships.add(this.relationshipList.get(c1.getClassName() + item.getClassName()));
         }
         else if(this.relationshipList.get(item.getClassName() + c1.getClassName()) != null){
            classRelationships.add(this.relationshipList.get(item.getClassName() + c1.getClassName()));
         }
      }
      System.out.println(classRelationships);

      return classRelationships;
   }

   //prints to screen all relationships in relationshipList
   public String listAllRelationships(){
      String str = "Relationship List: \n";
      int i = 1;
      for (Relationship relationship : relationshipList.values()) {
         str += String.valueOf(i) +": ";
         str += relationship.toString();
         i++;
      }

      return str;
   }

   //prints to screen all relationships for one class
   public String listOneClassRelationships(final Class c1) {
      String str = "Relationships: \n";
      int i = 1;

      for(Class item : this.classList.values()){
         if(item.equals(c1)) continue;
         if(this.relationshipList.get(c1.getClassName() + item.getClassName()) != null){
            str += String.valueOf(i) + ": " + this.relationshipList.get(c1.getClassName() + item.getClassName()).toString();
            i++;
         }
         else if(this.relationshipList.get(item.getClassName() + c1.getClassName()) != null){
            str += String.valueOf(i) + ": " + this.relationshipList.get(item.getClassName() + c1.getClassName()).toString();
            i++;
         }
      }

      return str;
   }

   public Diagram createSnapshot(String saveLocation, String title, HashMap<String, Class> classList, HashMap<String, Relationship> relationshipList) {
      Diagram diagram = new Diagram(title);
      diagram.setSaveLocation(saveLocation);
      diagram.setClassList(classList);
      diagram.setRelationshipList(relationshipList);
      return diagram;
   }
   
   /*
   Printing out entire diagram
   */
   public String toString(){
      if (this.classList.isEmpty()) {
         return "\nDiagram " + this.title + " is empty.\n";
      }
      String diagramString = "";
      diagramString += this.title + "\n";
      for (Class c : this.classList.values()){
         diagramString += "\n" + c.toString();
      }
      
      return "\nDiagram: " + diagramString + "\n" + this.listAllRelationships();
   }
   
   public void setSaveLocation(String saveLocation){
      this.saveLocation = saveLocation;
   }

   public String getSaveLocation(){
      return this.saveLocation;
   }
}
package Diagram;

import Class.Class;
import Relationships.Relationship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//Class name subject to change for what we name the project
public class Diagram {
   
   private String title;
   private List<Class> classList = new ArrayList<Class>();
   private Scanner scanner = new Scanner(System.in);
   
   public Diagram(final String title) {

      if (title == null) {
         throw new NullPointerException("invalid param in Diagram constructor");
      }
      //if diagram comes in as null/empty, should we initialize an empty arraylist?
      this.title = title;
    	this.classList = new ArrayList<>();

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
   public List<Class> getClassList(){
	   return this.classList;
   }

   /*
   * Setter for classList
   * */

   public void setClassList(List<Class> classList){
      this.classList = classList;
   }
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
      //need to delete from hashmap while retaining temp class object and then read with new name
      //also need to change the name of the actual class object
      if(old != null && !(newName.isEmpty())){
         Class temp = this.classList.get(old.getClassName());
         this.classList.remove(temp.getClassName());
         temp.setClassName(newName);
         this.classList.put(newName, temp);
      }
      else{
         System.out.println("Bad Parameters");
      }
   }

         if(c1Relationship != null)
         {
            deletedClass.deleteRelationship(c1Relationship);
         }
         if(c2Relationship != null)
         {
            item.deleteRelationship(c2Relationship);
         }
      }
   }
   
   /*
   Lists out all of the classes present in the classList
   */
   public void listClasses() {
      if(this.classList.size() == 0){
         System.out.println("Diagram is empty.");
      }
      else {
         System.out.println("Classes: ");
         for(int i = 0; i < this.classList.size(); i++){
            System.out.println(this.classList.get(i).toString());
         }
      }
      
   } 
   
   /*
   Finds out if class exists
   */
   public Class findSingleClass(final String className) {
      if(className == null) {
         System.out.println("Invalid class name.");
         return null;
      }
      for (int i = 0; i < this.classList.size(); i++) {
         if (this.classList.get(i).getClassName().equals(className)) {
            return this.classList.get(i);
         }
      }
      return null;
   }
   
   /*
   Prints out all information about a given class
   */
   public void printSingleClass() {
      System.out.println("Enter name of class to view: ");
      String className = this.scanner.nextLine();
      Class c = findSingleClass(className);
      if(c != null) {
         System.out.println(c.toString());
      }
   }

   public Class getSingleClass(String className) {
      return classList.get(className);
   }

   /*
    * Prompts user for both class names, then prompts for all relevant relationship information 
    * and builds relationships between the classes, then adds it to either of their relationship lists
    */
   public void addRelationship() {
      System.out.println("What is the name of the owner class?");
      String ownerString = this.scanner.nextLine();
      Class c1 = findSingleClass(ownerString);
      if (c1 == null) {
         System.out.println("Class does not exist");
         return;
      }
      System.out.println("What is the name of the other class?");
      String otherString = this.scanner.nextLine();
      Class c2 = findSingleClass(otherString);
      if (c2 == null) {
         System.out.println("Class does not exist");
         return;
      }

      Relationship.RelationshipType relationshipType = null;
      int c1Cardinality = -2;
      int c2Cardinality = -2;
      Boolean owner = false;

      relationshipType = MenuPrompts.relationshipTypePrompt();
      if(relationshipType == null){
         return;
      }

      c1.addRelationship(relationshipType, c2, c1Cardinality, c2Cardinality, owner);
      c2.addRelationship(relationshipType, c1, c2Cardinality, c1Cardinality, !owner);
   }
   

      /*
    * Finds out both classes beloning to the relationship and deletes the relationship from both of the classes corresponding lists
    */
   public void deleteRelationship(){
      System.out.println("What is the name of the owner class?");
      String ownerString = this.scanner.nextLine();
      Class c1 = findSingleClass(ownerString);
      if (c1 == null) {
         System.out.println("Class does not exist");
         return;
      }
      System.out.println("Whats is the name of the other class?");
      String otherString = this.scanner.nextLine();
      Class c2 = findSingleClass(otherString);
      if (c2 == null) {
         System.out.println("Class does not exist");
         return;
      }

      Relationship c1Relationship = c1.getRelationship(c2);
      Relationship c2Relationship = c2.getRelationship(c1);

      if(c1Relationship != null)
      {
         c1.deleteRelationship(c1Relationship);
      }
      if(c2Relationship != null)
      {
         c2.deleteRelationship(c2Relationship);
      }
      
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

   
   /*
   Printing out entire diagram
   */
   public String toString(){
      if (this.classList == null) {
         return "Diagram does not exist.";
      }
      String diagramString = "";
      diagramString += this.title + "\n\n";
      
      for(int i = 0; i < this.classList.size(); i++){
         diagramString += this.classList.get(i).toString();
      }

      return "Diagram: " + diagramString;
   }
   

}
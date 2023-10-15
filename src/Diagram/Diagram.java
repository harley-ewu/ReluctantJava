package Diagram;

import Class.Class;
import Relationships.Relationship;

import java.util.ArrayList;
import java.util.List;
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
   Menu of choices once inside an existing diagram, or when creating a new diagram
   */
   public void menu(){
      int cont = -99, choice = -99;
      while (cont < 0) {
         do {
            System.out.println("UML Diagram Editor Menu");
            System.out.println("Enter a number:\n\n1 - Add Class\n2 - Delete Class\n3 - Rename Class\n4 - Edit Class\n5 - Edit Relationships\n6 - View Class\n7 - View Diagram\n8 - Exit");
            String op = scanner.nextLine();
            if (!op.isEmpty() && Character.isDigit(op.charAt(0)) && op.length() == 1) {
               choice = Integer.parseInt(op);
            } else {
               choice = -99;
            }
            }while(choice < 0 && choice > 8);
            switch (choice) {
               //Add Class - name needed
               case 1:
                  this.addClass();
                  break;
               //Delete Class - name needed
               case 2:
                  this.deleteClass();
                  break;
               //Rename Class - current and new name needed
               case 3:
                  this.renameClass();
                  break;
               //Edit Class - name needed
               case 4:
                  this.editClass();
                  break;
               //edit relationships
               case 5:
                  this.editRelationships();
                  break;
               //View class - name needed
               case 6:
                  this.printSingleClass();
                  break;
               //View Diagram
               case 7:
                  System.out.println(this);
                  break;
               case 8:
                  cont = 1;
               default:
                  break;
               
            }
      }
      
   }
   /*
    * sub menu for when class is first added, user is immediately prompted to add attributes/relationships
    */
   public void classMenu(final Class currentClass) {
      int cont = -99, choice = -99;
      while (cont < 0) {
         do {
            System.out.println("Class Editor");
            System.out.println("Enter a number: \n\n1 - Add Attribute\n2 - Add Relationship\n3 - Back to Diagram Menu");
            String op = scanner.nextLine();
            if (!op.isEmpty() && Character.isDigit(op.charAt(0)) && op.length() == 1) {
               choice = Integer.parseInt(op);
            } else {
               choice = -99;
            }
         }while(choice < 0 && choice > 3);
         switch(choice) {
            //Add attribute
            case 1:
               currentClass.addAttribute();
               break;
            //Add relationship
            case 2:
               this.addRelationship();
               break;
            case 3:
               cont = 1;
            default:
               break;
         }
      }
   }
   
   /*
   Adds a class to the classList
   */
   public void addClass(){
      System.out.println("Enter a class name to add: ");
      String className = this.scanner.nextLine();
      
      for(int i = 0; i < this.classList.size(); i++) {
         if (className == this.classList.get(i).getClassName()){
            System.out.println("Class name already exists.");
            return;
         }
      }

      Class c = new Class(className);
      this.classList.add(c);
      this.classMenu(c);
   }
   
   /*
   Deletes a class from the classList and also severs existing relationships
   - Iterate through the classList
   - For each class, iterate through relationship list
   - If relationship list contains relationship.otherClassName == deletedNameName
   - call current class that the loop is on, .deleterelationship(deletedClass)
   */
   public void deleteClass(){
      System.out.println("Enter a class name to delete: ");
      String className = this.scanner.nextLine();
      Class deletedClass = findSingleClass(className);
      if (deletedClass == null) {
         throw new IllegalArgumentException("invalid param in removeClass method");
      }

      int i = 0;
      for (i = 0; i < classList.size(); i++){
         if(classList.get(i) == deletedClass) {
            break;
         }
      }
      classList.remove(classList.get(i));
      
      for(Class item : classList){
         Relationship c1Relationship = deletedClass.getRelationship(item);
         Relationship c2Relationship = item.getRelationship(deletedClass);

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
   Renames a class in the classList
   */
   public void renameClass() {
      String oldClassName, newClassName;
      System.out.println("Enter the original name of the class.");
      oldClassName = this.scanner.nextLine();
      if(findSingleClass(oldClassName) != null){
         System.out.println("Class exists. Enter new name for the class.");
         newClassName = this.scanner.nextLine();
         Class c = findSingleClass(oldClassName);
         if (c != null){
            c.setClassName(newClassName);
         }
      }
      else {
         System.out.println("Class does not exist.");
      }
      
   }
   

   /*
    * Checks to see if class exists then calls subMenu method from Class class
    */
   public void editClass(){
      System.out.println("Enter name of class to edit: ");
      String className = this.scanner.nextLine();
      if(findSingleClass(className) == null){
         System.out.println("Class does not exist.");
      }
      for (int i = 0; i < this.classList.size(); i++){
         if(this.classList.get(i).getClassName().equals(className)) {
            this.classList.get(i).subMenu();
         }
      }
   }

   /*
    * Submenu to edit relationships (add/delete) calls those methods in this method
    */

   public void editRelationships(){
      int choice = -99;
      System.out.println("Enter a number:\n1.Add Relationship.\n2.Delete Relationship");
      String op = scanner.nextLine();
      if (!op.isEmpty() && Character.isDigit(op.charAt(0)) && op.length() == 1) {
         choice = Integer.parseInt(op);
      } else {
            choice = -99;
         }

      if (choice == 1) {
         this.addRelationship();
      }
      else if(choice == 2) {
         this.deleteRelationship();
      }
      else {
         System.out.println("Invalid option");
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
         throw new IllegalArgumentException("param invalid in findSingleClass");
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

      int choice;
      while(relationshipType == null) {
         System.out.println("What Type of Relationship?\n" +
                 "1. Association \n2. Aggregation \n3.Composition \n4.Generalization");
         choice = Integer.parseInt(this.scanner.nextLine());
         if(choice < 1 || choice > 4){
            System.out.println("Please enter 1 through 4 as your choice");
         }
         else if(choice == 1){
            relationshipType = Relationship.RelationshipType.Association;
         }
         else if(choice == 2) {
            relationshipType = Relationship.RelationshipType.Aggregation;
         }
         else if (choice == 3){
            relationshipType = Relationship.RelationshipType.Composition;
         }
         else{
            relationshipType = Relationship.RelationshipType.Generalization;
         }

      }

      while(c1Cardinality < -1){
         System.out.println("What is "+c1.getClassName()+"'s Class Cardinality? (Enter -1 for * Cardinality)");
         c1Cardinality = Integer.parseInt(this.scanner.nextLine());
         if(c1Cardinality < -1){
            System.out.println("Please enter a valid cardinality");
         }
      }

      while(c2Cardinality < -1){
         System.out.println("What is "+c2.getClassName()+"'s Class Cardinality? (Enter -1 for * Cardinality)");
         c2Cardinality = Integer.parseInt(this.scanner.nextLine());
         if(c2Cardinality < -1){
            System.out.println("Please enter a valid cardinality");
         }
      }

      choice = 0;
      while(choice != 1 && choice != 2){
         System.out.println("Which class is the owner?\n" +
                 "1. "+c1.getClassName()+"\n2. "+c2.getClassName()+"\n");
         choice = Integer.parseInt(this.scanner.nextLine());

         if(choice != 1 && choice != 2){
            System.out.println("Please enter 1 or 2 as your choice");
         }
         else if(choice == 1){
            owner = true;
         }
         else {
            owner = false;
         }
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


   
   /*
   Printing out entire diagram
   */
   public String toString(){
      if (this.classList == null) {
         throw new IllegalArgumentException("Diagram is null");
      }
      String diagramString = "";
      diagramString += this.title + "\n\n";
      
      for(int i = 0; i < this.classList.size(); i++){
         diagramString += this.classList.get(i).toString();
      }

      return "Diagram: " + diagramString;
   }
   

}
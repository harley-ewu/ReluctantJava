package Diagram;

import Class.Class;
import Relationships.Relationship;
import MenuPrompts.MenuPrompts;
import com.google.gson.annotations.Expose;

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
   private static HashMap<String, Class> classList;
   @Expose
   private static HashMap<String, Relationship> relationshipList;
   private Scanner scanner = new Scanner(System.in);
   
   public Diagram(final String title) {

      if (title == null) {
         throw new NullPointerException("invalid param in Diagram constructor");
      }
      //if diagram comes in as null/empty, should we initialize an empty arraylist?
      this.title = title;
      Diagram.classList = new HashMap<>();
      Diagram.relationshipList = new HashMap<>();
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
   public static HashMap<String, Class> getClassList(){
      return Diagram.classList;
   }

   /*
   * Setter for classList
   * */

   public void setClassList(HashMap<String, Class> classList){
      Diagram.classList = classList;
   }

   //Getter for RelationshipList
   public static HashMap<String, Relationship> getRelationshipList() { return Diagram.relationshipList; }
   //Setter for RelationshipList
   public void setRelationshipList(HashMap<String, Relationship> relationshipList) { Diagram.relationshipList = relationshipList; }

   /*
   Menu of choices once inside an existing diagram, or when creating a new diagram
   */
   /*public void menu(){
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
               System.out.println("Please enter a valid option, 1-8");
            }
            }while(choice < 1 && choice > 8);
            String className = "";
            switch (choice) {
               //Add Class - name needed
               case 1:
                  className = this.addClassPrompt();
                  this.addClass(className);
                  this.classMenu(this.classList.get(className));
                  break;
               //Delete Class - name needed
               case 2:
                  Class deletedClass = this.deleteClassPrompt();
                  this.deleteClass(deletedClass);
                  break;
               //Rename Class - current and new name needed
               case 3:
                  Class old = renameClassPromptOriginalName();
                  String newName = renameClassPromptNewName(old);
                  this.renameClass(old, newName);
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
      
   }*/
   /*
    * sub menu for when class is first added, user is immediately prompted to add attributes/relationships
    */
   /*public void classMenu(final Class currentClass) {
      int cont = -99, choice = -99;
      while (cont < 0) {
         do {
            System.out.println("New Class Editor");
            System.out.println("Enter a number: \n\n1 - Add Attribute\n2 - Add Relationship\n3 - Back to Diagram Menu");
            String op = scanner.nextLine();
            if (!op.isEmpty() && Character.isDigit(op.charAt(0)) && op.length() == 1) {
               choice = Integer.parseInt(op);
            } else {
               choice = -99;
               System.out.println("Please enter a valid option, 1-3");
            }
         }while(choice < 1 && choice > 3);
         switch(choice) {
            //Add attribute
            case 1:
               currentClass.addAttribute();
               break;
            //Add relationship
            case 2:
               System.out.println("Which class do you want to make a relationship with?");
               System.out.print("Class: ");
               String input = scanner.nextLine();
               Class c2 = null;
               do {
                  for(Class item : classList.values()){
                     if(input.equals(item.getClassName())) {
                        c2 = item;
                     }
                  }

                  if(c2 == null) {
                     System.out.println("class does not exist, please enter a valid class");
                     System.out.print("Class: ");
                     input = scanner.nextLine();
                  }
               }while(c2 == null);

               this.addRelationship(currentClass, c2);
               break;
            case 3:
               cont = 1;
            default:
               break;
         }
      }
   }*/
   
   /*
   Adds a class to the classList
   */
   public void addClass(final String className){
      
      Class c = Diagram.classList.get(className);
      if (c == null) {
         Diagram.classList.put(className, new Class(className));
      }
      else {
         System.out.println("Class already exists.");
      }
   }

   /*public String addClassPrompt() {
      System.out.println("Enter a class name to add: ");
      String className = this.scanner.nextLine();
      while (className.isEmpty()) {
         System.out.println("Please enter a name between 1 and 50 characters inclusive");
         className = this.scanner.nextLine();
      }

      return className;
   }*/
   
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
   
   /*public Class deleteClassPrompt() {
      System.out.println("Enter a class name to delete: ");
      String className = this.scanner.nextLine();
      while (className.isEmpty()) {
         System.out.println("Please enter a name between 1 and 50 characters inclusive");
         className = this.scanner.nextLine();
      }
      Class deletedClass = findSingleClass(className);
      if (deletedClass == null) {
         System.out.println("Class does not exist.");
         return null;
      }

      return deletedClass;
   }*/


   /*
   Renames a class in the classList
   */
   public void renameClass(final Class old, final String newName) {
      //need to delete from hashmap while retaining temp class object and then readd with new name
      //also need to change the name of the actual class object
      if(old != null && !(newName.isEmpty())){
         Class temp = Diagram.classList.get(old.getClassName());
         Diagram.classList.remove(temp.getClassName());
         temp.setClassName(newName);
         Diagram.classList.put(newName, temp);
      }
      else{
         System.out.println("Bad Parameters");
      }
      
      
   }

   /*
    * Separated the prompt of the original class name into its own method so I could do proper testing
    */
   /*public Class renameClassPromptOriginalName() {
      String oldClassName;
      System.out.println("Enter the original name of the class.");
      oldClassName = this.scanner.nextLine();
      Class c = findSingleClass(oldClassName);
      if(c != null){
         System.out.println("Class exists.");
         return c;
      }
      else {
         System.out.println("Class does not exist.");
         return null;
      }
   }*/

   /*
    * Separated prompting of new class name to separate method so I could test
    */
   /*public String renameClassPromptNewName(final Class old) {
      String newClassName = "";
      if(old != null) {
         System.out.println("Enter a new name for the class.");
         newClassName = this.scanner.nextLine();
         while (newClassName.isEmpty()) {
            System.out.println("Please enter a name between 1 and 50 characters inclusive");
            newClassName = this.scanner.nextLine();
         }
      }
      return newClassName;
   }*/
   

   /*
    * Checks to see if class exists then calls subMenu method from Class class
    */
   /*public void editClass(){
      String className = MenuController.
      if(className.isEmpty()) {
         return;
      }
      //this.classList.get(className).subMenu();
   }*/

   /*
    * Submenu to edit relationships (add/delete) calls those methods in this method
    */

   /*public void editRelationships(){
      int choice = -99;
      System.out.println("Enter a number:\n1.Add Relationship.\n2.Delete Relationship");
      String op = scanner.nextLine();
      if (!op.isEmpty() && Character.isDigit(op.charAt(0)) && op.length() == 1) {
         choice = Integer.parseInt(op);
      } else {
            choice = -99;
         }

      System.out.println("What is the name of the first class?");
      String ownerString = this.scanner.nextLine();
      Class c1 = findSingleClass(ownerString);
      if (c1 == null) {
         System.out.println("Class does not exist");
         return;
      }
      System.out.println("What is the name of the second class?");
      String otherString = this.scanner.nextLine();
      Class c2 = findSingleClass(otherString);
      if (c2 == null) {
         System.out.println("Class does not exist");
         return;
      }
      

      //needs to be moved to the MenuController
      if (choice == 1) {
         this.addRelationship(c1, c2);
      }
      else if(choice == 2) {
         this.deleteRelationship(c1, c2);
      }
      else {
         System.out.println("Invalid option");
      }
   }*/


   /*
   Lists out all of the classes present in the classList
   */
   public void listClasses() {
      if(Diagram.classList.size() == 0){
         System.out.println("Diagram is empty.");
      }
      else {
         System.out.println("Classes: ");
         System.out.println(Diagram.classList.values());
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
      Class c = Diagram.classList.get(className);
      return c;
   }
   
   /*
   Prints out all information about a given class
   */
   public void printSingleClass(final Class c) {
      if (c == null){
         System.out.println("Class does not exist.");
      }
      else {
         System.out.println(c.toString() + "\n"
         +"---------------------\n"
         + this.listOneClassRelationships(c));
      }
   }
   /*
    * Prompts user for both class names, then prompts for all relevant relationship information 
    * and builds relationships between the classes, then adds it to either of their relationship lists
    */
   public void addRelationship(Class c1, Class c2) {
      if (c1 == c2) return;

      Relationship.RelationshipType relationshipType = null;
      int c1Cardinality = -2;
      int c2Cardinality = -2;
      Boolean owner = false;

      /*int choice;
      while(relationshipType == null) {
         System.out.println("What Type of Relationship?\n" +
                 "1. Association \n2. Aggregation \n3.Composition \n4.Generalization");
         choice = Integer.parseInt(this.scanner.nextLine());
         if(choice < 1 || choice > 4){
            System.out.println("Please enter 1 through 4 as your choice");
         }
         else if(choice == 1){
            relationshipType = Relationship.RelationshipType.Realization;
         }
         else if(choice == 2) {
            relationshipType = Relationship.RelationshipType.Aggregation;
         }
         else if (choice == 3){
            relationshipType = Relationship.RelationshipType.Composition;
         }
         else{
            relationshipType = Relationship.RelationshipType.Inheritance;
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
         System.out.println("Which class is the owner of the relationship?\n" +
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
      }*/
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
   }

   public void addRelationship(final Relationship relationship) {
      String relationshipName = relationship.getClass1().getClassName() + relationship.getClass2().getClassName();
      Diagram.relationshipList.put(relationshipName, relationship);
   }
   

      /*
    * Finds out both classes belonging to the relationship and deletes the relationship from both of the classes corresponding lists
    */
   public void deleteRelationship(final Class c1, final Class c2){

      String relationshipName = c1.getClassName()+c2.getClassName();
      String relationshipName2 = c2.getClassName()+c1.getClassName();

      Diagram.relationshipList.remove(relationshipName);
      Diagram.relationshipList.remove(relationshipName2);
      
   }

   public Relationship findSingleRelationship(final Class c1, final Class c2) {
      String relationshipName = c1.getClassName()+c2.getClassName();
      Relationship relationship = Diagram.relationshipList.get(relationshipName);
      if(relationship == null)
      {
         relationshipName = c2.getClassName()+c1.getClassName();
         relationship = Diagram.relationshipList.get(relationshipName);
      }

      if (relationship == null)
      {
         System.out.println("No relationship exists between these two classes");
      }

      return relationship;
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

      for(Class item : Diagram.classList.values()){
         if(item.equals(c1)) continue;
         if(Diagram.relationshipList.get(c1.getClassName() + item.getClassName()) != null){
            str += String.valueOf(i) + ": " + Diagram.relationshipList.get(c1.getClassName() + item.getClassName()).toString();
            i++;
         }
         else if(Diagram.relationshipList.get(item.getClassName() + c1.getClassName()) != null){
            str += String.valueOf(i) + ": " + Diagram.relationshipList.get(item.getClassName() + c1.getClassName()).toString();
            i++;
         }
      }

      return str;
   }
   
   /*
   Printing out entire diagram
   */
   public String toString(){
      if (Diagram.classList.isEmpty()) {
         return "\nDiagram " + this.title + " is empty.\n";
      }
      String diagramString = "";
      diagramString += this.title + "\n\n";
      for (Class c : Diagram.classList.values()){
         diagramString += c.toString();
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
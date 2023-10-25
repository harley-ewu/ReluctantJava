package Diagram;

import Class.Class;
import Relationships.Relationship;

import java.util.HashMap;
import java.util.Scanner;

//Class name subject to change for what we name the project
public class Diagram {
   
   private String title;
   //private List<Class> classList = new ArrayList<Class>();
   private HashMap<String, Class> classList;
   private Scanner scanner = new Scanner(System.in);
   
   public Diagram(final String title) {

      if (title == null) {
         throw new NullPointerException("invalid param in Diagram constructor");
      }
      //if diagram comes in as null/empty, should we initialize an empty arraylist?
      this.title = title;
    	this.classList = new HashMap<>();

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
   /*
   Menu of choices once inside an existing diagram, or when creating a new diagram
   */
   public void menu(){
      int cont = -99, choice = -99;
      while (cont < 0) {
         do {
            System.out.println("UML Diagram Editor Menu");
            System.out.println("Enter a number:\n\n1 - Add Class\n2 - Delete Class\n3 - Rename Class\n4 - Edit Class\n5 - Edit Relationships\n6 - View Class\n7 - View Diagram\n8 - Help \n9 - Exit");
            String op = scanner.nextLine();
            if (!op.isEmpty() && Character.isDigit(op.charAt(0)) && op.length() == 1) {
               choice = Integer.parseInt(op);
            } else {
               choice = -99;
               System.out.println("Please enter a valid option, 1-8");
            }
            }while(choice < 1 && choice > 9);
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
                  this.diagramMenuHelp();
                  break;
               case 9:
                  cont = 1;
               default:
                  break;
               
            }
      }
      
   }
   public void diagramMenuHelp() {
         System.out.println("""
                 DIAGRAM MENU COMMANDS:
 
                 Option 1 - Add Class: Adds a class to the current diagram.
                 
                 Option 2 - Delete Class: Option to delete class if it exists.
                 
                 Option 3 - Rename Class: Gives the option to change the name of an existing class.
                 
                 Option 4 - Edit Class: Displays another menu with attribute and relationship options.
                 
                 Option 5 - Edit Relationships: Once two classes are specified, if a relationship is shared, it can be edited.
                 
                 Option 6 - View Class: Lists specified class and all its attributes and relationships.

                 Option 7 - View Diagram: Prints out current diagram with all its contents.

                 Option 8 - Help: Lists descriptions of available commands.

                 Option 9 - Exit: Exit the program.
                 """);
   }
   /*
    * sub menu for when class is first added, user is immediately prompted to add attributes/relationships
    */
   public void classMenu(final Class currentClass) {
      int cont = -99, choice = -99;
      while (cont < 0) {
         do {
            System.out.println("Class Editor");
            System.out.println("Enter a number: \n\n1 - Add Attribute\n2 - Add Relationship\n3 - Help\n4 - Back to Diagram Menu");
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
               this.addRelationship();
               break;
            case 3:
               this.classMenuHelp();
               break;
            case 4:
               cont = 1;
            default:
               break;
         }
      }
   }

   public void classMenuHelp(){
         System.out.println("""
                 NEW CLASS MENU COMMANDS:
 
                 Option 1 - Add Attribute: Add an attribute to the current class.
                 
                 Option 2 - Add Relationship: Add a relationship to current class with another existing class.
                 
                 Option 3 - Help: Lists a description of all available commands.
                 
                 Option 4 - Back to Diagram Menu: Returns back to the current diagram's edit menu.
                 """);
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

   public String addClassPrompt() {
      System.out.println("Enter a class name to add: ");
      String className = this.scanner.nextLine();
      while (className.isEmpty()) {
         System.out.println("Please enter a name between 1 and 50 characters inclusive");
         className = this.scanner.nextLine();
      }

      return className;
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
   
   public Class deleteClassPrompt() {
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
   }


   /*
   Renames a class in the classList
   */
   public void renameClass(final Class old, final String newName) {
      //need to delete from hashmap while retaining temp class object and then readd with new name
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

   /*
    * Separated the prompt of the original class name into its own method so I could do proper testing
    */
   public Class renameClassPromptOriginalName() {
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
   }

   /*
    * Separated prompting of new class name to separate method so I could test
    */
   public String renameClassPromptNewName(final Class old) {
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
   }
   

   /*
    * Checks to see if class exists then calls subMenu method from Class class
    */
   public void editClass(){
      System.out.println("Enter name of class to edit: ");
      String className = this.scanner.nextLine();
      while (className.isEmpty()) {
         System.out.println("Please enter a name between 1 and 50 characters inclusive");
         className = this.scanner.nextLine();
      }
      if(findSingleClass(className) == null){
         System.out.println("Class does not exist.");
         return;
      }
      this.classList.get(className).subMenu();
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
         System.out.println(this.classList.values());
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
      Class c = this.classList.get(className);
      return c;
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
      else {
         System.out.println("Bug");
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
         return this.title + " is empty.";
      }
      String diagramString = "";
      diagramString += this.title + "\n\n";
      for (Class c : this.classList.values()){
         diagramString += c.toString();
      }
      
      return "Diagram: " + diagramString;
   }
   

}
package Diagram;
import Class.*;
import Class.Class;

import java.util.*;

//Class name subject to change for what we name the project
public class Diagram {
   
   private String title;
   private List<Class> classList = new ArrayList<Class>();
   private Scanner scanner = new Scanner(System.in);
   
   public Diagram(final String title, final List<Class> classList) {
	  
      if (title == null) {
         throw new IllegalArgumentException("invalid param in Diagram constructor");
      }
      //if diagram comes in as null/empty, should we initialize and empty arraylist?
      this.title = title;
      
      if (classList == null) {
    	  this.classList = new ArrayList<>();
      }
      else {
    	  this.classList = classList;
      }
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
         System.out.println("Enter a number:\n\n1 - Add Class\n2 - Delete Class\n3 - Rename Class\n4 - Edit Class\n5 - View Class\n6 - View Diagram\n7 - Exit");
         choice = Integer.parseInt(this.scanner.nextLine());
         }while(choice < 0 && choice > 6);
         String className;
         switch (choice) {
            //Add Class - name needed
            
            case 1:
               System.out.println("Enter a class name to add: ");
               className = this.scanner.nextLine();
               this.addClass(className);
               break;
               
            //Delete Class - name needed
            case 2:
               System.out.println("Enter a class name to delete: ");
               className = this.scanner.nextLine();
               Class c = findSingleClass(className);
               this.deleteClass(c);
               break;
            
            //Rename Class - current and new name needed
            case 3:
               String oldClassName, newClassName;
               System.out.println("Enter the original name of the class.");
               oldClassName = this.scanner.nextLine();
               if(findSingleClass(oldClassName) != null){
                  System.out.println("Class exists. Enter new name for the class.");
                  newClassName = this.scanner.nextLine();
                  this.renameClass(oldClassName, newClassName);
               }
               else {
                  System.out.println("Class does not exist.");
               }
               break;
               
            //Edit Class - name needed
            case 4:
               System.out.println("Enter name of class to edit: ");
               className = this.scanner.nextLine();
               if(findSingleClass(className) == null){
                  System.out.println("Class does not exist.");
               }
               for (int i = 0; i < this.classList.size(); i++){
                  if(this.classList.get(i).getClassName().equals(className)) {
                     this.classList.get(i).subMenu();
                  }
               }
               break;
               
            //View class - name needed
            case 5:
               System.out.println("Enter name of class to view: ");
               className = this.scanner.nextLine();
               c = findSingleClass(className);
               if(c != null) {
                  printSingleClass(c);
               }
               break;
               
            //View Diagram
            case 6:
               System.out.println(this.classList);
               break;
            case 7: 
               cont = 1;
            default:
               break;
            
         }
      }
      
   }
   
   /*
   Adds a class to the classList
   */
   public void addClass(final String className){
      if (className == null) {
         throw new IllegalArgumentException("invalid param in addClass method");
      }
      for(int i = 0; i < this.classList.size(); i++) {
         if (className == this.classList.get(i).getClassName()){
            System.out.println("Class name already exists.");
            
         }
      }
      this.classList.add(new Class(className));
   }
   
   /*
   Deletes a class from the classList
   TODO: Need to check for relationships with other classes and sever them before deleting class
   - Iterate through the classList
   - For each class, iterate through relationship list
   - If relationship list contains relationship.otherClassName == deletedNameName
   - call current class that the loop is on, .deleterelationship(deletedClass)
   */
   public void deleteClass(final Class deletedClass){
      if (deletedClass == null) {
         throw new IllegalArgumentException("invalid param in removeClass method");
      }
      
      
      
   }
   
   /*
   Renames a class in the classList
   */
   public void renameClass(final String oldClassName, final String newClassName) {
      if (oldClassName == null || newClassName == null) {
         throw new IllegalArgumentException("invalid param in renameClass method");
      }
      
      Class c = findSingleClass(oldClassName);
      if (c != null){
         c.setClassName(newClassName);
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
   public void printSingleClass(final Class c) {
      if (c == null) {
         throw new IllegalArgumentException("class is null");
      }
      
      System.out.println(c.toString());
   }
   
   public void addRelationship(final Class c1, final Class c2) {
      //update to prompt for additional info
      //c1.addRelationship(c2);
      //c2.addRelationship(c1);
   }
   
   public void deleteRelationship(final Class c1, final Class c2){
      //relationship passed in
      //either add a get relationship method to Class class
      //or iterate through c1's relationship list to find the relationship with c2
      //and iterate through c2's relationship list to find the relationship with c1
      //c1.deleteRelationship(c2);
      //c2.deleteRelationship(c1);
   }


   
   /*
   Printing out entire diagram
   */
   public String toString(){
      if (this.classList == null) {
         throw new IllegalArgumentException("Diagram is null");
      }
      String diagramString = "";
      diagramString += this.title + "\n";
      
      for(int i = 0; i < this.classList.size(); i++){
         diagramString += this.classList.get(i).toString();
      }

      return "Diagram: " + diagramString;
   }
   

}
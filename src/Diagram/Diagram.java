package Diagram;
import Class.*;
import java.util.*;

//Class name subject to change for what we name the project
public class Diagram {
   
   private String title;
   private List<Class> diagram = new ArrayList<Class>();
   private Scanner scanner = new Scanner(System.in);
   
   public Diagram(final String title, final List<Class> diagram) {
      if (diagram == null || title == null) {
         throw new IllegalArgumentException("invalid param in Diagram constructor");
      }
      
      this.title = title;
      this.diagram = diagram;
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
               this.diagram.addClass(className);
               break;
               
            //Delete Class - name needed
            case 2:
               System.out.println("Enter a class name to delete: ");
               className = this.scanner.nextLine();
               this.diagram.deleteClass(className);
               break;
            
            //Rename Class - current and new name needed
            case 3:
               String oldClassName, newClassName;
               System.out.println("Enter the original name of the class.");
               oldClassName = this.scanner.nextLine();
               if(findSingleClass(oldClassName) != null){
                  System.out.println("Class exists. Enter new name for the class.");
                  newClassName = this.scanner.nextLine();
                  this.diagram.renameClass(oldClassName, newClassName);
               }
               else {
                  System.out.println("Class does not exist.");
               }
               break;
               
            //Edit Class - name needed
            case 4:
               System.out.println("Enter name of class to edit: ");
               className = this.scanner.nextLine();
               if(findSingleClass == null){
                  System.out.println("Class does not exist.");
               }
               for (int i = 0; i < this.diagram.size(); i++){
                  if(this.diagram[i].getClassName() == className) {
                     this.diagram[i].subMenu();
                  }
               }
               break;
               
            //View class - name needed
            case 5:
               System.out.println("Enter name of class to view: ");
               className = this.scanner.nextLine();
               c = findSingleClass;
               if(c != null) {
                  printSingleClass(c);
               }
               break;
               
            //View Diagram
            case 6:
               System.out.println(this.diagram)
               break;
            case 7: 
               cont = 1;
            default:
               break;
            
         }
      }
      
   }
   
   /*
   Adds a class to the diagram
   */
   public void addClass(final String className){
      if (className == null) {
         throw new IllegalArgumentException("invalid param in addClass method");
      }
      for(int i = 0; i < this.diagram.size(); i++) {
         if (className == this.diagram[i].getClassName()){
            System.out.println("Class name already exists.");
            
         }
      }
      this.diagram.add(new Class(className));
   }
   
   /*
   Deletes a class from the diagram
   TODO: Need to check for relationships with other classes and sever them before deleting class
   */
   public void deleteClass(final String className){
      if (className == null) {
         throw new IllegalArgumentException("invalid param in removeClass method");
      }
      
      
      
   }
   
   /*
   Renames a class in the diagram
   */
   public void renameClass(final String oldClassName, final String newClassName) {
      if (oldClassName == null || newClassName == null) {
         throw new IllegalArgumentException("invalid param in renameClass method");
      }
      
      Class c = findSingleClass(oldClassName);
      if (c != null){
         c.setName(newClassName);
      }
      
   }
   
   /*
   Lists out all of the classes present in the diagram
   */
   public void listClasses() {
      if(this.diagram.size() == 0){
         System.out.println("Diagram is empty.");
      }
      else {
         System.out.println("Classes: ");
         for(int i = 0; i < this.diagram.size(); i++){
            System.out.println(this.diagram[i].toString());
         }
      }
      
   } 
   
   /*
   Finds out if class exists
   */
   public Class findSingleClass(final String className) {
      if(name == null) {
         throw new IllegalArgumentException("param invalid in findSingleClass");
      }
      for (int i = 0; i < this.diagram.size(); i++) {
         if (this.diagram[i].getClassName().equals(name)) {
            return this.diagram[i];
         }
      }
      return null;
   }
   
   /*
   Prints out all information about a given class
   */
   public void printSingleClass(final Class class) {
      if (class == null) {
         throw new IllegalArgumentException("class is null");
      }
      
      System.out.println(class.toString());
   }
   
   public void addRelationship(final Class c1, final Class c2) {
      //update to prompt for additional info
      c1.addRelationship(c2);
      c2.addRelationship(c1);
   }
   
   public void deleteRelationship(final Class c1, final Class c2){
      c1.deleteRelationship(c2);
      c2.deleteRelationship(c1);
   }
   
   /*
   Printing out entire diagram
   */
   public String toString(){
      if (this.diagram == null) {
         throw new IllegalArgumentException("Diagram is null");
      }
      String diagramString;
      diagramString += this.title + "\n";
      
      for(int i = 0; i < this.diagram.size(); i++){
         diagramString += this.diagram[i].toString();
      }

      return diagramString;
   }
   

}
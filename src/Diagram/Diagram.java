package Diagram;
import Class.*;
import java.util.*;

//Class name subject to change for what we name the project
public class Diagram {
   
   //TODO: Implement add/delete/rename class
   //TODO: Implement class listing function
   //TODO: Implement listing single class 
   //TODO: Implement listing class relationships
   
   private List<Class> diagram = new List<Class>();
   
   public class Diagram(final List diagram) {
      if (diagram == null) {
         throw new IllegalArgumentException("invalid param in Diagram constructor");
      }
      
      this.diagram = diagram;
   }
   
   /*
   Lists out all of the classes present in the diagram
   */
   public void listClasses() {
      for(int i = 0; i < this.diagram.size(); i++){
         System.out.println(this.diagram[i].getClassName());
      }
   } 
   
   /*
   Finds out if class exists
   */
   public Class findSingleClass(final String name) {
      if(name == null || name.isEmpty()) {
         throw new IllegalArgumentException("param invalid in findSingleClass");
      }
      for (int i = 0; i < this.diagram.size()) {
         if (this.diagram[i].getClassName() == name) {
            return this.diagram[i];
         }
      }
      
   }
   
   /*
   Prints out all information about a given class
   */
   public void printSingleClass(final Class class) {
      if (class == null) {
         throw new IllegalArgumentException("invalid param in printSingleClass");
      }
      
      //Need to come back to when class information is set
      System.out.println(class);
   }
   

}
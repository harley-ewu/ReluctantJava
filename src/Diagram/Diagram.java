package Diagram;
import Class.*;
import Attributes.*;
import java.util.HashMap;

//Class name subject to change for what we name the project
public class Diagram {
   
   //TODO: Implement add/remove/rename class
   //TODO: Implement class listing function
   //TODO: Implement listing single class 
   //TODO: Implement listing class relationships
   
   private HashMap<Class, Attributes> diagram = new HashMap<Class, Attributes>();
   
   public class Diagram(final HashMap diagram) {
      if (diagram == null) {
         throw new IllegalArgumentException("invalid param in Diagram constructor");
      }
      
      this.diagram = diagram;
   }
   
   /*
   Lists out all of the classes present in the diagram
   */
   public void listClasses() {
      for (Class c : this.diagram.keySet()){
         System.out.println(c);
      }
   } 
   
   /*
   If exists, prints out single class and all attributes of given class
   */
   public Class findSingleClass(final String name) {
      if(name == null || name.isEmpty()) {
         throw new IllegalArgumentException("param invalid in findSingleClass");
      }
      for (Class c : this.diagram.keySet()) {
         if (c.getClassName() == name) {
            System.out.println(
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
      
      System.out.println(class);
   }
   

}
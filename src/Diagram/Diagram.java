package Diagram;

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
         throw new IllegalArgumentException("invalid paramter");
      }
      
      this.diagram = diagram;
   }
   
   public void listClasses() {
      for (Class c : this.diagram.keySey()){
         System.out.println("Class: " + c);
         System.out.println("Attributes: " + this.diagram.get(c));
      }
   } 
   

}
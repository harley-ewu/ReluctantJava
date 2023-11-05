package GUI;

import Diagram.Diagram;

public class DiagramMenuController {

    Diagram diagram = new Diagram("test");

    public void addClassGUI(){
        System.out.println("Adding class");
    }

    public void deleteClassGUI(){
        System.out.println("Deleting class");
    }

    public void renameClassGUI(){
        System.out.println("Renaming class");
    }

    public void editClassGUI(){
        System.out.println("Editing class");
    }
    public void editRelationshipsGUI(){
        System.out.println("Editing relationships");
    }

    public void viewClassGUI(){
        System.out.println("Viewing class");
    }

    public void viewDiagramGUI(){
        System.out.println("Viewing diagram");
    }

    public void diagramHelpGUI(){
        System.out.println("HELP");
    }

    public void exitUI(){
        System.exit(0);
    }
}

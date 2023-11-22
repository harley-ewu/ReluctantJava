import application.GUI.GUIDiagramProject;
import org.junit.jupiter.api.Test;
import Diagram.Diagram;
import Class.Class;
import Relationships.Relationship;
import application.Application;
import application.CLI.CommandLineInterface;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GUITests {
    @Test
    public void initializeDiagramContentsTest() {
        GUIDiagramProject testGUI = new GUIDiagramProject();

        // Create a sample diagram with classes and relationships
        Diagram testDiagram = new Diagram("Test Diagram");
        Class class1 = new Class("Class1");
        Class class2 = new Class("Class2");
        ArrayList<Class> classAssets = new ArrayList<>();
        classAssets.add(class1);
        classAssets.add(class2);
        Relationship relationship = new Relationship(Relationship.RelationshipType.Aggregation, class1, class2, 1, 1, false);
        testDiagram.addClass("Class1");
        testDiagram.addClass("Class2");
        testDiagram.addRelationship(relationship);
        testGUI.addClassAssets();

        Application.setCurrentDiagram(testDiagram);

        assertNotNull(testGUI.getClassAssets());
        assertNotNull(testGUI.getRelationshipAssets());
        assertNotNull(testGUI.getClassPanes());
        assertNotNull(testGUI.getRelationshipPanes());
        assertNotNull(testGUI.getClassList());
        assertNotNull(testGUI.getRelationshipList());

        assertEquals(2, testGUI.getClassAssets().size());
    }

    @Test
    public void testRelationshipAssetSize() {
        GUIDiagramProject testGUI = new GUIDiagramProject();
        ArrayList<Relationship> aRelationship = new ArrayList<>();
        Class class1 = new Class("Class1");
        Class class2 = new Class("Class2");
        Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, class1, class2, 1, 1, false);
        aRelationship.add(testRelationship);
        testGUI.addRelationshipAsset(aRelationship);
        assertEquals(1, testGUI.getRelationshipAssets().size());
    }
}

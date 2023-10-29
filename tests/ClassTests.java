import Class.Class;
import Diagram.Diagram;
import Relationships.Relationship;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClassTests {

    private final PrintStream ORIGINALOUT = System.out;
    private final InputStream ORIGINALIN = System.in;
    private ByteArrayOutputStream testOut;
    private ByteArrayInputStream testIn;

    Diagram newDiagram;
    Class newClass;

    @BeforeEach
    public void init() {

        this.testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(this.testOut));
    }

    @AfterEach
    public void cleanUp() {

        System.setOut(this.ORIGINALOUT);
        System.setIn(this.ORIGINALIN);
    }

    private void injectInput(final String msg) throws IllegalAccessException, NoSuchFieldException {

        this.testIn = new ByteArrayInputStream((msg +System.lineSeparator()).getBytes());
        System.setIn(this.testIn);
        Field field = Keyboard.class.getDeclaredField("in");
        field.setAccessible(true);
        field.set(null, new BufferedReader(new InputStreamReader(System.in)));
    }

    @BeforeEach
    void setUp() {
        this.newDiagram = new Diagram("Test Diagram");
        this.newClass = new Class("Test Class");
        //newDiagram.addClass(this.newClass);
    }

    @Test
    public void testGetClassName() {
        assertEquals("Test Class", this.newClass.getClassName());
    }

    @Test
    public void testSetClassName() {
        this.newClass.setClassName("New Class Name");
        assertEquals("New Class Name", this.newClass.getClassName());
    }

    @Test
    void testDisplayAttributes2() {
        Class test = new Class("Test Class");
        ArrayList<String> testList = new ArrayList<>();
        ArrayList<String> testList2 = new ArrayList<>();
        ArrayList<String> testList3 = new ArrayList<>();

        testList2.add("Boolean");
        test.addAttribute("testMethod", testList, 2);
        test.addAttribute("testField1", testList2, 1);
        test.addAttribute("testMethod2", testList, 2);

        test.sortArrayList(test.getAttributes());

        String expected = "Available Fields and Methods: \n" +
                "1. testMethod()\n" +
                "2. testMethod2()\n"+
                "3. testField1: Boolean\n";
        assertEquals(expected, test.displayAttributes());
    }

    /*
    @Test
    public void testAddAttributeMethod() {
        try {
            this.injectInput("color");
        }catch(IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        this.newClass.addAttribute();
        assertEquals("color", this.newClass.getAttributes().getAttributes().get(0).toString());
    }

    @Test
    public void testDeleteAttributeMethod() {
        this.newClass.getAttributes().addAttribute("color");
        this.newClass.getAttributes().addAttribute("age");
        try {
            this.injectInput("age");
        }catch (IllegalAccessException | NoSuchFieldException e){
            throw new RuntimeException(e);
        }

        this.newClass.deleteAttribute();
        assertTrue(this.newClass.getAttributes().getAttributes().size() == 1);
    }

    @Test
    public void testAlreadyContainsAttribute() {
        this.newClass.getAttributes().addAttribute("color");

        try {
            this.injectInput("color");
        }catch(IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        this.newClass.addAttribute();
        assertEquals("Attribute is already in the list!", this.testOut.toString());

    }

    @Test
    public void testAddRelationship() {

        Relationship.RelationshipType relationshipType = Relationship.RelationshipType.Association;
        Class otherClass = new Class("Other Class",this.newDiagram);
        int thisClassCardinality = 1;
        int otherClassCardinality = -1;
        boolean owner = true;

        Relationship testRelationship = new Relationship(relationshipType, otherClass, thisClassCardinality, otherClassCardinality, owner);

        this.newClass.addRelationship(relationshipType, otherClass, thisClassCardinality, otherClassCardinality, owner);
        assertEquals(testRelationship, this.newClass.getRelationship(otherClass));

    }

    @Test
    public void testDeleteRelationship() {

        Relationship.RelationshipType relationshipType = Relationship.RelationshipType.Association;
        Class otherClass = new Class("Other Class",this.newDiagram);
        int thisClassCardinality = 1;
        int otherClassCardinality = -1;
        boolean owner = true;

        Relationship testRelationship = new Relationship(relationshipType, otherClass, thisClassCardinality, otherClassCardinality, owner);
        this.newClass.addRelationship(relationshipType, otherClass, thisClassCardinality, otherClassCardinality, owner);
        assertEquals(testRelationship, this.newClass.getRelationship(otherClass));
        this.newClass.deleteRelationship(testRelationship);
        assertTrue(this.newClass.getRelationships().isEmpty());

    }
*/
    @Test
    public void testRelationshipListEmpty() {
        Relationship.RelationshipType relationshipType = Relationship.RelationshipType.Association;
        Class otherClass = new Class("Other Class");
        int thisClassCardinality = 1;
        int otherClassCardinality = -1;
        boolean owner = true;

        Relationship testRelationship = new Relationship(relationshipType, otherClass, thisClassCardinality, otherClassCardinality, owner);
        this.newClass.deleteRelationship(testRelationship);
        assertEquals("There are no relationships assigned to this class.\n", this.testOut.toString());

    }

    @Test
    public void testDisplayRelationships() {
        Relationship.RelationshipType relationshipType = Relationship.RelationshipType.Association;
        Class otherClass = new Class("Other Class 1");
        int thisClassCardinality = 1;
        int otherClassCardinality = -1;
        boolean owner = true;

        Relationship.RelationshipType relationshipType2 = Relationship.RelationshipType.Association;
        Class otherClass2 = new Class("Other Class 2");
        int thisClassCardinality2 = 1;
        int otherClassCardinality2 = -1;
        boolean owner2 = true;

        this.newClass.addRelationship(relationshipType, otherClass, thisClassCardinality, otherClassCardinality, owner);
        this.newClass.addRelationship(relationshipType2, otherClass2, thisClassCardinality2, otherClassCardinality2, owner2);

        String testText = "Relationships in the Test Class class: \n" +
                "Class has a Association relationship with Other Class 1\n" +
                "Owner: true\n" +
                "This Class Cardinality: 1\n" +
                "Other Class 1 Class Cardinality: *\n" +
                "\n" +
                "Class has a Association relationship with Other Class 2\n" +
                "Owner: true\n" +
                "This Class Cardinality: 1\n" +
                "Other Class 2 Class Cardinality: *\n\n";

        assertEquals(testText, this.newClass.displayRelationships());

    }


    @Test
    public void  testDisplayAttributes() {
        //this.newClass.getAttributes().addAttribute("color");
        //this.newClass.getAttributes().addAttribute("skin");

        String output = "Attributes in the Test Class class:\n" +
                "color\n" +
                "skin";

        assertEquals(output, this.newClass.displayAttributes());

    }

    @Test
    public void testToString() {
        //this.newClass.getAttributes().addAttribute("color");
       // this.newClass.getAttributes().addAttribute("skin");

        Relationship.RelationshipType relationshipType = Relationship.RelationshipType.Association;
        Class otherClass = new Class("Other Class 1");
        int thisClassCardinality = 1;
        int otherClassCardinality = -1;
        boolean owner = true;

        Relationship.RelationshipType relationshipType2 = Relationship.RelationshipType.Association;
        Class otherClass2 = new Class("Other Class 2");
        int thisClassCardinality2 = 1;
        int otherClassCardinality2 = -1;
        boolean owner2 = true;

        this.newClass.addRelationship(relationshipType, otherClass, thisClassCardinality, otherClassCardinality, owner);
        this.newClass.addRelationship(relationshipType2, otherClass2, thisClassCardinality2, otherClassCardinality2, owner2);

        String output = "Class Name: Test Class\n" +
                "\n" +
                "\n" +
                "Attributes: \n" +
                "color\n" +
                "skin\n" +
                "\n" +
                "Relationships: \n" +
                "\n" +
                "Class has a Association relationship with Other Class 1\n" +
                "Owner: true\n" +
                "This Class Cardinality: 1\n" +
                "Other Class 1 Class Cardinality: *\n" +
                "\n" +
                "Class has a Association relationship with Other Class 2\n" +
                "Owner: true\n" +
                "This Class Cardinality: 1\n" +
                "Other Class 2 Class Cardinality: *\n\n";

        assertEquals(output, this.newClass.toString());

    }
}


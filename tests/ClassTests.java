import Class.Class;
import Diagram.Diagram;
import Relationships.Relationship;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Field;

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
        this.newClass = new Class("Test Class", newDiagram);
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

        this.newClass.addRelationship(relationshipType, otherClass, thisClassCardinality, otherClassCardinality, owner);
        assertEquals("Other Class", this.newClass.getRelationship("Other Class").getOtherClassName());

    }

    @Test
    public void testDeleteRelationship() {

    }

    @Test
    public void testNoRelationshipsInClass() {

    }

    @Test
    public void testDisplayRelationships() {

    }

    @Test
    public void  testDisplayAttributes() {

    }

    @Test
    public void testToString() {

    }
}

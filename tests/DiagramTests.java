import Class.Class;
import Diagram.Diagram;
import Diagram.DiagramCaretaker;
import Diagram.DiagramMemento;
import Relationships.Relationship;
import Controller.MenuController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class DiagramTests {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;

	@BeforeEach
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	@AfterEach
	public void restoreStreams() {
		System.setOut(originalOut);
		System.setErr(originalErr);
	}
	@Test
	void getTitleTest() {
		
		Diagram diagram = new Diagram("test");
		assertEquals("test", diagram.getTitle());
	}
	
	@Test 
    void setTitleTest() {
		Diagram diagram = new Diagram("");
		diagram.setTitle("test");
		assertEquals("test", diagram.getTitle());
	}
	@Test
	void getClassListTest() {
		Diagram diagram = new Diagram("test");
		HashMap<String, Class> hash1 = new HashMap<String, Class>();
		assertEquals(hash1, diagram.getClassList());
		hash1.put("testClass1", new Class("testClass1"));
		diagram.setClassList(hash1);
		assertEquals(hash1, diagram.getClassList());
	}

	@Test
	void setClassListTest() {
		Diagram diagram = new Diagram("test");
		HashMap<String, Class> hash1 = new HashMap<String, Class>();
		hash1.put("testClass1", new Class("testClass1"));
		diagram.setClassList(hash1);
		assertEquals(hash1, diagram.getClassList());
	}

	@Test
	void findSingleClassTest() {
		Diagram d = new Diagram("test");
		assertEquals(null, d.findSingleClass("test"));
		d.addClass("testClass");
		Class c1 = d.getClassList().get("testClass");
		assertEquals(c1, d.findSingleClass("testClass"));
		assertEquals(null, d.findSingleClass(""));
	}
	@Test 
	void nullClassList() {
		Diagram diagram = new Diagram("");
		assertEquals(new HashMap<String, Class>(), diagram.getClassList());
	}

	@Test
	void getSetClassListTest() {
		Diagram diagram = new Diagram("");
		HashMap<String, Class> testClassList = new HashMap<>();
		Class testClass = new Class("test");
		testClassList.put("test", testClass);

		diagram.setClassList(testClassList);

		assertEquals(testClassList, diagram.getClassList());
	}
	
	@Test
	void testAddClass() {
		Diagram UMLDiagram = new Diagram("");
		UMLDiagram.setTitle("test");
		HashMap<String, Class> classList = UMLDiagram.getClassList();
		
		Class c = new Class("testClass");
		
		UMLDiagram.addClass(c.getClassName());
		
		assertEquals(c.getClassName(), UMLDiagram.getClassList().get(c.getClassName()).getClassName());
		UMLDiagram.addClass("testClass");
		assertEquals("Class already exists.\n", outContent.toString().replace("\r\n", "\n"));
	}

	@Test
	void deleteClassTest() {
		Diagram UMLDiagram = new Diagram("");
		UMLDiagram.setTitle("test");
		HashMap<String, Class> classList = UMLDiagram.getClassList();

		Class testClass = new Class("testClass");
		Class testClass2 = new Class("testClass2");

		UMLDiagram.addClass(testClass.getClassName());
		UMLDiagram.addClass(testClass2.getClassName());
		Relationship r = new Relationship(Relationship.RelationshipType.Aggregation, testClass, testClass2, 1, 1, true);
		UMLDiagram.addRelationship(r);

		UMLDiagram.addClass(testClass.getClassName());

		assertEquals(testClass.getClassName(), UMLDiagram.getClassList().get(testClass.getClassName()).getClassName());

		UMLDiagram.deleteClass(testClass);
		assertEquals(new HashMap<>(), UMLDiagram.getRelationshipList());

		assertEquals(null, UMLDiagram.getClassList().get(testClass.getClassName()));
	}

	@Test
	void renameClassTest() {
		Diagram UMLDiagram = new Diagram("");
		UMLDiagram.setTitle("test");
		HashMap<String, Class> classList = UMLDiagram.getClassList();

		Class testClass = new Class("testClass");

		UMLDiagram.addClass(testClass.getClassName());

		assertEquals(testClass.getClassName(), UMLDiagram.getClassList().get(testClass.getClassName()).getClassName());

		String newName = "classTest";
		UMLDiagram.renameClass(testClass, newName);
		assertEquals(newName, UMLDiagram.getClassList().get(newName).getClassName());

		Class wrongClass = new Class("classTestWrong");
		UMLDiagram.renameClass(wrongClass, "newName");
		assertEquals("Class does not exist to rename.\n", outContent.toString().replace("\r\n", "\n"));

		UMLDiagram.renameClass(null, "newName");
		assertEquals("Class does not exist to rename.\nBad Parameters\n", outContent.toString().replace("\r\n", "\n"));
	}

	@Test
	void nullRelationshipList() {
		Diagram diagram = new Diagram("");
		assertEquals(new HashMap<String, Relationship>(), diagram.getRelationshipList());
	}

	@Test
	void getSetRelationshipListTest() {
		Diagram diagram = new Diagram("");
		HashMap<String, Relationship> testRelationshipList = new HashMap<>();
		Class testClass = new Class("testClass");
		Class testClass2 = new Class("testClass2");
		Relationship testRelationship = new Relationship(Relationship.RelationshipType.Composition, testClass,
				testClass2, 1, 1, false);
		testRelationshipList.put("testClasstestClass2", testRelationship);

		diagram.setRelationshipList(testRelationshipList);

		assertEquals(testRelationshipList, diagram.getRelationshipList());
	}

	@Test
	void addRelationshipTest() throws Exception {
		Diagram UMLDiagram = new Diagram("testD");

		Class testClass = new Class("testClass");
		UMLDiagram.addClass(testClass.getClassName());
		Class testClass2 = new Class("testClass2");
		UMLDiagram.addClass(testClass2.getClassName());

		Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, testClass2, 1, 1, true);
		String testRelationshipKey = testClass.getClassName() + testClass2.getClassName();
		UMLDiagram.addRelationship(testRelationship);

		assertEquals(testRelationship, UMLDiagram.getRelationshipList().get(testRelationshipKey));

		MenuController.addRelationship(testClass, testClass, UMLDiagram);
		assertEquals(1, UMLDiagram.getRelationshipList().size());

	}

	@Test
	void deleteRelationshipTest() throws Exception {
		Diagram UMLDiagram = new Diagram("");

		Class testClass = new Class("testClass");
		UMLDiagram.addClass(testClass.getClassName());
		Class testClass2 = new Class("testClass2");
		UMLDiagram.addClass(testClass2.getClassName());

		Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, testClass2, 1, 1, true);
		String testRelationshipKey = testClass.getClassName() + testClass2.getClassName();
		UMLDiagram.addRelationship(testRelationship);

		assertEquals(testRelationship, UMLDiagram.getRelationshipList().get(testRelationshipKey));

		UMLDiagram.deleteRelationship(testClass, testClass2);

		assertEquals(null, UMLDiagram.getRelationshipList().get(testRelationshipKey));
	}

	@Test
	void findSingleRelationshipTest() throws Exception {
		Diagram UMLDiagram = new Diagram("");

		Class testClass = new Class("testClass");
		UMLDiagram.addClass(testClass.getClassName());
		Class testClass2 = new Class("testClass2");
		UMLDiagram.addClass(testClass2.getClassName());
		assertEquals(null, UMLDiagram.findSingleRelationship(testClass, testClass2));
		Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, testClass2, 1, 1, true);
		String testRelationshipKey = testClass.getClassName() + testClass2.getClassName();
		UMLDiagram.addRelationship(testRelationship);

		assertEquals(testRelationship, UMLDiagram.findSingleRelationship(testClass, testClass2));
		assertEquals(testRelationship, UMLDiagram.findSingleRelationship(testClass2, testClass));
	}

	@Test
	void listAllRelationshipsTest() throws Exception {
		Diagram UMLDiagram = new Diagram("");

		Class testClass = new Class("testClass");
		UMLDiagram.addClass(testClass.getClassName());
		Class testClass2 = new Class("testClass2");
		UMLDiagram.addClass(testClass2.getClassName());
		Class testClass3 = new Class("testClass3");
		UMLDiagram.addClass(testClass3.getClassName());

		Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, testClass2, 1, 1, true);
		UMLDiagram.addRelationship(testRelationship);

		Relationship testRelationship2 = new Relationship(Relationship.RelationshipType.Aggregation, testClass, testClass3, 1, 1, true);
		UMLDiagram.addRelationship(testRelationship2);

		Relationship testRelationship3 = new Relationship(Relationship.RelationshipType.Aggregation, testClass2, testClass3, 1, 1, true);
		UMLDiagram.addRelationship(testRelationship3);

		String testStr = "\n--------------------------\n"
				+ "Relationship List: \n\n"
				+ "1: testClass2 has a Aggregation relationship with testClass3\n"
				+ "Owner: true\n"
				+ "testClass2 Class Cardinality: 1\n"
				+ "testClass3 Class Cardinality: 1\n"
				+ "2: testClass has a Aggregation relationship with testClass2\n"
				+ "Owner: true\n"
				+ "testClass Class Cardinality: 1\n"
				+ "testClass2 Class Cardinality: 1\n"
				+ "3: testClass has a Aggregation relationship with testClass3\n"
				+ "Owner: true\n"
				+ "testClass Class Cardinality: 1\n"
				+ "testClass3 Class Cardinality: 1\n"
				+ "\n--------------------------\n";

		assertEquals(testStr, UMLDiagram.listAllRelationships());
	}

	@Test
	void listOneClassRelationshipsTest() {
		Diagram UMLDiagram = new Diagram("");

		Class testClass = new Class("testClass");
		UMLDiagram.addClass(testClass.getClassName());
		Class testClass2 = new Class("testClass2");
		UMLDiagram.addClass(testClass2.getClassName());
		Class testClass3 = new Class("testClass3");
		UMLDiagram.addClass(testClass3.getClassName());

		Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, testClass2, 1, 1, true);
		UMLDiagram.addRelationship(testRelationship);

		Relationship testRelationship2 = new Relationship(Relationship.RelationshipType.Aggregation, testClass, testClass3, 1, 1, true);
		UMLDiagram.addRelationship(testRelationship2);

		Relationship testRelationship3 = new Relationship(Relationship.RelationshipType.Aggregation, testClass3, testClass2, 1, 1, true);
		UMLDiagram.addRelationship(testRelationship3);

		String testStr = "Relationships: \n"
				+ "1: testClass has a Aggregation relationship with testClass3\n"
				+ "Owner: true\n"
				+ "testClass Class Cardinality: 1\n"
				+ "testClass3 Class Cardinality: 1\n"
				+ "2: testClass has a Aggregation relationship with testClass2\n"
				+ "Owner: true\n"
				+ "testClass Class Cardinality: 1\n"
				+ "testClass2 Class Cardinality: 1\n";

		assertEquals(testStr, UMLDiagram.listOneClassRelationships(testClass));
		String testStr2 = "Relationships: \n" +
				"1: testClass3 has a Aggregation relationship with testClass2\n" +
				"Owner: true\n" +
				"testClass3 Class Cardinality: 1\n" +
				"testClass2 Class Cardinality: 1\n" +
				"2: testClass has a Aggregation relationship with testClass2\n" +
				"Owner: true\n" +
				"testClass Class Cardinality: 1\n" +
				"testClass2 Class Cardinality: 1\n";
		assertEquals(testStr2, UMLDiagram.listOneClassRelationships(testClass2));
	}

	@Test
	void getSaveLocationTest() {
		Diagram d = new Diagram("test");
		assertEquals(null, d.getSaveLocation());
	}
	@Test
	void setSaveLocationTest() {
		Diagram d = new Diagram("test");
		String saveLocation = "testLocation";
		d.setSaveLocation("testLocation");
		assertEquals(saveLocation, d.getSaveLocation());

	}

	@Test
	void getSingleClassTest(){
		Diagram d = new Diagram("test");
		d.addClass("testClass");
		assertEquals("testClass", d.getSingleClass("testClass").getClassName());
		assertEquals(null, d.findSingleClass("testClass2"));
	}

	@Test
	void printSingleClassTest() {
		Diagram d = new Diagram("test");

		assertEquals("Class does not exist.", d.printSingleClass(null));
		d.addClass("testClass");
		Class c = d.getSingleClass("testClass");

		String testString = "\n" +
				"--------------------------\n" +
				"Class Name: testClass\n" +
				"--------------------------\n" +
				"Fields: \n" +
				"Methods: \n" +
				"\n" +
				"--------------------------\n" +
				"\n" +
				"--------------------------\n" +
				"Relationships: \n";
		assertEquals(testString, d.printSingleClass(c));
	}

	@Test
	void listClasses() {
		Diagram d = new Diagram("test");
		assertEquals("Diagram is empty.", d.listClasses());

		d.addClass("testClass");
		String testString = "Classes: [\n" +
				"--------------------------\n" +
				"Class Name: testClass\n" +
				"--------------------------\n" +
				"Fields: \n" +
				"Methods: \n" +
				"\n" +
				"--------------------------\n" +
				"]";
		assertEquals(testString, d.listClasses());
	}

	@Test
	void diagramConstructorTest() {
		assertThrows(NullPointerException.class, () ->
				new Diagram((null)));
	}

	@Test
	void toStringDiagramTest() {
		Diagram d = new Diagram("test");
		String testString = "\nDiagram test is empty.\n";
		assertEquals(testString, d.toString());
		d.addClass("testClass");
		testString = "\n" +
				"--------------------------\n" +
				"Diagram: test\n" +
				"\n" +
				"\n" +
				"--------------------------\n" +
				"Class Name: testClass\n" +
				"--------------------------\n" +
				"Fields: \n" +
				"Methods: \n" +
				"\n" +
				"--------------------------\n" +
				"\n" +
				"\n" +
				"--------------------------\n" +
				"Relationship List: \n" +
				"\n" +
				"\n" +
				"--------------------------\n";
		assertEquals(testString, d.toString());
	}

	@Test
	void getSingleClassRelationshipsTest() {
		Diagram d = new Diagram ("test");
		Class c1 = new Class("c1");
		Class c2 = new Class("c2");
		assertEquals(new ArrayList<>(), d.getSingleClassRelationships(c1));
		d.addClass(c1.getClassName());
		d.addClass(c2.getClassName());
		Relationship r = new Relationship(Relationship.RelationshipType.Aggregation, c1, c2, 1, 1, true);
		d.addRelationship(r);
		ArrayList<Relationship> test = new ArrayList<Relationship>();
		test.add(r);
		assertEquals(test, d.getSingleClassRelationships(c1));
		assertEquals(test, d.getSingleClassRelationships(c2));
	}

	@Test
	void createSnapshotTest() {
		Diagram d = new Diagram("test");
		d.createSnapshot();
		DiagramCaretaker dc = d.getCaretaker();
		DiagramMemento dm = new DiagramMemento(d);

		assertNotNull(dc.getDiagramMementoList().get(0));
	}

	@Test
	void applyMementoTest() {
		Diagram d = new Diagram("test");
		DiagramMemento dm = new DiagramMemento(d);
		//d.applyMemento(dm);
		assertEquals(dm.getTitle(), d.getTitle());
		assertEquals(dm.getSaveLocation(), d.getSaveLocation());
		assertEquals(dm.getClassList(), d.getClassList());
		assertEquals(dm.getRelationshipList(), d.getRelationshipList());
	}

	@Test
	void getCoordinatesTest() {
		Diagram d = new Diagram("test");
		d.setCoordinates(null);
		assertEquals(null, d.getCoordinates());
	}
}
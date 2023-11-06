import Class.Class;
import Diagram.Diagram;
import Relationships.Relationship;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiagramTests {
	
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
	void nullClassList() {
		Diagram diagram = new Diagram("");
		assertEquals(new HashMap<String, Class>(), diagram.getClassList());
	}
	
	@Test
	void testAddClass() {
		Diagram UMLDiagram = new Diagram("");
		UMLDiagram.setTitle("test");
		HashMap<String, Class> classList = UMLDiagram.getClassList();
		
		Class c = new Class("testClass");
		
		UMLDiagram.addClass(c.getClassName());
		
		assertEquals(c.getClassName(), UMLDiagram.getClassList().get(c.getClassName()).getClassName());
		
	}

	@Test
	void nullRelationshipList() {
		Diagram diagram = new Diagram("");
		assertEquals(new HashMap<String, Relationship>(), diagram.getRelationshipList());
	}

	@Test
	void addRelationshipTest() throws Exception {
		Diagram UMLDiagram = new Diagram("");

		Class testClass = new Class("testClass");
		UMLDiagram.addClass(testClass.getClassName());
		Class testClass2 = new Class("testClass2");
		UMLDiagram.addClass(testClass2.getClassName());

		Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, testClass2, 1, 1, true);
		String testRelationshipKey = testClass.getClassName() + testClass2.getClassName();
		UMLDiagram.addRelationship(testRelationship);

		assertEquals(testRelationship, UMLDiagram.getRelationshipList().get(testRelationshipKey));

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

		String testStr = "Relationship List: \n"
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
				+ "testClass3 Class Cardinality: 1\n";

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

		Relationship testRelationship3 = new Relationship(Relationship.RelationshipType.Aggregation, testClass2, testClass3, 1, 1, true);
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
	}
}
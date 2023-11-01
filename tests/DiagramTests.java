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
		
		assertEquals(c, classList.get(c.getClassName()).getClass());
		
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
}

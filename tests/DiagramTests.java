import Diagram.Diagram;
import Relationships.Relationship;
import org.junit.jupiter.api.Test;
import Class.Class;

import java.io.InputStream;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

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
	/*
	@Test
	void addRelationshipTest() {
		Diagram UMLDiagram = new Diagram("");
		UMLDiagram.setTitle("test");
		HashMap<String, Class> classList = UMLDiagram.getClassList();

		Class testClass = new Class("testClass");
		UMLDiagram.addClass(testClass.getClassName());
		Class testClass2 = new Class("testClass2");
		UMLDiagram.addClass(testClass2.getClassName());

		Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, testClass2, 1, 1, false);
		UMLDiagram.addRelationship(testClass, testClass2);

	}
	*/
	@Test
	void addRelationshipUnitTest() throws Exception {
		Diagram UMLDiagram = new Diagram("");

		Class testClass = new Class("testClass");
		UMLDiagram.addClass(testClass.getClassName());
		Class testClass2 = new Class("testClass2");
		UMLDiagram.addClass(testClass2.getClassName());

		Relationship testRelationship = new Relationship(Relationship.RelationshipType.Aggregation, testClass, testClass2, 1, 1, true);

		String string = "2";
		InputStream stringStream = new java.io.ByteArrayInputStream(string.getBytes());
		string = "1";
		stringStream = new java.io.ByteArrayInputStream(string.getBytes());
		stringStream = new java.io.ByteArrayInputStream(string.getBytes());
		stringStream = new java.io.ByteArrayInputStream(string.getBytes());
	}
}

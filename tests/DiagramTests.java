import Diagram.Diagram;
import org.junit.jupiter.api.Test;
import Class.Class;
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

	@Test void testAddClass() {
	Diagram diagram = new Diagram("");
	diagram.setTitle("test");
	diagram.addClass("testClass");
	Class c = diagram.getClassList().get("testClass");
	assertEquals(c, diagram.getClassList().get(c.getClassName()));
	}

	@Test
	void deleteClassTest() {
		Diagram diagram = new Diagram("testDiagram");
		diagram.addClass("testClass");

		diagram.deleteClass(diagram.getClassList().get("testClass"));

		assertEquals(null, diagram.getClassList().get("testClass"));
		//need to also test relationships being deleted

	}

	@Test
	void renameClassTest() {
		Diagram d = new Diagram("testD");
		d.addClass("test");
		d.renameClass(d.getClassList().get("test"), "newName");
		assertEquals("newName", d.getClassList().get("newName").getClassName());
	}

	@Test
	void findSingleClassTest() {
		Diagram d = new Diagram("test");
		assertEquals(null, d.findSingleClass("test"));
		d.addClass("testClass");
		Class c1 = d.getClassList().get("testClass");
		assertEquals(c1, d.findSingleClass("testClass"));
	}

	@Test
	void addRelationshipTest() {

	}

	@Test 
	void deleteRelationshipTest() {

	}

	@Test 
	void nullClassList() {
		Diagram diagram = new Diagram("");
		assertEquals(new HashMap<>(), diagram.getClassList());
	}
	

}

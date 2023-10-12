import Diagram.Diagram;
import org.junit.jupiter.api.Tests;
import org.w3c.dom.Attr;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DiagramTests {
	
	@Test
	void getTitleTest() {
		
		Diagram diagram = new Diagram("test", null);
		assertEquals("test", diagram.getName());
	}
	
	@Test 
    void setTitleTest() {
		Diagram diagram = new Diagram();
		diagram.setTitle("test");
		assertEquals("test", diagram.getTitle());
	}
	
	@Test nullClassList() {
		Diagram diagram = new Diagram();
		assertEquals(new ArrayList<>(), diagram.getClassList())
	}
	
	@Test void testAddClass() {
		Diagram UMLdiagram = new Diagram();
		UMLdiagram.setTitle("test");
		classList = UMLDiagram.getDiagram();
		
		Class c = new Class("testClass");
		
		classList.addClass("testClass");
		
		assertEquals(c, classList.get(0));
		
	}
}

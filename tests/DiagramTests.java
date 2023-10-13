import Diagram.Diagram;
import org.junit.jupiter.api.Test;
import Class.Class;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class DiagramTests {
	
	@Test
	void getTitleTest() {
		
		Diagram diagram = new Diagram("test", null);
		assertEquals("test", diagram.getTitle());
	}
	
	@Test 
    void setTitleTest() {
		Diagram diagram = new Diagram("", null);
		diagram.setTitle("test");
		assertEquals("test", diagram.getTitle());
	}
	
	@Test 
	void nullClassList() {
		Diagram diagram = new Diagram("", null);
		assertEquals(new ArrayList<>(), diagram.getClassList());
	}
	
	@Test void testAddClass() {
		Diagram UMLDiagram = new Diagram("", null);
		UMLDiagram.setTitle("test");
		List<Class> classList = UMLDiagram.getClassList();
		
		Class c = new Class("testClass");
		
		UMLDiagram.addClass(c.getClassName());
		
		assertEquals(c.getClass(), classList.get(0).getClass());
		
	}
}

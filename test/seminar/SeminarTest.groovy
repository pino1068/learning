package seminar;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

class SeminarTest {

	@Test
	void noSeminar() {
		assertEquals("no seminar", new SeminarUI().toString())
	}
	
	@Test
	void viewSeminar() {
		def seminar = new Seminar(
			location:"Lugano",
			course:new Course(name:"math", number:3, description:"Mathematics"), 
			seats:10)
		seminar.enroll(new Student(name:"Alessandro", surname:"Misenta"))
		seminar.enroll(new Student(name:"Giuseppe", surname:"Di Pierri"))
		
		assertEquals(
"""
	------------ math (3) ------------
	Mathematics in Lugano
	seats left: 8
	Students:
		Alessandro Misenta
		Giuseppe Di Pierri
""", view(seminar))
	}

	private String view(Seminar seminar) {
		new SeminarUI(seminar:seminar).toString()
	}
}

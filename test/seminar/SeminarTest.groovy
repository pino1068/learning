package seminar;

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Ignore
import org.junit.Test

class SeminarTest {
	def ui
	
	@Before
	void setup(){
		ui = new SeminarUI()
	}

	@Test
	void noSeminar() {
		assertEquals("no seminar", new SeminarUI().text)
	}
	
	@Test
	void seminarText() {
		def seminar = new Seminar(
			where: new Location(name:"Lugano",seats:10),
			course:new Course(name:"math", number:3, description:"Mathematics"), 
			)
		seminar.enroll(new Student(name:"Alessandro", surname:"Misenta"))
		seminar.enroll(new Student(name:"Giuseppe", surname:"Di Pierri"))
		
		ui << seminar
		
		assertEquals(
"""
	------------ math (3) ------------
	Mathematics in Lugano
	seats left: 8
	Students:
		Alessandro Misenta
		Giuseppe Di Pierri""", ui.text)
	}
	
	@Test
	void moreSeminarsText() {
		def seminar = new Seminar(
			where: new Location(name:"Lugano",seats:10),
			course:new Course(name:"math", number:3, description:"Mathematics"), 
				)
		seminar.enroll(new Student(name:"Alessandro", surname:"Misenta"))
		seminar.enroll(new Student(name:"Giuseppe", surname:"Di Pierri"))
		
		ui << seminar
		ui << seminar
		
		assertEquals(
"""
	------------ math (3) ------------
	Mathematics in Lugano
	seats left: 8
	Students:
		Alessandro Misenta
		Giuseppe Di Pierri

	------------ math (3) ------------
	Mathematics in Lugano
	seats left: 8
	Students:
		Alessandro Misenta
		Giuseppe Di Pierri""", ui.text)
	}
}

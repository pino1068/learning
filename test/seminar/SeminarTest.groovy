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
		def seminar = createSeminar()
		
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

	private Seminar createSeminar() {
		def seminar = new Seminar(
				where: new Location(name:"Lugano",seats:10),
				course:new Course(name:"math", number:3, description:"Mathematics"),
				)
		seminar.enroll(new Student(name:"Alessandro", surname:"Misenta"))
		seminar.enroll(new Student(name:"Giuseppe", surname:"Di Pierri"))
		return seminar
	}
	
	@Test 
	void htmlVersion(){
		def seminar = createSeminar()
		
		ui << seminar
		ui << seminar
		
		assertEquals(
"""<html>
  <head>
    <title>Your Title Here</title>
  </head>
  <body BGCOLOR='FFFFFF'>
    <h1>Seminars</h1>
    <h2>math (3) in Lugano</h2>
    <p>8 seats left</p>
    <p>
      <b>Students are:</b>
    </p>
    <ul>
      <li>Alessandro Misenta</li>
      <li>Giuseppe Di Pierri</li>
    </ul>
    <h2>math (3) in Lugano</h2>
    <p>8 seats left</p>
    <p>
      <b>Students are:</b>
    </p>
    <ul>
      <li>Alessandro Misenta</li>
      <li>Giuseppe Di Pierri</li>
    </ul>
  </body>
</html>""", ui.html)
	
	}
}

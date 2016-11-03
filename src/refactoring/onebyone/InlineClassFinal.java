package refactoring.onebyone;

import static org.junit.Assert.*;

import org.junit.Test;

public class InlineClassFinal {

	class Person {
		public Person(String name) {
			_name = name;
		}
		
		public void setAreaCode(String areaCode){
			_areaCode = areaCode;
		}
		
		public void setNumber(String number) {
			_number = number;
		}

		public String getName() {
			return _name;
		}

		public String getTelephoneNumber() {
			return ("(" + _areaCode + ") " + _number);
		}

		private String _name;
		private String _number;
		private String _areaCode;
	}

	@Test
	public void scenarios(){
		Person person = new Person("pippo");
		person.setAreaCode("044");
		person.setNumber("7777");
		
		assertEquals("(044) 7777", person.getTelephoneNumber());
	}
}

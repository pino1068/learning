package refactoring.onebyone;

import static org.junit.Assert.*;

import org.junit.Test;

public class InlineClassOriginal {

	class Person {
		public Person(String name, TelephoneNumber aTelephoneNumber) {
			_name = name;
			_officeTelephone = aTelephoneNumber;
		}

		public String getName() {
			return _name;
		}

		public String getTelephoneNumber() {
			return _officeTelephone.getTelephoneNumber();
		}

		private String _name;
		private TelephoneNumber _officeTelephone = new TelephoneNumber("","");
	}

	class TelephoneNumber {
		private String _number;
		private String _areaCode;

		public TelephoneNumber(String area, String nr) {
			_areaCode = area;
			_number = nr;
		}

		public String getTelephoneNumber() {
			return ("(" + _areaCode + ") " + _number);
		}

		String getAreaCode() {
			return _areaCode;
		}

		void setAreaCode(String arg) {
			_areaCode = arg;
		}

		String getNumber() {
			return _number;
		}

		void setNumber(String arg) {
			_number = arg;
		}
	}
	
	@Test
	public void scenarios(){
		assertEquals("(091) 666", new Person("pippo", new TelephoneNumber("091","666")).getTelephoneNumber());
	}

}

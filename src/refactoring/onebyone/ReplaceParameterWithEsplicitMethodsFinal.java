package refactoring.onebyone;
import static org.junit.Assert.*;
import static refactoring.onebyone.ReplaceParameterWithEsplicitMethodsFinal.Employee.*;

import org.junit.Test;

public class ReplaceParameterWithEsplicitMethodsFinal {

	static abstract class Employee {
		private static Manager createManager() {
			return new ReplaceParameterWithEsplicitMethodsFinal().new Manager();
		}

		private static Salesman createSalesman() {
			return new ReplaceParameterWithEsplicitMethodsFinal().new Salesman();
		}

		private static Engineer createEngineer() {
			return new ReplaceParameterWithEsplicitMethodsFinal().new Engineer();
		}
		
		abstract int getValue();
	}

	class Engineer extends Employee {
		@Override int getValue() {return 10;}
	}

	class Salesman extends Employee {
		@Override int getValue() {return 5;}
	}

	class Manager extends Employee {
		@Override int getValue() {return 1;}
	}
	
	@Test
	public void scenarios(){
		assertEquals(10, Employee.createEngineer().getValue());
		assertEquals(5, Employee.createSalesman().getValue());
		assertEquals(1, Employee.createManager().getValue());
	}
}

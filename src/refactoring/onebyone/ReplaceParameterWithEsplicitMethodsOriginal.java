package refactoring.onebyone;
import static org.junit.Assert.*;
import static refactoring.onebyone.ReplaceParameterWithEsplicitMethodsOriginal.Employee.*;

import org.junit.Test;

public class ReplaceParameterWithEsplicitMethodsOriginal {

	static abstract class Employee {
		static final int ENGINEER = 0;
		static final int SALESMAN = 1;
		static final int MANAGER = 2;

		public static Employee create(int type) {
			switch (type) {
			case ENGINEER:
				return new ReplaceParameterWithEsplicitMethodsOriginal().new Engineer();
			case SALESMAN:
				return new ReplaceParameterWithEsplicitMethodsOriginal().new Salesman();
			case MANAGER:
				return new ReplaceParameterWithEsplicitMethodsOriginal().new Manager();
			default:
				throw new IllegalArgumentException("Incorrect type code value");
			}
		}
		
		abstract int getValue();
	}

	class Engineer extends Employee {
		@Override
		int getValue() {
			return 10;
		}
	}

	class Salesman extends Employee {
		@Override
		int getValue() {
			return 5;
		}
	}

	class Manager extends Employee {
		@Override
		int getValue() {
			return 1;
		}
	}
	
	@Test
	public void scenarios(){
		assertEquals(10, Employee.create(ENGINEER).getValue());
		assertEquals(5, Employee.create(SALESMAN).getValue());
		assertEquals(1, Employee.create(MANAGER).getValue());
	}
}

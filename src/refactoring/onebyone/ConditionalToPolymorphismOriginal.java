package refactoring.onebyone;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConditionalToPolymorphismOriginal {

	class Employee {
		private EmployeeType _type;
		private int _monthlySalary, _commission, _bonus;

		public Employee(EmployeeType type, int sal, int com, int bon) {
			_type = type;
			_monthlySalary = sal;
			_commission = com;
			_bonus = bon;
		}

		int payAmount() {
			switch (_type) {
			case ENGINEER:
				return _monthlySalary;
			case SALESMAN:
				return _monthlySalary + _commission;
			case MANAGER:
				return _monthlySalary + _bonus;
			default:
				throw new RuntimeException("Incorrect Employee");
			}
		}
	}

	enum EmployeeType {
		ENGINEER, SALESMAN, MANAGER
	}
	
	@Test
	public void scenarios(){
		assertEquals(1, new Employee(EmployeeType.ENGINEER, 1, 2, 4).payAmount());
		assertEquals(3, new Employee(EmployeeType.SALESMAN, 1, 2, 4).payAmount());
		assertEquals(5, new Employee(EmployeeType.MANAGER, 1, 2, 4).payAmount());
	}
}

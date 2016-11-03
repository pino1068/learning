package refactoring.onebyone;

import static org.junit.Assert.*;

import org.junit.Test;

public class CollapseHirarchyOriginal {
	class Employee {
		private EmployeeType _type;
		private int _monthlySalary, _commission, _bonus;

		public int getMonthlySalary() {
			return _monthlySalary;
		}

		public int getCommission() {
			return _commission;
		}

		public int getBonus() {
			return _bonus;
		}

		public Employee(EmployeeType type, int sal, int com, int bon) {
			_type = type;
			_monthlySalary = sal;
			_commission = com;
			_bonus = bon;
		}

		int payAmount() {
			return _type.payAmount(this);
		}
	}

	abstract class EmployeeType {
		public int payAmount(Employee e){
			return e.getMonthlySalary() + e.getCommission();
		}
	}

	class Engineer extends EmployeeType {
		@Override
		public int payAmount(Employee e) {
			return e.getMonthlySalary();
		}
	}

	class Salesman extends EmployeeType {
		@Override
		public int payAmount(Employee e) {
			return e.getMonthlySalary() + e.getCommission();
		}
	}

	class Manager extends EmployeeType {
		@Override
		public int payAmount(Employee e) {
			return e.getMonthlySalary() + e.getBonus();
		}
	}

	@Test
	public void scenarios() {
		assertEquals(1, new Employee(new Engineer(), 1, 2, 4).payAmount());
		assertEquals(3, new Employee(new Salesman(), 1, 2, 4).payAmount());
		assertEquals(5, new Employee(new Manager(), 1, 2, 4).payAmount());
	}
}

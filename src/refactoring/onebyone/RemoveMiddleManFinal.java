package refactoring.onebyone;
import static org.junit.Assert.*;

import org.junit.Test;

public class RemoveMiddleManFinal {

	class Hierarchy{
		Person member;

		public Hierarchy(Person member) {
			this.member = member;
		}

		public Person getManager() {
			return member.dept.manager;
		}
	}
	
	class Person{
		Department dept;
		String name;

		public Person(String name) {
			this.name = name;
		}

		public Person(Department dept) {
			this.dept = dept;
		}
	}
	
	class Department{
		Person manager;
		public Department(Person manager) {
			this.manager = manager;
		}
	}
	
	@Test
	public void scenario(){
		Person manager = new Person("boss");
		Department sales = new Department(manager);
		Person member = new Person(sales);
		Hierarchy org = new Hierarchy(member );
		
		assertEquals("boss", org.getManager().name);
	}
}

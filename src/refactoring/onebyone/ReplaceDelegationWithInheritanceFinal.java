package refactoring.onebyone;

import static org.junit.Assert.*;

import org.junit.Test;

public class ReplaceDelegationWithInheritanceFinal {

	class Employee extends Person{
		public Employee(String name) {super(name);}
		public String getName() {return name;}
		
	}
	class Person{
		String name;
		public Person(String name) {this.name = name;}
	}
	
	@Test
	public void scenario(){
		Employee e = new Employee("giovanni");
		
		assertEquals("giovanni", e.getName());
	}
}

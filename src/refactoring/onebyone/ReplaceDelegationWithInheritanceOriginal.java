package refactoring.onebyone;

import static org.junit.Assert.*;

import org.junit.Test;

public class ReplaceDelegationWithInheritanceOriginal {

	class Employee{
		private Person person;
		public Employee(String name) {this.person = new Person(name);}
		public String getName() {return person.name;}
		
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

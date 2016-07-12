package refactoring;

import static org.junit.Assert.*;

import org.junit.Test;

public class RentalTest {

	@Test
	public void test() {
		def c = new Customer("pino");
		
		assertEquals("""Rental Record for pino
Amount owed is 0.0
You earned 0 frequent renter points""", c.statement())
	}

}

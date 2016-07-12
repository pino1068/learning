package refactoring;

import static org.junit.Assert.*;

import org.junit.Test;

public class RentalTest {

	@Test
	public void empty() {
		def c = new Customer("pino");
		
		assertEquals(
"""Rental Record for pino
Amount owed is 0.0
You earned 0 frequent renter points""", c.statement())
	}
	
	@Test
	void "rent some movies for a few days"() {
		def c = new Customer("pino");
		c.addRental(new Rental(new Movie("KPax", Movie.REGULAR), 10))
		c.addRental(new Rental(new Movie("L.A. Confidential", Movie.REGULAR), 15))
		
		assertEquals(
"""Rental Record for pino
	KPax	14.0
	L.A. Confidential	21.5
Amount owed is 35.5
You earned 2 frequent renter points""", c.statement())
	}

}

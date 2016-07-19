package refactoring;
import static org.junit.Assert.*;
import static refactoring.Lines.*;

import org.junit.Test;

public class RentalTest {

	@Test
	public void empty() {
		Customer c = new Customer("pino");
		
		assertEquals(
_("Rental Record for pino")
.add("Amount owed is 0.0")
.add("You earned 0 frequent renter points").toString(), c.statement());
	}
	
	@Test
	public void rent_some_movies_for_a_few_days() {
		Customer c = new Customer("pino");
		c.addRental(new Rental(new Movie("KPax", Movie.REGULAR), 10));
		c.addRental(new Rental(new Movie("L.A. Confidential", Movie.REGULAR), 15));
		
		assertEquals(
_("Rental Record for pino")
.indent("KPax	14.0")
.indent("L.A. Confidential	21.5")
.add("Amount owed is 35.5")
.add("You earned 2 frequent renter points").toString(), c.statement());
	}
}

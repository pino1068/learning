package refactoring.onebyone;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BidirectionalToUniDirectionalOriginal {

	class Order {
		private Customer customer;
		private String description;
		private int grossPrice;

		public Order(Customer customer, String description, int price) {
			this.customer = customer;
			this.description = description;
			this.grossPrice = price;
		}

		public double getPrice() {
			return grossPrice * (1 - customer.discount);
		}

		public Customer getCustomer() {
			return customer;
		}

	}

	class Customer {
		private double discount;

		public Customer(String name, double discount) {
			this.name = name;
			this.discount = discount;
		}

		String name;
	}

	@Test
	public void scenario() {
		Customer c = new Customer("giovanni", .2);
		Order o = new Order(c, "book's author: Arielle Ford", 10);

		assertEquals(8, o.getPrice(), 0.1);
		assertEquals(c, o.getCustomer());
		//order price for all customers?
	}
}

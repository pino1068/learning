package refactoring.onebyone;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class BidirectionalToUniDirectionalFinal {

	class Order {
		private int grossPrice;

		public Order(String description, int price) {
			this.grossPrice = price;
		}

		public double getPrice(Customer c) {
			return grossPrice * (1 - c.discount);
		}

		public Customer getCustomer() {
			for (Customer c: Customer.customers) {
				if(c.has(this))
					return c;
			}
			return null;
		}

	}

	static class Customer {
		private double discount;
		private List<Order> orders = new ArrayList<Order>();
		static private final List<Customer> customers = new ArrayList<Customer>();
		
		public Customer(String name, double discount) {
			this.name = name;
			this.discount = discount;
			customers.add(this);
		}
		
		public boolean has(Order order) {
			return orders.contains(order);
		}

		double getPriceFor(Order o){
			return o.getPrice(this);
		}

		String name;

		public void add(Order o) {
			this.orders.add(o);
		}
	}

	@Test
	public void scenario() {
		Customer c = new Customer("giovanni", .2);
		Order o = new Order("book's author: Arielle Ford", 10);
		c.add(o);
		
		assertEquals(8, o.getPrice(c), 0.1);
		assertEquals(c, o.getCustomer());
		assertEquals(8, c.getPriceFor(o), 0.1);
	}
}

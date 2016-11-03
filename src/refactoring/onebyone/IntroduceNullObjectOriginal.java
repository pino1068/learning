package refactoring.onebyone;

import static org.junit.Assert.*;

import org.junit.Test;

public class IntroduceNullObjectOriginal {
	class Site {
		Customer customer;

		Customer getCustomer() {
			return customer;
		}

		public BillingPlan getPlan() {
			BillingPlan plan;
			if (customer == null)
				plan = BASIC;
			else
				plan = customer.getPlan();
			return plan;
		}

		public String getCustomerName() {
			String customerName;
			if (customer == null)
				customerName = "occupant";
			else
				customerName = customer.getName();
			return customerName;
		}

		public int getWeeksDelinquent() {
			int weeksDelinquent;
			if (customer == null)
				weeksDelinquent = 0;
			else
				weeksDelinquent = customer.getHistory()
						.getWeeksDelinquentInLastYear();
			return weeksDelinquent;
		}
	}

	class Customer {
		String name;
		BillingPlan plan;
		PaymentHistory history;

		public String getName() {
			return name;
		}

		public BillingPlan getPlan() {
			return plan;
		}

		public PaymentHistory getHistory() {
			return history;
		}
	}

	class BillingPlan {
	}

	static final BillingPlan BASIC = new IntroduceNullObjectOriginal().new BillingPlan();

	class PaymentHistory {
		private int weeksDelinquentInLastYear;

		public PaymentHistory(int weeksDelinquentInLastYear) {
			this.weeksDelinquentInLastYear = weeksDelinquentInLastYear;
		}

		int getWeeksDelinquentInLastYear() {
			return weeksDelinquentInLastYear;
		}
	}

	@Test
	public void scenariosNull() {
		assertEquals(BASIC, new Site().getPlan());
		assertEquals("occupant", new Site().getCustomerName());
		assertEquals(0, new Site().getWeeksDelinquent());
	}

	@Test
	public void scenariosNormal() {
		Site site = new Site();
		site.customer = new Customer();
		site.customer.name = "pippo";
		BillingPlan billingPlan = new BillingPlan();
		site.customer.plan =  billingPlan;
		site.customer.history = new PaymentHistory(10);
		
		assertNotEquals(billingPlan, BASIC);
		assertEquals(billingPlan, site.getPlan());
		assertEquals("pippo", site.getCustomerName());
		assertEquals(10, site.getWeeksDelinquent());
	}
}

package refactoring.onebyone;

import static org.junit.Assert.*;

import org.junit.Test;

public class IntroduceNullObjectFinal {
	class Site {
		Customer customer = new NullCustomer();

		Customer getCustomer() {return customer;}
		public BillingPlan getPlan() {return customer.getPlan();}
		public String getCustomerName() {return customer.getName();}
		public int getWeeksDelinquent() {return customer.getHistory().getWeeksDelinquentInLastYear();}
	}

	class Customer {
		String name;
		BillingPlan plan;
		PaymentHistory history;

		public String getName() {return name;}
		public BillingPlan getPlan() {return plan;}
		public PaymentHistory getHistory() {return history;}
	}
	
	class NullCustomer extends Customer{
		public String getName(){return "occupant";}
		public BillingPlan getPlan() {return BASIC;}
		public PaymentHistory getHistory() {return new PaymentHistory(0);}
	}

	class BillingPlan {}

	static final BillingPlan BASIC = new IntroduceNullObjectFinal().new BillingPlan();

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

package refactoring.onebyone;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MoveFieldFinal {

	class AccountType {
		private int _interestRate;

		public AccountType(int rate) {
			_interestRate = rate;
		}

		public int getInterestRate() {
			return _interestRate;
		}

		public void setInterestRate(int aInterestRate) {
			_interestRate = aInterestRate;
		}
	}

	class Account {
		private AccountType _type;

		public Account(AccountType type) {
			_type = type;
		}

		double interestForAmount_days(double amount, int days) {
			return _type._interestRate * amount * days / 365;
		}
	}

	@Test
	public void scenario() {
		assertEquals(5.47,
				new Account(new AccountType(4))
						.interestForAmount_days(50.0, 10), 0.01);
	}
}

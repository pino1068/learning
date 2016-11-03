package refactoring.onebyone;
import static org.junit.Assert.*;
import org.junit.Test;

public class MoveMethodsFinal {

	class AccountType {
		private static final double NORMAL_OVERDRAFT_DAILY_RATE = 1.75;
		private static final double EXTRA_DAILY_CHARGE_OVER_ONE_WEEK = 0.85;
		private static final int PREMIUM_OVERDRAFT_RATE_WITHIN_ONE_WEEK = 10;
		private static final int WEEK = 7;
		private boolean premium;

		public AccountType(boolean premium){
			this.premium = premium;
		}
		
		public double overdraftFor(Account account) {
			int _days = account.getDaysOverdrawn();
			int extraDays = _days - WEEK;
			if (premium) {
				double result = PREMIUM_OVERDRAFT_RATE_WITHIN_ONE_WEEK;
				if (extraDays > 0) 
					result += extraDays * EXTRA_DAILY_CHARGE_OVER_ONE_WEEK;
				return result;
			} else
				return _days * NORMAL_OVERDRAFT_DAILY_RATE;
		}
	}

	class Account {
		private AccountType _type;
		private int _daysOverdrawn;
		
		public Account(AccountType type, int daysOverdrawn) {
			_type = type;
			_daysOverdrawn = daysOverdrawn;
		}

		public int getDaysOverdrawn() {
			return _daysOverdrawn;
		}

		double bankCharge() {
			double result = 4.5;
			if (_daysOverdrawn > 0)
				result += _type.overdraftFor(this);
			return result;
		}
	}
	
	@Test
	public void scenarios(){
		assertEquals(14.5, 	new Account(new AccountType(true), 	4).bankCharge(), 0.0);
		assertEquals(21.3, 	new Account(new AccountType(true), 	15).bankCharge(), 0.0);
		assertEquals(11.5, 	new Account(new AccountType(false), 4).bankCharge(), 0.0);
		assertEquals(30.75, new Account(new AccountType(false), 15).bankCharge(), 0.0);
	}
}

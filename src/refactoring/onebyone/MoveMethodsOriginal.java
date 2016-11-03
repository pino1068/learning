package refactoring.onebyone;
import static org.junit.Assert.*;

import org.junit.Test;

import refactoring.onebyone.MoveMethodsFinal.Account;
import refactoring.onebyone.MoveMethodsFinal.AccountType;

public class MoveMethodsOriginal {

	class AccountType {

		private boolean premium;

		public AccountType(boolean premium){
			this.premium = premium;
		}
		
		public boolean isPremium() {
			return premium;
		}
	}

	class Account {
		
		public Account(AccountType type, int daysOverdrawn) {
			_type = type;
			_daysOverdrawn = daysOverdrawn;
		}

		double overdraftCharge() {
			if (_type.isPremium()) {
				double result = 10;
				if (_daysOverdrawn > 7)
					result += (_daysOverdrawn - 7) * 0.85;
				return result;
			} else
				return _daysOverdrawn * 1.75;
		}

		double bankCharge() {
			double result = 4.5;
			if (_daysOverdrawn > 0)
				result += overdraftCharge();
			return result;
		}

		private AccountType _type;
		private int _daysOverdrawn;
	}
	
	@Test
	public void scenario(){
		assertEquals(14.5, 	new Account(new AccountType(true), 	4).bankCharge(), 0.0);
		assertEquals(21.3, 	new Account(new AccountType(true), 	15).bankCharge(), 0.0);
		assertEquals(11.5, 	new Account(new AccountType(false), 4).bankCharge(), 0.0);
		assertEquals(30.75, new Account(new AccountType(false), 15).bankCharge(), 0.0);
	}
}

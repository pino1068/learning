package refactoring.onebyone;
import static org.junit.Assert.*;

import org.junit.Test;

import refactoring.onebyone.MoveMethodsFinal.Account;
import refactoring.onebyone.MoveMethodsFinal.AccountType;

public class MoveFieldOriginal {

	class Account {
		private int _interestRate;
		
		public Account(int interestRate) {
			_interestRate = interestRate;
		}
		double interestForAmount_days (double amount, int days) {
			 return _interestRate * amount * days / 365;
		 }
	}
	
	@Test
	public void scenario(){
		assertEquals(5.47, 	new Account(4).interestForAmount_days(50.0, 10), 0.01);
	}
}

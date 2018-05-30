
package logic;

import com.ferrari.finances.dk.bank.InterestRate;

public class Bank {
	// gets the current rate from the bank
	public Double getCurrentRate() {

		return InterestRate.i().todaysRate();

	}

}
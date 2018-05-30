
package logic;

import com.ferrari.finances.dk.bank.InterestRate;

public class Bank {

	public Double getCurrentRate() {

		return InterestRate.i().todaysRate();

	}

}
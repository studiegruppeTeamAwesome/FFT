
package logic;

import com.ferrari.finances.dk.bank.InterestRate;

public class Bank {

	private static Bank inst = null;

	private Bank() {
	}

	public static Bank instance() {

		if (inst == null)

			inst = new Bank();

		return inst;

	}

	public Double getCurrentRate() {

		return InterestRate.i().todaysRate();

	}

}
package logic;

import com.ferrari.finances.dk.rki.CreditRator;

public class RKI {

	public void setCreditRating(Customer customer) {

		customer.setRating(CreditRator.i().rate(customer.getCPR()));

	}

}
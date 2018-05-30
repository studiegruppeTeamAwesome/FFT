package logic;

import com.ferrari.finances.dk.rki.CreditRator;

public class RKI {
	//Sets the credit rating gotten from RKI on the customer
	public void setCreditRating(Customer customer) {

		customer.setRating(CreditRator.i().rate(customer.getCPR()));

	}

}
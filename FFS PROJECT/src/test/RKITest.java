package test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ferrari.finances.dk.rki.Rating;

import logic.Customer;
import logic.RKI;

public class RKITest { //ansvar:Martin review Shahnaz

	@Test
	public void testCustomerRKIRating() {
		Customer customer = new Customer();
		RKI rki = new RKI();
		customer.setCPR("1511859999");
		rki.setCreditRating(customer);
		System.out.println(customer.getRating()); // for at kende rating
		assertEquals(Rating.B, customer.getRating());
	}

}

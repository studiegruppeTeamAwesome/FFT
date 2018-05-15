package test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ferrari.finances.dk.rki.Rating;

import logic.Customer;
import logic.RKI;

public class RKITest {

	@Test
	public void testCustomerRKIRating() {
		Customer customer = new Customer();
		customer.setCPR("1511859999");
		RKI.instance().setCreditRating(customer);
		System.out.println(customer.getRating()); // for at kende rating
		assertEquals(Rating.B, customer.getRating());
	}

}

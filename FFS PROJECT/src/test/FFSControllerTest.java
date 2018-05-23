package test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ferrari.finances.dk.rki.Rating;

import logic.FFSController;
import logic.FacadeController;
import logic.PoorCreditRatingException;

public class FFSControllerTest {

	@Test
	public void calcInterestRatingATest() throws PoorCreditRatingException {
		FacadeController cont = new FFSController();
		double currentRate = 5.0;
		Rating rate = Rating.A;
		int downPayment = 250000;
		int numberOfMonts = 60;
		int carPrice = 1000000;

		assertEquals(8.0, cont.calculateInterestRate(rate, currentRate, downPayment, numberOfMonts, carPrice), 0);
	}
}
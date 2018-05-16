package test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ferrari.finances.dk.rki.Rating;

import logic.BadCreditRatingException;
import logic.InterestCalculator;

public class InterestCalculatorTest {

	@Test
	public void calcInterestRateTest() throws BadCreditRatingException {
		InterestCalculator intcalc = new InterestCalculator();
		double currentRate = 5.0;
		Rating rate = Rating.A;
		int downPayment = 250000;
		int numberOfMonts = 60;
		int carPrice = 1000000;
		
		assertEquals(8.0,intcalc.calcInterestRate(rate, currentRate, downPayment, numberOfMonts, carPrice),0);
	}
	
	@Test
	public void calcInterestRateTest2() throws BadCreditRatingException {
		InterestCalculator intcalc = new InterestCalculator();
		double currentRate = 5.0;
		Rating rate = Rating.B;
		int downPayment = 250000;
		int numberOfMonts = 60;
		int carPrice = 1000000;
		
		assertEquals(9.0,intcalc.calcInterestRate(rate, currentRate, downPayment, numberOfMonts, carPrice),0);
	}
	
	@Test
	public void calcInterestRateTest3() throws BadCreditRatingException {
		InterestCalculator intcalc = new InterestCalculator();
		double currentRate = 5.0;
		Rating rate = Rating.C;
		int downPayment = 250000;
		int numberOfMonts = 60;
		int carPrice = 1000000;
		
		assertEquals(10.0,intcalc.calcInterestRate(rate, currentRate, downPayment, numberOfMonts, carPrice),0);
	}

}

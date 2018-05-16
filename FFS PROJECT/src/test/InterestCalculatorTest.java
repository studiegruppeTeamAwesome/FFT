package test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ferrari.finances.dk.rki.Rating;

import logic.BadCreditRatingException;
import logic.InterestCalculator;

public class InterestCalculatorTest {

	@Test
	public void calcInterestRateingATest() throws BadCreditRatingException {
		InterestCalculator intcalc = new InterestCalculator();
		double currentRate = 5.0;
		Rating rate = Rating.A;
		int downPayment = 250000;
		int numberOfMonts = 60;
		int carPrice = 1000000;
		
		assertEquals(8.0,intcalc.calcInterestRate(rate, currentRate, downPayment, numberOfMonts, carPrice),0);
	}
	
	@Test
	public void calcInterestRatingBTest() throws BadCreditRatingException {
		InterestCalculator intcalc = new InterestCalculator();
		double currentRate = 5.0;
		Rating rate = Rating.B;
		int downPayment = 250000;
		int numberOfMonts = 60;
		int carPrice = 1000000;
		
		assertEquals(9.0,intcalc.calcInterestRate(rate, currentRate, downPayment, numberOfMonts, carPrice),0);
	}
	
	@Test
	public void calcInterestRatingCTest() throws BadCreditRatingException {
		InterestCalculator intcalc = new InterestCalculator();
		double currentRate = 5.0;
		Rating rate = Rating.C;
		int downPayment = 250000;
		int numberOfMonts = 60;
		int carPrice = 1000000;
		
		assertEquals(10.0,intcalc.calcInterestRate(rate, currentRate, downPayment, numberOfMonts, carPrice),0);
	}

	@Test (expected = BadCreditRatingException.class)
	public void calcInterestRatingDTest() throws BadCreditRatingException {
		InterestCalculator intcalc = new InterestCalculator();
		double currentRate = 5.0;
		Rating rate = Rating.D;
		int downPayment = 250000;
		int numberOfMonts = 60;
		int carPrice = 1000000;
		
		assertEquals(10.0,intcalc.calcInterestRate(rate, currentRate, downPayment, numberOfMonts, carPrice),0);
	}
}

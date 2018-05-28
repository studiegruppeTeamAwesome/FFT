package test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ferrari.finances.dk.rki.Rating;

import logic.PoorCreditRatingException;
import logic.Calculator;

public class CalculatorTest {

	
	
	// first of 4 test to test if calculations are correct based on rating.
	@Test
	public void calcInterestRatingATest() throws PoorCreditRatingException {
		Calculator intcalc = new Calculator();
		double currentRate = 5.0;
		Rating rate = Rating.A;
		int downPayment = 250000;
		int numberOfMonts = 60;
		int carPrice = 1000000;

		assertEquals(8.0, intcalc.calcInterestRate(rate, currentRate, downPayment, numberOfMonts, carPrice), 0);
	}

	// second of 4 test to test if calculations are correct based on rating.
	@Test
	public void calcInterestRatingBTest() throws PoorCreditRatingException {
		Calculator intcalc = new Calculator();
		double currentRate = 5.0;
		Rating rate = Rating.B;
		int downPayment = 250000;
		int numberOfMonts = 60;
		int carPrice = 1000000;

		assertEquals(9.0, intcalc.calcInterestRate(rate, currentRate, downPayment, numberOfMonts, carPrice), 0);
	}

	// third of 4 test to test if calculations are correct based on rating.
	@Test
	public void calcInterestRatingCTest() throws PoorCreditRatingException {
		Calculator intcalc = new Calculator();
		double currentRate = 5.0;
		Rating rate = Rating.C;
		int downPayment = 250000;
		int numberOfMonts = 60;
		int carPrice = 1000000;

		assertEquals(10.0, intcalc.calcInterestRate(rate, currentRate, downPayment, numberOfMonts, carPrice), 0);
	}

	// fourth of 4 test to test if calculations are correct based on rating.
	// note that we dont want to create loans for people with a D rating.
	@Test(expected = PoorCreditRatingException.class)
	public void calcInterestRatingDTest() throws PoorCreditRatingException {
		Calculator intcalc = new Calculator();
		double currentRate = 5.0;
		Rating rate = Rating.D;
		int downPayment = 250000;
		int numberOfMonts = 60;
		int carPrice = 1000000;

		assertEquals(10.0, intcalc.calcInterestRate(rate, currentRate, downPayment, numberOfMonts, carPrice), 0);
	}

	// test if the loan period is over 3 years. this one should not add more as it
	// is no over but exactly 3 years.
	@Test
	public void calcInterestNumberOfMonthsTest() throws PoorCreditRatingException {
		Calculator intcalc = new Calculator();
		double currentRate = 5.0;
		Rating rate = Rating.A;
		int downPayment = 250000;
		int numberOfMonts = 36;
		int carPrice = 1000000;

		assertEquals(7.0, intcalc.calcInterestRate(rate, currentRate, downPayment, numberOfMonts, carPrice), 0);
	}

	// test if the loan period is over 3 years. this one should add more as it is
	// over 3 years.
	@Test
	public void calcInterestNumberOfMonths2Test() throws PoorCreditRatingException {
		Calculator intcalc = new Calculator();
		double currentRate = 5.0;
		Rating rate = Rating.A;
		int downPayment = 250000;
		int numberOfMonts = 37;
		int carPrice = 1000000;

		assertEquals(8.0, intcalc.calcInterestRate(rate, currentRate, downPayment, numberOfMonts, carPrice), 0);
	}

	// this is to test if the downpayment is under 50% of the carprice, this one should add 1 percent to the total as it is under 50% 
	@Test
	public void calcInterestDownPaymentTest() throws PoorCreditRatingException {
		Calculator intcalc = new Calculator();
		double currentRate = 5.0;
		Rating rate = Rating.A;
		int downPayment = 499999;
		int numberOfMonts = 37;
		int carPrice = 1000000;

		assertEquals(8.0, intcalc.calcInterestRate(rate, currentRate, downPayment, numberOfMonts, carPrice), 0);
	}
	
	// this is to test if the downpayment is under 50% of the carprice, this one should not add to the total as it is 50% 
	@Test
	public void calcInterestDownPayment2Test() throws PoorCreditRatingException {
		Calculator intcalc = new Calculator();
		double currentRate = 5.0;
		Rating rate = Rating.A;
		int downPayment = 500000;
		int numberOfMonts = 37;
		int carPrice = 1000000;

		assertEquals(7.0, intcalc.calcInterestRate(rate, currentRate, downPayment, numberOfMonts, carPrice), 0);
	}
	// testing the method to convert a yearly rate to a monthly rate
	@Test
	public void calcMonthlyInterestRateTest() {
		Calculator intcalc = new Calculator();
		double currentRate = 5.0;
		
		assertEquals(0.407, intcalc.calcMonthlyInterestRate(currentRate), 0.01);
	}
	//testing the monthly repayments
	@Test
	public void calcRepaymentsTest() {
		Calculator intcalc = new Calculator();
		int amount = 8000000;
		int noOfMonths = 120;
		double monthlyRate = 0.407;
		
		assertEquals(84399.54, intcalc.calcRepayments(amount, monthlyRate, noOfMonths), 0.1);
	}
	
	
	
	
}
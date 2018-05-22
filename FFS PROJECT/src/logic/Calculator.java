package logic;

import com.ferrari.finances.dk.rki.Rating;

public class Calculator {

	public double calcInterestRate(Rating rating, double currentRate, int downPayment, int numberOfMonths, int carPrice)
			throws PoorCreditRatingException {
		return currentRate + intRateFromRating(rating) + intRateFromDP(downPayment, carPrice)
				+ intRateFromMonths(numberOfMonths);
	}
	
	public double calcMontlyInterestRate(double interestRate) {
		return ((Math.pow((1.0+(interestRate/100.0)), (1.0/12.0))) - 1)*100.0;
	}

	private double intRateFromRating(Rating rating) throws PoorCreditRatingException {
		if (rating == Rating.A)
			return 1.0;
		if (rating == Rating.B)
			return 2.0;
		if (rating == Rating.C)
			return 3.0;
		else
			throw new PoorCreditRatingException("BAD CREDIT RATING!");
	}

	private double intRateFromDP(int downPayment, int carPrice) {
		if (downPayment < carPrice / 2)
			return 1.0;
		else
			return 0;
	}

	private double intRateFromMonths(int numberOfMonths) {
		if (numberOfMonths > 36)
			return 1.0;
		else
			return 0;
	}
	
	public double calcRepayments(int price, double monthlyRate, int noOfMonths) {
		return (price*(monthlyRate/100.0))/(1-Math.pow(1+(monthlyRate/100.0), -1*noOfMonths));
	}
}
package logic;

import java.util.ArrayList;
import java.util.HashMap;

import com.ferrari.finances.dk.rki.Rating;

public class Calculator { // ansvar:Martin, Sofie, review:Shahnaz

	/**
	 * calculates the personal rate for a custommer based on the parameters
	 * @param rating A rating from RKI
	 * @param currentRate The current rate, used as a base number to calculate the personal rate
	 * @param downPayment Indicates how much the customer has put as downpayment for the loan
	 * @param numberOfMonths Indicates how many months until the loan as been payed off
	 * @param carPrice The price of the car the customer is trying to lend money for
	 * @return The interest rate the customer is gonna get on his/her loan
	 */
	public double calcInterestRate(Rating rating, double currentRate, int downPayment, int numberOfMonths, int carPrice)
			throws PoorCreditRatingException {
		return currentRate + intRateFromRating(rating) + intRateFromDP(downPayment, carPrice)
				+ intRateFromMonths(numberOfMonths);
	}
	
	/**
	 * Calculates monthly rate based on a yearly interest rate
	 * @param interestRate The yearly interest rate we want to turn into a monthly interest rate
	 * @return The monthly interest rate
	 */
	public double calcMonthlyInterestRate(double interestRate) {
		return ((Math.pow((1.0 + (interestRate / 100.0)), (1.0 / 12.0))) - 1) * 100.0;
	}
	
	/**
	 * Calculates the monthly repayments
	 * @param price	The amount of the loan
	 * @param monthlyRate The monthly rate of the loan
	 * @param noOfMonths The number of months before the loan as been payed off
	 * @return the total amount the customer has to pay every month until the loan has been payed off
	 */
	public double calcRepayments(int price, double monthlyRate, int noOfMonths) {
		return (price * (monthlyRate / 100.0)) / (1 - Math.pow(1 + (monthlyRate / 100.0), -1 * noOfMonths));
	}
	
	/**
	 * 
	 * @param rating A rating from RKI
	 * @return The amount to add to the customers personal rate based on the customers RKI rating
	 * @throws PoorCreditRatingException throws this exception if the customers rating is too poor
	 */
	private double intRateFromRating(Rating rating) throws PoorCreditRatingException {
		if (rating == Rating.A)
			return 1.0;
		if (rating == Rating.B)
			return 2.0;
		if (rating == Rating.C)
			return 3.0;
		else
			throw new PoorCreditRatingException("KREDITVÆRDIGHED FOR LAV!");
	}
	
	/**
	 * 
	 * @param downPayment Indicates how much the customer has put as downpayment for the loan
	 * @param carPrice The price of the car the customer is trying to lend money for
	 * @return The amount to add to the customers personal rate based on downpayment % of car price
	 */
	private double intRateFromDP(int downPayment, int carPrice) {
		if (downPayment < carPrice / 2)
			return 1.0;
		else
			return 0;
	}
	
	/**
	 * 
	 * @param numberOfMonths Indicates how many months until the loan as been payed off
	 * @return The amount to add to the customers personal rate based on how long it takes to pay the loan off
	 */
	private double intRateFromMonths(int numberOfMonths) {
		if (numberOfMonths > 36)
			return 1.0;
		else
			return 0;
	}
	
	/**
	 * 
	 * @param loanOffer the loanOffer object for which a repayment plan is desired
	 * @return a hasmap of the components of the repayment plan, using the enum-class LoanPlanComponent as keys
	 */
	public HashMap<LoanPlanComponent, ArrayList<Double>> loanPlanCalculation(LoanOffer loanOffer) {

		// a hashmap of all components in the repayment plan
		HashMap<LoanPlanComponent, ArrayList<Double>> comps = new HashMap<LoanPlanComponent, ArrayList<Double>>();

		// outstanding debt, starting from the principal amount
		double outsDebt = loanOffer.getCar().getPrice() - loanOffer.getDownPayment();
		
		// rate pr month based on the annual rate
		double rateMonth = calcMonthlyInterestRate(loanOffer.getAnnualCost());

		// an ArrayList containing the rate in kr. per repayment
		ArrayList<Double> rate = new ArrayList<Double>();

		// an ArrayList containing the installment per repayment minus the rate
		ArrayList<Double> install = new ArrayList<Double>();

		// an ArrayList containing the outstanding debt at the end of each term
		ArrayList<Double> outDebt = new ArrayList<Double>();

		for (int i = 0; i < loanOffer.getNumberOfMonths(); i++) {

			// calculate the rate per repayment in kr. (rate * outstanding debt)
			rate.add((rateMonth / 100) * outsDebt);

			// calculate installment per repayment minus the rate (repayment - rate pr term in kr.)
			install.add(loanOffer.getRepayments() - rate.get(i));

			// subtract the repayment from the outstanding debt and assign that as outstanding debt at the end of the term.
			outsDebt = outsDebt - install.get(i);

			// add the outstandign debt the the ArrayList
			outDebt.add(outsDebt);
		}

		// put the ArrayLists into the HashMap
		comps.put(LoanPlanComponent.RATE, rate);
		comps.put(LoanPlanComponent.INSTALL, install);
		comps.put(LoanPlanComponent.OUT_DEBT, outDebt);

		return comps;
	}

}

package logic;

import java.util.ArrayList;
import java.util.HashMap;

import com.ferrari.finances.dk.rki.Rating;

public class Calculator { // ansvar:Martin, Sofie, review:Shahnaz

	public double calcInterestRate(Rating rating, double currentRate, int downPayment, int numberOfMonths, int carPrice)
			throws PoorCreditRatingException {
		return currentRate + intRateFromRating(rating) + intRateFromDP(downPayment, carPrice)
				+ intRateFromMonths(numberOfMonths);
	}

	public double calcMonthlyInterestRate(double interestRate) {
		return ((Math.pow((1.0 + (interestRate / 100.0)), (1.0 / 12.0))) - 1) * 100.0;
	}

	public double calcRepayments(int price, double monthlyRate, int noOfMonths) {
		return (price * (monthlyRate / 100.0)) / (1 - Math.pow(1 + (monthlyRate / 100.0), -1 * noOfMonths));
	}

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

	public HashMap<LoanPlanComponent, ArrayList<Double>> loanPlanCalculation(LoanOffer loanOffer) {

		// et map over alle komponenter i tilbagebetalingsplanen
		HashMap<LoanPlanComponent, ArrayList<Double>> comps = new HashMap<LoanPlanComponent, ArrayList<Double>>();

		// primo restgæld, startende med hovedstol
		double outsDebt = loanOffer.getCar().getPrice() - loanOffer.getDownPayment();
		// renten pr måned regnet ud fra ÅOP
		double rateMonth = calcMonthlyInterestRate(loanOffer.getAnnualCost());

		// indeholder rente pr. termin i kr.
		ArrayList<Double> rate = new ArrayList<Double>();

		// indeholder afdrag pr termin minus rente
		ArrayList<Double> install = new ArrayList<Double>();

		// indeholder ultimo restgæld
		ArrayList<Double> outDebt = new ArrayList<Double>();

		for (int i = 0; i < loanOffer.getNumberOfMonths(); i++) {

			// udregn renten pr termin i kr. (rente * primo restgæld)
			rate.add((rateMonth / 100) * outsDebt);

			// udregn afdrag pr termin uden rente (ydelse - rente pr. termin i kr.)
			install.add(loanOffer.getRepayments() - rate.get(i));

			// træk afdrag fra primo restgæld og sæt det til at være ultimo restgæld
			outsDebt = outsDebt - install.get(i);

			// tilføj den nuværende ultimo restgæld til array
			outDebt.add(outsDebt);
		}

		comps.put(LoanPlanComponent.RATE, rate);
		comps.put(LoanPlanComponent.INSTALL, install);
		comps.put(LoanPlanComponent.OUT_DEBT, outDebt);

		return comps;
	}

}

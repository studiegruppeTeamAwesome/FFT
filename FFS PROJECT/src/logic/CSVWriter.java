package logic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class CSVWriter { // ansvar:Sofie, Martin review:Shahnaz
	// using ";" as separator because the "," doesent seem to work correctly
	private String separator = ";";
	Calculator calculator = new Calculator();

	/**
	 * 
	 * @param loanOffer
	 *            The loanOffer we want to export to a CSV-file
	 * @throws IOException
	 */
	public void exportLoan(LoanOffer loanOffer) throws IOException {
		/*
		 * adding the loan offers auto generated id + the "Låne Tilbud" string
		 * to create unique names for the exported file
		 */
		BufferedWriter writer = new BufferedWriter(new FileWriter("Låne Tilbud " + loanOffer.getId() + ".csv"));
		/*
		 * using decimalFormatter to round and only show the first two decimals after
		 * the comma. this has the added benefit of acually using the real comma instead
		 * of the dot.
		 */
		DecimalFormat formatter = new DecimalFormat("##.##");

		/*
		 * using the HashMap and its enums along the the ArrayList of doubles to
		 * generate a correctly formated file
		 */
		HashMap<LoanPlanComponent, ArrayList<Double>> comps = calculator.loanPlanCalculation(loanOffer);
		writer.write("Termin" + separator + "Fast Ydelse" + separator + "Rente pr. termin" + separator + "Afdrag"
				+ separator + "Ultimo restgæld");
		writer.newLine();

		for (int i = 0; i < loanOffer.getNumberOfMonths(); i++) {
			writer.write((i + 1) + separator + formatter.format(loanOffer.getRepayments()) + separator
					+ formatter.format(comps.get(LoanPlanComponent.RATE).get(i)) + separator
					+ formatter.format(comps.get(LoanPlanComponent.INSTALL).get(i)) + separator
					+ formatter.format(comps.get(LoanPlanComponent.OUT_DEBT).get(i)));
			writer.newLine();
		}

		writer.close();

	}
}

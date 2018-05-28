package logic;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CSVWriter {
	private String sep = ";";

	public void exportLoan(LoanOffer loanOffer) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("Låne Tilbud " + loanOffer.getId() + ".csv"));
		
		HashMap<LoanPlanComponent, ArrayList<Double>> comps = Calculator.loanPlanCalculation(loanOffer);
		writer.write("Termin" + sep + "Fast Ydelse" + sep + "Rente pr. termin" + sep + "Afdrag" + sep + "Ultimo restgæld");
		writer.newLine();
		
		for (int i = 0; i < loanOffer.getNumberOfMonths(); i++) {
			writer.write(i + sep + loanOffer.getRepayments() + sep + comps.get(LoanPlanComponent.RATE).get(i) + sep + 
					comps.get(LoanPlanComponent.INSTALL).get(i) + sep + comps.get(LoanPlanComponent.OUT_DEBT).get(i));
			writer.newLine();
		}
		
	}
}

package logic;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CSVWriter {
	private String separator = ";";
	Calculator calculator = new Calculator();

	public void exportLoan(LoanOffer loanOffer) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("Låne Tilbud " + loanOffer.getId() + ".csv"));
		
		HashMap<LoanPlanComponent, ArrayList<Double>> comps = calculator.loanPlanCalculation(loanOffer);
		writer.write("Termin" + separator + "Fast Ydelse" + separator + "Rente pr. termin" + separator + "Afdrag" + separator + "Ultimo restgæld");
		writer.newLine();
		
		for (int i = 0; i < loanOffer.getNumberOfMonths(); i++) {
			writer.write(i + separator + loanOffer.getRepayments() + separator + comps.get(LoanPlanComponent.RATE).get(i) + separator + 
					comps.get(LoanPlanComponent.INSTALL).get(i) + separator + comps.get(LoanPlanComponent.OUT_DEBT).get(i));
			writer.newLine();
		}
		
		writer.close();
		
	}
}

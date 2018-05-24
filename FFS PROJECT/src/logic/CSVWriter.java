package logic;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {
	private String separator = ";";

	public void exportLoan(LoanOffer loanOffer) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("Låne Tilbud " + loanOffer.getId() + ".csv"));
		writer.write("Kunde");
		writer.newLine();
		writer.write("Navn" + separator + loanOffer.getCostumer().getName());
		writer.newLine();
		writer.write("Tlf" + separator + loanOffer.getCostumer().getPhone());
		writer.newLine();
		writer.write("Bil");
		writer.newLine();
		writer.write("Model" + separator + loanOffer.getCar().getModel());
		writer.newLine();
		writer.write("Pris" + separator + loanOffer.getCar().getPrice() );
		writer.newLine();
		writer.write("Sælger");
		writer.newLine();
		writer.write("Navn" + separator + loanOffer.getSalesman().getName());
		writer.newLine();
		writer.write("Detaljer");
		writer.newLine();
		writer.write("Udbetaling" + separator + loanOffer.getDownPayment());
		writer.newLine();
		writer.write("Antal Ydelser" + separator + loanOffer.getNumberOfMonths());
		writer.newLine();
		writer.write("Afdrag" + separator + loanOffer.getRepayments());
		writer.newLine();
		writer.write("ÅOP" + separator + loanOffer.getAnnualCost());
		writer.close();
	}
}

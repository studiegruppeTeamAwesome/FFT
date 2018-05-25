package logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ferrari.finances.dk.rki.Rating;

import data.DataLayerImp;

public class FFSController implements FacadeController {
	DataLayerImp dataController = new DataLayerImp();
	Calculator calculator = new Calculator();
	CSVWriter writer = new CSVWriter();

	@Override

	public double getCurrentRate() {
		return Bank.instance().getCurrentRate();
	}

	@Override

	public void setCreditRating(Customer customer) {
		RKI.instance().setCreditRating(customer);
	}

	@Override
	public ArrayList<Car> getAllCars() {
		return dataController.getAllCars();
	}

	@Override
	public Salesman getSalesmenByName(String name) {
		return dataController.getSalesmanByName(name);
	}

	@Override
	public double calculateInterestRate(Rating rating, double currentRate, int downPayment, int numberOfMonths,
			int carPrice) throws PoorCreditRatingException {
		return calculator.calcInterestRate(rating, currentRate, downPayment, numberOfMonths, carPrice);
	}

	@Override
	public Customer getCustomerByPhone(int phone) {
		return dataController.getCustomerByPhone(phone);
	}

	@Override
	public double calculateMonthlyRate(double interestRate) {
		return calculator.calcMonthlyInterestRate(interestRate);
	}

	@Override
	public double calculateRepayments(Car car, int downPayment, double monthlyRate, int noOfMonths) {
		return calculator.calcRepayments((car.getPrice() - downPayment), monthlyRate, noOfMonths);
	}

	@Override
	public boolean saveLoanOffer(LoanOffer loan) {
		return dataController.InsertloanOffers(loan);
	}

	public List<LoanOffer> getLoansByApproved(boolean approved){
		return dataController.getAllloanOffersByApproved(approved);
	}

	@Override
	public boolean approveLoan(LoanOffer loan) {
		return dataController.updateLoanOfferById(loan);
	}

	@Override
	public void printLoan(LoanOffer chosenLoanOffer) {
		try {
			writer.exportLoan(chosenLoanOffer);
		} catch (IOException e) {
			System.out.println("en fejl ved eksport til csv");
			e.printStackTrace();
		}
	}
	@Override
	public boolean updateCustomerHasOffer(boolean hasOffer) {
		//TODO
		return false;
	}
	
	
}
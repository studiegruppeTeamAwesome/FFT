package logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ferrari.finances.dk.rki.Rating;

import data.DataLayer;

public class FFSController implements FacadeController { // ansvar:Sofie, Shahnaz review:Martin
	DataLayer dataController = DataLayer.instance();
	Calculator calculator = new Calculator();
	CSVWriter writer = new CSVWriter();

	@Override

	public double getCurrentRate() {
		Bank bank = new Bank();
		return bank.getCurrentRate();
	}

	@Override

	public void setCreditRating(Customer customer) {
		RKI rki = new RKI();
		rki.setCreditRating(customer);
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

	public List<LoanOffer> getLoansByApproved(boolean approved) {
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
	public boolean updateCustomerHasOffer(Customer customer) {
		return dataController.updateCustomerByHasOffer(customer);
	}

}
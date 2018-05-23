package logic;

import java.util.ArrayList;
import java.util.List;

import com.ferrari.finances.dk.rki.Rating;

import data.DataLayerImp;

public class FFSController implements FacadeController {
	DataLayerImp dataController = new DataLayerImp();
	Calculator calculator = new Calculator();

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

	public List<LoanOffer> getUnapprovedLoans(){
		return dataController.getAllloanOffersByApproved(false);;
	}

	@Override
	public boolean approveLoan(LoanOffer loan) {
		// TODO call method from datalayer
		return false;
	}
	
	
}
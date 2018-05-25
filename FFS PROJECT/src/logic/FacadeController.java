package logic;

import java.util.ArrayList;
import java.util.List;

import com.ferrari.finances.dk.rki.Rating;

public interface FacadeController {
	
	public double getCurrentRate();

	public void setCreditRating(Customer customer);

	public ArrayList<Car> getAllCars();
	
	public Salesman getSalesmenByName(String name);
	
	public double calculateInterestRate(Rating rating, double currentRate, int downPayment, 
			int numberOfMonths, int carPrice) throws PoorCreditRatingException;
	
	public Customer getCustomerByPhone(int phone);
	
	public double calculateMonthlyRate(double interestRate);
	
	public double calculateRepayments(Car car, int downPayment, double monthlyRate, int noOfMonths);
	
	public boolean saveLoanOffer(LoanOffer loan);
	
	public List<LoanOffer> getLoansByApproved(boolean approved);
	
	public boolean approveLoan(LoanOffer loan);

	public void printLoan(LoanOffer chosenLoanOffer);
	
	public boolean updateCustomerHasOffer(boolean hasOffer);
	
}
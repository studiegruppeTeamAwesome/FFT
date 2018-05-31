package logic;

import java.util.ArrayList;
import java.util.List;

import com.ferrari.finances.dk.rki.Rating;

public interface FacadeController { // ansvar:Sofie, Shahnaz review:Martin
	
	/**
	 * Gets the current rate from the bank
	 * @return current rate from the bank 
	 */
	public double getCurrentRate();

	/**
	 * sets the personal rate for the customer
	 * @param customer The customer we want to set the credit rating for
	 */
	public void setCreditRating(Customer customer);

	/**
	 * Gets all cars from the database
	 * @return all cars currently in the database as an Arraylist
	 */
	public ArrayList<Car> getAllCars();

	/**
	 * Gets salesman by his name from the database
	 * @param name of the salesman
	 * @return salesman object associated with the name
	 */
	public Salesman getSalesmanByName(String name);
	
	/**
	 * calculates the personal rate for a custommer based on the parameters
	 * @param rating A rating from RKI
	 * @param currentRate The current rate, used as a base number to calculate the personal rate
	 * @param downPayment Indicates how much the customer has put as downpayment for the loan
	 * @param numberOfMonths Indicates how many months until the loan as been payed off
	 * @param carPrice The price of the car the customer is trying to lend money for
	 * @return The interest rate the customer is gonna get on his/her loan
	 */
	public double calculateInterestRate(Rating rating, double currentRate, int downPayment, int numberOfMonths,
			int carPrice) throws PoorCreditRatingException;
	/**
	 * used to get a customer from the database based on a phone number
	 * @param phone the phone number we want to look up in the database	
	 * @return a Customer object from the database
	 */
	public Customer getCustomerByPhone(int phone);

	/**
	 * Calculates monthly rate based on a yearly interest rate
	 * @param interestRate The yearly interest rate we want to turn into a monthly interest rate
	 * @return The monthly interest rate
	 */
	public double calculateMonthlyRate(double interestRate);

	/**
	 * 
	 * @param car The car we want to create a lona for
	 * @param downPayment The amount of downpayment on the loan
	 * @param monthlyRate The monthly rate of the loan
	 * @param noOfMonths The number of months before the loan as been payed off
	 * @return the total amount the customer has to pay every month until the loan has been payed off
	 */
	public double calculateRepayments(Car car, int downPayment, double monthlyRate, int noOfMonths);

	/**
	 * inserts a loanoffer into the database
	 * @param loanOffers The loan offer we want to insert into the database
	 * @return returns true if success false if unsuccessful
	 */
	public boolean saveLoanOffer(LoanOffer loan);

	/**
	 * Gets all loanoffers based on whether they are approved or not
	 * @param approved only 2 options true or false
	 * @return a list of loanoffers that are either approved or not
	 */
	public List<LoanOffer> getLoansByApproved(boolean approved);
	
	/**
	 *  used to set if the loan if approved in the database
	 * @param loan the loan we want to approve
	 * @return True if it succeeded false if not
	 */
	public boolean approveLoan(LoanOffer loan);

	/**
	 * Exports the loan offer to a CSV-file
	 * @param chosenLoanOffer The loanoffer we want to export
	 */
	public void printLoan(LoanOffer chosenLoanOffer);

	/**
	 * updates the customers hasActiveLoan atribute in the database
	 * @param customer the customer we want to update
	 * @return true if update succeeded, false if not
	 */
	public boolean updateCustomerHasOffer(Customer customer);

}
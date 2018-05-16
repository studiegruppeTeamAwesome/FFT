package logic;

import java.util.ArrayList;

import com.ferrari.finances.dk.rki.Rating;

public interface FacadeController {

	public double getCurrentRate();

	public void setCreditRating(Customer customer);

	public ArrayList<Car> getAllCars();
	
	public Salesman getSalesmenByName(String name);
	
	public double calculateInterestRate(Rating rating, double currentRate, int downPayment, 
			int numberOfMonths, int carPrice) throws BadCreditRatingException;
	
	public Customer getCustomerByPhone(int phone);
	
}
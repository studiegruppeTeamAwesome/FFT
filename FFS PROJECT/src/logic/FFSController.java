package logic;

import java.util.ArrayList;

import com.ferrari.finances.dk.rki.Rating;

import data.DataLayerImp;

public class FFSController implements FacadeController {
	DataLayerImp dataController = new DataLayerImp();

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
		// TODO getSalesmenByName
		return null;
	}

	@Override
	public double calculateInterestRate(Rating rating, double currentRate, int downPayment, int numberOfMonths, 
			int carPrice) throws BadCreditRatingException {
		
		InterestCalculator calculator = new InterestCalculator();
		return calculator.calcInterestRate(rating, currentRate, downPayment, numberOfMonths, carPrice);
	}

	@Override
	public Customer getCustomerByPhone(int phone) {
		dataController.getCustomerByPhone(phone);
		return null;
	}

}
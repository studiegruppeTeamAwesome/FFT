package data;

import java.util.ArrayList;

import logic.*;

public interface DataLayer {
	public void openConnection();
	public ArrayList<Car> getAllCars();
	public Customer getCustomerByPhone(int Tlf);
	public boolean InsertloanOffers(LoanOffer loanOffers);
	public Salesman getSalesmanByName(String name);
	public LoanOffer getloanOfferByApproved(boolean approved);
	public Salesman getSalsmanById(int id);
	public Car getCarById(int id);
}

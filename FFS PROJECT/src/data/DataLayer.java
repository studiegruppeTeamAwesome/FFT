package data;

import java.util.ArrayList;

import logic.*;

public interface DataLayer {
	public void openConnection();
	public ArrayList<Car> getAllCars();
	public Customer getCustomerByPhone(int Tlf);
	public boolean InsertloanOffers(LoanOffer loanOffers);
	public Salesman getSalesmanByName(String name);
	Salesman getSalesmanByBoss(boolean boss);
	LoanOffer getloanOfferByApproved(boolean approved);
	Salesman getSalsmanById(int id);
	Car getCarById(int id);
}

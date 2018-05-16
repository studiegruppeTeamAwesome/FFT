package data;

import java.util.ArrayList;

import logic.*;

public interface DataLayer {
	public void openConnection();
	public ArrayList<Car> getAllCars();
	public Customer getCustomerByPhone(int Tlf);
	public boolean InsertloanOffers(LoanOffer loanOffers);
	 public Salesmen getSalemenNameBayName(String name);
	
}
//
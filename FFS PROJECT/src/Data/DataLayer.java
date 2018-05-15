package Data;

import java.util.ArrayList;

import Logic.*;

public interface DataLayer {
	public void openConnection();
	public ArrayList<Car> getAllCars();
	public Customer getCustomerByPhone(int Tlf);
	public boolean InsertloanOffers(loanOffers loanOffers);
	public String getSalemenNameBayLoanOffer(int salesmanId);// men kan finder salesmanid i loanoffer table
}

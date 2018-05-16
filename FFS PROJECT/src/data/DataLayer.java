package data;

import java.util.ArrayList;

import logic.*;

public interface DataLayer {
	public void openConnection();
	public ArrayList<Car> getAllCars();
	public Customer getCustomerByPhone(int phone);
	public boolean InsertloanOffers(LoanOffer loanOffers);
<<<<<<< HEAD
	 public Salesmen getSalemenNameBayName(String name);
<<<<<<< HEAD
	
=======
	public Salesman getSalemanNameBayName(String name);
>>>>>>> TangosTests
=======
	 public ArrayList<String> getAlleCarsName();
>>>>>>> parent of 44d8a4a... fix
}
//
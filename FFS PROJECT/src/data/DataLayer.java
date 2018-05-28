package data;

import java.util.ArrayList;
import java.util.List;
import logic.*;

public interface DataLayer { // ansvar:Shahnaz review:Martin
	public void openConnection();

	public ArrayList<Car> getAllCars();

	public Customer getCustomerByPhone(int tlf);

	public boolean InsertloanOffers(LoanOffer loanOffers);

	public Salesman getSalesmanByName(String name);

	public Salesman getSalesmanByBoss(boolean boss);

	public boolean updateLoanOfferById(LoanOffer loanOffer);

	public List<LoanOffer> getAllloanOffersByApproved(boolean approved);

	public Car getCarById(int id);

	public Salesman getSalsmanById(int id);

	public boolean updateCustomerByHasOffer(Customer customer);

}

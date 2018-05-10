package Data;

import java.util.ArrayList;

import Logic.*;

public interface DataLayer {
	public void openConnection();
	public ArrayList<Cars> getAllCarsName();
	public Customer getCustomerByTlf(int Tlf);
	public boolean InsertloanOffers(loanOffers loanOffers);
}

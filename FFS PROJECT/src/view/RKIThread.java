package view;

import java.util.Observable;
import logic.Customer;
import logic.FFSController;
import logic.FacadeController;

public class RKIThread extends Observable implements Runnable {
// ansvar: alle
	
	private FacadeController controller = new FFSController();
	private Customer customer;

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public void run() {
		controller.setCreditRating(customer);
		setChanged();
		notifyObservers();
		System.out.println("RKI Thread died");
	}
}

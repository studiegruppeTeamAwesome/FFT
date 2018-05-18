package view;

import java.util.Observable;

import com.ferrari.finances.dk.rki.Rating;

import logic.Customer;
import logic.FFSController;
import logic.FacadeController;

public class RKIThread extends Observable implements Runnable {

	private FacadeController controller = new FFSController();
	private Customer customer;
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public void run() {
		controller.setCreditRating(customer);
		if(hasChanged()) {
			notifyObservers();
		}
		System.out.println("RKI Thread died");
	}
}

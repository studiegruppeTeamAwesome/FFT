package view;

import java.util.Observable;

import logic.FFSController;
import logic.FacadeController;

public class BankThread extends Observable implements Runnable {

	FacadeController controller = new FFSController();
	private double currentRate;
	
	@Override
	public void run() {
		currentRate = controller.getCurrentRate();
		setChanged();
		notifyObservers(currentRate);
		System.out.println("Bank Thread died");
	}
}

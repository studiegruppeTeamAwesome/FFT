package view;

import java.util.Observable;

import logic.FFSController;
import logic.FacadeController;

public class BankThread extends Observable implements Runnable {
// ansvar: alle
	
	private FacadeController controller = new FFSController();
	private double currentRate;

	@Override
	public void run() {
		currentRate = controller.getCurrentRate();
		setChanged();
		notifyObservers(currentRate); // vi har brugt push til vorse obsarver og sender obj med
		System.out.println("Bank Thread died");
	}
}

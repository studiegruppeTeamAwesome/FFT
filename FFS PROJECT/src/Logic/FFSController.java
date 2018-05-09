package Logic;

import java.util.ArrayList;

import Data.DataLayer;

public class FFSController implements FacadeController {
DataLayer dataController = new DataLayer();
	@Override

	public double getCurrentRate() {

		return Bank.instance().getCurrentRate();

	}

	@Override

	public void setCreditRating(Customer customer) {

		RKI.instance().setCreditRating(customer);

	}

	@Override
	public ArrayList<Cars> getAllCars() {
		return dataController.getAllCars();
	}

}
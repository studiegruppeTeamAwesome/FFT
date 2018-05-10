package Logic;

import java.util.ArrayList;

import Data.DataLayerImp;

public class FFSController implements FacadeController {
DataLayerImp dataController = new DataLayerImp();
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
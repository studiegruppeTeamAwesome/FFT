package Data;

import java.util.ArrayList;

import Logic.Cars;

public interface DataLayerInterFace {
	public void openConnection();
	public ArrayList<Cars> getAllCars();
}

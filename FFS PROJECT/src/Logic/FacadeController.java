package Logic;

import java.util.ArrayList;

public interface FacadeController {

	public double getCurrentRate();

	public void setCreditRating(Customer customer);

	public ArrayList<Car> getAllCars();
}
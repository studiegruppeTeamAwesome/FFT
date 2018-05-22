package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import data.DataLayer;
import data.DataLayerImp;
import logic.Car;
import logic.Customer;
import logic.Salesman;

public class DataLayerImpTest {

	@Test
	public void getCustomerByPhoneTest() {
		DataLayer data = new DataLayerImp();
		Customer customer;
		int phoneNumber = 11111111;
		customer = data.getCustomerByPhone(phoneNumber);
	
		assertEquals("Testy McTesterson", customer.getName());
	}
	
	@Test
	public void getSalesmanByNameTest() {
		DataLayer data = new DataLayerImp();
		Salesman sm;
		String salesmanName = "sara";
		sm = data.getSalesmanByName(salesmanName);
		
		assertEquals(salesmanName, sm.getName());
		
	}
	@Test
	public void getAllCarsTest() {
		DataLayer data = new DataLayerImp();
		ArrayList<Car> cars = data.getAllCars();
		Car car = new Car();
		car.setModel("Ferrari 6754");
		assertEquals(car.getModel(),cars.get(0).getModel());
	}

}

package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import data.DataLayer;
import data.DataLayerImp;
import logic.Car;
import logic.Customer;
import logic.LoanOffer;
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
		String salesmanName = "martin";
		sm = data.getSalesmanByName(salesmanName);

		assertEquals(salesmanName, sm.getName());

	}

	@Test
	public void getAllCarsTest() {
		DataLayer data = new DataLayerImp();
		ArrayList<Car> cars = data.getAllCars();
		Car car = new Car();
		car.setModel("Ferrari 6754");
		assertEquals(car.getModel(), cars.get(0).getModel());
	}

	@Test
	public void getCarByIDTest() {
		DataLayer data = new DataLayerImp();
		Car car;
		car = data.getCarById(1);
		assertEquals(1, car.getId());

	}

	@Test
	public void getSalsmanByIDTest() {
		DataLayer data = new DataLayerImp();
		Salesman sm;
		String salesmanName = "martin";
		sm = data.getSalesmanByName(salesmanName);

		assertEquals(salesmanName, sm.getName());

	}

	@Test
	public void getAllLoanOfferByApprovedTest() {
		DataLayer data = new DataLayerImp();
		LoanOffer l = data.getAllloanOffersByApproved(false).get(1);
		String model = "Ferrari 6754";
		System.out.println(l.getId());

		assertEquals(model, l.getCar().getModel());

	}

	@Test
	public void getSalsmanByBossTest() {
		DataLayer data = new DataLayerImp();
		Salesman sm;
		sm = data.getSalesmanByBoss(true);
		assertEquals(true, sm.isBoss());

	}

	@Test
	public void InsertloanOffersTest() {
		DataLayer data = new DataLayerImp();
		Customer c = data.getCustomerByPhone(11111111);
		Salesman s = data.getSalesmanByName("bloms");
		LoanOffer l = new LoanOffer(1, 1000.0, 10000, 2000, 10, c, data.getCarById(1), s);

		assertEquals(true, data.InsertloanOffers(l));

	}

	@Test
	public void updateLoanOfferByIdTest() {
		DataLayer data = new DataLayerImp();

		LoanOffer l = data.getAllloanOffersByApproved(false).get(0);
		l.setApproved(true);
		l.setId(1);
		assertEquals(true, data.updateLoanOfferById(l));
	}

}

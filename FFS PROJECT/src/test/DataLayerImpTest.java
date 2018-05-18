package test;

import static org.junit.Assert.*;

import org.junit.Test;

import data.DataLayer;
import data.DataLayerImp;
import logic.Customer;

public class DataLayerImpTest {

	@Test
	public void getCustomerByPhoneTest() {
		DataLayer data = new DataLayerImp();
		Customer customer;
		int phoneNumber = 54456754;
		customer = data.getCustomerByPhone(phoneNumber);
		System.out.println(customer.getAdress());
		System.out.println(customer.getCPR());
		System.out.println(customer.getName());
		System.out.println(customer.getPhone()
				);
		assertEquals("sara rig", customer.getName());
	}

}

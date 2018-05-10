package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ferrari.finances.dk.rki.Rating;

import Logic.Cars;
import Logic.Customer;


public class DataLayerImp implements DataLayer {

	private Connection connection;

	public void openConnection() {
		try {

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		} catch (ClassNotFoundException e) {

			System.exit(0);
		}

		try {

			DriverManager.getConnection("jdbc:sqlserver://localhost:1433;" + "instanceName=SQLEXPRESS;"
					+ "databaseName=" + "FFSDB" + ";" + "integratedSecurity=true;");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Cars> getAllCarsName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer getCustomerByTlf(int phone) {
		Customer customer;
		try {
			Statement statement = connection.createStatement();
			String sql = "SELECT * FROM costumer WHERE phone=" + phone;
			ResultSet resultSet = statement.executeQuery(sql);
			String name = resultSet.getString("name");
			String adress = resultSet.getString("address");
			Rating creditRating = (Rating) resultSet.getObject("creditRating");
			String CPR = resultSet.getString("CPR");
			customer=new Customer();
			customer.setAdress(adress);
			customer.setCPR(CPR);
			customer.setName(name);
			customer.setRating(creditRating);
			statement.close();
			
			return customer;

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return null;
	}

}

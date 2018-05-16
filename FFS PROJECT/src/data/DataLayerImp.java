package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.ferrari.finances.dk.rki.Rating;

import logic.*;

public class DataLayerImp implements DataLayer {

	private Connection connection;

	public void openConnection() {
		try {
			System.out.println("Loading JDBC Driver...");

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			System.out.println("JDBC Driver loaded");
		} catch (ClassNotFoundException e) {
			System.out.println("Failed to load JDBC Driver!");

			System.exit(0);
		}

		
		String databaseName = "FFSDB";

		String connectionString = "jdbc:sqlserver://localhost:1433;" + "instanceName=SQLEXPRESS;" + "databaseName="
				+ databaseName + ";" + "integratedSecurity=true;";

		try {
			System.out.println("Connecting to database...");
			System.out.println(connectionString);

			connection = DriverManager.getConnection(connectionString);

			if (connection != null)
				System.out.println("Connected to database");
			else
				System.out.println("Could not connect to database");

			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Car> getAllCars() {

		ArrayList<Car> cars = new ArrayList<Car>();
		try {
			Statement statement = connection.createStatement();

			String sql = "SELECT * FROM car ";
			System.out.println(sql);

			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				int price = resultSet.getInt("price");
				Car car = new Car();
				car.setId(id);
				car.setName(name);
				car.setPrice(price);
				cars.add(car);
			}
			return cars;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cars;
	}

	@Override
	public Customer getCustomerByPhone(int phone) {
		Customer customer;
		try {
			Statement statement = connection.createStatement();
			String sql = "SELECT * FROM costumer WHERE phone=" + phone;
			ResultSet resultSet = statement.executeQuery(sql);
			String name = resultSet.getString("name");
			String adress = resultSet.getString("address");
			Rating creditRating = (Rating) resultSet.getObject("creditRating");
			String CPR = resultSet.getString("CPR");
			customer = new Customer();
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

	@Override
	public boolean InsertloanOffers(LoanOffer loanOffers) {
		try {
			String sql = "INSERT INTO loanOffers VALUES (?, ?, ?, ?, ?, ?)";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, loanOffers.getDate());
			statement.setInt(2, loanOffers.getDownPayment());
			statement.setInt(3, loanOffers.getRepayments());
			statement.setInt(4, loanOffers.getCostumerPhone());
			statement.setInt(5, loanOffers.getCarId());
			statement.setString(6, loanOffers.getSalesmanName());

			return statement.executeUpdate() == 1;

		} catch (SQLException e) {
			return false;
		}

	}

	// @Override
	// public String getSalemenNameBayLoanOffer(int salesmenId) { // den salemenId
	// skal setes med loanoffer.getsalesmenid
	// try {
	// Statement statement = connection.createStatement();
	// String sql = "SELECT name FROM Salesmen,loanOffers WHERE id=" + salesmenId;
	// ResultSet resultSet = statement.executeQuery(sql);
	// String name = resultSet.getString("name");
	// return name;
	// } catch (SQLException ex) {
	// return null;
	// }
	// }

}

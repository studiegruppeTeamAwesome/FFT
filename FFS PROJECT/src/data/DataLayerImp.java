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
		try { openConnection();
			Statement statement = connection.createStatement();

			String sql = "SELECT * FROM cars ";
			System.out.println(sql);

			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("model");
				int price = resultSet.getInt("price");
				Car car = new Car();
				car.setId(id);
				car.setModel(name);
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
		Customer c = new Customer();
		try {openConnection() ;
			String sql = "select * from customers where phone = " + phone;
			System.out.println(sql);

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			if (resultSet.next()) {
				c.setAddress(resultSet.getString("customerAddress"));
				c.setPhone(resultSet.getInt("phone"));
				c.setName(resultSet.getString("customerName"));
				c.setPostalCode(resultSet.getInt("postalCode"));
				c.setCPR(resultSet.getString("CPR"));
				c.setHasActiveOffer(resultSet.getBoolean("hasActiveLoan"));
				System.out.println(c.getName());
				return c;
			} else

				return null;
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public boolean InsertloanOffers(LoanOffer loanOffers) {
		try {openConnection() ;
			String sql = "INSERT INTO loanOffers VALUES (?, ?, ?, ?, ?, ?)";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setInt(2, loanOffers.getDownPayment());
			statement.setInt(3, loanOffers.getRepayments());
			statement.setInt(4, loanOffers.getCostumer().getPhone());
			statement.setInt(5, loanOffers.getCar().getId());
			statement.setString(6, loanOffers.getSalesman().getName());

			return statement.executeUpdate() == 1;

		} catch (SQLException e) {
			return false;
		}

	}

	@Override
	public Salesman getSalesmanByName(String name) {
		Salesman s = new Salesman();
		try {openConnection() ;
		String sql = "select * from salesmen where salesmanName = '" + name + "'"
				+ ""
				+ "";
		System.out.println(sql);

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

			if (resultSet.next()) {
				s.setId(resultSet.getInt("id"));
				s.setName(resultSet.getString("salesmanName"));
				s.setBoss(resultSet.getBoolean("boss"));
				s.setLoanLimit(resultSet.getInt("loanLimit"));
				System.out.println(s.getName());
				return s;
				
			} else
				return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}

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
			openConnection();
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
		Customer c = new Customer();
		try {
			openConnection();
			String sql = "select * from customer where phone=" + phone;
			System.out.println(sql);

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			if (resultSet.next()) {
				c.setAdress(resultSet.getString("adress"));
				c.setPhone(resultSet.getInt("phone"));
				c.setName(resultSet.getString("name"));
				c.setCPR(resultSet.getString("CPR"));
				c.setHasActiveOffer(resultSet.getBoolean("hasActivLoan"));
				c.setRating(Rating.valueOf(resultSet.getString("creditRating")));

				return c;
			} else

				return null;
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public boolean InsertloanOffers(LoanOffer loanOffers) {
		try {openConnection();
			String sql = "INSERT INTO loanOffers VALUES (?, ?, ?, ?, ?, ?)";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, ""+loanOffers.getDate());
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

	@Override
	public Salesmen getSalemenNameBayName(String name) {
		Salesmen s = new Salesmen();
		try {openConnection();
			String sql = "select * from Salesmen where name=" + name;
			System.out.println(sql);

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			if (resultSet.next()) {
				s.setId(resultSet.getInt("id"));
				s.setChef(resultSet.getBoolean("chef"));
				s.setName(resultSet.getString("name"));
				return s;
			} else
				return null;
		} catch (SQLException e) {
			return null;
		}
	}

}

package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.*;

public class DataLayerImp implements DataLayer { // ansvar:Shahnaz review:Martin

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

			String sql = "SELECT * FROM cars ";

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
		try {
			openConnection();
			String sql = "select * from customers where phone = " + phone;

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			if (resultSet.next()) {
				c.setAddress(resultSet.getString("customerAddress"));
				c.setPhone(resultSet.getInt("phone"));
				c.setName(resultSet.getString("customerName"));
				c.setPostalCode(resultSet.getInt("postalCode"));
				c.setCPR(resultSet.getString("CPR"));
				c.setHasActiveOffer(resultSet.getBoolean("hasActiveLoan"));
				return c;
			} else

				return null;
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public boolean InsertloanOffers(LoanOffer loanOffers) {
		try {
			openConnection();
			String sql = "INSERT INTO loanOffers VALUES (?,?,?,?,?,?,?,?)";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setDouble(1, loanOffers.getAnnualCost());
			statement.setInt(2, loanOffers.getDownPayment());
			statement.setDouble(3, loanOffers.getRepayments());
			statement.setInt(4, loanOffers.getNumberOfMonths());
			statement.setInt(5, loanOffers.getCostumer().getPhone());
			statement.setInt(6, loanOffers.getCar().getId());
			statement.setInt(7, loanOffers.getSalesman().getId());
			statement.setBoolean(8, loanOffers.isApproved());
			return statement.executeUpdate() == 1;

		} catch (SQLException e) {
			return false;
		}

	}

	@Override
	public Salesman getSalesmanByName(String name) {
		Salesman s = new Salesman();
		try {
			openConnection();
			String sql = "select * from salesmen where salesmanName = '" + name + "'" + "" + "";

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			if (resultSet.next()) {
				s.setId(resultSet.getInt("id"));
				s.setName(resultSet.getString("salesmanName"));
				s.setBoss(resultSet.getBoolean("boss"));
				s.setLoanLimit(resultSet.getInt("loanLimit"));
				return s;

			} else
				return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<LoanOffer> getAllloanOffersByApproved(boolean approved) {
		ArrayList<LoanOffer> loanOffers = new ArrayList<LoanOffer>();
		try {
			openConnection();

			String sql = "select * from loanOffers where isApproved=" + convertBooleanToByte(approved);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				LoanOffer l = new LoanOffer(resultSet.getInt("id"), resultSet.getDouble("annualCost"),
						resultSet.getInt("downPayment"), resultSet.getDouble("repayments"),
						resultSet.getInt("noOfMonths"), getCustomerByPhone(resultSet.getInt("customerPhone")),
						getCarById(resultSet.getInt("CarId")), getSalsmanById(resultSet.getInt("SalesmanId")));
				loanOffers.add(l);
			}
			return loanOffers;

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return loanOffers;
	}

	@Override
	public Salesman getSalsmanById(int id) {
		Salesman s = new Salesman();
		try {
			openConnection();
			String sql = "select * from salesmen where id = " + id;

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			if (resultSet.next()) {
				s.setId(resultSet.getInt("id"));
				s.setName(resultSet.getString("salesmanName"));
				s.setBoss(resultSet.getBoolean("boss"));
				s.setLoanLimit(resultSet.getInt("loanLimit"));
				return s;

			} else
				return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Car getCarById(int id) {
		Car car = new Car();
		try {
			openConnection();
			String sql = "select * from cars where id = " + id;

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			if (resultSet.next()) {
				car.setId(resultSet.getInt("id"));
				car.setModel(resultSet.getString("model"));
				car.setPrice(resultSet.getInt("price"));
				return car;

			} else
				return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private byte convertBooleanToByte(boolean b) {
		if (b)
			return 1;
		else
			return 0;
	}

	@Override
	public Salesman getSalesmanByBoss(boolean boss) {
		Salesman s = new Salesman();
		try {
			openConnection();
			String sql = "select * from salesmen where boss = " + convertBooleanToByte(boss);

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			if (resultSet.next()) {
				s.setId(resultSet.getInt("id"));
				s.setName(resultSet.getString("salesmanName"));
				s.setBoss(resultSet.getBoolean("boss"));
				s.setLoanLimit(resultSet.getInt("loanLimit"));
				return s;
			} else
				return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean updateLoanOfferById(LoanOffer loanOffer) {
		openConnection();

		try {
			Statement statement = connection.createStatement();
			String sql = "UPDATE loanOffers SET isApproved =" + convertBooleanToByte(loanOffer.isApproved())
					+ " where id=" + loanOffer.getId();

			return statement.executeUpdate(sql) == 1;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateCustomerByHasOffer(Customer customer) {
		openConnection();

		try {
			Statement statement = connection.createStatement();
			String sql = "UPDATE customers SET hasActiveLoan = " + convertBooleanToByte(customer.hasActiveOffer())
					+ "WHERE phone = " + customer.getPhone();
			return statement.executeUpdate(sql) == 1;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}
}

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

public class DataLayer { // ansvar:Shahnaz review:Martin
	private static DataLayer inst = null;
	private Connection connection;
	
	//need for singleton
	public static DataLayer instance() {
		if (inst == null)
			inst = new DataLayer();
		
		return inst;
	}
	//needed for singleton
	private DataLayer() {
		openConnection();
	}
	/**
	 * private method for opening a connection to our MSSQL server
	 */
	private void openConnection() {
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
	/**
	 * private method used for closing statements when accessing the database
	 * @param statement The Statement we want to close
	 */
	private void closeStatement(Statement statement) {
		try {
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * private method for converting boolean to bytes used in the database
	 * @param b The boolean we want to convert
	 * @return 1 for true 0 for false
	 */
	private byte convertBooleanToByte(boolean b) {
		if (b)
			return 1;
		else
			return 0;
	}
	
	/**
	 * Gets all cars currently in the database 
	 * @return ArraList containing all cars in the database
	 */
	public ArrayList<Car> getAllCars() {
		Statement statement = null;
		ArrayList<Car> cars = new ArrayList<Car>();
		try {
			statement = connection.createStatement();

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
		}finally {
			closeStatement(statement);
		}

		return cars;
	}

	/**
	 * used to get a customer from the database based on a phone number
	 * @param phone the phone number we want to look up in the database	
	 * @return a Customer object from the database
	 */
	public Customer getCustomerByPhone(int phone) {
		Customer c = new Customer();
		Statement statement = null;
		try {
			String sql = "select * from customers where phone = " + phone;

			statement = connection.createStatement();
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
		}finally {
			closeStatement(statement);
		}
	}

	/**
	 * inserts a loanoffer into the database
	 * @param loanOffers The loan offer we want to insert into the database
	 * @return returns true if success false if unsuccessful
	 */
	public boolean InsertloanOffers(LoanOffer loanOffers) {
		PreparedStatement statement = null;
		try {
			String sql = "INSERT INTO loanOffers VALUES (?,?,?,?,?,?,?,?)";

			statement = connection.prepareStatement(sql);

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
		}finally {
			closeStatement(statement);
		}

	}

	/**
	 * Gets a salesman based on his name from the database
	 * @param name The name of the salesman we want to look up
	 * @return Salesman object associated with the name from the parameter
	 */
	public Salesman getSalesmanByName(String name) {
		Salesman s = new Salesman();
		Statement statement = null;
		try {
			String sql = "select * from salesmen where salesmanName = '" + name + "'" + "" + "";

			statement = connection.createStatement();
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
		}finally {
			closeStatement(statement);
		}
	}

	/**
	 * Gets all loanoffers based on whether they are approved or not
	 * @param approved only 2 options true or false
	 * @return a list of loanoffers that are either approved or not
	 */
	public List<LoanOffer> getAllloanOffersByApproved(boolean approved) {
		ArrayList<LoanOffer> loanOffers = new ArrayList<LoanOffer>();
		Statement statement = null;
		try {

			String sql = "select * from loanOffers where isApproved=" + convertBooleanToByte(approved);
			statement = connection.createStatement();
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

		}finally {
			closeStatement(statement);
		}
		return loanOffers;
	}

	/**
	 * get a salesman by his unique autogenerated id from the database
	 * @param id the unique id of the salesman
	 * @return Salesman object based on the id
	 */
	public Salesman getSalsmanById(int id) {
		Salesman s = new Salesman();
		Statement statement = null;
		try {
			String sql = "select * from salesmen where id = " + id;

			statement = connection.createStatement();
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
		}finally {
			closeStatement(statement);
		}
	}

	/**
	 * gets a car based on its unique id in the database
	 * @param id the unique id of the car
	 * @return Car object based on the id
	 */
	public Car getCarById(int id) {
		Car car = new Car();
		Statement statement = null;
		try {
			String sql = "select * from cars where id = " + id;

			statement = connection.createStatement();
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
		}finally {
			closeStatement(statement);
		}
	}

	/**
	 * used to look up the boss in the database
	 * Note only really usable to look up the boss because there are several salesmen who arent bosses
	 * @param boss boolean value only really usefull if set to true
	 * @return return salesman based on the boss value in the database
	 */
	
	public Salesman getSalesmanByBoss(boolean boss) {
		Salesman s = new Salesman();
		Statement statement = null;
		try {
			String sql = "select * from salesmen where boss = " + convertBooleanToByte(boss);

			statement = connection.createStatement();
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
		}finally {
			closeStatement(statement);
		}
	}

	/**
	 * used to update a loan offer in the database based on its unique id.
	 * @param loanOffer the loanoffer we want to update, the method will automatically extract the id
	 * @return true if it succeeded false if not
	 */
	public boolean updateLoanOfferById(LoanOffer loanOffer) {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			String sql = "UPDATE loanOffers SET isApproved =" + convertBooleanToByte(loanOffer.isApproved())
					+ " where id=" + loanOffer.getId();

			return statement.executeUpdate(sql) == 1;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeStatement(statement);
		}
	}

	/**
	 * updates the customers hasActiveLoan atribute in the database
	 * @param customer the customer we want to update
	 * @return true if update succeeded, false if not
	 */
	public boolean updateCustomerByHasOffer(Customer customer) {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			String sql = "UPDATE customers SET hasActiveLoan = " + convertBooleanToByte(customer.hasActiveOffer())
					+ "WHERE phone = " + customer.getPhone();
			return statement.executeUpdate(sql) == 1;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			closeStatement(statement);
		}

	}
}

package Data;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import Logic.Cars;

public class DataLayer implements DataLayerInterFace{
	 

	public void openConnection() {
		try {
			
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			} catch (ClassNotFoundException e) {
						
			System.exit(0);
		}
	
		try {
			
			DriverManager.getConnection("jdbc:sqlserver://localhost:1433;" + "instanceName=SQLEXPRESS;" + "databaseName="
					+ "FFSDB" + ";" + "integratedSecurity=true;");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Cars> getAllCars() {
		// TODO Auto-generated method stub
		return null;
	}
}

package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataLayer {
	 

	public void openConnection() {
		try {
			
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			} catch (ClassNotFoundException e) {
						
			System.exit(0);
		}

		
		String databaseName = "FFSDB";

		String connectionString = "jdbc:sqlserver://localhost:1433;" + "instanceName=SQLEXPRESS;" + "databaseName="
				+ databaseName + ";" + "integratedSecurity=true;";
		try {
			
			DriverManager.getConnection(connectionString);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

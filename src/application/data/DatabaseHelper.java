package application.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {
	private static final String DB_URL = "jdbc:sqlite:loopo.db";
	
	
	public static Connection connect() throws SQLException{
		
		return DriverManager.getConnection(DB_URL);
	}
	
	public static void initializeDatabase() {
		try(Connection conn = connect();
				Statement stmt = conn.createStatement()) {
			
			
			String sql = "CREATE TABLE IF NOT EXISTS users (" + 
					"username TEXT PRIMARY KEY, " +
					"password TEXT NOT NULL)";
			stmt.execute(sql);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}

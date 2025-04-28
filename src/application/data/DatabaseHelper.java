package application.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * Helper class for managing the SQLite database connection
 * and initializing necessary tables for the Loopo application.
 */

public class DatabaseHelper {
	
	/*JDBC URL for connecting to the SQLite database file 'loopo.db'*/
	private static final String DB_URL = "jdbc:sqlite:loopo.db";
	
	/*@return Connection object to interact with the database*/
    /*@throws SQLException if a database access error occurs*/
	public static Connection connect() throws SQLException{
		// Create a connection to the SQLite database using the DB_URL
		return DriverManager.getConnection(DB_URL);
	}
	
	
	/*
     * Initializes the database by creating necessary tables
     * if they do not already exist. Currently creates the 'users' table.
     */
	public static void initializeDatabase() {
		try(Connection conn = connect();
				//Use try-with-resources to ensure Connection and Statement are closed automatically
				Statement stmt = conn.createStatement()) {
			
			// SQL statement to create the 'users' table with 'username' as primary key
			String sql = "CREATE TABLE IF NOT EXISTS users (" + 
					"username TEXT PRIMARY KEY, " +
					"password TEXT NOT NULL)";
			// Execute the table creation statement
			stmt.execute(sql);
			
		}catch(SQLException e) {
			// Print stack trace for any SQL errors during initialization
			e.printStackTrace();
		}
	}
}

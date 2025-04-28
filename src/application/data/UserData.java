package application.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Utility class for user authentication and management in the Loopo application.
 * Provides methods to verify credentials, fetch user IDs, and register new users.
 */
public class UserData {
	
	/*
     * Verifies whether the provided username and password match an entry in the users table.
     *
     * @param username the username to check
     * @param password the corresponding password
     * @return true if credentials are valid; false otherwise or on error
     */
	public static boolean checkCredentials(String username, String password) {
		String query = "SELECT * FROM users WHERE username = ? AND password = ?";
		
		
		try(Connection conn = DatabaseHelper.connect();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			
			// Bind username and password parameters
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			// Execute query and return whether a matching record exists
			ResultSet rs = pstmt.executeQuery();
			return rs.next();
			
		} catch (SQLException e) {
			// Print stack trace for debugging and return false on error
			e.printStackTrace();
			return false;
		}
	}
	
	/*
     * Retrieves the unique database ID for a given username.
     *
     * @param username the username whose ID to fetch
     * @return user ID if found; -1 if not found or on error
     */
	public static int getUserId(String username) {
		int id = -1;
		String sql = "SELECT id FROM Users WHERE username = ?";
		
		try(Connection conn = DriverManager.getConnection("jdbc:sqlite:loopo.db");
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			 // Bind username parameter
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			
			// If a record is found, extract the ID
			if(rs.next()) {
				id = rs.getInt("id");
			}
		}catch ( SQLException e) {
			// Print stack trace for debugging
			e.printStackTrace();
		}
		
		return id;
	}
	
	/*
     * Registers a new user by inserting their username and password into the database.
     *
     * @param username the new user's username
     * @param password the new user's password
     * @return true if registration succeeds; throws SQLException on failure
     * @throws SQLException if a database error occurs during registration
     */
	public static boolean registerUser(String username, String password) throws SQLException {
		String query = "INSERT INTO users(username, password) VALUES(?, ?)";
		
		
		try(Connection conn = DatabaseHelper.connect();
				PreparedStatement pstmt = conn.prepareStatement(query)){
			// Bind parameters and execute insertion
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.executeUpdate();
			return true;
			
		}catch(SQLException e) {
			// Wrap and rethrow exception with contextual message
			 throw new SQLException("Database error: " + e.getMessage());
		}
	}
	
}

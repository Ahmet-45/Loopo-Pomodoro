package application.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserData {
	public static boolean checkCredentials(String username, String password) {
		String query = "SELECT * FROM users WHERE username = ? AND password = ?";
		
		
		try(Connection conn = DatabaseHelper.connect();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			
			
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			
			
			return rs.next();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static boolean registerUser(String username, String password) throws SQLException {
		String query = "INSERT INTO users(username, password) VALUES(?, ?)";
		
		
		try(Connection conn = DatabaseHelper.connect();
				PreparedStatement pstmt = conn.prepareStatement(query)){
			
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.executeUpdate();
			return true;
			
		}catch(SQLException e) {
			 throw new SQLException("Database error: " + e.getMessage());
		}
	}
	
}

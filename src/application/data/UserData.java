package application.data;

import java.sql.Connection;
import java.sql.DriverManager;
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
	
	public static int getUserId(String username) {
		int id = -1;
		String sql = "SELECT id FROM Users WHERE username = ?";
		
		try(Connection conn = DriverManager.getConnection("jdbc:sqlite:loopo.db");
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				id = rs.getInt("id");
			}
		}catch ( SQLException e) {
			e.printStackTrace();
		}
		
		return id;
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

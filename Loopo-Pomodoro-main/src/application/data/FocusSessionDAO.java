package application.data;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
 * Data Access Object (DAO) for FocusSession entities.
 * Handles all database operations related to focus and break sessions.
 */
public class FocusSessionDAO {
	//Establishes and returns a Connection to the SQLite database 'loopo.db'.
	//@return Connection to the Loopo database
	//@throws SQLException if a database access error occurs
	private Connection connect() throws SQLException{
		
		return DriverManager.getConnection("jdbc:sqlite:loopo.db");
	}
	
	/*
     * Inserts a new focus or break session into the FocusSession table.
     * @param session FocusSession object containing session details
     */
	public void insertSession(FocusSession session) {
		
		String sql = "INSERT INTO FocusSession (user_id, start_time, end_time, duration, is_break) VALUES (?, ?, ?, ?, ?)";
		
		
		try(Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
			// Set parameters: userId, startTime, endTime, duration, isBreak flag
			pstmt.setInt(1, session.getUserId());
			pstmt.setString(2, session.getStartTime());
			pstmt.setString(3, session.getEndTime());
			pstmt.setInt(4, session.getDuration());
			pstmt.setInt(5, session.isBreak() ? 1 : 0);
			
			pstmt.executeUpdate();
			System.out.println("The session has been recorded successfully.");
		}catch(SQLException e) {
			System.out.println("An error occurred while saving the session:");
			e.printStackTrace();
		}
	}
	
	/*
     * Returns the total focus duration in minutes for the current day.
     * @param userId ID of the user
     * @return total duration in minutes (0 if none or on error)
     */
	
	public int getTodayFocusDuration(int userId) {
		String sql = "SELECT SUM(duration) FROM FocusSession WHERE user_id = ? AND is_break = 0 AND date(start_Time) = date('now')";
		try(Connection conn = connect();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setInt(1, userId);
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					return rs.getInt(1);
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/*
     * Retrieves all focus and break sessions for a specific date.
     * @param userId ID of the user
     * @param date   date for which to fetch sessions
     * @return list of FocusSession objects
     */
	public List<FocusSession> getSessionsByDate(int userId, LocalDate date) {
		List<FocusSession> sessions = new ArrayList<>();
			String sql = "SELECT user_id, start_time, end_time, duration, is_break FROM FocusSession "+
						 "WHERE user_id = ? AND date(start_time) = ?";
			try(Connection conn = connect();
					PreparedStatement pstmt = conn.prepareStatement(sql)){
				pstmt.setInt(1, userId);
				pstmt.setString(2, date.toString());
				try(ResultSet rs = pstmt.executeQuery()){
					while(rs.next()) {
						int uid = rs.getInt("user_id");
						String startTime = rs.getString("start_time");
	                    String endTime = rs.getString("end_time");
	                    int duration = rs.getInt("duration");
	                    boolean isBreak = rs.getInt("is_break") == 1;
	                    sessions.add(new FocusSession(uid, startTime, endTime, duration, isBreak));
					}
				}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return sessions;
	}
}

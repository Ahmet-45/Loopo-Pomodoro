package application.data;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FocusSessionDAO {
	
	private Connection connect() throws SQLException{
		return DriverManager.getConnection("jdbc:sqlite:loopo.db");
	}
	
	public void insertSession(FocusSession session) {
		
		String sql = "INSERT INTO FocusSession (user_id, start_time, end_time, duration, is_break) VALUES (?, ?, ?, ?, ?)";
		
		
		try(Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
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

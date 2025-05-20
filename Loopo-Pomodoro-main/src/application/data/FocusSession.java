package application.data;

public class FocusSession {
	private int id;
	private int userId;
	private String startTime;
	private String endTime;
	private int duration;
	private boolean isBreak;
	
	
	public FocusSession(int userId, String startTime, String endTime, int duration, boolean isBreak) {
		this.userId = userId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.duration = duration;
		this.isBreak = isBreak;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	public int getDuration() {
		return duration;
	}


	public void setDuration(int duration) {
		this.duration = duration;
	}


	public boolean isBreak() {
		return isBreak;
	}


	public void setBreak(boolean isBreak) {
		this.isBreak = isBreak;
	}
	
	
	
}

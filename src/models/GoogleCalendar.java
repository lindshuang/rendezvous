package models;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class GoogleCalendar {
	
	//one event, or block of "busy" time
	private String userId;
	private int yearId;
	private int monthId;
	private int dateId;
	private LocalTime startTime;
	private LocalTime endTime;
		
	public GoogleCalendar(String userId, int yearId, int monthId, int dateId, String startTime, String endTime) {
		this.userId = userId;
		this.yearId = yearId;
		this.monthId = monthId;
		this.dateId = dateId;
		this.startTime = LocalTime.parse(startTime.substring(0,8), DateTimeFormatter.ofPattern("HH:mm:ss"));
		this.endTime = LocalTime.parse(endTime.substring(0,8), DateTimeFormatter.ofPattern("HH:mm:ss"));
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getYearId() {
		return yearId;
	}

	public void setYearId(int yearId) {
		this.yearId = yearId;
	}

	public int getMonthId() {
		return monthId;
	}

	public void setMonthID(int monthId) {
		this.monthId = monthId;
	}

	public int getDateId() {
		return dateId;
	}

	public void setDateId(int dateId) {
		this.dateId = dateId;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

}

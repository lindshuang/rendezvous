package models;

import java.util.ArrayList;

public class Schedule {
	
	//arraylist of GoogleCalendar objects
	private ArrayList<GoogleCalendar> calendarList;

	public Schedule() {
		calendarList = new ArrayList<>();
	}

	public void addCalendar(String userId, int yearId, int monthId, int dateId, String startTime, String endTime) {
		calendarList.add(new GoogleCalendar(userId, yearId, monthId, dateId, startTime, endTime));
	}

	public void pushCalendar(GoogleCalendar calendar) {
		calendarList.add(calendar);
	}

	public ArrayList<GoogleCalendar> getCalendarList() {
		return calendarList;
	}

}

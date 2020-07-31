package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import db.DBProcessor;

/**
 * Servlet implementation class AuthServlet
 */
@WebServlet("/AuthServlet")
public class AuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AuthServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("AuthServlet - doGet");
		String code = request.getParameter("code");
		String email = request.getParameter("email");
		
		String url = request.getRequestURL().toString();
		url = url.substring(0, url.indexOf("AuthServlet"));
		System.out.println("MYURL:  " + url);
	

		URL obj = new URL("https://accounts.google.com/o/oauth2/token");
		HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

		// Create header
		connection.setRequestMethod("POST");

		connection.setDoOutput(true);

		// Build body
		String inputString = "grant_type=authorization_code&" + "code=" + code + "&"
				+"redirect_uri=" + "http://localhost:8080/Rendezvous/index.jsp&"
				+ "client_id=716240911606-iurfuk50hasuf1b6rhg9ppcia90114dm.apps.googleusercontent.com&"
				+ "client_secret=gCrGiixPJLchYsligghdSXpq";

		// Write to body
		try (OutputStream os = connection.getOutputStream()) {
			byte[] input = inputString.getBytes("utf-8");
			os.write(input, 0, input.length);
		}

		connection.connect();

		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String responseString = in.lines().collect(Collectors.joining("\n"));
		System.out.println(responseString);
		JSONObject responseObject = new JSONObject(responseString);
		String authToken = responseObject.getString("access_token");
		getCalendar(authToken, email, response, request);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public void getCalendar(String authToken, String id, HttpServletResponse responseHttp, HttpServletRequest request) {

		System.out.println("AuthServlet - getCalendar");

		String url = "https://www.googleapis.com/calendar/v3/freeBusy";
		String userAuth = authToken;
		LocalDateTime start = LocalDateTime.now();
		LocalDateTime end = start.plusDays(35);
		String calendarID = id;
		Connection conn = null;

		try {

			// Make API call to Google Calendar freebusy endpoint
			URL obj = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

			// Create header
			connection.setRequestProperty("Authorization", "Bearer " + userAuth);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");

			connection.setDoOutput(true);

			// Build body
			String jsonInputString = "{\"timeMax\": \"" + end + "Z\", \"timeMin\": \"" + start.toString() + "Z\","
					+ " \"timeZone\":" + "'America/Los_Angeles'," +
					" \"items\": [{\"id\": \"" + calendarID + "\"}]}";

			// Write to body
			try (OutputStream os = connection.getOutputStream()) {
				byte[] input = jsonInputString.getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			connection.connect();

			// Get response
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String responseString = in.lines().collect(Collectors.joining("\n"));

			JSONObject response = new JSONObject(responseString);
			JSONObject calendars = response.getJSONObject("calendars");
			JSONObject isolatedCalendar = calendars.getJSONObject(calendarID);
			JSONArray busyTimes = isolatedCalendar.getJSONArray("busy");

			// Loop through each event and create pairs of start and end times
			ArrayList<TimeInterval> busyTimeList = new ArrayList<TimeInterval>();
			for (int i = 0; i < busyTimes.length(); ++i) {
				String s = busyTimes.getJSONObject(i).getString("start");
				String y = s.substring(0, 4);
				String m = s.substring(5, 7);
				String d = s.substring(8, 10);
				s = s.substring(11, 19);
				String e = busyTimes.getJSONObject(i).getString("end");
				e = e.substring(11, 19);
				busyTimeList.add(new TimeInterval(s, e, d, m, y));
			}

			DBProcessor db = new DBProcessor();
			conn = db.getConnection();

			int userID = 0;
			PreparedStatement ps2 = conn.prepareStatement("SELECT u.userID FROM cal.users u WHERE email=?");
			ps2.setString(1, id);
			ResultSet rs2 = ps2.executeQuery();
			if (rs2.next()) {
				userID = rs2.getInt("userID");
			}

			rs2.close();
			System.out.println(userID);

			for (TimeInterval t : busyTimeList) {
				System.out.println(userID);
				System.out.println("Start: " + t.start + ", End: " + t.end + ", Day: " + t.day + ", Month: " + t.month
						+ ", Year: " + t.year);
				PreparedStatement psCalendar = conn.prepareStatement("INSERT INTO cal.schedule (userID, "
						+ "yearID, monthID, dateID, startTime, endTime) VALUES (?, ?, ?, ?, ?, ?)");

				psCalendar.setInt(1, userID);
				psCalendar.setInt(2, Integer.parseInt(t.year));
				psCalendar.setInt(3, Integer.parseInt(t.month));
				psCalendar.setInt(4, Integer.parseInt(t.day));
				psCalendar.setString(5, t.start);
				psCalendar.setString(6, t.end);
				psCalendar.execute();
			}

			HttpSession session = request.getSession(true);
			String userStatus = (String) session.getAttribute("USERSTATUS");
			System.out.println("getCalendar - Insert data into table!");
			PrintWriter out = responseHttp.getWriter();
			out.println(userStatus);
			out.flush();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception ex) {

			}
		}
	}

}

class TimeInterval {
	String start, end, day, month, year;

	public TimeInterval(String s, String e, String d, String m, String y) {
		start = s;
		end = e;
		day = d;
		month = m;
		year = y;
	}
}

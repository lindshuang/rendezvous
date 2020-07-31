package db;

/* call google API
  {
  "timeMin": "2020-03-20T00:00:00.000z",
  "timeMax": "2020-03-25T00:00:00.000z",
  "timeZone": "GMT-5:00",
  "items": [
    {
      "id": "lindsayjhuang@gmail.com"
    }
  ]
}
 */
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import models.Schedule;

public class DBProcessor {

	private final String username = "root"; // change to your own username
	private final String password = "root"; // change to your own password
	private final String driver = "com.mysql.jdbc.Driver";
	private final String url = "jdbc:mysql://localhost/cal?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	public Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(this.driver);
			connection = DriverManager.getConnection(this.url, this.username, this.password);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return connection;
	}

	private ArrayList<String> queryGroupTable(String email) {

		ArrayList<String> groups = new ArrayList<String>();
		Statement statement = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			conn = getConnection();
			StringBuffer sql = new StringBuffer();

			if (email == null) {
				sql.append("SELECT distinct(groupName) FROM groups");
			} else {
				// sql statement: return all groups associated with the user ID
				sql.append("SELECT groups.groupName ");
				sql.append("FROM users, groups ");
				sql.append("WHERE users.userID = groups.userID AND users.email =" + "'" + email + "'");
				sql.append("order by groupName;");
			}

			System.out.println("sql => " + sql.toString());

			// execute query
			statement = conn.createStatement();
			rs = statement.executeQuery(sql.toString()); // sql query

			// read rows
			while (rs.next()) {
				String groupName = rs.getString(1);
				groups.add(groupName);
			}

		} catch (SQLException sqle) {
			sqle.getStackTrace();
		} finally {
			try {
				rs.close();
				statement.close();
				conn.close();// close connection
			} catch (Exception ex) {
			}
		}

		return groups;
	}

	private String queryName(String email) {

		Statement statement = null;
		ResultSet rs = null;
		Connection conn = null;
		String name = "";

		try {
			conn = getConnection();
			StringBuffer sql = new StringBuffer();

			// sql statement: return all groups associated with the user ID
			sql.append("SELECT users.fname, users.lname ");
			sql.append("FROM users ");
			sql.append("WHERE users.email = '").append(email).append("';");
			System.out.println("sql => " + sql.toString());

			// execute query
			statement = conn.createStatement();
			rs = statement.executeQuery(sql.toString()); // sql query

			// read rows
			while (rs.next()) {
				name = rs.getString(1) + " " + rs.getString(2);
			}

		} catch (SQLException sqle) {
			sqle.getStackTrace();
		} finally {
			try {
				rs.close();
				statement.close();
				conn.close();// close connection
			} catch (Exception ex) {
			}
		}
		return name;
	}

	private Schedule queryCalendarTable(LocalDate localDate, String userID, String groupID) {

		Schedule schedule = new Schedule();
		Statement statement = null;
		ResultSet rs = null;
		Connection conn = null;

		try {

			conn = getConnection();

			int year = localDate.getYear();
			int month = localDate.getMonthValue();
			int day = localDate.getDayOfMonth();

			System.out.println("============ DBProcessor ========");
			System.out.println("year: " + year);
			System.out.println("month: " + month);
			System.out.println("day: " + day);

			// make sql query
			StringBuffer sql = new StringBuffer();

			sql.append("SELECT groups.userID, email, yearID, monthID, dateID, startTime, endTime, groups.groupName ");
			sql.append("FROM schedule ");
			sql.append("JOIN users ON schedule.userID = users.userID ");
			sql.append("JOIN groups ON schedule.userID = groups.userID ");
			sql.append("WHERE ");

			// date
			// sql.append("WHERE yearID = 2020 and monthID = 4 and dateID >= 1 and dateID <=
			// 31");
			if (day >= 23) {
				sql.append("(monthID = ").append(month);
				sql.append(" or monthID = ").append(month + 1).append(")");
				sql.append(" and (dateID >= ").append(day);
				sql.append(" or dateID <= ").append(7).append(")");

			} else if (day >= 1 && day <= 31) {
				sql.append("monthID = ").append(month);
				sql.append(" and dateID >= ").append(1);
				sql.append(" and dateID <= ").append(31);
			}

			// groupName
			if (groupID == null) {
				sql.append(" AND groupName = (SELECT groupName FROM groups WHERE groups.userID = ");
				sql.append("(SELECT users.userID FROM users WHERE users.email = ").append("'").append(userID)
						.append("' ) order by groupName LIMIT 1) ");
			} else {
				sql.append(" AND groups.groupName = ").append("'").append(groupID).append("'");
			}

			sql.append(" order by monthID, dateID, startTime;");

			System.out.println("sql => " + sql.toString());

			statement = conn.createStatement();
			rs = statement.executeQuery(sql.toString()); // sql statement as strubg

			// Store calendar rows
			while (rs.next()) {
				schedule.addCalendar(rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getString(6),
						rs.getString(7));
			}

		} catch (SQLException sqle) {
			sqle.getStackTrace();
		} finally {
			try {
				rs.close();
				statement.close();
				conn.close();// close connection
			} catch (Exception ex) {

			}
		}

		return schedule;
	}

	// This is only method that gets called by other classes
	public Schedule getSchedule(LocalDate localDate, String userID, String groupID) {

		return queryCalendarTable(localDate, userID, groupID);

	}

	public ArrayList<String> getGroups(String email) {
		return queryGroupTable(email);
	}

	public String getName(String email) {
		return queryName(email);
	}

}

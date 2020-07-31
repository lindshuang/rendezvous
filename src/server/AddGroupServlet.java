package server;

import java.sql.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.DBProcessor;

/**
 * Servlet implementation class AddGroupServlet
 */
@WebServlet("/AddGroupServlet")
public class AddGroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = null;

		try {

			// new connection
			DBProcessor db = new DBProcessor();
			conn = db.getConnection();

			// Fill out the user and password for MySql database.
			Statement st = conn.createStatement();
			ResultSet rs = null;

			String groupName = request.getParameter("groupName");
			String[] groupMember = request.getParameterValues("member");
			
//			System.out.println("EMPTY? " + groupMember.length);

			// Inserting new group.s
			StringBuilder sb = new StringBuilder();
			if (groupMember!= null) {
			for (int i = 0; i < groupMember.length; i++) {
				sb.append(groupMember[i]);
				if (i != groupMember.length - 1) {
					sb.append(", ");
				}
			}
			}
			
			System.out.println("Inserting records into the table...");

			HttpSession session = request.getSession(true);
			String email = (String) session.getAttribute("USERID");
			String sqlUserID = "SELECT users.userID FROM users WHERE users.email = '" + email + "'";
			System.out.println("sqlUserID => " + sqlUserID);
			rs = st.executeQuery(sqlUserID);
			int userID = 0;
			if (rs.next()) {
				userID = rs.getInt(1);
			}
			

			String sqlYourself = "INSERT INTO groups (userID, groupName) VALUES ('" + userID + "', '" + groupName
					+ "')";

			System.out.println("sqlYourself => " + sqlYourself);
			st.execute(sqlYourself);
			System.out.println("SQL insert new user into group: " + sqlYourself);
			
		
			if(groupMember!=null) {
			for (int i = 0; i < groupMember.length; i++) {
				// get user
				String sqlUser = "SELECT users.userID FROM users WHERE users.email = '" + groupMember[i] + "';";
				System.out.println("SQL get user: " + sqlUser);
				rs = st.executeQuery(sqlUser);
				rs.next();
				
				//check if sqlUser 
				if (rs.getInt("userID") != userID) {
									
					// add into db
					String sqlGroup = "INSERT INTO groups (userID, groupName) VALUES (" + rs.getString(1) + ", '"
							+ groupName + "');";
					st.execute(sqlGroup);
					System.out.println("SQL insert new user into group: " + sqlGroup);
					
					}
				}
			}

			// Display the successful result/ redirects to the home page.
			response.sendRedirect(request.getContextPath() + "/schedule.jsp?groupname=" + groupName);

		} catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
		}

		finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
	}
}

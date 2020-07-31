package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import db.DBProcessor;

@WebServlet("/EditGroupServlet")
public class EditGroupServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private PreparedStatement ps = null;
	private String error = "";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		try {

			DBProcessor db = new DBProcessor();
			conn = db.getConnection();

			st = conn.createStatement();
			PrintWriter out = response.getWriter();
			String email = request.getParameter("email");

			HttpSession session = request.getSession(true);
			String groupName = (String) session.getAttribute("GROUPID");

			System.out.println("EDIT GROUP-----------------------");
			System.out.println("CURRENT GROUP: " + groupName);
			System.out.println("EMAIL: " + email);

			// get userID from DB
			String getID = "SELECT userID FROM cal.users WHERE email = '" + email + "';";
			ps = conn.prepareStatement(getID);
			rs = ps.executeQuery();
			int id = 0;
			boolean isRegister = false;
			if (rs.next()) {
				id = rs.getInt("userID");
				System.out.println("ID of user: " + id);
				isRegister = true;
			} else {
				System.out.println("User not registered.");
				isRegister = false;
			}
			rs.close();
			
			if(isRegister) {
				// ERROR CHECK : user is already in the group
				ps = conn.prepareStatement(
						"SELECT * FROM cal.groups WHERE userID = " + id + " AND groupName = '" + groupName + "';");
				rs = ps.executeQuery();
				if (rs.next()) {
					error = "User already in the group";
					request.getServletContext().getRequestDispatcher("/schedule.jsp?Status="+error + "&groupname=" + groupName).forward(request, response);
				}
				rs.close();
				// ERROR CHECK : Too many in the group to add another
				int count = 0;
				ps = conn.prepareStatement("Select COUNT(*) FROM cal.groups WHERE groupName = '" + groupName + "';");
				rs = ps.executeQuery();
	
				if (rs.next()) {
					count = rs.getInt("COUNT(*)");
				}
	
				rs.close();
				if (count >= 10) {
					error = "This group is currently full";
					request.getServletContext().getRequestDispatcher("/schedule.jsp?Status="+error + "&groupname=" + groupName).forward(request, response);
				}

				// PASSES ERRORS - INSERT
				String insertSQL = "INSERT INTO cal.groups (userID, groupName)" + "VALUES (" + id + ", '" + groupName
						+ "');";
				st.execute(insertSQL);
				System.out.println("successfully added");
				String succ = "Successfully Added";

				request.getServletContext().getRequestDispatcher("/schedule.jsp?Status="+succ + "&groupname=" + groupName).forward(request, response);
			}
			else {
				System.out.println("User not registered.");
				error = "User not registered";
				request.getServletContext().getRequestDispatcher("/schedule.jsp?Status="+error + "&groupname=" + groupName).forward(request, response);
			}

		} catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
	}
}

package server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.DBProcessor;

/**
 * Servlet implementation class NewUserLandingServlet
 */
@WebServlet("/CreateGroupServlet")
public class CreateGroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateGroupServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// String name = (String) request.getSession().getAttribute("userName");
		String email = (String) request.getSession().getAttribute("email");
		String groupName = request.getParameter("newGroupId");

		Connection conn = null;

		try {

			DBProcessor db = new DBProcessor();
			conn = db.getConnection();

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM cal.groups u WHERE groupName=?");
			ps.setString(1, groupName);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				request.setAttribute("error", "Group already exists");
				RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/welcome-page.jsp");
				dispatch.forward(request, response);

			} else {
				// creating group
				int id = 0;
				PreparedStatement ps2 = conn.prepareStatement("SELECT u.userID FROM cal.users u WHERE email=?");
				ps2.setString(1, email);
				ResultSet rs2 = ps2.executeQuery();
				if (rs2.next()) {
					id = rs2.getInt("userID");
				}
				String query = "INSERT INTO cal.groups (userID, groupName) VALUES (?, ?)";
				PreparedStatement ps1 = conn.prepareStatement(query);
				ps1.setInt(1, id);
				ps1.setString(2, groupName);
				ps1.execute();

				rs2.close();
				RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/schedule.jsp");
				dispatch.forward(request, response);
			}

			rs.close();

		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception ex) {

			}
		}
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
}

package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import db.DBProcessor;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	private String name;
	private String email;

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		name = req.getParameter("name");
		System.out.println("name: " + name);
		email = req.getParameter("email");
		System.out.println("email: " + email);
		if (name != null) {
			try {
				// ========================
				addDataToDatabse(req, resp);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	public void addDataToDatabse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException,
			ClassNotFoundException, InstantiationException, IllegalAccessException {

		Connection conn = null;
		try {

			PrintWriter out = resp.getWriter();
			Gson gson = new Gson();

			DBProcessor db = new DBProcessor();
			conn = db.getConnection();

			// check if User exists
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM cal.users WHERE email=?");
			ps.setString(1, email);
			ResultSet rs = null;
			rs = ps.executeQuery();

			// Already exists in database
			if (rs.next() && rs.getInt(1) != 0) {
				// get user id
				System.out.println("LoginServlet - Already exists in database");
				// String userId = name;
				HttpSession session = req.getSession(true);
				session.setAttribute("userName", name);
				session.setAttribute("email", email);
				// ================================
				session.setAttribute("WEEKNUM", 0);
				session.setAttribute("USERID", email);
				session.setAttribute("USERNAME", name);
				session.setAttribute("USERSTATUS", "existing");

				out.print(gson.toJson("existing"));
				out.flush();
				out.close();
			}
			// New user
			else {
				String query = "INSERT INTO cal.users (email, fname, lname) VALUES (?, ?, ?)";
				PreparedStatement ps1 = conn.prepareStatement(query);
				ps1.setString(1, email);
				String[] names = name.split(" ");
				ps1.setString(2, names[0]);
				ps1.setString(3, names[1]);
				ps1.execute();

				System.out.println("Add new user");

				// Send to new user landing page
				HttpSession session = req.getSession(true);
				session.setAttribute("userName", name);
				session.setAttribute("email", email);
				// =================================
				session.setAttribute("WEEKNUM", 0);
				session.setAttribute("USERID", email);
				session.setAttribute("USERNAME", name);
				session.setAttribute("USERSTATUS", "new");

				out.print(gson.toJson("new"));
				out.flush();
				out.close();
			}

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
}

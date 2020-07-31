package server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//session
		HttpSession session = request.getSession(true);
			
		//set attributes to null
		session.setAttribute("WEEKNUM", 0);
		session.setAttribute("USERID", null);
		session.setAttribute("USERNAME", null);
		session.setAttribute("GROUPID", null);
		
		//redirect to 
		request.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);		

	}
}

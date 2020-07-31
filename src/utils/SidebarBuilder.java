package utils;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import db.DBProcessor;

public class SidebarBuilder {

	public String buildSidebar(String email, HttpServletRequest request, String currentGroup) {

		StringBuffer html = new StringBuffer();
		ArrayList<String> groups = new ArrayList<String>();
		String name;

		// call DBprocessor to get groups
		DBProcessor dbProcessor = new DBProcessor();
		groups = dbProcessor.getGroups(email);
		// sql query to get current username
		name = dbProcessor.getName(email);

		System.out.println("GROUP NAME QUERY ---> " + groups.toString());

		// write html

		// ---------------------------------------------
		html.append("<div id = \"sidebar\">");
		html.append("<div class = \"container sidebar-container\">");
		html.append("<div class = \"row\">");
		html.append("<div class = \"col-sm-12 user-info\">");
		html.append("<p class = \"username\"><i class=\"fas fa-user fa-xs user-icon\"></i>").append(name).append("</p>")
				.append("</div>");

		// save in session
		HttpSession session = request.getSession(true);

		// --create links to groups
		int count = 1;
		for (String gName : groups) {
			
			html.append("<div class = \"col-sm-12 side-col\">");
			
			if (currentGroup != null && currentGroup.length() > 0) {
				
				if (gName.equals(currentGroup)) {
					html.append("<a href=\"#\" class =\"nav-link active-link\" id=\"group1\" onclick=\"goGroup('");
				}else {
					html.append("<a href=\"#\" class =\"nav-link\" id=\"group1\" onclick=\"goGroup('");
				}
				
			}else {
				
				if (count == 1) {
					html.append("<a href=\"#\" class =\"nav-link active-link\" id=\"group1\" onclick=\"goGroup('");
					// set the group name in the session
					session.setAttribute("GROUPID", gName);
				} else {
					html.append("<a href=\"#\" class =\"nav-link\" id=\"group1\" onclick=\"goGroup('");
				}
				
			}
			
			html.append(gName).append("')\">");
			html.append("<i class=\"fas fa-table cal-icon\">");
			html.append("</i>").append(gName).append("</a>").append("</div>");
			count++;
		}

		// create add new group button
		html.append("<div class = \"col-sm-12 side-col\">");
		html.append("<a href=\"addGroup.jsp\" class = \"nav-link\" id=\"add-group\" onclick=\"goGroup('')\">");
		html.append("<i class=\"fas fa-plus cal-icon\"></i></i>Add New Group</a>").append("</div>");
		html.append("</div>");
//		html.append("</div>").append("</div>");
		
//		log out button
		html.append("<div class = \"row\">");
		html.append("<div class = \"col-sm-12 side-col logout-div\">");
		html.append("<form action = \"LogoutServlet\" method=\"POST\">");
		html.append("<button type =\"submit\" class =\"btn btn-logout\">LOG OUT</button>");
		html.append("</form>");
		html.append("</div>").append("</div>");
		
		html.append("</div>").append("</div>");


		return html.toString();
	}
}

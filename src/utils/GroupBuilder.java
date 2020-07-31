package utils;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import db.DBProcessor;

public class GroupBuilder {

	private GroupBuilder() {

	}

	public static GroupBuilder getInstance(HttpServletRequest request) throws InstantiationException {

		HttpSession session = request.getSession(true); // get session from request
		Object obj = session.getAttribute("GroupBuilder"); // returns Object w/ specified name
		GroupBuilder groiupObject = null;
		if (obj == null || !(obj instanceof GroupBuilder)) { // if not found, create new and set as attr
			groiupObject = new GroupBuilder();
			session.setAttribute("AjaxProcessor", groiupObject);
		} else {
			groiupObject = (GroupBuilder) obj; // else, get obj from session and cast
		}

		return groiupObject;
	}

	public static String setupGroup() {

		StringBuffer html = new StringBuffer();
		ArrayList<String> groups = new ArrayList<String>();

		// call DBprocessor to get groups
		DBProcessor dbProcessor = new DBProcessor();
		groups = dbProcessor.getGroups(null);

		System.out.println("GROUP NAME QUERY ---> " + groups.toString());

		// ---------------------------------------------
		html.append("<select id=\"groups\">");
		for (String gName : groups) {
			html.append("<option value=\"").append(gName).append("\">");
			html.append(gName).append("</option>");

		}
		html.append("</select>");

		return html.toString();
	
	}
}
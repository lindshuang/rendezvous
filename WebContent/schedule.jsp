<!-- schedule.jsp -->
<!-- directives give file instructions, like imports, libraries, etc -->
<%@page import="java.io.Console"%>
<%@ page import="java.util.*"%>
<%@ page import="utils.TableBuilder"%>
<%@ page import="utils.SidebarBuilder"%>
<!-- java bean encapsulates many objects into 1 object  and you can access it from wherever, good re-usability-->
<!-- use the TableBuilder Bean class as tableBeanId -->
<jsp:useBean id="tableBeanId" scope="page" class="utils.TableBuilder"></jsp:useBean>
<!-- SideBar Builder Bean -->
<jsp:useBean id="sidebarBeanId" scope="page" class="utils.SidebarBuilder"></jsp:useBean>
<%@ page import="ajax.AjaxProcessor"%>
<%
	//when jsp loads, get instance of current session from request
	//browser sends request to server (us)
	AjaxProcessor ajaxCall = AjaxProcessor.getInstance(request);
	String userID = ajaxCall.getUserIdFromSession(request);
	String groupname = ajaxCall.getGroupName(request);
	ajaxCall.resetWeekNum(request);
	
	String status = request.getParameter("Status")==null?"":request.getParameter("Status");
	System.out.println("status == " + status);
	//if userID = null, means not authenticated --> error page
%>
<!DOCTYPE html>
<html>
<head>
	<title>201 Final Project</title>
	
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
	<link rel="stylesheet" type="text/css" href="./css/styles.css">
	<script src="./js/ajaxSchedule.js"></script>
	<script src="https://kit.fontawesome.com/de79172ee1.js" crossorigin="anonymous"></script>
	<link href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap" rel="stylesheet">
</head>
<body>
	
	<!-- if user logged in -->
	<div id = "header">
		<!-- background image -->
	</div>
	
	<!-- if user not logged in -->
	<% if (userID == null){ %>
		<br>
			<div class = "guest-view"><i class="fas warning fa-exclamation-triangle"></i>You are currently viewing <span class ="g-name"><%=groupname%></span> in Guest Mode. Log in <a href="index.jsp">here</a>.</div>
		<br>
		
		<div id="schedule">
			<!-- default display / ajax calls-->
			<%=tableBeanId.buildScheduleTable("NOW", 0, userID, groupname)%>
		</div><!-- //end schedule -->
	
	<% }else{ %>
	
		<div id = "navbar">
		<%=sidebarBeanId.buildSidebar(userID, request, groupname)%>
		</div>
	

		<div id="schedule">
			<% if (status.equals("") ){ %>
	
			<%}else if (status.equals("Successfully Added")) {%>
					<div class = "success"><i class="fas warning fa-check-circle"></i><%= status %></div>
			<%}else{ %>
				<div class = "status"><i class="fas warning fa-exclamation-triangle"></i><%= status %></div>
			<%} %>
			
			<!-- default display / ajax calls-->
			<%=tableBeanId.buildScheduleTable("NOW", 0, userID, groupname)%>
		</div><!-- //end schedule -->
	
		<!-- popup -->
		<div id="loginPopup">
		      <div class="form-popup" id="popupForm">
		        <form action="EditGroupServlet" class="form-container" method="POST">
		          <h4 class = "add-title">Add a New Group Member<span class="close">x</span></h4>          
		          <hr>
					<label for="email">
						<strong>Add New Member</strong>
					</label>
					<div>
						<input type="email" placeholder="  tommytrojan@usc.edu" name="email" required>
					</div>
					<button type="submit" class="btn add">Add Member</button>
					<!-- MAILANI'S CODE FOR PASSING IN USER -->
					<input value="3" class ="hidden" name="user">
					<!-- MAILANI'S CODE FOR PASSING IN USER -->
					
		        </form>
		      </div>
		    </div>
	<% } %>
</body>
<script src="http://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
<script src = "./js/cal.js"></script>
</html>
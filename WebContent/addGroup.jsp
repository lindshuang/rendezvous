<!-- schedule.jsp -->
<!-- directives give file instructions, like imports, libraries, etc -->
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
	ajaxCall.resetWeekNum(request);
	//if userID = null, means not authenticated --> error page
%>
<!DOCTYPE html>
<html>
<head>
	<title>Create New Group</title>
	
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- new additions aboves -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
	<link rel="stylesheet" type="text/css" href="./css/addGroup-style.css"> 
<!-- 	<script src="./js/ajaxSchedule.js"></script>
	<script src="https://code.jquery.com/jquery-1.12.4.js"></script> -->
	<script src="https://kit.fontawesome.com/de79172ee1.js" crossorigin="anonymous"></script>
	<link href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap" rel="stylesheet">
</head>
<body>
	
	<!-- new -->
	<div class = "branding"><img src="./images/rendezvous-logo.png" class="logo"></div>
	
	<% if (userID == null){ %>
		<div class ="login-error">
			<h2>Error:</h2>
			<h4>You are not logged in.</h4>
			<img class = "logout-img" src=./images/logout.png>
		</div>
	<%}else{ %>

	<div class="header">
		<h3 class = "create-group-title"><a href="schedule.jsp"><i class="fas fa-chevron-left back-btn"></i></a>Create New Group</h3>
	</div> 

	<div class="container body-div">
		<form name = "creategroup" action="AddGroupServlet" method="POST">
			<div class="form-group row">
				<label for="group-name-id" class="col-4"> 
					<h4>Group Name</h4> 
				</label>
			</div> <!-- .form-group -->

			<div class="col-5 input-col">
				<input type="text" class="form-control" id="group-name-id" name="groupName" required>
			</div>
			
			<div class="form-group row">
				<label for="username-id" class="col-5"> 
					<h4>Add Group Members By Username</h4> 
				</label>
			</div> <!-- .form-group -->

			<div class="input-group col-5 input-col">
				<div class="input-group-prepend">
			    	<button class="btn btn-outline-success btn-add" type="button">+ ADD MEMBER</button>
			 	 </div>
			 	 <input type="text" class="form-control" id="username-id" name="username">
			</div>
			
			<div id = "error-div" class = "col-5">
			<!--  <p class = "error-msg">Invalid Email</p> -->
			</div>

			<div class="row add-member col-5">
				<ul id="added-member-list">

				</ul>
			</div>	

			<div class="col-5 input-col">
				<button type="submit" name="submit" value="Submit Form" class="btn btn-submit btn-primary">CREATE GROUP</button>
			</div>
			
		</form>

		<img class = "meeting-img" src="./images/meeting.jpg">

	</div> <!-- .container -->
	
	<%}%>

</body>
<script
	src="http://code.jquery.com/jquery-3.4.1.min.js"
  	integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
  	crossorigin="anonymous"></script>
  	<script src="https://kit.fontawesome.com/536488f33b.js" crossorigin="anonymous"></script>
	<script src="./js/addGroup.js"></script>

</html>
<script
	src="http://code.jquery.com/jquery-3.4.1.min.js"
  	integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
  	crossorigin="anonymous"></script>
  	<script src="https://kit.fontawesome.com/536488f33b.js" crossorigin="anonymous"></script>
	<script src="./js/addGroup.js"></script>

</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<link
	href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@1,600&display=swap"
	rel="stylesheet">
<title>New User Landing | Rendezvous</title>
<style>
body, html {
	padding: 0px;
	margin: 0px;
	overflow-y: hidden;
}

#cover {
	z-index: -1;
	width: 100%;
}

#header {
	position: fixed;
	top: 12%;
	left: 0;
	right: 0;
	font-family: 'Roboto', sans-serif;
	text-shadow: 3px 3px 18px white;
	text-align: center;
}

#header h1 {
	font-size: 42px;
}

#header p {
	font-size: 1.1em;
	color: #1F221E;
}

#createGroup, #joinGroup {
	background-color: white;
	width: 570px;
	height: 130px;
	position: fixed;
	top: 47%;
	left: 50%;
	margin-left: -285px;
	margin-top: -100px;
	border-radius: 5px;
	box-shadow: 2px 2px 5px grey;
	padding-left: 30px;
	padding-right: 30px;
	padding-top: 20px;
}

#joinGroup {
	top: 76%;
}

button {
	width: 100%;
}
</style>
</head>
<body>
	<%
		String errorSignUp = (String) request.getAttribute("error");
		if (errorSignUp == null) {
			errorSignUp = "";
		}
		String errorJoin = (String) request.getAttribute("errorJoin");
		if (errorJoin == null) {
			errorJoin = "";
		}
	%>
	<img id="cover" src="./images/loginbkg.png" alt="cover">
	<div id="header">
		<h1>
			Welcome, <span id="userName"><%=session.getAttribute("userName")%></span>
		</h1>
		<p>Enter an existing group name or create a new group to get
			started!</p>
	</div>

	<div id="createGroup" class="container">
		<h5>Create New Group</h5>
		<form action="CreateGroupServlet" method="POST">
			<div class="form-group row">
				<div class="col-sm-9">
					<input type="text" class="form-control" id="newGroupId"
						name="newGroupId" placeholder="Enter New Group Name"> <font
						color="black"> <%=errorSignUp%>
					</font>
				</div>
				<div class="col-sm-3">
					<button type="submit" class="btn btn-primary">Create</button>
				</div>
			</div>
			<!-- .form-group -->
		</form>
	</div>
	<!-- .container -->

	<div id="joinGroup" class="container">
		<h5>Join Existing Group</h5>
		<form action="JoinGroupServlet" method="POST">
			<div class="form-group row">
				<div class="col-sm-9">
					<input type="text" class="form-control" id="joinGroupId"
						name="joinGroupId" placeholder="Enter Existing Group Name">
					<font color="black"> <%=errorJoin%>
					</font>
				</div>
				<div class="col-sm-3">
					<button type="submit" class="btn btn-primary">Join</button>
				</div>
			</div>
			<!-- .form-group -->
		</form>
	</div>
	<!-- .container -->

</body>
</html>
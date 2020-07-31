<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="utils.GroupBuilder"%>
<%
	GroupBuilder groupObj = GroupBuilder.getInstance(request);
%>

<!DOCTYPE html>

<html lang="en">
<head>
<title>Login Landing | Rendezvous</title>
<meta name="google-signin-scope" content="profile email">
<meta name="google-signin-client_id"
	content="716240911606-iurfuk50hasuf1b6rhg9ppcia90114dm.apps.googleusercontent.com">
<script src="https://apis.google.com/js/platform.js" async defer></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link
	href="https://fonts.googleapis.com/css2?family=Lora:ital,wght@1,600&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<script src="./js/ajaxSchedule.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>

</head>
<style>
body, html {
	padding: 0px;
	margin: 0px;
	overflow-y: hidden;
}

#cover {
	width: 100%;
	z-index: -1;
}

#login {
	background-color: white;
	width: 500px;
	height: 340px;
	border-radius: 3%;
	box-shadow: 0px 5px 20px grey;
	/*display: table;*/
	position: fixed;
	top: 50%;
	left: 50%;
	margin-left: -255px;
	margin-top: -170px;
}

#title {
	text-align: center;
	/*display: table-cell;
			vertical-align: middle;*/
	padding-top: 50px;
	padding-bottom: 30px;
	font-family: 'Lora', serif;
}

#title h1 {
	font-weight: bold;
}

#title h5 {
	color: grey;
	padding-top: 10px;
}

#loginButton {
	text-align: center;
	padding-top: 30px;
}

.logo{
	width: 200px;
}

.guest{
	color: gray;
	padding-left: 20px;
	font-size: 12px;
	margin-bottom: 0;
}

.guest-div{
	width: 350px;
	margin: auto;
}

.group-btn{
	margin-left: 10px;
	font-size: 14px;
	padding-right: 15px;
	padding-left: 15px;
	}

.guest-option{
	width: 320px;
	margin: auto;
	text-align: center;
}

.group-label{
font-size: 14px;
padding-bottom: 0;
margin-bottom: 0;
}

.hr-line{
margin-top: 25px;
margin-bottom: 25px;
}

#groups{
background-color: white;
}

</style>

<body onload="allowCalendar()">
	<img id="cover" src="./images/back-01.png" alt="cover">
	<div id="login">
		<div id="title">
			<img class="logo" src="./images/color.png">
		</div>

		<div class="g-signin2" data-width= "350" align="center" data-onsuccess="onSignIn" data-longtitle="true"
			data-theme="dark"></div>

		<hr class="hr-line">
		<div class ="guest-option"><p class="guest">Or, Continue as Guest</p></div>

		<div class="guest-div">


			<label for="grouplist" class = "group-label"></label><br> &nbsp;
			<%=groupObj.setupGroup()%>
			&nbsp;<button type="button" class ="btn btn-primary group-btn" onclick="onGuess()">View as Guest</button>
		</div>
	</div>


	<script>
		var profile;

		function onGuess() {
			var groupName = document.getElementById("groups").value;
			location.replace("schedule.jsp?groupname=" + groupName);
		}

		function onSignIn(googleUser) {
			
			

			//alert("onSignIn!!");
			// Useful data for your client-side scripts:
			profile = googleUser.getBasicProfile();

			console.log("ID: " + profile.getId()); // Don't send this directly to your server!
			console.log('Full Name: ' + profile.getName());
			console.log('Given Name: ' + profile.getGivenName());
			console.log('Family Name: ' + profile.getFamilyName());
			console.log("Image URL: " + profile.getImageUrl());
			console.log("Email: " + profile.getEmail());

			localStorage.setItem("profileEmail", profile.getEmail());
			// ajax request
			var xhttp = new XMLHttpRequest();
			xhttp.open("GET", "LoginServlet?name=" + profile.getName()
					+ "&email=" + profile.getEmail(), false);
			xhttp.send();

			// call window.locatio.href = "" from max 
			if (xhttp.responseText.trim().length > 0) {
				console.log(xhttp.responseText);
				//alert(xhttp.responseText);
				window.location.href = "https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=716240911606-iurfuk50hasuf1b6rhg9ppcia90114dm.apps.googleusercontent.com&redirect_uri=" + 
				"http:\/\/localhost:8080/Rendezvous/index.jsp&scope=https:\/\/www.googleapis.com/auth/calendar.readonly&state=https:\/\/www.googleapis.com/auth/calendar.readonly&access_type=offline";

			}
			gapi.auth2.getAuthInstance().disconnect();
		}

		function allowCalendar() {

			var urlParams = new URLSearchParams(window.location.href);
			var code = urlParams.get('code');

			if (code != null) {
				console.log("code " + code);
				console.log("profile email "
						+ localStorage.getItem("profileEmail"));

				var xhttp1 = new XMLHttpRequest();
				xhttp1.open("GET", "AuthServlet?code=" + urlParams.get('code')
						+ "&email=" + localStorage.getItem("profileEmail"),
						true);
				xhttp1.send();

				xhttp1.onreadystatechange = function() {

					if (xhttp1.readyState == 4 && xhttp1.status == 200) {

						var response = xhttp1.responseText;
						// alert("response => " + response);

						if (response.includes("existing")) {
							//alert("go to schedule.jsp");
							window.location.href = "schedule.jsp";
						} else {
							//alert("go to welcome-page.jsp");
							window.location.href = "welcome-page.jsp";
						}
					}

				}
			}
		}
	</script>
</body>
</html>
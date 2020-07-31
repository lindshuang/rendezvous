
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link href="./css/style-m.css" rel="stylesheet" />
	<title>Pop-up</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
	<link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">	
	<style type="text/css">
		body{
			background-color: white;
			font-family: 'Roboto', sans-serif;
		}
	</style>

</head>
<body>
	<!-- MAILANI'S CODE FOR SIDE BAR -->
	<div class="sidenav">
	  <a href="#"><img id = "group-img" src="group.png"></a>
	  <a href="#"><img id = "group-img" src="group.png"></a>
	  <a href="#"><img id = "group-img" src="group.png"></a>
	  <a href="#"><img id = "group-img" src="group.png"></a>
	</div>

	<!-- MAILANI'S CODE FOR POP UP -->

	<div class="container main">
	
		<div class="container">
			<div class="row">
				<h1 class="col-12 mt-4 mb-4">Edit a Group</h1>
			</div> <!-- .row -->
		</div> <!-- .container -->
		    
		    <div class="open-btn">
		      <button class="open-button" onclick="openForm()">
		      <strong>+/- Edit Group</strong>
		      </button>
		    </div>
		    <div id="loginPopup">
		      <div class="form-popup" id="popupForm">
		        <form action="EditGroupServlet" class="form-container" method="POST">
		          <h2>Add a New Group Member<span class="close" onclick="closeForm()">x</span></h2>
		          
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
	</div> <!-- .container -->
	
	<!-- MAILANI'S CODE FOR POP UP -->

	<script src="./js/PopFunctions.js"></script>
	<script src="http://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="crossorigin="anonymous"></script>
	
</body>
</html>

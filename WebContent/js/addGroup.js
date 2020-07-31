// Event handler where add member button is clicked
$(".btn-outline-success").on("click", function(event) {
	let member = $("#username-id").val();
	event.stopImmediatePropagation();
	event.preventDefault();
	$('#error-div').html("");
	
//	ajax call to check if member exists
	if (member == ""){
		$('#error-div').html("User not found.");
	}else{
		checkUser(member);
//		$("#added-member-list").append("<li><i class=\"fas fa-times\" aria-hidden=\"true\"></i><span class=\"added-member\">" + member + "</span>" +
//				"						<input type=\"hidden\" name=\"member\" value=" + member + "></li>");
//		$("#username-id").val("");

	}
});

// Event handler where delete signed is clicked
$("#added-member-list").on("click", "i", function(event) {
	event.preventDefault();
	// Fade out and remove the list
	$(this).parent().fadeOut(100, function() {
		$(this).remove();
	});
});

//// Event handler where add member button is clicked
//$(".btn-outline-success").on("click", function(event) {
//	let member = $("#username-id").val();
//	event.preventDefault();
//
//	if (member == "") {
//		alert("Invalid username");
//	} else {
//		$("#added-member-list").append("<li><i class=\"fas fa-times\" aria-hidden=\"true\"></i><span class=\"added-member\">" + member + "</span>" +
//				"						<input type=\"hidden\" name=\"member\" value=" + member + "></li>");
//		$("#username-id").val("");
//	}
//});
//
//// Event handler where delete signed is clicked
//$("#added-member-list").on("click", "i", function(event) {
//	event.preventDefault();
//	// Fade out and remove the list
//	$(this).parent().fadeOut(100, function() {
//		$(this).remove();
//	});
//});

function displayResult(result){
	if (result == ""){
//		success
		let member = $("#username-id").val();
		$("#added-member-list").append("<li><i class=\"fas fa-times\" aria-hidden=\"true\"></i><span class=\"added-member\">" + member + "</span>" +
				"						<input type=\"hidden\" name=\"member\" value=" + member + "></li>");
		$("#username-id").val("");		
	}else{
		$("#error-div").html(result);
	}
}

function checkUser(parameter){
    $.ajax({
        method : "POST",
        contentType : "application/json",
        url : "ajaxmembers.jsp?ajaxid="+parameter, //ajax id is the parameter we'll get on the server side
        dataType :  'json',
        timeout : 100000,
        success : function(result) {
//        	$("#error-div").html(result);
        	displayResult(result);
        },
        error : function(e) {
            console.log("ERROR in ajax locad: ", e);
        }
    });
}

//// Event handler where add member button is clicked
//$(".btn-outline-success").on("click", function(event) {
//	alert("onclick");
//	let member = $("#username-id").val();
//	event.preventDefault();
//	
////	ajax call to check if member exists
//	if (member == ""){
//		$('#error-div').html("User not found.");
//	}else{
//		checkUser(member);
////		$("#added-member-list").append("<li><i class=\"fas fa-times\" aria-hidden=\"true\"></i><span class=\"added-member\">" + member + "</span>" +
////				"						<input type=\"hidden\" name=\"member\" value=" + member + "></li>");
////		$("#username-id").val("");
//
//	}
//});
//
//// Event handler where delete signed is clicked
//$("#added-member-list").on("click", "i", function(event) {
//	event.preventDefault();
//	// Fade out and remove the list
//	$(this).parent().fadeOut(100, function() {
//		$(this).remove();
//	});
//});
//
////// Event handler where add member button is clicked
////$(".btn-outline-success").on("click", function(event) {
////	let member = $("#username-id").val();
////	event.preventDefault();
////
////	if (member == "") {
////		alert("Invalid username");
////	} else {
////		$("#added-member-list").append("<li><i class=\"fas fa-times\" aria-hidden=\"true\"></i><span class=\"added-member\">" + member + "</span>" +
////				"						<input type=\"hidden\" name=\"member\" value=" + member + "></li>");
////		$("#username-id").val("");
////	}
////});
////
////// Event handler where delete signed is clicked
////$("#added-member-list").on("click", "i", function(event) {
////	event.preventDefault();
////	// Fade out and remove the list
////	$(this).parent().fadeOut(100, function() {
////		$(this).remove();
////	});
////});
//
//function displayResult(result){
//	if (result == ""){
////		success
//		alert("success");
//		let member = $("#username-id").val();
//		$("#added-member-list").append("<li><i class=\"fas fa-times\" aria-hidden=\"true\"></i><span class=\"added-member\">" + member + "</span>" +
//				"						<input type=\"hidden\" name=\"member\" value=" + member + "></li>");
//		$("#username-id").val("");		
//	}else{
//		$("#error-div").html(result);
//	}
//}
//
//function checkUser(parameter){
//    $.ajax({
//        method : "POST",
//        contentType : "application/json",
//        url : "ajaxmembers.jsp?ajaxid="+parameter, //ajax id is the parameter we'll get on the server side
//        dataType :  'json',
//        timeout : 100000,
//        success : function(result) {
////        	$("#error-div").html(result);
//        	displayResult(result);
//        },
//        error : function(e) {
//            console.log("ERROR in ajax locad: ", e);
//        }
//    });
//}